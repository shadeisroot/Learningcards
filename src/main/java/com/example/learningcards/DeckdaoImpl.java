package com.example.learningcards;

import javafx.collections.ObservableList;

import java.sql.*;

public class DeckdaoImpl implements Deckdao {
    private Connection con;

    public DeckdaoImpl() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://10.176.111.34:1433;database=Dennisexams1;userName=CSe2023t_t_2;password=CSe2023tT2#23;encrypt=true;trustServerCertificate=true");
            System.out.println("Connected");

        } catch (SQLException e) {
            System.out.println("Cant connect to Database" + e);
        }
    }


    public void saveDeck(Deck deck) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO decks VALUES(?, ?);");
            ps.setString(1, deck.getName());
            ps.setInt(2, deck.getCards());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllDecks(ObservableList<Deck> deckdata) {
        deckdata.clear();
        int antal = 0;
        try {
            Statement database = con.createStatement();
            String sql = "SELECT * FROM decks";
            ResultSet rs = database.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("deck_name");
                int cards = rs.getInt("cards");
                Deck deck = new Deck(name, cards);
                deckdata.add(deck);
                ++antal;
            }
        } catch (SQLException e) {
            System.err.println("Kan ikke læse records" + e);
        }
    }

    public int getdeckidfromname(String deckname){
        int id = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT deck_id FROM decks WHERE deck_name = ?");
            preparedStatement.setString(1, deckname);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("deck_id");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public void deleteDeck(String deckname) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM decks WHERE deck_name = ? ");
            ps.setString(1, deckname);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updatecardcount(int cards, int id){
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "UPDATE decks SET cards = ? WHERE deck_id = ?"
            );
            preparedStatement.setInt(1, cards);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your application's error handling strategy
        }

    }
}