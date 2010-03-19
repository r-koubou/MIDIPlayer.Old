#!/bin/bash
export LD_LIBRARY_PATH=shared/jni/linux-i586:$LD_LIBRARY_PATH
export CLASSPATH=MIDIPlayer.jar:shared/jar/*:$CLASSPATH

java -classpath $CLASSPATH org.rz.midiplayer.Main
