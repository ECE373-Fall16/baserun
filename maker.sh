#!/bin/sh
cd $TRAVIS_BUILD_DIR/v1.0/code/server
set -e
make test
./tester
