package Task17;

import Task17.connection.ConnectionManager;
import Task17.connection.ConnectionManagerJdbcImpl;

import Task17.dao.comment.CommentDaoImpl;
import Task17.dao.user.UserDaoImpl;
import Task17.dao.article.ArticleDaoImpl;
import Task17.model.Article;
import Task17.model.ArticleAccess;
import Task17.model.Comment;
import Task17.model.User;
import Task17.model.blogException.AccessBlogException;
import Task17.model.blogException.ArticleNotFoundException;
import Task17.model.blogException.CommentNotFoundException;
import Task17.model.blogException.UserNotFoundException;


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


            UserDaoImpl userDao = new UserDaoImpl(connection);

            ArticleDaoImpl articleDao = new ArticleDaoImpl(connection, userDao);
            testArticleDaoImpl(articleDao);

            CommentDaoImpl commentDao = new CommentDaoImpl(connection, articleDao);
            testCommentDaoImpl(commentDao);

            testFunctionality(userDao, articleDao, commentDao);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (AccessBlogException nfe) {
            System.err.println(nfe.getMessage());
        }
    }

    /**
     * Проверка CRUD-операций с пользователем
     *
     * @param userDao - UserDaoImpl
     * @throws SQLException
     */
    private static void testUserDaoImpl(UserDaoImpl userDao) throws SQLException {

        //поиск по логину
        String login = "experienced_progger";
        User user = userDao.getByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Error: user not found or deleted"));
        System.out.println("Get data about experienced_progger:");
        System.out.println(user);

        //добавление нового юзера
        login = "unknown_spy";
        String password = "neverguess";
        int rating = -10;

        login = userDao.addUser(new User(login, password, rating));
        System.out.println("Add new user with login: " + login);

        //удаление по логину

        userDao.deleteByLogin(login);
        System.out.println("Successfully deleted user with login 'unknown_spy'");


        //обновление рейтинга по логину
        login = "beginner";
        password = "needchange";
        rating = 20;

        userDao.updateByLogin(new User(login, password, rating));
        System.out.println("Successfully updated user with login 'beginner'");

    }

    /**
     * Проверка CRUD-операций со статьями
     *
     * @param articleDao - ArticleDaoImpl
     * @throws SQLException
     */
    private static void testArticleDaoImpl(ArticleDaoImpl articleDao) throws SQLException {

        //получаем статью по id
        int id = 1;

        Article article = articleDao.getById(id).orElseThrow(() ->
                new ArticleNotFoundException("Error: article not found or deleted"));
        System.out.println("Get data about first article");
        System.out.println(article);


        //заносим в БД новую статью
        String title = "Second article";
        String content = "I don't know how to write articles";
        User author = article.getAuthor();
        ArticleAccess access = ArticleAccess.AVAILABLE_TO_AUTHORS;

        Article article2 = new Article(0, title, content, author, access);
        id = articleDao.addArticle(article2);
        article2.setId(id);
        System.out.println("Add new article with id = " + id);

        //удаляем статью по id
        id = 2;

        articleDao.deleteById(id);
        System.out.println("Successfully deleted article with id = 2");


        //редактируем статью в БД (меняем доступ)
        id = 1;
        access = ArticleAccess.AVAILABLE_TO_AUTHORS;

        article.setAccess(access);
        articleDao.updateById(article);

        access = articleDao.getById(1)
                .orElseThrow(() -> new ArticleNotFoundException("Error: article not found or deleted"))
                .getAccess();
        System.out.println("Successfully updated article with id = 1");
        System.out.println("Current access level: " + articleDao.getById(1)
                .orElseThrow(() -> new Task15.model.BlogException.ArticleNotFoundException("Error: article not found or deleted"))
                .getAccess());

    }


    /**
     * Проверка CRUD-операций с комментариямив  БД
     *
     * @param commentDao - CommentDaoImpl
     * @throws SQLException
     */
    private static void testCommentDaoImpl(CommentDaoImpl commentDao) throws SQLException {

        //получение комментария по id
        int id = 1;

        Comment comment = commentDao.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Error: comment not found or deleted"));
        System.out.println("Get data about first comment:");
        System.out.println(comment);

        //добавление нового коммента в БД
        String content = "Where is moderator?";

        Article article = commentDao.getArticleDao().getById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Arror: article not found or deleted"));

        String login = "author_of_the_year";

        User author = commentDao.getArticleDao().getUserDao().getByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Error: user not found or deleted"));

        Comment comment2 = new Comment(0, content, article, author);
        id = commentDao.addComment(comment2);
        comment2.setId(id);
        System.out.println("Add new comment with id = " + id);

        //удаление комментария по id
        id = 1;

        commentDao.deleteCommentById(id);
        System.out.println("Successfully deleted comment with id = 1");


        //изменение комментария (меняем текст)
        id = 2;

        comment2.setContent("Boring...");
        commentDao.updateCommentById(comment2);

        content = commentDao.getCommentById(2)
                .orElseThrow(() -> new CommentNotFoundException("Error: comment not found or deleted"))
                .getContent();
        System.out.println("Successfully updated comment with id = 2");
        System.out.println("Current text: " + content);

    }

    /**
     * Проверка методов, которые юзер получает, реализуя интерфейс автора
     *
     * @throws SQLException
     */
    private static void testFunctionality(UserDaoImpl userDao, ArticleDaoImpl articleDao, CommentDaoImpl commentDao) throws SQLException {

        //добавляем пользователя с неотрицательным рейтингом
        String login = "indian_progger";
        String pass = "trytoencrypt";
        int rating = 0;
        User indus = new User(login, pass, rating);
        login = userDao.addUser(indus);
        System.out.println("Successfully added user with login: " + login);

        //добавляем на сайт новую статью от пользователя
        String title = "Lorem";
        String articleText = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                "Aenean commodo ligula eget dolor. Aenean massa. " +
                "Cum sociis natoque penatibus et magnis dis parturient montes," +
                " nascetur ridiculus mus.";
        Article article = new Article(0, title, articleText, indus, ArticleAccess.OPEN);
        int id = articleDao.addArticle(article);
        article.setId(id);
        //получим все статьи автора
        List<Article> articleList = articleDao.getAllArticles(indus);
        for (Article ar : articleList) {
            System.out.println("Title: " + ar.getTitle());
            System.out.println("Text: " + ar.getContent());
        }

        //юзер пишет комментарий
        String content = "Did anybody read it?";

        Comment comment = new Comment(0, content, article, indus);
        int commentId = commentDao.addComment(comment);

        //получаем из базы другого юзера, вызываем ему метод оценки комментария и "минусуем" коммент
        String login2 = "experienced_progger";
        User experienced = userDao.getByLogin(login2)
                .orElseThrow(() -> new UserNotFoundException("Error: user not found or deleted"));

        userDao.rateComment(experienced,commentId,false);
        indus = userDao.getByLogin(login).get();
        System.out.println("Rating comment, current rating: " + indus.getRating());


    }

}
