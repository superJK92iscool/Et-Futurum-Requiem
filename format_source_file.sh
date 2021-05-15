#!/bin/sh
echo "$1"
cat $1 | expand --tabs=4 | unexpand --first-only --tabs=4 > "$1.tmp"
mv "$1.tmp" "$1"
