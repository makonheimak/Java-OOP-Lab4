@echo off
chcp 65001 > nul
cd /d "%~dp0"
title Lab 4 (Variant 5) - Yurash Maksim

if exist "Lab4_Main.class" goto run
echo [INFO] Compiling Java source files...
javac -encoding UTF-8 BouncingBallAnimation.java SnowfallAnimation.java TicTacToeGame.java Lab4_Main.java
if errorlevel 1 goto error

:run
start javaw -Dfile.encoding=UTF-8 -cp . Lab4_Main
goto end

:error
echo [ERROR] Compilation failed. Ensure JDK is installed.
pause

:end
