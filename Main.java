/**
 * This code is free software; you can redistribute it and/or modify it
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY
 */

package application;
import databasePart1.DatabaseHelper;
import java.util.List;

/**
 * This class consists exclusively of static methods that execute tests on
 * our database and verify whether they pass or fail. It automates the
 * process of inserting, updating, and retrieving data, ensuring that
 * the database operations function correctly.
 *
 * @author Zachary Pines
 */

public class Main {

    static int numPassed = 0;	// Counter of the number of passed tests
    static int numFailed = 0;	// Counter of the number of failed tests
    static int questionId;

    public static void main(String[] args) {
        DatabaseHelper db = new DatabaseHelper();
        try {
            db.connectToDatabase();
            db.clearQuestions();
            db.clearAnswers();

            System.out.println("______________________________________");
            System.out.println("\nTesting Automation");

            /************** Test cases semi-automation report header **************/
            /************** Start of the test cases **************/

            performTestCase(1, "Does pineapple belong on pizza?", db);
            performTestCase(2, "What is the meaning of life?", db);
            performTestCase(4, "Nobody knows the meaning of life.", db);
            performTestCase(5, "The answer is 42.", db);
            performTestCase(7, "", db);

            /************** End of the test cases **************/
            /************** Test cases semi-automation report footer **************/

            System.out.println("____________________________________________________________________________");
            System.out.println();
            System.out.println("Number of tests passed: "+ numPassed);
            System.out.println("Number of tests failed: "+ numFailed);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void performTestCase(int testCase, String inputText, DatabaseHelper db) throws Exception {

        /**
         * Executes a specific test case based on the given test ID.
         * @param testCase   The test case ID (1, 2, 4, 5, or 7).
         * @param inputText  The input text for the test case.
         * @param db         The DatabaseHelper instance used for database operations.
         * @throws Exception If an error occurs during database operations.
        */

        // Test 1: Create & Read a Question
        if (testCase == 1) {

            db.createQuestion(1, inputText);
            List<String[]> questions = db.getAllQuestions();
            questionId = Integer.parseInt(questions.get(0)[0]); // (For future recall)

            // Test is passed if there is 1 question and it has the text we expect
            if (questions.size() == 1 && questions.get(0)[2].equals("Does pineapple belong on pizza?")) {
                System.out.println("Test 1 Passed: Question created and read");
                numPassed++;
            } else {
                System.out.println("Test 1 Failed: Question not created / Question not read");
                numFailed++;
            }
        }

        // Test 2: Edit a Question
        if (testCase == 2) {

            db.updateQuestion(questionId, inputText);
            List<String[]> questions = db.getAllQuestions();

            // Test is passed if there is 1 question and it has the new text we expect
            if (questions.size() == 1 && questions.get(0)[2].equals("What is the meaning of life?")) {
                System.out.println("Test 2 Passed: Question updated.");
                numPassed++;
            } else {
                System.out.println("Test 2 Failed: Question not updated.");
                numFailed++;
            }
        }

        // Test 4: Create & Read an Answer
        if (testCase == 4) {

            db.createAnswer(questionId, 2, inputText);
            List<Answer> answers = db.getAnswersForQuestion(questionId);
            Answer answer = answers.get(0);

            // Test is passed if there is 1 answer and it has the text we expect
            if (answers.size() == 1 && answer.answerTextProperty().get().equals("Nobody knows the meaning of life.")) {
                System.out.println("Test 4 Passed: Answer created and read.");
                numPassed++;
            } else {
                System.out.println("Test 4 Failed: Answer not created / Answer not read");
                numFailed++;
            }
        }

        // Test 5: Update an Answer
        if (testCase == 5) {

            List<Answer> answers = db.getAnswersForQuestion(questionId);
            db.updateAnswer(answers.get(0).getId(), inputText);
            answers = db.getAnswersForQuestion(questionId);
            Answer answer = answers.get(0);

            // Test is passed if there is 1 answer and it has the new text we expect
            if (answers.size() == 1 && answer.answerTextProperty().get().equals("The answer is 42.")) {
                System.out.println("Test 5 Passed: Answer updated successfully.");
                numPassed++;
            } else {
                System.out.println("Test 5 Failed: Answer not updated.");
                numFailed++;
            }
        }

        // Test 7: Mark an Answer as Accepted
        if (testCase == 7) {

            List<Answer> answers = db.getAnswersForQuestion(questionId);
            answers = db.getAnswersForQuestion(questionId);
            db.markAnswerAsAccepted(answers.get(0).getId());
            answers = db.getAnswersForQuestion(questionId);
            Answer answer = answers.get(0);

            // Test is passed if our answer is marked accepted
            if (answer.isAcceptedProperty().get().equals("Accepted")) {
                System.out.println("Test 7 Passed: Answer marked as accepted.");
                numPassed++;
            } else {
                System.out.println("Test 7 Failed: Answer not accepted.");
                numFailed++;
            }
        }
    }
}
