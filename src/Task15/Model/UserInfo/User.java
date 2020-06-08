package Task15.Model.UserInfo;

import Task15.Dao.Article.ArticleDao;
import Task15.Dao.Article.ArticleDaoImpl;
import Task15.Dao.Comment.CommentDao;
import Task15.Dao.Comment.CommentDaoImpl;
import Task15.Dao.User.UserDao;
import Task15.Dao.User.UserDaoImpl;
import Task15.Model.Article;
import Task15.Model.ArticleAccess;
import Task15.Model.BlogException.ArticleNotFoundException;
import Task15.Model.BlogException.AuthorImplementException;
import Task15.Model.BlogException.CommentNotFoundException;
import Task15.Model.Comment;

import java.sql.SQLException;
import java.util.List;

/**
 * User
 * класс описывает пользователя блога,
 * реализует интерфейсы автора статей и комментатора
 * <p>
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class User implements Author, Commentator {
    private String login;
    private String password;
    private int rating;

    public User(String login, String password, int rating) {
        this.login = login;
        this.password = password;
        this.rating = rating;
    }

    /**
     * Геттер для поля login
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Геттер для поля password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Геттер для поля rating
     *
     * @return rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Создание пользователем новой статьи с занесением записи в БД с открытым доступом или только для авторов
     *
     * @param title   - заголовок
     * @param content - текст статьи
     * @param access  - уровень доступа
     * @return id статьи
     * @throws SQLException
     */
    @Override
    public int writeArticle(String title, String content, ArticleAccess access) throws SQLException {

        if (isAuthor()) { //проверка доступа к функционалу
            Article article = new Article(0, title, content, this, access);
            int id = new ArticleDaoImpl(new UserDaoImpl()).addArticle(article);
            article.setId(id);
            return id;
        } else {
            throw new AuthorImplementException("A positive rating is required to access author features");
        }
    }


    /**
     * Изменение статьи в БД
     *
     * @param id      id статьи
     * @param content текст
     * @param access  уровень доступа
     * @throws SQLException
     */
    @Override
    public void editArticle(int id, String content, ArticleAccess access) throws SQLException {

        if (isAuthor()) { //проверка доступа к функционалу, при негативном рейтинге запрет на редактирование статьи
            ArticleDao articleDao = new ArticleDaoImpl(new UserDaoImpl());
            Article article = articleDao.getById(id).orElseThrow(ArticleNotFoundException::new);
            if (article.getAuthor().login.equals(this.login)) {//проверка, не чужую ли статью редактируем
                article.setContent(content);
                article.setAccess(access);
                articleDao.updateById(article);
            } else {
                throw new AuthorImplementException("Access denied, it is not possible " +
                        "to edit an article of another author");
            }
        } else throw new AuthorImplementException("A positive rating is required to access author features");

    }

    /**
     * Удаление статьи по id
     *
     * @param id
     * @throws SQLException
     */
    @Override
    public void deleteArticle(int id) throws SQLException {
        ArticleDao articleDao = new ArticleDaoImpl(new UserDaoImpl());
        Article article = articleDao.getById(id).orElseThrow(ArticleNotFoundException::new);
        if (article.getAuthor().login.equals(this.login)) {//проверка, не чужую ли статью редактируем
            articleDao.deleteById(id);//проверка, не чужую ли статью удаляем
        } else {
            throw new AuthorImplementException("Access denied, it is not possible " +
                    "to delete an article of another author");
        }
    }

    /**
     * Получение всех статей автора
     *
     * @return List
     * @throws SQLException
     */
    @Override
    public List<Article> getAllArticles() throws SQLException {
        UserDao userDao = new UserDaoImpl();
        return userDao.getAllArticles(this);
    }

    /**
     * Добавление комментария к статье
     *
     * @param article статья
     * @param content текст комментария
     * @return id комментария
     * @throws SQLException
     */
    @Override
    public int writeComment(Article article, String content) throws SQLException {
        Comment comment = new Comment(0, content, article, this);
        int id = new CommentDaoImpl(new ArticleDaoImpl(new UserDaoImpl())).addComment(comment);
        comment.setId(id);
        return id;
    }

    /**
     * редактирование комментария к статье
     *
     * @param id      - id статьи
     * @param content - текст статьи
     * @throws SQLException
     */
    @Override
    public void editComment(int id, String content) throws SQLException {
        CommentDao commentDao = new CommentDaoImpl(new ArticleDaoImpl(new UserDaoImpl()));
        Comment comment = commentDao.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Error: comment not found or deleted"));
        if (comment.getAuthor().login.equals(login)) {//проверка доступа
            comment.setContent(content);
            commentDao.updateCommentById(comment);
        } else {
            throw new AuthorImplementException("Access denied, it is not possible" +
                    " to edit a comment of another author");
        }

    }

    /**
     * удаление комментария к статье
     *
     * @param id - id статьи
     * @throws SQLException
     */
    @Override
    public void deleteComment(int id) throws SQLException {
        CommentDao commentDao = new CommentDaoImpl(new ArticleDaoImpl(new UserDaoImpl()));
        Comment comment = commentDao.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Error: comment not found or deleted"));
        if (comment.getAuthor().login.equals(login)) {//проверка доступа
            commentDao.deleteCommentById(id);
        } else {
            throw new AuthorImplementException("Access denied, it is not possible" +
                    " to delete a comment of another author");
        }
    }

    /**
     * Оценка комментария, положительная оценка повышает рейтинг комментатора на 10 единиц,
     * отрицательная - снижает на 10 единиц
     *
     * @param id - id комментария
     * @param up - установка в true означает положительную оценку
     * @throws SQLException
     */
    @Override
    public void rateComment(int id, boolean up) throws SQLException {
        Task15.Model.UserInfo.User author = new CommentDaoImpl(new ArticleDaoImpl(new UserDaoImpl())).getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Error: comment not found or deleted")).getAuthor();
        if (author.login.equals(login)) {//проверка, оценка собственных комментариев недопустима
            throw new AuthorImplementException("Access denied, it is not possible to manage your own rating");
        }
        int value = author.getRating();
        if (up) {
            author.setRating(value + 10);

        } else {
            author.setRating(value - 10);
        }
        new UserDaoImpl().updateByLogin(author);
    }

    /**
     * Получение всех комментариев автора
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Comment> getAllComments() throws SQLException {
        UserDao userDao = new UserDaoImpl();
        return userDao.getAllComments(this);
    }

    /**
     * Сеттер для rating
     *
     * @param rating - рейтинг пользователя
     */
    private void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Проверка доступа к интерфейсу автора
     *
     * @return true если рейтинг неотрицателен
     * @throws SQLException
     */
    public boolean isAuthor() throws SQLException {
        return new UserDaoImpl().isAuthor(this);
    }

    /**
     * Вывод инфо о пользователе в строку
     *
     * @return
     */
    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", rating=" + rating +
                '}';
    }
}
