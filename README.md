# Quiz Generator with Timer

A comprehensive quiz application with timer functionality, built using Java backend and HTML/CSS/JavaScript frontend.

## Features

- **Question Management**: Read questions from JSON file or database
- **Random MCQ Generation**: Randomly select and display multiple choice questions
- **Countdown Timer**: Built-in timer for each question
- **Score Tracking**: Real-time score calculation and display
- **Responsive UI**: Modern, user-friendly interface
- **Session Management**: Track quiz progress and results

## Project Structure

```
Quiz Generator with Timer/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── quizgenerator/
│   │   │           ├── model/
│   │   │           ├── service/
│   │   │           ├── controller/
│   │   │           └── Main.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   └── index.html
│   │       └── questions.json
├── pom.xml
└── README.md
```

## Technologies Used

- **Backend**: Java (Core Java, no Spring Boot)
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla JS)
- **Data Format**: JSON
- **Build Tool**: Maven

## How to Run

1. **Prerequisites**: Java 8+ and Maven installed
2. **Build**: `mvn clean compile`
3. **Run**: `mvn exec:java -Dexec.mainClass="com.quizgenerator.Main"`
4. **Access**: Open `http://localhost:8080` in your browser

## Features in Detail

### Question Management
- Questions stored in JSON format
- Support for multiple choice questions
- Easy to add/modify questions

### Timer Functionality
- Configurable time limits per question
- Visual countdown display
- Automatic submission when time expires

### Scoring System
- Points awarded for correct answers
- Penalty for incorrect answers (optional)
- Final score calculation and display

### User Interface
- Clean, modern design
- Responsive layout
- Progress indicators
- Real-time feedback

## Configuration

Edit `src/main/resources/questions.json` to add or modify questions:

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

## License

MIT License
