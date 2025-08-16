# Contributing to Quiz Generator with Timer

Thank you for your interest in contributing to the Quiz Generator with Timer project! This document provides guidelines and information for contributors.

## 🤝 How to Contribute

### Types of Contributions

We welcome various types of contributions:

- **Bug Reports**: Report issues you encounter
- **Feature Requests**: Suggest new features or improvements
- **Code Contributions**: Submit code improvements or new features
- **Documentation**: Improve or add documentation
- **Testing**: Help test the application and report issues

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Git
- Basic knowledge of Java programming

### Setting Up the Development Environment

1. **Fork the repository**
   ```bash
   git clone https://github.com/your-username/quiz-generator-timer.git
   cd quiz-generator-timer
   ```

2. **Compile and test the application**
   ```bash
   # Windows
   .\compile-and-run.bat
   
   # Linux/macOS
   chmod +x compile-and-run.sh
   ./compile-and-run.sh
   ```

3. **Verify everything works**
   - Run the application
   - Test different quiz types
   - Verify timer functionality
   - Check scoring system

## 📝 Development Guidelines

### Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Add comments for complex logic
- Keep methods focused and concise
- Maintain consistent indentation

### Adding New Questions

To add new questions, edit `src/main/java/com/quizgenerator/service/QuestionService.java`:

```java
questionList.add(new Question(
    nextId,                    // Unique ID
    "Your question here?",     // Question text
    Arrays.asList(             // Multiple choice options
        "Option 1",
        "Option 2", 
        "Option 3",
        "Option 4"
    ),
    correctAnswerIndex,        // 0-based index of correct answer
    30                        // Time limit in seconds
));
```

### Adding New Features

1. **Plan your feature**
   - Define the requirements
   - Consider the impact on existing code
   - Plan the implementation approach

2. **Implement the feature**
   - Follow existing code patterns
   - Add appropriate error handling
   - Include input validation

3. **Test thoroughly**
   - Test with different scenarios
   - Verify edge cases
   - Ensure no regressions

### File Structure

Maintain the existing file structure:

```
src/main/java/com/quizgenerator/
├── ConsoleQuiz.java          # Main application (modify carefully)
├── model/
│   ├── Question.java         # Question data model
│   └── QuizSession.java      # Session management
└── service/
    ├── QuestionService.java  # Question management
    └── QuizService.java      # Quiz orchestration
```

## 🐛 Reporting Bugs

### Before Reporting

1. Check if the issue has already been reported
2. Try to reproduce the issue consistently
3. Test with different quiz types and scenarios

### Bug Report Template

```
**Bug Description**
Brief description of the issue

**Steps to Reproduce**
1. Step 1
2. Step 2
3. Step 3

**Expected Behavior**
What should happen

**Actual Behavior**
What actually happens

**Environment**
- Operating System: [Windows/Linux/macOS]
- Java Version: [e.g., Java 11]
- Quiz Type: [Quick/Standard/Challenge/Custom]

**Additional Information**
Any other relevant details
```

## 💡 Feature Requests

### Before Requesting

1. Check if the feature already exists
2. Consider if it fits the project's scope
3. Think about implementation complexity

### Feature Request Template

```
**Feature Description**
Brief description of the requested feature

**Use Case**
Why this feature would be useful

**Proposed Implementation**
How you think it could be implemented

**Alternatives Considered**
Other approaches you've considered
```

## 🔄 Pull Request Process

### Before Submitting

1. **Test your changes**
   - Compile successfully
   - Run without errors
   - Test all quiz types
   - Verify timer functionality

2. **Update documentation**
   - Update README.md if needed
   - Update PROJECT_STRUCTURE.md if file structure changes
   - Add comments to new code

3. **Follow commit conventions**
   - Use clear, descriptive commit messages
   - Reference issues when applicable
   - Keep commits focused and atomic

### Pull Request Template

```
**Description**
Brief description of changes

**Type of Change**
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Code refactoring

**Testing**
- [ ] Compiled successfully
- [ ] All tests pass
- [ ] Manual testing completed
- [ ] No regressions introduced

**Additional Notes**
Any additional information
```

## 📋 Code Review Process

1. **Review Request**
   - Submit pull request with clear description
   - Reference related issues
   - Include testing information

2. **Review Process**
   - Maintainers will review your code
   - Address feedback and suggestions
   - Make requested changes

3. **Merge**
   - Once approved, changes will be merged
   - Your contribution will be acknowledged

## 🎯 Areas for Contribution

### High Priority
- Bug fixes and stability improvements
- Performance optimizations
- Better error handling
- Enhanced user experience

### Medium Priority
- Additional question categories
- Customizable timer durations
- Score persistence
- Export results functionality

### Low Priority
- UI enhancements
- Additional quiz types
- Statistics and analytics
- Multi-language support

## 📞 Getting Help

If you need help with contributing:

1. **Check existing issues** for similar problems
2. **Review documentation** in README.md and PROJECT_STRUCTURE.md
3. **Ask questions** in issue discussions
4. **Join discussions** about project direction

## 🙏 Recognition

Contributors will be recognized in:
- Project README.md
- Release notes
- GitHub contributors list

## 📄 License

By contributing to this project, you agree that your contributions will be licensed under the MIT License.

---

Thank you for contributing to Quiz Generator with Timer! 🎉
