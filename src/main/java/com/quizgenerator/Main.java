package com.quizgenerator;

import com.quizgenerator.controller.QuizController;
import com.quizgenerator.service.QuestionService;
import com.quizgenerator.service.QuizService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Main application class for the Quiz Generator with Timer
 */
public class Main {
    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        System.out.println("Starting Quiz Generator with Timer...");
        
        try {
            // Initialize services
            QuestionService questionService = new QuestionService();
            QuizService quizService = new QuizService(questionService);
            QuizController quizController = new QuizController(quizService);

            // Create HTTP server
            HttpServer server = HttpServer.create(new InetSocketAddress(HOST, PORT), 0);
            
            // Set up routes
            server.createContext("/", exchange -> {
                try {
                    quizController.handleRequest(exchange);
                } catch (Exception e) {
                    System.err.println("Error handling request: " + e.getMessage());
                    exchange.sendResponseHeaders(500, 0);
                    exchange.close();
                }
            });

            // Set thread pool
            server.setExecutor(Executors.newFixedThreadPool(10));

            // Start server
            server.start();
            
            System.out.println("✅ Quiz Generator with Timer is running!");
            System.out.println("🌐 Server URL: http://" + HOST + ":" + PORT);
            System.out.println("📊 Loaded " + questionService.getTotalQuestionCount() + " questions");
            System.out.println("⏰ Press Ctrl+C to stop the server");
            System.out.println("=" .repeat(50));

            // Add shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\n🛑 Shutting down Quiz Generator...");
                server.stop(0);
                System.out.println("✅ Server stopped successfully!");
            }));

        } catch (IOException e) {
            System.err.println("❌ Failed to start server: " + e.getMessage());
            System.exit(1);
        }
    }
}
