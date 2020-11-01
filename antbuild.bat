@echo off
title Building The Maze Generator
ant 
if %errorlevel%==9009 echo Please Adequetly Install Ant, and set the enviroment variables properly, Namely the path variable must point to the bin directory containing the ant file
pause