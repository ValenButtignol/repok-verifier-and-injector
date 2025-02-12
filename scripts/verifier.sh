#!/bin/bash

TEST_DIR="src/test/java"

# 1. Delete previous test files
echo "Cleaning test files..."
rm -f "$TEST_DIR/ErrorTest.java" "$TEST_DIR/ErrorTest0.java" "$TEST_DIR/RegressionTest.java" "$TEST_DIR/RegressionTest0.java"

gradle build > logs/compile.log 2>&1
if [ $? -ne 0 ]; then
    exit -1
fi

gradle runRandoop > logs/randoop.log 2>&1
if [ $? -ne 0 ]; then
    exit -1
fi

# 3. Verify generated test
if grep -q "Error" logs/randoop.log; then
    exit 0
else
    exit 1
fi
