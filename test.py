#!/usr/bin/env python3

# Author:
# Timon Blattner timonbl@ethz.ch

import sys
import os
import os.path
import glob
import subprocess
from os.path import basename

JAVA_HOME_BUILD = ''
JAVA_HOME_RUN = ''
APRON_HOME_BUILD = ''
APRON_HOME_RUN = ''

# build and run the test cases
def runTests():
    loadPaths()
    build()

    # treat arguments as mask
    testMask = []
    if len(sys.argv) > 1:
        testMask = sys.argv[1:]

    dir_src = "src/test/"
    for code_test in glob.iglob(dir_src + "*.java", recursive=True):
        name = basename(code_test).replace(".java", "")
        # skip if mask doesn't include it
        if len(testMask) > 0 and not name in testMask:
            continue
        descr = ''
        expNP = ''
        expOOB = ''
        # read header (description, expected output)
        with open(code_test, "r", encoding='utf8', errors='replace') as fcode:
            descr = fcode.readline()[2:].strip()
            expNP = fcode.readline()[2:].strip()
            expOOB = fcode.readline()[2:].strip()

        resNP, resOOB = executeTest(code_test, name)

        print("[" + name + "]: " + descr)
        if expNP == resNP:
            print("\tNP correct")
        else:
            print("\t[EE] Expected {" + expNP + "} but got {" + resNP + "}")
        if expOOB == resOOB:
            print("\tOOB correct")
        else:
            print("\t[EE] Expected {" + expOOB + "} but got {" + resOOB + "}")

# execute a single test and get the result back
def executeTest(code_test, name):
    resNP = ""
    resOOB = ""
    with subprocess.Popen(getRunCommand(name), stdout=subprocess.PIPE, shell=True) as fcode:
        output, err = fcode.communicate()
        for line in output.splitlines():
            line = line.decode("utf-8")
            if line.startswith(name+' '):
                result_code = line[len(name+' '):]
                if result_code == 'NO_DIV_ZERO' or result_code == 'MAY_DIV_ZERO':
                    resNP = result_code
                elif result_code == 'NO_OUT_OF_BOUNDS' or result_code == 'MAY_OUT_OF_BOUNDS':
                    resOOB = result_code

    return resNP, resOOB

# load library paths from existing build.sh and run.sh
def loadPaths():
    with open("build.sh", "r", encoding='utf8', errors='replace') as fbuild:
        for line in fbuild:
            if line.startswith("JAVA_HOME"):
                pre,tail = line.split("=",1)
                global JAVA_HOME_BUILD
                JAVA_HOME_BUILD = tail.rstrip()
            elif line.startswith("APRON_HOME"):
                pre,tail= line.split("=",1)
                global APRON_HOME_BUILD
                APRON_HOME_BUILD = tail.rstrip()
    with open("run.sh", "r", encoding='utf8', errors='replace') as frun:
        for line in frun:
            if line.startswith("JAVA_HOME"):
                pre,tail = line.split("=",1)
                global JAVA_HOME_RUN
                JAVA_HOME_RUN = tail.rstrip()
            elif line.startswith("APRON_HOME"):
                pre,tail= line.split("=",1)
                global APRON_HOME_RUN
                APRON_HOME_RUN = tail.rstrip()

# construct the run command
def getRunCommand(className):
    base = os.getcwd()
    return (
        r"export CLASSPATH=.:"+base+r"/soot-2.5.0.jar:"+APRON_HOME_RUN+
        r"/japron/apron.jar:"+APRON_HOME_RUN+r"/japron/gmp.jar:"+base+r"/bin; " +
        r"export LD_LIBRARY_PATH="+APRON_HOME_RUN+r"/box:"+APRON_HOME_RUN+
        r"/octagons:"+APRON_HOME_RUN+r"/newpolka:"+APRON_HOME_RUN+r"/apron:"+
        APRON_HOME_RUN+r"/japron:"+APRON_HOME_RUN+r"/japron/gmp; " +
        JAVA_HOME_RUN + r"/java ch.ethz.sae.Verifier " + className
    )

# construct the build command
def build():
    print("start building..")
    base = os.getcwd()
    subprocess.call(
        r"export CLASSPATH=bin/:"+base+r"/soot-2.5.0.jar:"+APRON_HOME_BUILD+
        r"/apron.jar:"+APRON_HOME_BUILD+r"/gmp.jar; " +
        r"export LD_LIBRARY_PATH="+base+r"/; " +
        "mkdir -p bin; " +
        JAVA_HOME_BUILD + r"/bin/javac -d bin src/*.java; " +
        JAVA_HOME_BUILD + r"/bin/javac -d bin src/ch/ethz/sae/*.java; " +
        JAVA_HOME_BUILD + r"/bin/javac -d bin src/test/*.java; ",
        shell=True)
    print("finished building.")

if __name__ == "__main__":
    runTests()
