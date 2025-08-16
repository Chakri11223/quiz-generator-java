#!/bin/bash

echo "========================================"
echo "    Quiz Generator with Timer"
echo "========================================"
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java 11 or higher"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo "Please install Maven"
    exit 1
fi

echo "Building project..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "ERROR: Build failed"
    exit 1
fi

echo
echo "Starting Quiz Generator..."
echo
echo "Choose an option:"
echo "1. Web Application (HTTP Server)"
echo "2. Console Application"
echo
read -p "Enter your choice (1 or 2): " choice

case $choice in
    1)
        echo "Starting web application..."
        echo "Server will be available at: http://localhost:8080"
        echo "Press Ctrl+C to stop the server"
        echo
        mvn exec:java -Dexec.mainClass="com.quizgenerator.Main"
        ;;
    2)
        echo "Starting console application..."
        echo
        mvn exec:java -Dexec.mainClass="com.quizgenerator.ConsoleQuiz"
        ;;
    *)
        echo "Invalid choice. Please run the script again."
        exit 1
        ;;
esac
