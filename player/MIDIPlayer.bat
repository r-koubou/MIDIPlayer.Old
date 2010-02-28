@echo off

if exist jre\ (
    rem ----------------------------------------------------------------------
    rem ���� JRE
    rem ----------------------------------------------------------------------
    start jre\bin\javaw -jar MIDIPlayer.jar
) else if exist %windir%\SysWoW64\javaw.exe (
    rem ----------------------------------------------------------------------
    rem 64bit���� JRE (32bit��)
    rem ----------------------------------------------------------------------
    start %windir%\SysWoW64\javaw.exe -jar MIDIPlayer.jar
) else if exist %windir%\System32\javaw.exe (
    rem ----------------------------------------------------------------------
    rem x86 Windows �� JRE
    rem x64 Windows �� JRE (64bit��)
    rem ----------------------------------------------------------------------
    start %windir%\System32\javaw.exe -jar MIDIPlayer.jar
) else (
    rem PATH ���ʂ��Ă���΂����
    start javaw -jar MIDIPlayer.jar
)

if not "%ERRORLEVEL%" == "0" (
    echo ---------------------------------------------------------------------
    echo ���v���O�������N���ł��Ȃ������\������B
    echo �����L URL ���� Java 6 �ȏ�� �̃����^�C�����C���X�g�[�����Ă��������B
    echo http://www.java.com/
    echo ---------------------------------------------------------------------
    pause
)
