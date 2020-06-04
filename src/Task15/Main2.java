package Task15;

import Task15.Dao.Article.ArticleDao;
import Task15.Dao.Article.ArticleDaoImpl;
import Task15.Dao.Comment.CommentDao;
import Task15.Dao.Comment.CommentDaoImpl;
import Task15.Dao.User.UserDao;
import Task15.Dao.User.UserDaoImpl;
import Task15.Model.Article;
import Task15.Model.Comment;
import Task15.Model.User;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Main2
 * created by Ksenya_Ushakova at 02.06.2020
 */
public class Main2 {
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    public static void main(String[] args) {
        try (Connection connection = connectionManager.getConnection()) {
            DBManager.renewDataBase(connection);
            UserDao userDao = new UserDaoImpl();
            User spy = new User("unknown_spy", "trytoencrypt", 0);
            String login = userDao.addUser(spy);
            System.out.println("Successfully added user with login: " + login);

            if(spy.isAuthor()){
                String articleText = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                        "Aenean commodo ligula eget dolor. Aenean massa. " +
                        "Cum sociis natoque penatibus et magnis dis parturient montes," +
                        " nascetur ridiculus mus.";
                int id = spy.writeArticle("Lorem", articleText, Article.ArticleAccess.OPEN);
                List<Article> articleList = spy.getAllArticles();
                System.out.println("All articles by " + login);
                for (Article article : articleList) {
                    System.out.println("Title: " + article.getTitle());
                    System.out.println("Text: " + article.getContent());
                }
                boolean edit = spy.editArticle(id, "Never read it", Article.ArticleAccess.AVAILABLE_TO_AUTHORS);
                if (edit) {
                    System.out.println("After edit: ");
                    System.out.println("Text: " + new ArticleDaoImpl().getById(id).getContent());
                }
                boolean delete = spy.deleteArticle(id);
                if(delete){
                    System.out.println("Deleted");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}