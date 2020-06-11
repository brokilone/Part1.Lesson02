package Task15.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectionManagerJdbcImpl
 * класс реализует интерфейс ConnectionManager
 * для получения соединения к БД
 */
public class ConnectionManagerJdbcImpl implements ConnectionManager {

    public static ConnectionManager INSTANCE;

    private ConnectionManagerJdbcImpl() {
    }

    /**
     * Возвращает единственный экземпляр ConnectionManager
     * @return ConnectionManager
     */
    public static ConnectionManager getInstance() {
        if (INSTANCE == null){
            INSTANCE = new ConnectionManagerJdbcImpl();

        }
        return INSTANCE;
    }

    /**
     * Метод получает соединение с сервером MySql
     * и инициализирует работу с БД my_blog
     * @return
     */
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
