package com.quizgenerator.model;

import java.util.*;

/**
 * Represents a quiz session with scoring and progress tracking
 */
public class QuizSession {
    private String sessionId;
    private List<Question> questions;
    private List<Question> answeredQuestions;
    private Map<Integer, Integer> userAnswers; // questionId -> selectedAnswer
    private int currentQuestionIndex;
    private int score;
    private boolean isCompleted;
    private Date startTime;
    private Date endTime;

    public QuizSession(String sessionId, List<Question> questions) {
        this.sessionId = sessionId;
        this.questions = new ArrayList<>(questions);
        this.answeredQuestions = new ArrayList<>();
        this.userAnswers = new HashMap<>();
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.isCompleted = false;
        this.startTime = new Date();
    }

    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<Question> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public Map<Integer, Integer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Map<Integer, Integer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Get the current question
     * @return current question or null if quiz is completed
     */
    public Question getCurrentQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            return null;
        }
        return questions.get(currentQuestionIndex);
    }

    /**
     * Submit an answer for the current question
     * @param selectedAnswer the selected answer index (0-based)
     * @return true if answer is correct, false otherwise
     */
    public boolean submitAnswer(int selectedAnswer) {
        Question currentQuestion = getCurrentQuestion();
        if (currentQuestion == null) {
            return false;
        }

        // Store the answer
        userAnswers.put(currentQuestion.getId(), selectedAnswer);
        answeredQuestions.add(currentQuestion);

        // Check if answer is correct
        boolean isCorrect = currentQuestion.isCorrect(selectedAnswer);
        if (isCorrect) {
            score += 10; // Award 10 points for correct answer
        }

        // Move to next question
        currentQuestionIndex++;

        // Check if quiz is completed
        if (currentQuestionIndex >= questions.size()) {
            isCompleted = true;
            endTime = new Date();
        }

        return isCorrect;
    }

    /**
     * Get the total number of questions
     * @return total questions count
     */
    public int getTotalQuestions() {
        return questions.size();
    }

    /**
     * Get the number of answered questions
     * @return answered questions count
     */
    public int getAnsweredQuestionsCount() {
        return answeredQuestions.size();
    }

    /**
     * Get the progress percentage
     * @return progress as percentage (0-100)
     */
    public int getProgressPercentage() {
        if (questions.isEmpty()) {
            return 0;
        }
        return (answeredQuestions.size() * 100) / questions.size();
    }

    /**
     * Get the quiz duration in seconds
     * @return duration in seconds
     */
    public long getDurationInSeconds() {
        Date end = endTime != null ? endTime : new Date();
        return (end.getTime() - startTime.getTime()) / 1000;
    }

    /**
     * Get the correct answers count
     * @return number of correct answers
     */
    public int getCorrectAnswersCount() {
        return (int) userAnswers.entrySet().stream()
                .filter(entry -> {
                    Question question = questions.stream()
                            .filter(q -> q.getId() == entry.getKey())
                            .findFirst()
                            .orElse(null);
                    return question != null && question.isCorrect(entry.getValue());
                })
                .count();
    }

    /**
     * Get the accuracy percentage
     * @return accuracy as percentage (0-100)
     */
    public int getAccuracyPercentage() {
        if (answeredQuestions.isEmpty()) {
            return 0;
        }
        return (getCorrectAnswersCount() * 100) / answeredQuestions.size();
    }
}
