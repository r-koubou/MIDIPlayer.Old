@echo off

setlocal

set PATH=shared\jni\windows-amd64;%PATH%
set CLASSPATH=MIDIPlayer.jar;shared\jar\*;%CLASSPATH%
set VMARG=-Xms32M
set ARG=%VMARG% -classpath "%CLASSPATH%" org.rz.midiplayer.Main

if exist jre\ (
    rem ----------------------------------------------------------------------
    rem ���� JRE
    rem ----------------------------------------------------------------------
    start jre\bin\javaw %ARG%
) else (
    rem PATH ���ʂ��Ă���΂����
    start javaw %ARG%
)

if not "%ERRORLEVEL%" == "0" (
    echo ---------------------------------------------------------------------
    echo ���v���O�������N���ł��Ȃ������\������B
    echo �����L URL ���� Java 6 �ȏ�� �̃����^�C�����C���X�g�[�����Ă��������B
    echo http://www.java.com/
    echo ---------------------------------------------------------------------
    pause
)

endlocal
