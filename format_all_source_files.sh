#!/bin/sh
find "src" -iname "*.java" -exec "./format_source_file.sh" {} \;
find "src" -iname "*.png" -exec optipng -o7 -nc -clobber {} \;
