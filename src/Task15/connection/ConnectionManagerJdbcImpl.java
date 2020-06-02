package Task15.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerJdbcImpl implements ConnectionManager {

    public static ConnectionManager INSTANCE;

    private ConnectionManagerJdbcImpl() {
    }

    public static ConnectionManager getInstance() {
        if (INSTANCE == null){
            INSTANCE = new ConnectionManagerJdbcImpl();

        }
        return INSTANCE;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306",
                    "root",
                    "npatfb4psdf");
            connection.createStatement().execute("USE my_blog");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
