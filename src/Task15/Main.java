package Task15;

import Task15.Dao.Article.ArticleDao;
import Task15.Dao.Article.ArticleDaoImpl;
import Task15.Dao.Comment.CommentDao;
import Task15.Dao.Comment.CommentDaoImpl;
import Task15.Dao.User.UserDao;
import Task15.Dao.User.UserDaoImpl;
import Task15.Model.Article;
import Task15.Model.ArticleAccess;
import Task15.Model.BlogException.*;
import Task15.Model.UserInfo.Comment;
import Task15.Model.UserInfo.User;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * Main
 * проверка работы созданных классов для взаимодействия с БД my_blog
 * <p>
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class Main {
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    public static void main(String[] args) {
        try (Connection connection = connectionManager.getConnection()) {

            DBManager.renewDataBase(connection);//сброс и инициализация БД

            testUserDaoImpl();

            testArticleDaoImpl();

            testCommentDaoImpl();

            testAuthorInterface();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (AccessBlogException nfe) {
            nfe.printStackTrace();
            System.err.println(nfe.getMessage());
        }
    }

    /**
     * Проверка CRUD-операций с пользователем
     *
     * @throws SQLException
     */
    private static void testUserDaoImpl() throws SQLException {
        UserDao impl = new UserDaoImpl();

        //поиск по логину
        User user = impl.getByLogin("experienced_progger")
                .orElseThrow(() -> new UserNotFoundException("Error: user not found or deleted"));
        System.out.println("Get data about experienced_progger:");
        System.out.println(user);

        //добавление нового юзера
        String login = impl.addUser(new User("unknown_spy", "neverguess", -10));
        System.out.println("Add new user with login: " + login);

        //удаление по логину
        impl.deleteByLogin("unknown_spy");
        System.out.println("Successfully deleted user with login 'unknown_spy'");

        //обновление рейтинга по логину
        impl.updateByLogin(new User("beginner", "needchange", 20));

        System.out.println("Successfully updated user with login 'beginner'");
        System.out.println("Current rating: " + impl.getByLogin("beginner")
                .orElseThrow(() -> new UserNotFoundException("Error: user not found or deleted"))
                .getRating());

        System.out.println("-----------------------------------------------------");
    }

    /**
     * Проверка CRUD-операций со статьями
     *
     * @throws SQLException
     */
    private static void testArticleDaoImpl() throws SQLException {
        ArticleDao impl = new ArticleDaoImpl();

        //получаем статью по id
        Article article = impl.getById(1).orElseThrow(() ->
                new ArticleNotFoundException("Error: article not found or deleted"));
        System.out.println("Get data about first article");
        System.out.println(article);

        //заносим в БД новую стать.
        Article article2 = new Article(0, "Second article", "I don't know how to write articles",
                article.getAuthor(), ArticleAccess.AVAILABLE_TO_AUTHORS);
        int id = impl.addArticle(article2);
        article2.setId(id);
        System.out.println("Add new article with id = " + id);

        //удаляем статью по id
        impl.deleteById(2);
        System.out.println("Successfully deleted article with id = 2");

        //редактируем статью в БД (меняем доступ)
        article.setAccess(ArticleAccess.AVAILABLE_TO_LIST);
        impl.updateById(article);
        System.out.println("Successfully updated article with id = 1");
        System.out.println("Current access level: " + impl.getById(1)
                .orElseThrow(() -> new ArticleNotFoundException("Error: article not found or deleted"))
                .getAccess());

        System.out.println("-----------------------------------------------------");

    }


    /**
     * Проверка CRUD-операций с комментариямив  БД
     *
     * @throws SQLException
     */
    private static void testCommentDaoImpl() throws SQLException {
        CommentDao impl = new CommentDaoImpl();

        //получение комментария по id
        Comment comment = impl.getCommentById(1)
                .orElseThrow(() -> new CommentNotFoundException("Error: comment not found or deleted"));
        System.out.println("Get data about first comment:");
        System.out.println(comment);

        //добавление нового коммента в БД
        Comment comment2 = new Comment(0, "Where is moderator?", new ArticleDaoImpl().getById(1).
                orElseThrow(() -> new ArticleNotFoundException("Arror: article not found or deleted")),
                new UserDaoImpl().getByLogin("author_of_the_year")
                        .orElseThrow(() -> new UserNotFoundException("Error: user not found or deleted")));
        int id = impl.addComment(comment2);
        comment2.setId(id);
        System.out.println("Add new comment with id = " + id);

        //удаление комментария по id
        impl.deleteCommentById(1);
        System.out.println("Successfully deleted comment with id = 1");

        //изменение комментария (меняем текст)
        comment2.setContent("Boring...");
        impl.updateCommentById(comment2);
        System.out.println("Successfully updated comment with id = 2");
        System.out.println("Current text: " + impl.getCommentById(2)
                .orElseThrow(() -> new CommentNotFoundException("Error: comment not found or deleted"))
                .getContent());

        System.out.println("-----------------------------------------------------");

    }

    /**
     * Проверка методов, которые юзер получает, реализуя интерфейс автора
     *
     * @throws SQLException
     */
    private static void testAuthorInterface() throws SQLException {

        //добавляем пользователя с неотрицательным рейтингом
        User indus = new User("indian_progger", "trytoencrypt", 0);
        UserDao userDao = new UserDaoImpl();
        String login = userDao.addUser(indus);
        System.out.println("Successfully added user with login: " + login);

        //добавляем на сайт новую статью от пользователя
        String articleText = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                "Aenean commodo ligula eget dolor. Aenean massa. " +
                "Cum sociis natoque penatibus et magnis dis parturient montes," +
                " nascetur ridiculus mus.";
        int id = indus.writeArticle("Lorem", articleText, ArticleAccess.OPEN);
        //получим все статьи автора
        List<Article> articleList = indus.getAllArticles();
        System.out.println("All articles by " + login);
        for (Article article : articleList) {
            System.out.println("Title: " + article.getTitle());
            System.out.println("Text: " + article.getContent());
        }

        //юзер пишет комментарий
        int commentId = indus.writeComment(new ArticleDaoImpl().getById(id)
                        .orElseThrow(() -> new ArticleNotFoundException("Error: article not found or deleted")),
                "Did anybody read it?");

        //получаем из базы другого юзера, вызываем ему метод оценки комментария и "минусуем" коммент
        User experienced = userDao.getByLogin("experienced_progger")
                .orElseThrow(() -> new UserNotFoundException("Error: user not found or deleted"));
        experienced.rateComment(commentId, false);

        //редактирование текста - будет ошибка. У юзера теперь отрицательный рейтинг
        indus.editArticle(id, "Never read it", ArticleAccess.AVAILABLE_TO_AUTHORS);

        System.out.println("After edit: ");
        System.out.println("Text: " + new ArticleDaoImpl().getById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Error: article not found or deleted"))
                .getContent());

        System.out.println("-----------------------------------------------------");

    }

}
