package Task15;

import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DBManager
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class DBManager {
    public static void renewDataBase(Connection connection){
       try(Statement statement = connection.createStatement()){

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
           statement.addBatch("CREATE TABLE access_level\n" +
                   "(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                   "description VARCHAR(50)\n" +
                   ");");
           statement.addBatch("INSERT INTO access_level\n" +
                   "(description)\n" +
                   "VALUES\n" +
                   "('open');\n");
           statement.addBatch("INSERT INTO access_level\n" +
                   "(description)\n" +
                   "VALUES\n" +
                   "('available to list');");
           statement.addBatch("INSERT INTO access_level\n" +
                   "(description)\n" +
                   "VALUES\n" +
                   "('available to authorized users');");
           statement.addBatch("INSERT INTO access_level\n" +
                   "(description)\n" +
                   "VALUES\n" +
                   "('available to authors');");
           statement.addBatch("CREATE TABLE article\n" +
                   "(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                   "title VARCHAR(100) NOT NULL,\n" +
                   "content BLOB,\n" +
                   "author VARCHAR(50) NOT NULL,\n" +
                   "access_level INT NOT NULL,\n" +
                   "FOREIGN KEY (author) REFERENCES user_info(login),\n" +
                   "FOREIGN KEY (access_level) REFERENCES access_level(id)\n" +
                   ");");
           statement.addBatch("INSERT INTO article\n" +
                   "(title, content, author, access_level)\n" +
                   "VALUES\n" +
                   "('First article', 'I have a string and I want to find the first \n" +
                   "character which occurs only once, in this string I have 2 characters \n" +
                   "which occurs only once which is a space and a hyphen but the first \n" +
                   "character is space so I want to return that from a method.',\n" +
                   "'beginner', 1);");
           statement.addBatch("CREATE TABLE comment_info\n" +
                   "(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                   "content BLOB NOT NULL,\n" +
                   "author VARCHAR(50) NOT NULL,\n" +
                   "source INT NOT NULL,\n" +
                   "FOREIGN KEY (author) REFERENCES user_info(login),\n" +
                   "FOREIGN KEY (source) REFERENCES article(id)\n" +
                   ");");
           statement.addBatch("INSERT INTO comment_info\n" +
                   "(content, author, source)\n" +
                   "VALUES\n" +
                   "('Author, I wrote about it yesterday', 'experienced_progger', 1);");
           statement.executeBatch();
       }catch (SQLException e){
            e.printStackTrace();
       }

    }

}
