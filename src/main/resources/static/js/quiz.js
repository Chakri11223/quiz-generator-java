// Quiz Application JavaScript
class QuizApp {
    constructor() {
        this.currentSession = null;
        this.currentQuestion = null;
        this.timer = null;
        this.timeLeft = 30;
        this.isAnswerSubmitted = false;
        
        this.initializeEventListeners();
    }

    initializeEventListeners() {
        // Add any global event listeners here
        document.addEventListener('DOMContentLoaded', () => {
            console.log('Quiz App initialized');
        });
    }

    // Show different screens
    showScreen(screenId) {
        document.querySelectorAll('.screen').forEach(screen => {
            screen.classList.remove('active');
        });
        document.getElementById(screenId).classList.add('active');
    }

    // Show loading overlay
    showLoading() {
        document.getElementById('loading-overlay').classList.add('active');
    }

    // Hide loading overlay
    hideLoading() {
        document.getElementById('loading-overlay').classList.remove('active');
    }

    // Show notification
    showNotification(message, type = 'success') {
        const notification = document.getElementById('notification');
        const messageEl = notification.querySelector('.notification-message');
        const iconEl = notification.querySelector('.notification-icon');

        messageEl.textContent = message;
        
        // Set icon based on type
        if (type === 'success') {
            iconEl.className = 'notification-icon fas fa-check-circle';
            notification.className = 'notification success';
        } else {
            iconEl.className = 'notification-icon fas fa-exclamation-circle';
            notification.className = 'notification error';
        }

        notification.classList.add('show');

        // Auto hide after 3 seconds
        setTimeout(() => {
            notification.classList.remove('show');
        }, 3000);
    }

