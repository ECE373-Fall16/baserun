#!/bin/sh
cd $TRAVIS_BUILD_DIR/server
set -e
make -f MakefileTest
./tester
