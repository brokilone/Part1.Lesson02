package Task15.Dao.Comment;

import Task15.Model.Article;
import Task15.Model.Comment;
import Task15.Model.User;


/**
 * CommentDao
 * created by Ksenya_Ushakova at 01.06.2020
 */
public interface CommentDao {
    int addComment(Article article, User user, String content);

    Comment getCommentById(int id);

    boolean updateCommentById(Comment comment);

    boolean deleteCommentById(int id);
}
