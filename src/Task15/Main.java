package Task15;

import Task15.Dao.Article.ArticleDao;
import Task15.Dao.Article.ArticleDaoImpl;
import Task15.Dao.Comment.CommentDao;
import Task15.Dao.Comment.CommentDaoImpl;
import Task15.Dao.User.UserDaoImpl;
import Task15.Model.Article;
import Task15.Model.Comment;
import Task15.Model.User;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Main
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class Main {
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();
    public static void main(String[] args) {
        try (Connection connection = connectionManager.getConnection()){
            DBManager.renewDataBase(connection);

            testUserDaoImpl();

            testArticleDaoImpl();

            testCommentDaoImpl();


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void testCommentDaoImpl() {
        CommentDao impl = new CommentDaoImpl();
        Comment comment = impl.getCommentById(1);
        System.out.println("Get data about the first comment:");
        System.out.println("Id: " + comment.getId());
        System.out.println("Source: " + comment.getSource().getTitle());
        System.out.println("Author: " + comment.getAuthor().getLogin());
        System.out.println("Text: " + comment.getContent());

        Comment comment2 = new Comment(0, "Where is moderator?", new ArticleDaoImpl().getById(1),
                new UserDaoImpl().getByLogin("author_of_the_year"));
        int id = impl.addComment(comment2);
        comment2.setId(id);
        System.out.println("Add new comment with id = " + id);

        boolean deleteFirst = impl.deleteCommentById(1);
        if(deleteFirst){
            System.out.println("Successfully deleted comment with id = 1");
        }
        comment2.setContent("Boring...");
        boolean updateSecond = impl.updateCommentById(comment2);
        if (updateSecond){
            System.out.println("Successfully updated comment with id = 2");
            System.out.println("Current text: " + impl.getCommentById(2).getContent());
        }

    }
    private static void testArticleDaoImpl() {
        ArticleDao impl = new ArticleDaoImpl();
        Article article = impl.getById(1);
        System.out.println("Get data about the first article:");
        System.out.println("Id: " + article.getId());
        System.out.println("Title:" + article.getTitle());
        System.out.println("Text:" + article.getContent());
        System.out.println("Author:" + article.getAuthor().getLogin());

        Article article2 = new Article(0, "Second article", "I don't know how to write articles",
                article.getAuthor(), Article.ArticleAccess.AVAILABLE_TO_AUTHORS);
        int id = impl.addArticle(article2);
        article2.setId(id);
        System.out.println("Add new article with id = " + id);

        boolean deleteSecond = impl.deleteById(2);
        if (deleteSecond) {
            System.out.println("Successfully deleted article with id = 2");
        }

        article.setAccess(Article.ArticleAccess.AVAILABLE_TO_AUTHORIZED_USERS);
        boolean updateFirst = impl.updateById(article);
        if (updateFirst){
            System.out.println("Successfully updated article with id = 1");
            System.out.println("Current access level: " + impl.getById(1).getAccess());
        }
        System.out.println("-----------------------------------------------------");

    }
    private static void testUserDaoImpl() {
        UserDaoImpl impl = new UserDaoImpl();
        User user =  impl.getByLogin("experienced_progger");
        System.out.println("Get data about experienced_progger:");
        System.out.println("login: " + user.getLogin());
        System.out.println("password: " + user.getPassword());
        System.out.println("rating: " + user.getRating());

        String login = impl.addUser(new User("unknown_spy", "neverguess", -10));
        System.out.println("Add new user with login: " + login);

        boolean deleteSpy = impl.deleteByLogin("unknown_spy");
        if (deleteSpy) {
            System.out.println("Successfully deleted user with login 'unknown_spy'");
        }

        boolean updateBeginner = impl.updateByLogin(new User("beginner", "needchange", 20));
        if(updateBeginner){
            System.out.println("Successfully updated user with login 'beginner'");
            System.out.println("Current rating: " + impl.getByLogin("beginner").getRating());
        }
        System.out.println("-----------------------------------------------------");
    }

    public static List<User> getAllUsers(){
        return null;
    }

    public static List<User> getOnlineUsers(){
        return null;
    }

    public static List<User> getAllAuthors(){
        return null;
    }
}
