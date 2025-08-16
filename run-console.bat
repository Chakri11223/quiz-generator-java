@echo off
echo ========================================
echo    Quiz Generator Console Edition
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

echo Compiling the project...
javac -d target/classes src/main/java/com/quizgenerator/*.java src/main/java/com/quizgenerator/*/*.java

if errorlevel 1 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo.
echo Starting Quiz Generator Console Edition...
echo.
java -cp target/classes com.quizgenerator.ConsoleQuiz

pause
