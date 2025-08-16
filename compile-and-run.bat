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

echo Creating target directory...
if not exist target\classes mkdir target\classes

echo Compiling Question.java...
javac -d target/classes src/main/java/com/quizgenerator/model/Question.java
if errorlevel 1 (
    echo ERROR: Failed to compile Question.java
    pause
    exit /b 1
)

echo Compiling QuizSession.java...
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/model/QuizSession.java
if errorlevel 1 (
    echo ERROR: Failed to compile QuizSession.java
    pause
    exit /b 1
)

echo Compiling QuestionService.java...
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/service/QuestionService.java
if errorlevel 1 (
    echo ERROR: Failed to compile QuestionService.java
    pause
    exit /b 1
)

echo Compiling QuizService.java...
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/service/QuizService.java
if errorlevel 1 (
    echo ERROR: Failed to compile QuizService.java
    pause
    exit /b 1
)

echo Compiling ConsoleQuiz.java...
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/ConsoleQuiz.java
if errorlevel 1 (
    echo ERROR: Failed to compile ConsoleQuiz.java
    pause
    exit /b 1
)

echo.
echo ✅ Compilation successful!
echo.
echo Starting Quiz Generator Console Edition...
echo.
java -cp target/classes com.quizgenerator.ConsoleQuiz

pause
