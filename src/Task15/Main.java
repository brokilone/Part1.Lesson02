package Task15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Main
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306",
                "root", "npatfb4psdf");){
            DBManager.renewDataBase(connection);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
