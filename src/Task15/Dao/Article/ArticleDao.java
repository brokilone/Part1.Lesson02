package Task15.Dao.Article;

import Task15.Model.Article;

/**
 * ArticleDao
 * created by Ksenya_Ushakova at 01.06.2020
 */
public interface ArticleDao {
    int addArticle(Article article);
    Article getById(int id);
    boolean updateById(Article article);
    boolean deleteById(int id);
}
