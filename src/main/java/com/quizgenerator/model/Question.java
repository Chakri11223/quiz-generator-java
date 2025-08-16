package com.quizgenerator.model;

import java.util.List;

/**
 * Represents a quiz question with multiple choice options
 */
public class Question {
    private int id;
    private String question;
    private List<String> options;
    private int correctAnswer;
    private int timeLimit;

    // Default constructor for JSON deserialization
    public Question() {}

    public Question(int id, String question, List<String> options, int correctAnswer, int timeLimit) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.timeLimit = timeLimit;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     * Check if the given answer is correct
     * @param selectedAnswer the selected answer index (0-based)
     * @return true if correct, false otherwise
     */
    public boolean isCorrect(int selectedAnswer) {
        return selectedAnswer == correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", options=" + options +
                ", correctAnswer=" + correctAnswer +
                ", timeLimit=" + timeLimit +
                '}';
    }
}
