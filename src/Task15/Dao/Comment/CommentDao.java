package Task15.Dao.Comment;

import Task15.Model.Comment;



/**
 * CommentDao
 * created by Ksenya_Ushakova at 01.06.2020
 */
public interface CommentDao {
    int addComment(Comment comment);

    Comment getCommentById(int id);

    boolean updateCommentById(Comment comment);

    boolean deleteCommentById(int id);
}
