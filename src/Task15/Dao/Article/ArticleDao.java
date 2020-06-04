package Task15.Dao.Article;

import Task15.Model.Article;
import Task15.Model.UserInfo.Comment;

import java.sql.SQLException;
import java.util.List;

/**
 * ArticleDao
 * интерфейс CRUD-операций со статьями блога
 *
 * created by Ksenya_Ushakova at 01.06.2020
 */
public interface ArticleDao {
    int addArticle(Article article) throws SQLException;
    Article getById(int id) throws SQLException;
    boolean updateById(Article article);
    boolean deleteById(int id);
    List<Comment> getListOfComments(Article article);
}
