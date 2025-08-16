package com.quizgenerator.service;

import com.quizgenerator.model.Question;
import com.quizgenerator.model.QuizSession;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing quiz sessions
 */
public class QuizService {
    private final QuestionService questionService;

    public QuizService(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Create a new quiz session with the specified number of questions
     * @param questionCount number of questions in the quiz
     * @return new quiz session
     */
    public QuizSession createQuizSession(int questionCount) {
        String sessionId = UUID.randomUUID().toString();
        List<Question> questions = questionService.getRandomQuestions(questionCount);
        return new QuizSession(sessionId, questions);
    }

    /**
     * Get a quiz session by ID (for future implementation)
     * @param sessionId session ID
     * @return quiz session or null if not found
     */
    public QuizSession getSession(String sessionId) {
        // For now, return a default session with all questions
        // This could be enhanced to store sessions in memory or database
        return new QuizSession(sessionId, questionService.getRandomQuestions(25));
    }

    /**
     * Get the total number of available questions
     * @return total question count
     */
    public int getTotalQuestionCount() {
        return questionService.getTotalQuestionCount();
    }
}
