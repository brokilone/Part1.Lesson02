package Task15.Model.UserInfo;

import Task15.Model.Article;
import Task15.Model.ArticleAccess;

import java.util.List;

/**
 * Author
 * created by Ksenya_Ushakova at 02.06.2020
 */
public interface Author {

    int writeArticle(String title, String content, ArticleAccess access);
    int writeArticle(String title, String content, ArticleAccess access, String[] logins);
    boolean editArticle(int id, String content, ArticleAccess access);
    boolean deleteArticle(int id);

    List<Article> getAllArticles();

}
