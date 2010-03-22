#!/bin/bash
export LD_LIBRARY_PATH=shared/jni/linux-amd64:$LD_LIBRARY_PATH
export CLASSPATH=MIDIPlayer.jar:shared/jar/*:$CLASSPATH
export VMARG=-Xms32M

java $VMARG -classpath $CLASSPATH org.rz.midiplayer.Main
