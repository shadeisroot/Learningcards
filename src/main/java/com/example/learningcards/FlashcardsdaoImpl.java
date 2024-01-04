package com.example.learningcards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
