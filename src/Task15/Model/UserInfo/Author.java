package Task15.Model.UserInfo;

import Task15.Model.Article;
import Task15.Model.ArticleAccess;

import java.sql.SQLException;
import java.util.List;

/**
 * Author
 * интерфейс автора статей
 * created by Ksenya_Ushakova at 02.06.2020
 */
public interface Author {

    int writeArticle(String title, String content, ArticleAccess access) throws SQLException;
    int writeArticle(String title, String content, ArticleAccess access, String[] logins) throws SQLException;
    void editArticle(int id, String content, ArticleAccess access) throws SQLException;
    void deleteArticle(int id) throws SQLException;

    List<Article> getAllArticles() throws SQLException;

}
