#!/bin/bash

TEST_DIR="src/test/java"

# 1. Delete previous test files
echo "Cleaning test files..."
rm -f "$TEST_DIR/ErrorTest.java" "$TEST_DIR/ErrorTest0.java" "$TEST_DIR/RegressionTest.java" "$TEST_DIR/RegressionTest0.java"

# 1. Compile generated class
echo "Compiling..."
gradle build >> logs/compile.log 2>&1
if [ $? -ne 0 ]; then
    echo "Error: Failed compilation. Check compile.log"
    exit -1
else
    echo -e "\n####################\n" >> logs/compile.log
fi

# 2. Run Randoop
echo "Running Randoop..."
gradle runRandoop >> logs/randoop.log 2>&1
if [ $? -ne 0 ]; then
    echo "Error: Failed randoop execution. Check randoop.log"
    exit -1
else
    echo -e "\n####################\n" >> logs/compile.log
fi

# 3. Verify generated test
if grep -q "Error" logs/randoop.log; then
    echo "Error: Error test generated. Check repOk."
    exit 0
else
    echo "Success: Class has been tested succesfully."
    exit 1
fi
