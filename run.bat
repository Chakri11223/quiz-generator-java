@echo off
echo ========================================
echo    Quiz Generator with Timer
echo ========================================
echo.

echo Checking if Java is installed...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 11 or higher
    pause
    exit /b 1
)

echo Checking if Maven is installed...
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven
    pause
    exit /b 1
)

echo Building project...
call mvn clean compile

if errorlevel 1 (
    echo ERROR: Build failed
    pause
    exit /b 1
)

echo.
echo Starting Quiz Generator...
echo.
echo Choose an option:
echo 1. Web Application (HTTP Server)
echo 2. Console Application
echo.
set /p choice="Enter your choice (1 or 2): "

if "%choice%"=="1" (
    echo Starting web application...
    echo Server will be available at: http://localhost:8080
    echo Press Ctrl+C to stop the server
    echo.
    call mvn exec:java -Dexec.mainClass="com.quizgenerator.Main"
) else if "%choice%"=="2" (
    echo Starting console application...
    echo.
    call mvn exec:java -Dexec.mainClass="com.quizgenerator.ConsoleQuiz"
) else (
    echo Invalid choice. Please run the script again.
)

pause
