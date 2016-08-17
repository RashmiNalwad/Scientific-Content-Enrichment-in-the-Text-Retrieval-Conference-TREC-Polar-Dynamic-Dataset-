#!/bin/bash

$i=0;
for file in /home/aditya/OCR/*; do
 # echo ${file##*/}

CT="Content-Type:image/tiff"

TEST="curl -T $file http://localhost:9998/tika --header $CT"
echo $TEST

RESPONSE=`$TEST`
echo $RESPONSE
done
