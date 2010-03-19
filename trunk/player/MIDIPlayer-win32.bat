@echo off

setlocal

set PATH=shared\jni\windows-i586;%PATH%
set CLASSPATH=MIDIPlayer.jar;shared\jar\*;"%CLASSPATH%"
set ARG=-classpath %CLASSPATH% org.rz.midiplayer.Main

if exist jre\ (
    rem ----------------------------------------------------------------------
    rem 同梱 JRE
    rem ----------------------------------------------------------------------
    start jre\bin\javaw %ARG%
) else (
    rem PATH が通っていればそれを
    start javaw %ARG%
)

if not "%ERRORLEVEL%" == "0" (
    echo ---------------------------------------------------------------------
    echo ※プログラムが起動できなかった可能性あり。
    echo ※下記 URL から Java 6 以上の のランタイムをインストールしてください。
    echo http://www.java.com/
    echo ---------------------------------------------------------------------
    pause
)

endlocal
