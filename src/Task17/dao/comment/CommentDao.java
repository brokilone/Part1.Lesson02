package Task17.dao.comment;


import Task17.model.Article;
import Task17.model.Comment;
import Task17.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


/**
 * CommentDao
 * интерфейс CRUD-операций с комментариями к статьям блога
 *
 * created by Ksenya_Ushakova at 01.06.2020
 */
public interface CommentDao {
    int addComment(Comment comment) throws SQLException;

    Optional<Comment> getCommentById(int id) throws SQLException;

    void updateCommentById(Comment comment) throws SQLException;

    void deleteCommentById(int id) throws SQLException;

    List<Comment> getListOfComments(Article article) throws SQLException;
    List<Comment> getAllComments(User user) throws SQLException;
}
