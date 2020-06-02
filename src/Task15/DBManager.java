package Task15;

import java.sql.*;

/**
 * DBManager
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class DBManager {
    public static void renewDataBase(Connection connection){
        Savepoint first = null;
        Statement statement = null;
       try{
           statement = connection.createStatement();
           connection.setAutoCommit(false);
           statement.addBatch("USE my_blog");
           statement.addBatch("DROP TABLE IF EXISTS user_info, article, access_level, comment_info");
           statement.addBatch("CREATE TABLE user_info\n" +
                   "(login VARCHAR(50) PRIMARY KEY NOT NULL,\n" +
                   "password VARCHAR(20) NOT NULL,\n" +
                   "rating INT NOT NULL DEFAULT 0\n" +
                   ");");
           statement.addBatch("INSERT INTO user_info\n" +
                   "VALUES\n" +
                   "('author_of_the_year','qwerty', 100);");
           statement.addBatch("INSERT INTO user_info\n" +
                   "VALUES\n" +
                   "('experienced_progger','hackinganyway', 50);");
           statement.addBatch("INSERT INTO user_info\n" +
                   "VALUES\n" +
                   "('beginner','l0alhf48xkv9', 0);");

           first = connection.setSavepoint("first");

           statement.addBatch("CREATE TABLE article\n" +
                   "(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                   "title VARCHAR(100) NOT NULL,\n" +
                   "content BLOB,\n" +
                   "author VARCHAR(50) NOT NULL,\n" +
                   "access_level ENUM('open','available to list','available to authorized users','available to authors'),\n" +
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
           statement.executeBatch();
           connection.commit();
       }catch (SQLException e){
            e.printStackTrace();
           try {
               connection.rollback(first);
               statement.executeBatch();
               connection.commit();
           } catch (SQLException e1){
               e1.printStackTrace();
           }
       }

    }
}
