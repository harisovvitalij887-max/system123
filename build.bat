@echo off
REM Needs JDK 21 and Gradle 8.12+ in PATH (https://gradle.org/install/)
where java >nul 2>nul || (echo JDK 21 not found & pause & exit /b 1)
where gradle >nul 2>nul || (echo Gradle not found & pause & exit /b 1)
gradle build
echo.
echo Jar: build\libs\clienthud-1.0.0.jar
pause
