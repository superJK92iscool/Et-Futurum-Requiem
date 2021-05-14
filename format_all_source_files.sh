#!/bin/sh
find -iname "*.java" -exec "./format_source_file.sh" {} \;
