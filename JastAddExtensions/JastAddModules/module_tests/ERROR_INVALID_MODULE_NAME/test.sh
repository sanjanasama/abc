#!/bin/sh
../testlib/pretest.sh
../testlib/ja-modules.sh -jastaddframework  -debug -d classes -instance-module "m1::m2" *.java *.module ../testlib/*.module 2>&1 > out
../testlib/posttest.sh
