#!/bin/bash
export CLASSPATH=MIDIPlayer.jar:lib/jar/*:$CLASSPATH
export VMARG=-Xms32M

java $VMARG -classpath $CLASSPATH org.rz.midiplayer.Main