    // API calls
    async apiCall(url, method = 'GET', data = null) {
        try {
            const options = {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                }
            };

            if (data) {
                options.body = JSON.stringify(data);
            }

            const response = await fetch(url, options);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('API call failed:', error);
            throw error;
        }
    }

    // Start quiz
    async startQuiz(questionCount = 10) {
        try {
            this.showLoading();
            
            const response = await this.apiCall('/api/quiz/create', 'POST', {
                questionCount: questionCount
            });

            this.currentSession = response.sessionId;
            this.currentQuestion = response.firstQuestion;
            
            this.updateQuizUI();
            this.showScreen('quiz-screen');
            this.startTimer();
            
            this.hideLoading();
            this.showNotification('Quiz started! Good luck!');
            
        } catch (error) {
            this.hideLoading();
            this.showNotification('Failed to start quiz. Please try again.', 'error');
            console.error('Error starting quiz:', error);
        }
    }

    // Update quiz UI
    updateQuizUI() {
        if (!this.currentQuestion) return;

        // Update question text
        document.getElementById('question-text').textContent = this.currentQuestion.question;
        
        // Update question counter
        document.getElementById('current-question').textContent = 
            this.currentSession ? this.getCurrentQuestionNumber() : 1;
        
        // Update total questions
        document.getElementById('total-questions').textContent = 
            this.currentQuestion.totalQuestions || 10;
        
        // Update progress bar
        const progress = this.getProgressPercentage();
        document.getElementById('progress-fill').style.width = `${progress}%`;
        
        // Populate options
        this.populateOptions();
        
        // Reset answer submission flag
        this.isAnswerSubmitted = false;
    }

    // Populate answer options
    populateOptions() {
        const container = document.getElementById('options-container');
        container.innerHTML = '';

        this.currentQuestion.options.forEach((option, index) => {
            const optionElement = document.createElement('div');
            optionElement.className = 'option';
            optionElement.textContent = option;
            optionElement.dataset.index = index;
            
            optionElement.addEventListener('click', () => {
                if (!this.isAnswerSubmitted) {
                    this.selectOption(index);
                }
            });
            
            container.appendChild(optionElement);
        });
    }

    // Select an option
    selectOption(optionIndex) {
        // Remove previous selections
        document.querySelectorAll('.option').forEach(option => {
            option.classList.remove('selected');
        });
        
        // Select current option
        const selectedOption = document.querySelector(`[data-index="${optionIndex}"]`);
        if (selectedOption) {
            selectedOption.classList.add('selected');
        }
    }

    // Submit answer
    async submitAnswer() {
        if (this.isAnswerSubmitted) return;

        const selectedOption = document.querySelector('.option.selected');
        if (!selectedOption) {
            this.showNotification('Please select an answer!', 'error');
            return;
        }

        const selectedIndex = parseInt(selectedOption.dataset.index);
        
        try {
            this.isAnswerSubmitted = true;
            this.stopTimer();
            
            const response = await this.apiCall('/api/quiz/answer', 'POST', {
                sessionId: this.currentSession,
                selectedAnswer: selectedIndex
            });

            // Show correct/incorrect feedback
            this.showAnswerFeedback(selectedIndex, response.isCorrect);
            
            // Update score
            document.getElementById('current-score').textContent = response.score;
            
            // Wait a bit then move to next question or show results
            setTimeout(() => {
                if (response.isCompleted) {
                    this.showResults();
                } else {
                    this.currentQuestion = response.nextQuestion;
                    this.updateQuizUI();
                    this.startTimer();
                }
            }, 2000);
            
        } catch (error) {
            this.showNotification('Failed to submit answer. Please try again.', 'error');
            console.error('Error submitting answer:', error);
            this.isAnswerSubmitted = false;
            this.startTimer();
        }
    }

    // Show answer feedback
    showAnswerFeedback(selectedIndex, isCorrect) {
        const options = document.querySelectorAll('.option');
        
        options.forEach((option, index) => {
            option.classList.add('disabled');
            
            if (index === selectedIndex) {
                option.classList.add(isCorrect ? 'correct' : 'incorrect');
            } else if (index === this.currentQuestion.correctAnswer) {
                option.classList.add('correct');
            }
        });

        // Show notification
        if (isCorrect) {
            this.showNotification('Correct! Well done! 🎉');
        } else {
            this.showNotification('Incorrect. Better luck next time! 💪');
        }
    }

    // Timer functions
    startTimer() {
        this.timeLeft = this.currentQuestion.timeLimit || 30;
        this.updateTimerDisplay();
        
        this.timer = setInterval(() => {
            this.timeLeft--;
            this.updateTimerDisplay();
            
            if (this.timeLeft <= 0) {
                this.timeUp();
            } else if (this.timeLeft <= 10) {
                this.addTimerWarning();
            }
        }, 1000);
    }

    stopTimer() {
        if (this.timer) {
            clearInterval(this.timer);
            this.timer = null;
        }
        this.removeTimerWarning();
    }

    updateTimerDisplay() {
        const timerElement = document.getElementById('timer');
        timerElement.textContent = this.timeLeft;
    }

    addTimerWarning() {
        const timerElement = document.getElementById('timer');
        timerElement.classList.add('timer-warning');
    }

    removeTimerWarning() {
        const timerElement = document.getElementById('timer');
        timerElement.classList.remove('timer-warning');
    }

    timeUp() {
        this.stopTimer();
        this.showNotification('Time\'s up! Moving to next question...', 'error');
        
        // Auto-submit if no answer selected
        if (!this.isAnswerSubmitted) {
            this.submitAnswer();
        }
    }

    // End quiz
    async endQuiz() {
        if (confirm('Are you sure you want to end the quiz?')) {
            try {
                this.stopTimer();
                this.showLoading();
                
                const response = await this.apiCall('/api/quiz/end', 'POST', {
                    sessionId: this.currentSession
                });

                this.showResults(response.results);
                
            } catch (error) {
                this.showNotification('Failed to end quiz. Please try again.', 'error');
                console.error('Error ending quiz:', error);
                this.hideLoading();
            }
        }
    }

    // Show results
    async showResults(results = null) {
        try {
            this.stopTimer();
            this.showLoading();
            
            if (!results) {
                const response = await this.apiCall(`/api/quiz/${this.currentSession}/results`);
                results = response;
            }

            this.populateResults(results);
            this.showScreen('results-screen');
            this.hideLoading();
            
        } catch (error) {
            this.showNotification('Failed to load results. Please try again.', 'error');
            console.error('Error loading results:', error);
            this.hideLoading();
        }
    }

    // Populate results
    populateResults(results) {
        // Update main stats
        document.getElementById('final-score').textContent = results.score;
        document.getElementById('correct-answers').textContent = results.correctAnswers;
        document.getElementById('accuracy').textContent = `${results.accuracyPercentage}%`;
        document.getElementById('total-time').textContent = `${results.durationSeconds}s`;

        // Populate detailed results
        const detailsContainer = document.getElementById('results-details');
        detailsContainer.innerHTML = '';

        if (results.answerDetails && results.answerDetails.length > 0) {
            const detailsTitle = document.createElement('h3');
            detailsTitle.textContent = 'Question Details';
            detailsContainer.appendChild(detailsTitle);

            results.answerDetails.forEach((detail, index) => {
                const answerItem = document.createElement('div');
                answerItem.className = `answer-item ${detail.isCorrect ? 'correct' : 'incorrect'}`;
                
                const questionText = document.createElement('div');
                questionText.className = 'question-text';
                questionText.textContent = `${index + 1}. ${detail.question}`;
                
                const status = document.createElement('div');
                status.className = `answer-status ${detail.isCorrect ? 'correct' : 'incorrect'}`;
                status.innerHTML = detail.isCorrect ? 
                    '<i class="fas fa-check"></i> Correct' : 
                    '<i class="fas fa-times"></i> Incorrect';
                
                answerItem.appendChild(questionText);
                answerItem.appendChild(status);
                detailsContainer.appendChild(answerItem);
            });
        }
    }

    // Restart quiz
    restartQuiz() {
        this.currentSession = null;
        this.currentQuestion = null;
        this.stopTimer();
        this.showScreen('welcome-screen');
    }

    // Utility functions
    getCurrentQuestionNumber() {
        // This would need to be tracked by the backend
        return 1; // Placeholder
    }

    getProgressPercentage() {
        // This would need to be calculated based on current progress
        return 10; // Placeholder
    }
}

