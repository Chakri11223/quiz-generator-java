# Quiz Generator with Timer - Project Structure

## 📁 Directory Structure

```
Quiz Generator with Timer/
├── 📄 README.md                           # Main project documentation
├── 📄 PROJECT_STRUCTURE.md                # This file - project structure guide
├── 📄 pom.xml                             # Maven configuration (optional)
├── 📄 compile-and-run.bat                 # Windows run script
├── 📄 compile-and-run.sh                  # Linux/macOS run script
├── 📄 run-console.bat                     # Alternative Windows script
├── 📁 src/
│   └── 📁 main/
│       ├── 📁 java/
│       │   └── 📁 com/
│       │       └── 📁 quizgenerator/
│       │           ├── 📄 ConsoleQuiz.java           # Main console application
│       │           ├── 📁 model/
│       │           │   ├── 📄 Question.java          # Question data model
│       │           │   └── 📄 QuizSession.java       # Quiz session management
│       │           └── 📁 service/
│       │               ├── 📄 QuestionService.java   # Question management service
│       │               └── 📄 QuizService.java       # Quiz session service
│       └── 📁 resources/
│           └── 📄 questions.json                     # Legacy JSON file (not used)
└── 📁 target/
    └── 📁 classes/                        # Compiled Java classes
```

## 🏗️ Architecture Overview

### Core Components

#### 1. **ConsoleQuiz.java** - Main Application
- **Purpose**: Entry point and user interface
- **Features**:
  - Menu-driven interface
  - Quiz type selection
  - Timer management
  - User input handling
  - Results display

#### 2. **Model Layer** (`model/`)

##### Question.java
- **Purpose**: Data model for quiz questions
- **Properties**:
  - `id`: Unique question identifier
  - `question`: Question text
  - `options`: List of multiple choice options
  - `correctAnswer`: Index of correct answer (0-based)
  - `timeLimit`: Time limit in seconds

##### QuizSession.java
- **Purpose**: Manages quiz session state and progress
- **Features**:
  - Session tracking
  - Score calculation
  - Progress monitoring
  - Answer validation
  - Performance analytics

#### 3. **Service Layer** (`service/`)

##### QuestionService.java
- **Purpose**: Manages question data and operations
- **Features**:
  - Hardcoded question database (25 questions)
  - Random question selection
  - Question shuffling
  - Category management

##### QuizService.java
- **Purpose**: Orchestrates quiz sessions
- **Features**:
  - Session creation
  - Session management
  - Question distribution
  - Service coordination

## 🔄 Data Flow

```
User Input → ConsoleQuiz → QuizService → QuestionService → Question Model
     ↓
Results ← ConsoleQuiz ← QuizSession ← Answer Processing ← User Selection
```

## 🎯 Key Features Implementation

### Timer System
- **Location**: `ConsoleQuiz.java`
- **Implementation**: `ScheduledExecutorService`
- **Features**:
  - 30-second countdown per question
  - Thread-safe execution
  - Automatic timeout handling

### Scoring System
- **Location**: `QuizSession.java`
- **Implementation**: Real-time score tracking
- **Features**:
  - 10 points per correct answer
  - Accuracy percentage calculation
  - Performance feedback

### Question Management
- **Location**: `QuestionService.java`
- **Implementation**: Hardcoded question database
- **Features**:
  - 25 diverse questions
  - Multiple categories
  - Random selection and shuffling

## 🛠️ Build and Run Process

### Compilation Order
1. `Question.java` - Base model
2. `QuizSession.java` - Depends on Question
3. `QuestionService.java` - Depends on Question
4. `QuizService.java` - Depends on QuestionService
5. `ConsoleQuiz.java` - Depends on all services

### Run Scripts
- **Windows**: `compile-and-run.bat`
- **Linux/macOS**: `compile-and-run.sh`
- **Manual**: Direct javac/java commands

## 📊 Question Categories

The application includes questions from:
- **Geography** (5 questions)
- **Science** (6 questions)
- **History** (3 questions)
- **Mathematics** (2 questions)
- **Literature** (2 questions)
- **General Knowledge** (7 questions)

## 🔧 Configuration Points

### Adding Questions
- **File**: `QuestionService.java`
- **Method**: `initializeQuestions()`
- **Format**: `new Question(id, question, options, correctAnswer, timeLimit)`

### Modifying Timer
- **File**: `QuestionService.java`
- **Parameter**: `timeLimit` in Question constructor

### Adjusting Scoring
- **File**: `QuizSession.java`
- **Method**: `submitAnswer()`

### Customizing UI
- **File**: `ConsoleQuiz.java`
- **Sections**: Menu display, question display, results display

## 🚀 Deployment

### Requirements
- Java 11 or higher
- No external dependencies
- Cross-platform compatibility

### Distribution
- Source code distribution
- Compiled JAR file (optional)
- Run scripts for different platforms

## 📝 Development Notes

### Design Patterns
- **MVC Pattern**: Model-View-Controller separation
- **Service Layer**: Business logic encapsulation
- **Singleton Pattern**: Service instances

### Error Handling
- Input validation in `ConsoleQuiz.java`
- Exception handling for timer operations
- Graceful degradation for invalid inputs

### Performance Considerations
- Minimal memory footprint
- Efficient question shuffling
- Thread-safe timer implementation

---

**Note**: This project is designed as a pure Java console application with no external dependencies, making it highly portable and easy to deploy.
