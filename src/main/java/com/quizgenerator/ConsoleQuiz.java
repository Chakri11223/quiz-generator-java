package com.quizgenerator;

import com.quizgenerator.model.Question;
import com.quizgenerator.model.QuizSession;
import com.quizgenerator.service.QuestionService;
import com.quizgenerator.service.QuizService;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Console-based version of the Quiz Generator
 */
public class ConsoleQuiz {
    private static final Scanner scanner = new Scanner(System.in);
    private static volatile boolean timeUp = false;

    public static void main(String[] args) {
        System.out.println("🎯 Welcome to Quiz Generator Console Edition!");
        System.out.println("=" .repeat(50));

        QuestionService questionService = new QuestionService();
        QuizService quizService = new QuizService(questionService);

        if (questionService.getTotalQuestionCount() == 0) {
            System.out.println("❌ No questions found. Please check the questions.json file.");
            return;
        }

        System.out.println("📊 Loaded " + questionService.getTotalQuestionCount() + " questions");
        System.out.println();

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Quick Quiz (5 questions)");
            System.out.println("2. Standard Quiz (10 questions)");
            System.out.println("3. Challenge Quiz (15 questions)");
            System.out.println("4. Custom Quiz");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    startQuiz(quizService, 5);
                    break;
                case 2:
                    startQuiz(quizService, 10);
                    break;
                case 3:
                    startQuiz(quizService, 15);
                    break;
                case 4:
                    startCustomQuiz(quizService);
                    break;
                case 5:
                    System.out.println("👋 Thanks for playing! Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
            
            System.out.println();
        }
    }

    private static void startCustomQuiz(QuizService quizService) {
        System.out.print("Enter number of questions (1-" + quizService.getSession(null).getTotalQuestions() + "): ");
        int questionCount = getIntInput();
        
        if (questionCount > 0 && questionCount <= 25) {
            startQuiz(quizService, questionCount);
        } else {
            System.out.println("❌ Invalid number of questions. Please try again.");
        }
    }

    private static void startQuiz(QuizService quizService, int questionCount) {
        System.out.println("\n🚀 Starting quiz with " + questionCount + " questions...");
        System.out.println("⏰ Each question has 30 seconds time limit.");
        System.out.println("📝 Press Enter when ready to start...");
        scanner.nextLine(); // Clear buffer
        scanner.nextLine(); // Wait for Enter

        QuizSession session = quizService.createQuizSession(questionCount);
        System.out.println("✅ Quiz session created: " + session.getSessionId());
        System.out.println();

        int questionNumber = 1;
        while (!session.isCompleted()) {
            Question currentQuestion = session.getCurrentQuestion();
            if (currentQuestion == null) break;

            System.out.println("Question " + questionNumber + " of " + session.getTotalQuestions());
            System.out.println("Score: " + session.getScore());
            System.out.println("Time remaining: " + currentQuestion.getTimeLimit() + " seconds");
            System.out.println("-".repeat(50));
            System.out.println(currentQuestion.getQuestion());
            System.out.println();

            // Display options
            for (int i = 0; i < currentQuestion.getOptions().size(); i++) {
                System.out.println((i + 1) + ". " + currentQuestion.getOptions().get(i));
            }
            System.out.println();

            // Start timer with a new executor for each question
            timeUp = false;
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.schedule(() -> {
                timeUp = true;
                System.out.println("\n⏰ Time's up!");
            }, currentQuestion.getTimeLimit(), TimeUnit.SECONDS);

            // Get user input
            System.out.print("Enter your answer (1-" + currentQuestion.getOptions().size() + "): ");
            int userAnswer = getIntInput() - 1; // Convert to 0-based index

            // Cancel timer and shutdown executor
            executor.shutdownNow();

            if (timeUp) {
                System.out.println("⏰ Time ran out! No points awarded.");
                userAnswer = -1; // Invalid answer
            } else if (userAnswer >= 0 && userAnswer < currentQuestion.getOptions().size()) {
                boolean isCorrect = session.submitAnswer(userAnswer);
                if (isCorrect) {
                    System.out.println("✅ Correct! +10 points");
                } else {
                    System.out.println("❌ Incorrect. The correct answer was: " + 
                        (currentQuestion.getCorrectAnswer() + 1));
                }
            } else {
                System.out.println("❌ Invalid answer. No points awarded.");
            }

            System.out.println("Current score: " + session.getScore());
            System.out.println();
            questionNumber++;
        }

        // Show final results
        showFinalResults(session);
    }

    private static void showFinalResults(QuizSession session) {
        System.out.println("🎉 Quiz completed!");
        System.out.println("=" .repeat(50));
        System.out.println("Final Results:");
        System.out.println("Total Questions: " + session.getTotalQuestions());
        System.out.println("Correct Answers: " + session.getCorrectAnswersCount());
        System.out.println("Final Score: " + session.getScore());
        System.out.println("Accuracy: " + session.getAccuracyPercentage() + "%");
        System.out.println("Total Time: " + session.getDurationInSeconds() + " seconds");
        System.out.println("=" .repeat(50));

        // Performance feedback
        int accuracy = session.getAccuracyPercentage();
        if (accuracy >= 90) {
            System.out.println("🏆 Excellent! Outstanding performance!");
        } else if (accuracy >= 80) {
            System.out.println("🥇 Great job! Well done!");
        } else if (accuracy >= 70) {
            System.out.println("🥈 Good work! Keep it up!");
        } else if (accuracy >= 60) {
            System.out.println("🥉 Not bad! Room for improvement.");
        } else {
            System.out.println("📚 Keep studying! Practice makes perfect!");
        }
        System.out.println();
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("❌ Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
