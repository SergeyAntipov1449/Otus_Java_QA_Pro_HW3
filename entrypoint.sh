#!/bin/bash

BASE_URL=""

 while [[ $# -gt 0 ]]; do
     case "$1" in
         --base_url)
             BASE_URL="$2"
             shift 2
             ;;
         *)
             echo "Unknown argument"
             exit 1
             ;;
     esac
 done

mvn test -Dbase.url="$BASE_URL"