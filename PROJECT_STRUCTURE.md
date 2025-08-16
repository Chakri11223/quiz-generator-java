# Quiz Generator with Timer - Project Structure

## Overview
A comprehensive quiz application with timer functionality, built using Java backend and HTML/CSS/JavaScript frontend.

## Project Structure

```
Quiz Generator with Timer/
├── 📁 src/
│   └── 📁 main/
│       ├── 📁 java/
│       │   └── 📁 com/
│       │       └── 📁 quizgenerator/
│       │           ├── 📁 model/
│       │           │   ├── 📄 Question.java          # Question data model
│       │           │   └── 📄 QuizSession.java       # Quiz session management
│       │           ├── 📁 service/
│       │           │   ├── 📄 QuestionService.java   # Question loading & management
│       │           │   └── 📄 QuizService.java       # Quiz session & scoring logic
│       │           ├── 📁 controller/
│       │           │   └── 📄 QuizController.java    # HTTP request handling
│       │           ├── 📄 Main.java                  # Web application entry point
│       │           └── 📄 ConsoleQuiz.java           # Console application entry point
│       └── 📁 resources/
│           ├── 📁 static/
│           │   ├── 📁 css/
│           │   │   └── 📄 style.css                  # Modern responsive styling
│           │   ├── 📁 js/
│           │   │   └── 📄 quiz.js                    # Frontend JavaScript logic
│           │   └── 📄 index.html                     # Main web interface
│           └── 📄 questions.json                     # Quiz questions database
├── 📄 pom.xml                                        # Maven project configuration
├── 📄 README.md                                      # Project documentation
├── 📄 PROJECT_STRUCTURE.md                           # This file
├── 📄 run.bat                                        # Windows execution script
└── 📄 run.sh                                         # Unix/Linux execution script
```

## Key Features

### 🎯 Core Functionality
- **Question Management**: Load questions from JSON file
- **Random MCQ Generation**: Randomly select questions for each quiz
- **Countdown Timer**: 30-second timer per question with visual feedback
- **Score Tracking**: Real-time score calculation and display
- **Session Management**: Track quiz progress and results

### 🌐 Web Application
- **Modern UI**: Beautiful, responsive design with gradient backgrounds
- **Real-time Updates**: Live score and progress tracking
- **Interactive Elements**: Hover effects, animations, and visual feedback
- **Mobile Responsive**: Works on all device sizes
- **Keyboard Shortcuts**: Number keys (1-4) for answers, Enter to submit

### 💻 Console Application
- **Text-based Interface**: Simple console-based quiz experience
- **Timer Functionality**: Countdown timer with automatic submission
- **Detailed Results**: Comprehensive score and performance analysis
- **Performance Feedback**: Encouraging messages based on accuracy

## Technology Stack

### Backend
- **Java 11+**: Core application logic
- **Maven**: Build tool and dependency management
- **Jackson**: JSON processing for questions
- **HTTP Server**: Built-in Java HTTP server (no Spring Boot)

### Frontend
- **HTML5**: Semantic markup
- **CSS3**: Modern styling with gradients, animations, and responsive design
- **JavaScript (ES6+)**: Vanilla JS with async/await for API calls
- **Font Awesome**: Icons for better UX
- **Google Fonts**: Inter font family for modern typography

## API Endpoints

### Quiz Management
- `POST /api/quiz/create` - Create new quiz session
- `POST /api/quiz/answer` - Submit answer for current question
- `POST /api/quiz/end` - End quiz session early

### Data Retrieval
- `GET /api/quiz/{sessionId}/question` - Get current question
- `GET /api/quiz/{sessionId}/stats` - Get session statistics
- `GET /api/quiz/{sessionId}/results` - Get final results

### Static Files
- `GET /` - Main application page
- `GET /css/style.css` - Stylesheet
- `GET /js/quiz.js` - JavaScript logic

## Question Format

Questions are stored in JSON format with the following structure:

```json
{
  "questions": [
    {
      "id": 1,
      "question": "What is the capital of France?",
      "options": ["London", "Berlin", "Paris", "Madrid"],
      "correctAnswer": 2,
      "timeLimit": 30
    }
  ]
}
```

## Running the Application

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Quick Start
1. **Windows**: Double-click `run.bat`
2. **Unix/Linux**: Run `./run.sh`
3. **Manual**: 
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.quizgenerator.Main"
   ```

### Access Points
- **Web Application**: http://localhost:8080
- **Console Application**: Run directly in terminal

## Customization

### Adding Questions
Edit `src/main/resources/questions.json` to add or modify questions.

### Styling
Modify `src/main/resources/static/css/style.css` for custom styling.

### Configuration
- Timer duration: Modify `timeLimit` in questions.json
- Points per question: Change in `QuizSession.submitAnswer()`
- Server port: Modify `PORT` constant in `Main.java`

## Performance Features

### Frontend
- **Lazy Loading**: Questions loaded on demand
- **Efficient DOM Updates**: Minimal re-rendering
- **Smooth Animations**: CSS transitions for better UX
- **Error Handling**: Graceful error recovery

### Backend
- **Session Management**: Efficient session tracking
- **Memory Management**: Automatic cleanup of expired sessions
- **Concurrent Access**: Thread-safe session handling
- **JSON Processing**: Fast question loading and serialization

## Security Considerations

- **Input Validation**: All user inputs are validated
- **Session Isolation**: Each quiz session is independent
- **Error Handling**: Graceful error handling without exposing internals
- **CORS Support**: Proper CORS headers for web requests

## Future Enhancements

- **Database Integration**: Replace JSON with database storage
- **User Authentication**: User accounts and progress tracking
- **Question Categories**: Organize questions by topics
- **Leaderboards**: Global and local score tracking
- **Multiplayer**: Real-time multiplayer quiz sessions
- **Question Editor**: Web-based question management interface
