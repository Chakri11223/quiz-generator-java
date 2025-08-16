#!/bin/bash

echo "========================================"
echo "    Quiz Generator Console Edition"
echo "========================================"
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java 11 or higher"
    exit 1
fi

# Check Java version
java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$java_version" -lt 11 ]; then
    echo "ERROR: Java 11 or higher is required. Current version: $java_version"
    exit 1
fi

echo "Creating target directory..."
mkdir -p target/classes

echo "Compiling Question.java..."
javac -d target/classes src/main/java/com/quizgenerator/model/Question.java
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to compile Question.java"
    exit 1
fi

echo "Compiling QuizSession.java..."
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/model/QuizSession.java
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to compile QuizSession.java"
    exit 1
fi

echo "Compiling QuestionService.java..."
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/service/QuestionService.java
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to compile QuestionService.java"
    exit 1
fi

echo "Compiling QuizService.java..."
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/service/QuizService.java
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to compile QuizService.java"
    exit 1
fi

echo "Compiling ConsoleQuiz.java..."
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/ConsoleQuiz.java
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to compile ConsoleQuiz.java"
    exit 1
fi

echo
echo "✅ Compilation successful!"
echo
echo "Starting Quiz Generator Console Edition..."
echo
java -cp target/classes com.quizgenerator.ConsoleQuiz
