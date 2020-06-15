package Task16;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 * DBManager
 * класс реализует сброс и инициализацию БД
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class DBManager {

    private static final Logger logger = LogManager.getLogger(DBManager.class.getName());
    /**
     * Сброс и инициализация БД
     * @param connection соединение с БД
     */

    public static void renewDataBase(Connection connection){
        Savepoint first = null;
        Statement statement = null;
        try{
            logger.info("Try to renew database");
            statement = connection.createStatement();
            connection.setAutoCommit(false);//ручное управление

            //добавляем операции в батч
            statement.addBatch("USE my_blog");
            statement.addBatch("DROP TABLE IF EXISTS user_info, article, access_level, comment_info");
            logger.info("Drop tables...");
            statement.addBatch("CREATE TABLE user_info\n" +
                    "(login VARCHAR(50) PRIMARY KEY NOT NULL,\n" +
                    "password VARCHAR(20) NOT NULL,\n" +
                    "rating INT NOT NULL DEFAULT 0\n" +
                    ");");
            statement.addBatch("INSERT INTO user_info\n" +
                    "VALUES\n" +
                    "('author_of_the_year','qwerty', 100),\n" +
                    "('experienced_progger','hackinganyway', 50), \n" +
                    "('beginner','l0alhf48xkv9', 0);");
            logger.info("Table user_info ...");
            first = connection.setSavepoint("first");//savepoint после создания первой таблицы

            statement.addBatch("CREATE TABLE article\n" +
                    "(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                    "title VARCHAR(100) NOT NULL,\n" +
                    "content BLOB,\n" +
                    "author VARCHAR(50) NOT NULL,\n" +
                    "access_level ENUM('open','available to authors', 'available to list') NOT NULL,\n" +
                    "FOREIGN KEY (author) REFERENCES user_info(login) ON DELETE CASCADE\n" +
                    ");");
            statement.addBatch("INSERT INTO article\n" +
                    "(title, content, author, access_level)\n" +
                    "VALUES\n" +
                    "('First article', 'I have a string and I want to find the first \n" +
                    "character which occurs only once, in this string I have 2 characters \n" +
                    "which occurs only once which is a space and a hyphen but the first \n" +
                    "character is space so I want to return that from a method.',\n" +
                    "'beginner', 'open');");
            logger.info("Table article ...");

            statement.addBatch("CREATE TABLE comment_info\n" +
                    "(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                    "content BLOB NOT NULL,\n" +
                    "author VARCHAR(50) NOT NULL,\n" +
                    "source INT NOT NULL,\n" +
                    "FOREIGN KEY (author) REFERENCES user_info(login) ON DELETE CASCADE,\n" +
                    "FOREIGN KEY (source) REFERENCES article(id) ON DELETE CASCADE\n" +
                    ");");
            statement.addBatch("INSERT INTO comment_info\n" +
                    "(content, author, source)\n" +
                    "VALUES\n" +
                    "('Author, I wrote about it yesterday', 'experienced_progger', 1);");
            logger.info("Table comment_info ...");

            statement.executeBatch();


            connection.commit();//ручное управление
            logger.info("Database is updated");
        }catch (SQLException e){

            logger.error("Database renew fail: {}", e);
            try {
                logger.info("Try to rollback");
                connection.rollback(first); //пытаемся откатиться к точке сохранения
                statement.executeBatch();
                connection.commit();
                logger.info("Rollback done");
            } catch (SQLException e1){

                logger.error("Database renew fail: {}", e1);
            }
        }

    }
}
