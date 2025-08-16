package com.quizgenerator.service;

import com.quizgenerator.model.Question;
import java.util.*;

/**
 * Service for managing quiz questions
 */
public class QuestionService {
    private final List<Question> questions;

    public QuestionService() {
        this.questions = initializeQuestions();
    }

    private List<Question> initializeQuestions() {
        List<Question> questionList = new ArrayList<>();
        
        // General Knowledge Questions
        questionList.add(new Question(
            "What is the capital of France?",
            Arrays.asList("London", "Berlin", "Paris", "Madrid"),
            2, // Paris is correct (index 2)
            30
        ));
        
        questionList.add(new Question(
            "Which planet is known as the Red Planet?",
            Arrays.asList("Venus", "Mars", "Jupiter", "Saturn"),
            1, // Mars is correct (index 1)
            30
        ));
        
        questionList.add(new Question(
            "What is the largest ocean on Earth?",
            Arrays.asList("Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"),
            3, // Pacific Ocean is correct (index 3)
            30
        ));
        
        questionList.add(new Question(
            "Who wrote 'Romeo and Juliet'?",
            Arrays.asList("Charles Dickens", "William Shakespeare", "Jane Austen", "Mark Twain"),
            1, // William Shakespeare is correct (index 1)
            30
        ));
        
        questionList.add(new Question(
            "What is the chemical symbol for gold?",
            Arrays.asList("Ag", "Au", "Fe", "Cu"),
            1, // Au is correct (index 1)
            30
        ));
        
        questionList.add(new Question(
            "Which year did World War II end?",
            Arrays.asList("1943", "1944", "1945", "1946"),
            2, // 1945 is correct (index 2)
            30
        ));
        
        questionList.add(new Question(
            "What is the largest mammal in the world?",
            Arrays.asList("African Elephant", "Blue Whale", "Giraffe", "Hippopotamus"),
            1, // Blue Whale is correct (index 1)
            30
        ));
        
        questionList.add(new Question(
            "Which programming language was created by James Gosling?",
            Arrays.asList("Python", "Java", "C++", "JavaScript"),
            1, // Java is correct (index 1)
            30
        ));
        
        questionList.add(new Question(
            "What is the square root of 144?",
            Arrays.asList("10", "11", "12", "13"),
            2, // 12 is correct (index 2)
            30
        ));
        
        questionList.add(new Question(
            "Which country is home to the kangaroo?",
            Arrays.asList("New Zealand", "South Africa", "Australia", "India"),
            2, // Australia is correct (index 2)
            30
        ));
        
        questionList.add(new Question(
            "What is the main component of the sun?",
            Arrays.asList("Liquid Lava", "Molten Iron", "Hydrogen Gas", "Solid Rock"),
            2, // Hydrogen Gas is correct (index 2)
            30
        ));
        
        questionList.add(new Question(
            "How many sides does a hexagon have?",
            Arrays.asList("5", "6", "7", "8"),
            1, // 6 is correct (index 1)
            30
        ));
        
        questionList.add(new Question(
            "Which element has the chemical symbol 'O'?",
            Arrays.asList("Osmium", "Oxygen", "Oganesson", "Osmium"),
            1, // Oxygen is correct (index 1)
            30
        ));
        
        questionList.add(new Question(
            "What is the largest desert in the world?",
            Arrays.asList("Sahara Desert", "Arabian Desert", "Gobi Desert", "Antarctic Desert"),
            0, // Sahara Desert is correct (index 0)
            30
        ));
        
        questionList.add(new Question(
            "Who painted the Mona Lisa?",
            Arrays.asList("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"),
            2, // Leonardo da Vinci is correct (index 2)
            30
        ));
        
        questionList.add(new Question(
            "What is the speed of light in vacuum?",
            Arrays.asList("299,792 km/s", "199,792 km/s", "399,792 km/s", "499,792 km/s"),
            0, // 299,792 km/s is correct (index 0)
            30
        ));
        
        questionList.add(new Question(
            "Which country has the largest population in the world?",
            Arrays.asList("India", "China", "United States", "Russia"),
            1, // China is correct (index 1)
            30
        ));
        
        questionList.add(new Question(
            "What is the smallest prime number?",
            Arrays.asList("0", "1", "2", "3"),
            2, // 2 is correct (index 2)
            30
        ));
        
        questionList.add(new Question(
            "Which planet is closest to the Sun?",
            Arrays.asList("Venus", "Mercury", "Earth", "Mars"),
            1, // Mercury is correct (index 1)
            30
        ));
        
        questionList.add(new Question(
            "What is the capital of Japan?",
            Arrays.asList("Seoul", "Beijing", "Tokyo", "Bangkok"),
            2, // Tokyo is correct (index 2)
            30
        ));
        
        questionList.add(new Question(
            "How many bones are in the human body?",
            Arrays.asList("206", "186", "226", "246"),
            0, // 206 is correct (index 0)
            30
        ));
        
        questionList.add(new Question(
            "What is the largest organ in the human body?",
            Arrays.asList("Heart", "Brain", "Liver", "Skin"),
            3, // Skin is correct (index 3)
            30
        ));
        
        questionList.add(new Question(
            "Which year did the first moon landing occur?",
            Arrays.asList("1967", "1968", "1969", "1970"),
            2, // 1969 is correct (index 2)
            30
        ));
        
        questionList.add(new Question(
            "What is the currency of Japan?",
            Arrays.asList("Yuan", "Won", "Yen", "Ringgit"),
            2, // Yen is correct (index 2)
            30
        ));
        
        questionList.add(new Question(
            "How many continents are there on Earth?",
            Arrays.asList("5", "6", "7", "8"),
            2, // 7 is correct (index 2)
            30
        ));
        
        return questionList;
    }

    public List<Question> getRandomQuestions(int count) {
        if (count > questions.size()) {
            count = questions.size();
        }
        
        List<Question> shuffledQuestions = new ArrayList<>(questions);
        Collections.shuffle(shuffledQuestions);
        return shuffledQuestions.subList(0, count);
    }

    public int getTotalQuestionCount() {
        return questions.size();
    }
}
