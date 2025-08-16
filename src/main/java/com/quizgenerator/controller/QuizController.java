package com.quizgenerator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizgenerator.model.Question;
import com.quizgenerator.model.QuizSession;
import com.quizgenerator.service.QuizService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for handling quiz-related HTTP requests
 */
public class QuizController {
    private final QuizService quizService;
    private final ObjectMapper objectMapper;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Handle HTTP requests
     * @param request the HTTP request
     * @param response the HTTP response
     */
    public void handleRequest(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        try {
            switch (method) {
                case "GET":
                    handleGetRequest(exchange, path);
                    break;
                case "POST":
                    handlePostRequest(exchange, path);
                    break;
                default:
                    sendResponse(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            System.err.println("Error handling request: " + e.getMessage());
            sendResponse(exchange, 500, "Internal server error");
        }
    }

    /**
     * Handle GET requests
     */
    private void handleGetRequest(com.sun.net.httpserver.HttpExchange exchange, String path) throws IOException {
        if (path.equals("/") || path.equals("/index.html")) {
            serveStaticFile(exchange, "/index.html", "text/html");
        } else if (path.equals("/css/style.css")) {
            serveStaticFile(exchange, "/css/style.css", "text/css");
        } else if (path.equals("/js/quiz.js")) {
            serveStaticFile(exchange, "/js/quiz.js", "application/javascript");
        } else if (path.startsWith("/api/quiz/")) {
            handleQuizApiGet(exchange, path);
        } else {
            sendResponse(exchange, 404, "Not found");
        }
    }

    /**
     * Handle POST requests
     */
    private void handlePostRequest(com.sun.net.httpserver.HttpExchange exchange, String path) throws IOException {
        if (path.equals("/api/quiz/create")) {
            handleCreateQuiz(exchange);
        } else if (path.equals("/api/quiz/answer")) {
            handleSubmitAnswer(exchange);
        } else if (path.equals("/api/quiz/end")) {
            handleEndQuiz(exchange);
        } else {
            sendResponse(exchange, 404, "Not found");
        }
    }

    /**
     * Handle quiz API GET requests
     */
    private void handleQuizApiGet(com.sun.net.httpserver.HttpExchange exchange, String path) throws IOException {
        String[] parts = path.split("/");
        if (parts.length < 4) {
            sendResponse(exchange, 400, "Invalid path");
            return;
        }

        String sessionId = parts[3];
        String action = parts.length > 4 ? parts[4] : "";

        switch (action) {
            case "question":
                handleGetQuestion(exchange, sessionId);
                break;
            case "stats":
                handleGetStats(exchange, sessionId);
                break;
            case "results":
                handleGetResults(exchange, sessionId);
                break;
            default:
                sendResponse(exchange, 400, "Invalid action");
        }
    }

    /**
     * Handle quiz creation
     */
    private void handleCreateQuiz(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
        // Read request body
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, Object> request = objectMapper.readValue(requestBody, Map.class);
        
        int questionCount = request.containsKey("questionCount") ? 
            (Integer) request.get("questionCount") : 10;

        QuizSession session = quizService.createQuizSession(questionCount);
        
        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getSessionId());
        response.put("totalQuestions", session.getTotalQuestions());
        response.put("firstQuestion", session.getCurrentQuestion());
        
        sendJsonResponse(exchange, 200, response);
    }

    /**
     * Handle answer submission
     */
    private void handleSubmitAnswer(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, Object> request = objectMapper.readValue(requestBody, Map.class);
        
        String sessionId = (String) request.get("sessionId");
        Integer selectedAnswer = (Integer) request.get("selectedAnswer");
        
        if (sessionId == null || selectedAnswer == null) {
            sendResponse(exchange, 400, "Missing sessionId or selectedAnswer");
            return;
        }

        boolean isCorrect = quizService.submitAnswer(sessionId, selectedAnswer);
        QuizSession session = quizService.getSession(sessionId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("isCorrect", isCorrect);
        response.put("nextQuestion", session.getCurrentQuestion());
        response.put("isCompleted", session.isCompleted());
        response.put("score", session.getScore());
        response.put("progress", session.getProgressPercentage());
        
        sendJsonResponse(exchange, 200, response);
    }

    /**
     * Handle quiz ending
     */
    private void handleEndQuiz(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, Object> request = objectMapper.readValue(requestBody, Map.class);
        
        String sessionId = (String) request.get("sessionId");
        
        if (sessionId == null) {
            sendResponse(exchange, 400, "Missing sessionId");
            return;
        }

        boolean ended = quizService.endSession(sessionId);
        String results = quizService.getFinalResults(sessionId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("ended", ended);
        response.put("results", objectMapper.readValue(results, Map.class));
        
        sendJsonResponse(exchange, 200, response);
    }

    /**
     * Handle getting current question
     */
    private void handleGetQuestion(com.sun.net.httpserver.HttpExchange exchange, String sessionId) throws IOException {
        Question question = quizService.getCurrentQuestion(sessionId);
        
        if (question == null) {
            sendResponse(exchange, 404, "Question not found");
            return;
        }
        
        sendJsonResponse(exchange, 200, question);
    }

    /**
     * Handle getting session stats
     */
    private void handleGetStats(com.sun.net.httpserver.HttpExchange exchange, String sessionId) throws IOException {
        String stats = quizService.getSessionStats(sessionId);
        sendResponse(exchange, 200, stats, "application/json");
    }

    /**
     * Handle getting final results
     */
    private void handleGetResults(com.sun.net.httpserver.HttpExchange exchange, String sessionId) throws IOException {
        String results = quizService.getFinalResults(sessionId);
        sendResponse(exchange, 200, results, "application/json");
    }

    /**
     * Serve static files
     */
    private void serveStaticFile(com.sun.net.httpserver.HttpExchange exchange, String resourcePath, String contentType) throws IOException {
        try {
            java.io.InputStream inputStream = getClass().getResourceAsStream("/static" + resourcePath);
            if (inputStream == null) {
                sendResponse(exchange, 404, "File not found");
                return;
            }

            byte[] content = inputStream.readAllBytes();
            inputStream.close();

            exchange.getResponseHeaders().add("Content-Type", contentType);
            exchange.sendResponseHeaders(200, content.length);
            
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(content);
            }
        } catch (Exception e) {
            sendResponse(exchange, 500, "Error serving file");
        }
    }

    /**
     * Send JSON response
     */
    private void sendJsonResponse(com.sun.net.httpserver.HttpExchange exchange, int statusCode, Object data) throws IOException {
        String jsonResponse = objectMapper.writeValueAsString(data);
        sendResponse(exchange, statusCode, jsonResponse, "application/json");
    }

    /**
     * Send text response
     */
    private void sendResponse(com.sun.net.httpserver.HttpExchange exchange, int statusCode, String response) throws IOException {
        sendResponse(exchange, statusCode, response, "text/plain");
    }

    /**
     * Send response with custom content type
     */
    private void sendResponse(com.sun.net.httpserver.HttpExchange exchange, int statusCode, String response, String contentType) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", contentType);
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
