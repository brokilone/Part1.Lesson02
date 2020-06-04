package Task15.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author
 * created by Ksenya_Ushakova at 02.06.2020
 */
public interface Author {
    Map<Integer, Article> listOfArticles = new HashMap<>();

    int writeArticle(String title, String content, Article.ArticleAccess access);
    boolean editArticle(int id, String content, Article.ArticleAccess access);
    boolean deleteArticle(int id);

    List<Article> getAllArticles();

}
