package Task15.Model.UserInfo;

import Task15.Model.Article;

import java.sql.SQLException;
import java.util.List;

/**
 * Commentator
 * интерфейс комментатора статей
 * created by Ksenya_Ushakova at 02.06.2020
 */
public interface Commentator {


    int writeComment(Article article, String content) throws SQLException;
    void editComment(int id, String content) throws SQLException;
    void deleteComment(int id) throws SQLException;
    void rateComment(int id, boolean up) throws SQLException;

    List<Comment> getAllComments() throws SQLException;

}
