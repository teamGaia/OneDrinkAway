#!/bin/bash
set -e
export ANDROID_HOME=$1

export PATH=$ANDROID_HOME/tools:$PATH

export PATH=$ANDROID_HOME/platform-tools:$PATH

android update project -p $2

android update project --name OneDrinkAway -p .

ant clean debug
