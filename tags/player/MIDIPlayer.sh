#!/bin/bash
export LD_LIBRARY_PATH=shared/jni:$LD_LIBRARY_PATH
export CLASSPATH=MIDIPlayer.jar:shared/jar/*:$CLASSPATH

java -classpath $CLASSPATH org.rz.midiplayer.Main
