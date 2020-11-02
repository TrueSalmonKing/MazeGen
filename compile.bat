@echo off
title Building The Maze Generator
echo Run this script to compile The Maze Generator java files, all prerequisites are in the readme file.
pause 

echo Compilation Process
mkdir bin
javac -d bin -sourcepath src src/com/UI/Window.java