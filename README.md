# Quiz Generator with Timer - Console Edition

A feature-rich console-based quiz application built in Java that tests your knowledge with timed questions and provides detailed performance feedback.

## 🎯 Features

- **Multiple Quiz Types**: Quick (5 questions), Standard (10 questions), Challenge (15 questions), and Custom quizzes
- **Timer Functionality**: 30-second time limit per question with automatic timeout
- **Real-time Scoring**: Immediate feedback and score tracking
- **Performance Analytics**: Detailed results with accuracy percentage and performance feedback
- **25 Diverse Questions**: Covering general knowledge, science, history, geography, and more
- **User-friendly Interface**: Clean console interface with emojis and clear instructions
- **No External Dependencies**: Pure Java implementation, no external libraries required

## 🚀 Quick Start

### Prerequisites

- Java 11 or higher
- Windows, macOS, or Linux

### Running the Application

1. **Clone or download the project**
2. **Navigate to the project directory**
3. **Run the application**:

#### Windows
```bash
.\compile-and-run.bat
```

#### Linux/macOS
```bash
chmod +x compile-and-run.sh
./compile-and-run.sh
```

#### Manual Compilation
```bash
# Create target directory
mkdir -p target/classes

# Compile all Java files
javac -d target/classes src/main/java/com/quizgenerator/model/Question.java
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/model/QuizSession.java
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/service/QuestionService.java
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/service/QuizService.java
javac -d target/classes -cp target/classes src/main/java/com/quizgenerator/ConsoleQuiz.java

# Run the application
java -cp target/classes com.quizgenerator.ConsoleQuiz
```

## 🎮 How to Play

1. **Choose Quiz Type**: Select from Quick, Standard, Challenge, or Custom quiz
2. **Read Questions**: Each question has 4 multiple-choice options
3. **Answer Quickly**: You have 30 seconds per question
4. **Get Feedback**: See immediate results and score updates
5. **Review Results**: Get detailed performance analysis at the end

## 📊 Sample Output

```
🎯 Welcome to Quiz Generator Console Edition!
==================================================
📊 Loaded 25 questions

Choose an option:
1. Quick Quiz (5 questions)
2. Standard Quiz (10 questions)
3. Challenge Quiz (15 questions)
4. Custom Quiz
5. Exit
Enter your choice (1-5): 1

🚀 Starting quiz with 5 questions...
⏰ Each question has 30 seconds time limit.
📝 Press Enter when ready to start...

✅ Quiz session created: 719cd7a6-fa3f-4731-95e0-ddac07b0fc01

Question 1 of 5
Score: 0
Time remaining: 30 seconds
--------------------------------------------------
What is the smallest prime number?

1. 0
2. 1
3. 2
4. 3

Enter your answer (1-4): 3
✅ Correct! +10 points
Current score: 10

🎉 Quiz completed!
==================================================
Final Results:
Total Questions: 5
Correct Answers: 1
Final Score: 10
Accuracy: 20%
Total Time: 4 seconds
==================================================
📚 Keep studying! Practice makes perfect!
```

## 🏗️ Project Structure

```
Quiz Generator with Timer/
├── src/main/java/com/quizgenerator/
│   ├── ConsoleQuiz.java          # Main console application
│   ├── model/
│   │   ├── Question.java         # Question data model
│   │   └── QuizSession.java      # Quiz session management
│   └── service/
│       ├── QuestionService.java  # Question management
│       └── QuizService.java      # Quiz session service
├── compile-and-run.bat           # Windows run script
├── compile-and-run.sh            # Linux/macOS run script
├── pom.xml                       # Maven configuration
└── README.md                     # This file
```

## 🎯 Question Categories

The application includes 25 diverse questions covering:

- **Geography**: Capitals, countries, continents, oceans
- **Science**: Physics, chemistry, biology, astronomy
- **History**: Important dates and events
- **Mathematics**: Basic calculations and concepts
- **Literature**: Famous authors and works
- **General Knowledge**: Various trivia topics

## 🛠️ Technical Details

- **Language**: Java 11+
- **Architecture**: MVC pattern with service layer
- **Dependencies**: None (pure Java)
- **Threading**: Concurrent timer implementation
- **Input Validation**: Robust error handling for user input

## 🎨 Features in Detail

### Timer System
- 30-second countdown per question
- Automatic timeout with visual feedback
- Thread-safe implementation

### Scoring System
- 10 points per correct answer
- 0 points for incorrect or timed-out answers
- Real-time score tracking

### Performance Feedback
- Accuracy percentage calculation
- Performance-based encouragement messages
- Detailed final results

### User Experience
- Clear menu navigation
- Intuitive question display
- Immediate feedback on answers
- Option to retry or exit

## 🔧 Customization

### Adding New Questions
Edit `src/main/java/com/quizgenerator/service/QuestionService.java` and add new questions to the `initializeQuestions()` method:

```java
questionList.add(new Question(
    questionId,
    "Your question here?",
    Arrays.asList("Option 1", "Option 2", "Option 3", "Option 4"),
    correctAnswerIndex, // 0-based index
    30 // time limit in seconds
));
```

### Modifying Timer Duration
Change the time limit in the Question constructor calls in `QuestionService.java`.

### Adjusting Scoring
Modify the scoring logic in `QuizSession.java` and feedback messages in `ConsoleQuiz.java`.

## 🐛 Troubleshooting

### Common Issues

1. **"Java is not installed"**
   - Install Java 11 or higher
   - Ensure JAVA_HOME is set correctly

2. **"Compilation failed"**
   - Check Java version compatibility
   - Ensure all source files are present

3. **"Permission denied" (Linux/macOS)**
   - Make script executable: `chmod +x compile-and-run.sh`

### Performance Tips

- Close unnecessary applications for better performance
- Ensure adequate system resources
- Use a terminal with good Unicode support for emojis

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 🙏 Acknowledgments

- Built with pure Java for maximum compatibility
- Designed for educational and entertainment purposes
- Inspired by classic quiz applications

---

**Happy Quizzing! 🎉**
