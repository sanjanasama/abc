#!/bin/sh
../testlib/pretest.sh
../testlib/ja-modules.sh -jastaddframework -d classes -instance-module m1 *.module *.java 2>&1 > out
../testlib/posttesterr.sh
