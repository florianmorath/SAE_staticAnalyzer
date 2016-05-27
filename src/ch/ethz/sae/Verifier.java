package ch.ethz.sae;

import java.util.HashMap;
import java.util.Map;

import apron.ApronException;
import apron.Interval;
import apron.MpqScalar;
import apron.Scalar;
import soot.Unit;
import apron.*;

import soot.jimple.InvokeExpr;
import soot.jimple.internal.JInvokeStmt;
import soot.jimple.internal.JSpecialInvokeExpr;
import soot.jimple.internal.JVirtualInvokeExpr;
import soot.jimple.spark.sets.DoublePointsToSet;
import soot.jimple.spark.sets.P2SetVisitor;
import soot.jimple.spark.SparkTransformer;
import soot.jimple.spark.pag.Node;
import soot.jimple.spark.pag.PAG;
import java.util.*;
import soot.*;
import soot.jimple.*;
import soot.jimple.internal.*;
import soot.Local;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.toolkits.graph.BriefUnitGraph;
import soot.ValueBox;

public class Verifier {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java -classpath soot-2.5.0.jar:./bin ch.ethz.sae.Verifier <class to test>");
			System.exit(-1);
		}
		String analyzedClass = args[0];
		SootClass c = loadClass(analyzedClass);

		PAG pointsToAnalysis = doPointsToAnalysis(c);

		int programCorrectFlag = 1;
		int divisionByZeroFlag = 1;

		for (SootMethod method : c.getMethods()) {

			Analysis analysis = new Analysis(new BriefUnitGraph(method.retrieveActiveBody()), c);
			analysis.run();

			if (!verifyBounds(method, analysis, pointsToAnalysis)) {
				programCorrectFlag = 0;
			}
			if (!verifyDivisionByZero(method, analysis)) {
				divisionByZeroFlag = 0;
			}
		}

		if (divisionByZeroFlag == 1) {
			System.out.println(analyzedClass + " NO_DIV_ZERO");
		} else {
			System.out.println(analyzedClass + " MAY_DIV_ZERO");
		}

		if (programCorrectFlag == 1) {
			System.out.println(analyzedClass + " NO_OUT_OF_BOUNDS");
		} else {
			System.out.println(analyzedClass + " MAY_OUT_OF_BOUNDS");
		}
	}

	private static boolean verifyDivisionByZero(SootMethod method, Analysis fixPoint) {
		for (Unit u : method.retrieveActiveBody().getUnits()) {
			AWrapper state = fixPoint.getFlowBefore(u);
			try {
				if (state.get().isBottom(Analysis.man))
				// unreachable code
				continue;
			} catch (ApronException e) {
				e.printStackTrace();
			}

			//TODO: Check that all divisors are not zero
			// Get use boxes
			List<ValueBox> vbList = u.getUseBoxes();
			// Iterate through all the used boxes
			for (ValueBox vb : vbList) {
				// Get the value from the current box
				Value v = vb.getValue();
				// Check if it is a division
				if (v instanceof JDivExpr) {
					// Yes: then get the divisor of the division
					Value divisor = ((JDivExpr) v).getOp2();
					// Case distinction on the type of the divisor
					// case 1: divisor is a constant
					if (divisor instanceof IntConstant) {
						int val = ((IntConstant) divisor).value;
						if (val == 0)
							return false;
					} else if (divisor instanceof JimpleLocal) {
						Interval i = fixPoint.getInterval(state, divisor);
						System.out.println("" + i.cmp(new MpqScalar(0)));
						if (i.cmp(new MpqScalar(0)) == 0 || i.cmp(new MpqScalar(0)) == 1)
							return false;
					}

				}
			}
		}

		//Return false if the method may have division by zero errors
		return true;
	}

	private static boolean verifyBounds(SootMethod method, Analysis fixPoint,
	PAG pointsTo) {

		//TODO: Create a list of all allocation sites for PrinterArray
		Map<InvokeExpr, SootMethod> invokes = pointsTo.callToMethod;
		HashMap<JimpleLocal, Integer> paMapToSize = new HashMap<JimpleLocal, Integer>();
		List<Triple<PointsToSet, Value, JVirtualInvokeExpr>> sendJobCalls = new ArrayList<Triple<PointsToSet, Value, JVirtualInvokeExpr>>();

		for (Unit u : method.retrieveActiveBody().getUnits()) {
			AWrapper state = fixPoint.getFlowBefore(u);

			try {
				if (state.get().isBottom(Analysis.man)) {
					// unreachable code
					continue;
				}
			} catch (ApronException e) {
				e.printStackTrace();
			}

			// Constuctor
			if (u instanceof JInvokeStmt && ((JInvokeStmt) u).getInvokeExpr() instanceof JSpecialInvokeExpr) {
				// TODO: Get the size of the PrinterArray given as argument to the constructor
				InvokeExpr e = ((JInvokeStmt) u).getInvokeExpr();
				JimpleLocal constrLoc = (JimpleLocal)((JSpecialInvokeExpr) e).getBaseBox().getValue();
				Value val = e.getArgBox(0).getValue();
				//System.out.println("\n\n" + val.toString() + "\n\n");
				int size = Integer.parseInt(val.toString());
				paMapToSize.put(constrLoc, size);
			}

			// Call of sendJob
			if (u instanceof JInvokeStmt && ((JInvokeStmt) u).getInvokeExpr() instanceof JVirtualInvokeExpr) {

				JInvokeStmt jInvStmt = (JInvokeStmt)u;

				JVirtualInvokeExpr invokeExpr = (JVirtualInvokeExpr)jInvStmt.getInvokeExpr();

				Local base = (Local) invokeExpr.getBase();
				DoublePointsToSet pts = (DoublePointsToSet) pointsTo.reachingObjects(base);

				if (invokeExpr.getMethod().getName().equals(Analysis.functionName)) {


					// TODO: Check whether the 'sendJob' method's argument is within bounds
					JimpleLocal key = (JimpleLocal)(invokeExpr).getBaseBox().getValue();
					PointsToSet set = pointsTo.reachingObjects(key);
					Value arg = (Value) invokeExpr.getArg(0);
					sendJobCalls.add(new Triple<PointsToSet, Value, JVirtualInvokeExpr>(set, arg, (JVirtualInvokeExpr) invokeExpr));

					for(Triple<PointsToSet, Value, JVirtualInvokeExpr> call : sendJobCalls) {
						for(JimpleLocal pa : paMapToSize.keySet()) {
							if(call.x.hasNonEmptyIntersection(pointsTo.reachingObjects(pa))) {
								Interval bounds = fixPoint.getInterval(state, call.y);
								List<Integer> possibleValues = getPossibleValues(bounds, call.y, fixPoint, call.z, state.man);
								if (possibleValues == null) break;
								int size = paMapToSize.get(pa);
								for (Integer i : possibleValues)
									if (i < 0 || i > size - 1)
										return false;
							}
						}
					}

					/*
					// Visit all allocation sites that the base pointer may reference
					MyP2SetVisitor visitor = new MyP2SetVisitor();
					pts.forall(visitor);
					*/
				}
			}
		}
		return true;
		//return false;
	}


	public static List<Integer> getPossibleValues(Interval ival,Value value,Analysis fixPoint, JVirtualInvokeExpr invokExpr, Manager man){
		List<Integer> possibleValues = new ArrayList<Integer>();
		try {
			if(value instanceof JimpleLocal) {
				int lowerBound = getInfForInterval(ival);
				int upperBound = getSupForInterval(ival);
				for (int i = lowerBound; i <= upperBound; i++) {
					Scalar leftLowerBound = new MpqScalar();
					leftLowerBound.setInfty(-1);
					Scalar leftUpperBound = new MpqScalar(i-1);
					Scalar rightLowerBound = new MpqScalar(i+1);
					Scalar rightUpperBound = new MpqScalar();
					rightUpperBound.setInfty(1);
					Boolean inLeftInterval = fixPoint.stateTracer.get(invokExpr).satisfy(man, ((JimpleLocal) value).getName(), new Interval(leftLowerBound, leftUpperBound));
					Boolean inRightInterval = fixPoint.stateTracer.get(invokExpr).satisfy(man, ((JimpleLocal) value).getName(), new Interval(rightLowerBound, rightUpperBound));
					if(!(inLeftInterval || inRightInterval)) {
						possibleValues.add(i);
					}
				}
			} else if (value instanceof IntConstant) {
				possibleValues.add(((IntConstant) value).value);
			}
		} catch (ApronException e ) {
			e.printStackTrace();
		}
		return possibleValues;

	}

	public static int getInfForInterval(Interval ival) {
        int result;
        try {
            result  = Integer.parseInt(ival.inf().toString());
        } catch (Exception e) {
            result =-1;
        }
        return result;
    }
    public static int getSupForInterval(Interval ival) {
        int result;
        try {
            result  = Integer.parseInt(ival.sup().toString());
        } catch (Exception e) {
            result = 100;
        }
        return result;
    }


	private static SootClass loadClass(String name) {
		SootClass c = Scene.v().loadClassAndSupport(name);
		c.setApplicationClass();
		return c;
	}

	private static PAG doPointsToAnalysis(SootClass c) {
		Scene.v().setEntryPoints(c.getMethods());

		HashMap<String, String> options = new HashMap<String, String>();
		options.put("enabled", "true");
		options.put("verbose", "false");
		options.put("propagator", "worklist");
		options.put("simple-edges-bidirectional", "false");
		options.put("on-fly-cg", "true");
		options.put("set-impl", "double");
		options.put("double-set-old", "hybrid");
		options.put("double-set-new", "hybrid");

		SparkTransformer.v().transform("", options);
		PAG pag = (PAG) Scene.v().getPointsToAnalysis();

		return pag;
	}
}

class MyP2SetVisitor extends P2SetVisitor{

	@Override
	public void visit(Node arg0) {
		//TODO: Check whether the argument given to sendJob is within bounds
	}
}