// Global quiz app instance
const quizApp = new QuizApp();

// Global functions for HTML onclick handlers
function startQuiz(questionCount) {
    quizApp.startQuiz(questionCount);
}

function startCustomQuiz() {
    const questionCount = parseInt(document.getElementById('custom-question-count').value);
    if (questionCount && questionCount > 0 && questionCount <= 25) {
        quizApp.startQuiz(questionCount);
    } else {
        quizApp.showNotification('Please enter a valid number of questions (1-25)', 'error');
    }
}

function endQuiz() {
    quizApp.endQuiz();
}

function restartQuiz() {
    quizApp.restartQuiz();
}

function showWelcome() {
    quizApp.showScreen('welcome-screen');
}

// Add keyboard shortcuts
document.addEventListener('keydown', (event) => {
    if (document.getElementById('quiz-screen').classList.contains('active')) {
        const key = event.key;
        
        // Number keys 1-4 for answer selection
        if (key >= '1' && key <= '4') {
            const optionIndex = parseInt(key) - 1;
            const options = document.querySelectorAll('.option');
            if (options[optionIndex] && !quizApp.isAnswerSubmitted) {
                quizApp.selectOption(optionIndex);
            }
        }
        
        // Enter key to submit answer
        if (key === 'Enter' && !quizApp.isAnswerSubmitted) {
            quizApp.submitAnswer();
        }
        
        // Escape key to end quiz
        if (key === 'Escape') {
            quizApp.endQuiz();
        }
    }
});

// Add click handlers for options (in case the data-index approach doesn't work)
document.addEventListener('click', (event) => {
    if (event.target.classList.contains('option') && !quizApp.isAnswerSubmitted) {
        const optionIndex = parseInt(event.target.dataset.index);
        if (!isNaN(optionIndex)) {
            quizApp.selectOption(optionIndex);
        }
    }
});

// Auto-submit when timer runs out
setInterval(() => {
    if (quizApp.timer && quizApp.timeLeft <= 0 && !quizApp.isAnswerSubmitted) {
        quizApp.submitAnswer();
    }
}, 1000);
