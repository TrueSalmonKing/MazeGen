@echo off
title Building The Maze Generator
echo Run this script to build The Maze Generator Application, all prerequisites are in the readme file.
pause 

echo Compilation Process
mkdir bin
javac -d bin -sourcepath src src/com/UI/Window.java

echo Jar File Creation Process
move resources\Icon.png bin
cd bin
jar cvfe MazeGen.jar com/UI/Window com/assets/*.class com/algorithms/*.class com/UI/*.class Icon.png
move MazeGen.jar ..
move Icon.png ..\resources
cd ..
rmdir /Q /S bin
echo Build finished !
