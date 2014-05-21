#!/bin/bash
# Takes in two parameters:
# $1 should be path to your android build tools folder (usually under the sdk)
# $2 should be the path to your appcompat-v7 project

set -e

if [ ! -n "$1" ]
then
  echo "Usage: first argument should be path to your android build tools folder (usually under the sdk)"
  echo "       second argument should be the path to your appcompat-v7 project"
  exit 1
fi

export ANDROID_HOME=$1

export PATH=$ANDROID_HOME/tools:$PATH

export PATH=$ANDROID_HOME/platform-tools:$PATH

android update project -p $2

android update project --name OneDrinkAway -p .

ant clean debug
