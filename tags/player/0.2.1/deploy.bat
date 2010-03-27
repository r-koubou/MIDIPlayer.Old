@echo off

@echo # linux-x64
call ant -Dplatform=linux-x64 -f deploy-linux-x64.xml deploy-bin
if not "%ERRORLEVEL%" == "0" pause

@echo # linux-x86
call ant -Dplatform=linux-x86 -f deploy-linux-x86.xml deploy-bin
if not "%ERRORLEVEL%" == "0" pause

@echo # macosx
call ant -Dplatform=macosx -f deploy-macosx.xml deploy-bin
if not "%ERRORLEVEL%" == "0" pause

@echo # windows-jre-included
call ant -Dplatform=windows-inc-jre -f deploy-windows-jre-included.xml deploy-bin
if not "%ERRORLEVEL%" == "0" pause

@echo # Windows x64
call ant -Dplatform=win64 -f deploy-windows-x64.xml deploy-bin
if not "%ERRORLEVEL%" == "0" pause

@echo # Windows x86
call ant -Dplatform=win32 -f deploy-windows-x86.xml deploy-bin
if not "%ERRORLEVEL%" == "0" pause

