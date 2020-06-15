package Task16;

import Task16.dao.article.ArticleDaoImpl;
import Task16.dao.comment.CommentDaoImpl;
import Task16.dao.user.UserDaoImpl;
import Task16.model.Article;
import Task16.model.ArticleAccess;
import Task16.model.User;
import Task16.model.blogException.*;
import Task16.model.Comment;
import Task16.connection.ConnectionManager;
import Task16.connection.ConnectionManagerJdbcImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();


    public static void main(String[] args) {
        try (Connection connection = connectionManager.getConnection()) {

            logger.debug("Try database init");
            DBManager.renewDataBase(connection);//сброс и инициализация БД
            logger.debug("Database init done");

            UserDaoImpl userDao = new UserDaoImpl(connection);
            testUserDaoImpl(userDao);

            ArticleDaoImpl articleDao = new ArticleDaoImpl(connection, userDao);
            testArticleDaoImpl(articleDao);

            CommentDaoImpl commentDao = new CommentDaoImpl(connection, articleDao);
            testCommentDaoImpl(commentDao);

            testFunctionality(userDao, articleDao, commentDao);


        } catch (SQLException e) {
            logger.error(e);
        } catch (AccessBlogException nfe) {
            logger.error(nfe.getMessage());
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
        logger.debug("Try to get user by login {}", login);
        User user = userDao.getByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Error: user not found or deleted"));
        logger.debug("Get data done: login {}, password {}, rating {}", user.getLogin(), user.getPassword(),
                user.getRating());

        //добавление нового юзера
        login = "unknown_spy";
        String password = "neverguess";
        int rating = -10;
        logger.debug("Try to add new user with login {}", login);
        login = userDao.addUser(new User(login, password, rating));
        logger.debug("Add new user, login: {}", login);

        //удаление по логину
        logger.debug("Try to delete user with login {}", login);
        userDao.deleteByLogin(login);
        logger.debug("Delete user, login: {}", login);

        //обновление рейтинга по логину
        login = "beginner";
        password = "needchange";
        rating = 20;
        logger.debug("Try to update user with login {}", login);
        userDao.updateByLogin(new User(login, password, rating));
        logger.debug("Update user, login: {}, password {}, rating {}", login, password, rating);
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
        logger.debug("Try to get an article, id - {}", id);
        Article article = articleDao.getById(id).orElseThrow(() ->
                new ArticleNotFoundException("Error: article not found or deleted"));
        logger.debug("Get data done: {}", article);


        //заносим в БД новую статью
        String title = "Second article";
        String content = "I don't know how to write articles";
        User author = article.getAuthor();
        ArticleAccess access = ArticleAccess.AVAILABLE_TO_AUTHORS;
        logger.debug("Add article in database: title {}, content {}, author {}, access {}", title,
                content, author, access);
        Article article2 = new Article(0, title, content, author, access);
        id = articleDao.addArticle(article2);
        article2.setId(id);
        logger.debug("Added new article with id  {}", id);

        //удаляем статью по id
        id = 2;
        logger.debug("Try to delete article with id {}", id);
        articleDao.deleteById(id);
        logger.debug("Successfully deleted article with id {}", id);

        //редактируем статью в БД (меняем доступ)
        id = 1;
        access = ArticleAccess.AVAILABLE_TO_AUTHORS;
        logger.debug("Try to edit article with id {}, try to set access {}", id, access);
        article.setAccess(access);
        articleDao.updateById(article);
        logger.debug("Successfully updated article with id {}", id);
        access = articleDao.getById(1)
                .orElseThrow(() -> new ArticleNotFoundException("Error: article not found or deleted"))
                .getAccess();
        logger.debug("Current access level {}", access);


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
        logger.debug("Try to get comment by id {}", id);
        Comment comment = commentDao.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Error: comment not found or deleted"));
        logger.debug("Comment data received: {}", comment);

        //добавление нового коммента в БД
        String content = "Where is moderator?";
        logger.debug("Try to get article, id {}", id);
        Article article = commentDao.getArticleDao().getById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Arror: article not found or deleted"));
        logger.debug("Article received");
        String login = "author_of_the_year";
        logger.debug("Try to get user, login {}", login);
        User author = commentDao.getArticleDao().getUserDao().getByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Error: user not found or deleted"));
        logger.debug("User received. Try to add comment");
        Comment comment2 = new Comment(0, content, article, author);
        id = commentDao.addComment(comment2);
        comment2.setId(id);
        logger.debug("Comment added: {}", comment2);

        //удаление комментария по id
        id = 1;
        logger.debug("Try to delete comment, id: {}", id);
        commentDao.deleteCommentById(id);
        logger.debug("Successfully deleted comment with id = {}", id);

        //изменение комментария (меняем текст)
        id = 2;
        logger.debug("Try to update comment, id {}", id);
        comment2.setContent("Boring...");
        commentDao.updateCommentById(comment2);
        logger.debug("Successfully updated comment with id: {}", id);
        logger.debug("Try to get new content, comment id {}", id);
        content = commentDao.getCommentById(2)
                .orElseThrow(() -> new CommentNotFoundException("Error: comment not found or deleted"))
                .getContent();
        logger.debug("Current text: {}", content);

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
        logger.debug("Try to add new user, login: {}, password: {}, rating: {}", login, pass, rating);
        User indus = new User(login, pass, rating);
        login = userDao.addUser(indus);
        logger.debug("Successfully added user: {}", indus);

        //добавляем на сайт новую статью от пользователя
        logger.debug("try to add new article by user: {}", login);
        String title = "Lorem";
        String articleText = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                "Aenean commodo ligula eget dolor. Aenean massa. " +
                "Cum sociis natoque penatibus et magnis dis parturient montes," +
                " nascetur ridiculus mus.";
        Article article = new Article(0, title, articleText, indus, ArticleAccess.OPEN);
        int id = articleDao.addArticle(article);
        article.setId(id);
        logger.debug("Added new article, id: {}, content: {}", id, articleText);
        //получим все статьи автора
        logger.debug("Try to get all articles by: {}", login);
        List<Article> articleList = articleDao.getAllArticles(indus);
        logger.debug("All articles by {}: ", login);
        for (Article ar : articleList) {
            logger.debug("Title: {}, content: {}", ar.getTitle(), ar.getContent());
        }

        //юзер пишет комментарий
        String content = "Did anybody read it?";
        logger.debug("Try to add new comment by user: {}, article id: {}, content: {}",
                login, id, content);
        Comment comment = new Comment(0, content, article, indus);
        int commentId = commentDao.addComment(comment);
        logger.debug("Added comment: id: {}", commentId);

        //получаем из базы другого юзера, вызываем ему метод оценки комментария и "минусуем" коммент
        String login2 = "experienced_progger";
        logger.debug("Try to get data about user, login: {}", login2);
        User experienced = userDao.getByLogin(login2)
                .orElseThrow(() -> new UserNotFoundException("Error: user not found or deleted"));
        logger.debug("Received user data: {}", experienced);
        logger.debug("{} rate comment by {}, comment id: {}", login2, login, commentId);

        userDao.rateComment(experienced,commentId,false);
        logger.debug("Successfully rate comment: {}", commentId);


    }

}
