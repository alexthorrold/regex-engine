#!/bin/bash

# Compile the Java file
javac REcompile.java

# Array of valid test cases
declare -a valid_regexps=("a" "abc" "123" "." "a.c" "1.3" "a*" "b+" "c?" ".*" ".+" ".?" "a*b+c?d"
                    "a|b" "abc|123" "a|b|c" "a*b|c+" "a|b*" "(a|b)*c" "\\\\" "\*" "\+" "\?" "\|" "\a"
                    "\1" "[abc]" "[123]" "[a-z]" "[A-Z0-9]" "(a|b)*abb" "a*|b*" "[a-z]*\d+"
                    "a\\b" "a\|b" "(a*|b*)c")

# Array of invalid test cases
declare -a invalid_regexps=("(a" "*a" "|a" "+a" "a||b" "a[]b" "a*b*c" "a\\")

# Test valid regexps
echo "Testing valid regexps..."
for regexp in "${valid_regexps[@]}"
do
    if ! java REcompile "${regexp}" > /dev/null 2>&1
    then
        echo "Failed: '${regexp}'"
    fi
done

# Test invalid regexps
echo "Testing invalid regexps..."
for regexp in "${invalid_regexps[@]}"
do
    if java REcompile "${regexp}" > /dev/null 2>&1
    then
        echo "Passed: '${regexp}'"
    fi
done
