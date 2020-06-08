package Task15.Dao.Article;

import Task15.Model.Article;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * ArticleDao
 * интерфейс CRUD-операций со статьями блога
 *
 * created by Ksenya_Ushakova at 01.06.2020
 */
public interface ArticleDao {
    int addArticle(Article article) throws SQLException;
    Optional<Article> getById(int id) throws SQLException;
    void updateById(Article article) throws SQLException;
    void deleteById(int id) throws SQLException;

    CachedRowSet allowedUsers(Article article) throws SQLException;
}
