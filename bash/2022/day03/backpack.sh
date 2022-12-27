#!/bin/bash

sum=0
input="input"
while IFS= read -r line
do
  left=${line:0:${#line}/2}
  right=${line:${#line}/2}

  result=`echo $left | grep -o -m 1 -- "[$right]"`
  letter=${result:0:1}
  number=`printf '%d' "'$letter"`
  
  val=0
  if [ $number -gt 96 ]
  then
   val=$((number-96))
  else
   val=$((number-64+26))
  fi
  sum=$((sum+val))
done < "$input"

echo "Part a" $sum


sum=0
current=""
i=0
input="input"
while IFS= read -r line
do
  if [ $i -eq 0 ]
  then
    current=$line
  else
    current=`echo $current | grep -o -m 1 -- "[$line]"`
  fi

  if [ $i -eq 2 ]
  then
    letter=${current:0:1}
    number=`printf '%d' "'$letter"`
  
    val=0
    if [ $number -gt 96 ]
    then
     val=$((number-96))
    else
     val=$((number-64+26))
    fi
    sum=$((sum+val))
  fi

  i=$((i+1))
  i=$((i%3))

done < "$input"

echo "Part b" $sum


