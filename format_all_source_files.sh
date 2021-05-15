#!/bin/sh
find "src" -iname "*.java" -exec "./format_source_file.sh" {} \;
