#!/bin/sh
cd ./server
set -e
make test
./tester
