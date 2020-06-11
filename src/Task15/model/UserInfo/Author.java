package Task15.model.UserInfo;

import Task15.model.Article;
import Task15.model.ArticleAccess;

import java.sql.SQLException;
import java.util.List;

/**
 * Author
 * интерфейс автора статей
 * created by Ksenya_Ushakova at 02.06.2020
 */
public interface Author {

    int writeArticle(String title, String content, ArticleAccess access) throws SQLException;
    void editArticle(int id, String content, ArticleAccess access) throws SQLException;
    void deleteArticle(int id) throws SQLException;

    List<Article> getAllArticles() throws SQLException;

}
