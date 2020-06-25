package Task17.connection;

import java.sql.Connection;

/**
 * ConnectionManager
 * интерфейс получения соединения с БД
 * created by Ksenya_Ushakova at 31.05.2020
 */
public interface ConnectionManager {
    Connection getConnection();
}
