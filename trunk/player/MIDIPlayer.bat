@echo off

if exist jre\ (
    rem ----------------------------------------------------------------------
    rem 同梱 JRE
    rem ----------------------------------------------------------------------
    start jre\bin\javaw -jar MIDIPlayer.jar
) else if exist %windir%\SysWoW64\javaw.exe (
    rem ----------------------------------------------------------------------
    rem 64bit環境の JRE (32bit版)
    rem ----------------------------------------------------------------------
    start %windir%\SysWoW64\javaw.exe -jar MIDIPlayer.jar
) else if exist %windir%\System32\javaw.exe (
    rem ----------------------------------------------------------------------
    rem x86 Windows の JRE
    rem x64 Windows の JRE (64bit版)
    rem ----------------------------------------------------------------------
    start %windir%\System32\javaw.exe -jar MIDIPlayer.jar
) else (
    rem PATH が通っていればそれを
    start javaw -jar MIDIPlayer.jar
)

if not "%ERRORLEVEL%" == "0" (
    echo ---------------------------------------------------------------------
    echo ※プログラムが起動できなかった可能性あり。
    echo ※下記 URL から Java 6 以上の のランタイムをインストールしてください。
    echo http://www.java.com/
    echo ---------------------------------------------------------------------
    pause
)
