package com.example.learningcards;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlashcardsdaoImpl implements Flashcardsdao{

    private Connection con;

    public FlashcardsdaoImpl() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://10.176.111.34:1433;database=Dennisexams1;userName=CSe2023t_t_2;password=CSe2023tT2#23;encrypt=true;trustServerCertificate=true");
            System.out.println("Connected");

        } catch (SQLException e) {
            System.out.println("Cant connect to Database" + e);
        }
    }


    public void saveFlashcard(Flashcard flashcard) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO flashcards VALUES(?,?,?);");
            ps.setString(1, flashcard.getQuestion());
            ps.setString(2, flashcard.getAnswer());
            ps.setInt(3, flashcard.getDeck_id());
            ps.executeUpdate();
            System.out.println(("Card Created"));
        } catch (SQLException e) {
            System.out.println("Cannot Add card" + e);
        }
    }



    public List<Flashcard> getFlashcardsByDeckId(int deckId) {
        List<Flashcard> flashcards = new ArrayList<>();

        try{
            String sql = "SELECT * FROM flashcards WHERE deck_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, deckId);

            ResultSet rs = preparedStatement.executeQuery();

            // Retrieve flashcard data from the ResultSet and add to the list
            while (rs.next()) {
                String question = rs.getString("question");
                String answer = rs.getString("answer");
                // Assuming your Flashcard class has a constructor taking question, answer, and deckId
                flashcards.add(new Flashcard(question, answer, deckId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return flashcards;
    }

    @Override
    public void deleteFlashcard(String question) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM flashcards WHERE question = ? ");
            ps.setString(1, question);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void deleteFlashcardbydeckid(int deck_id) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM flashcards WHERE deck_id = ? ");
            ps.setInt(1, deck_id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    }
