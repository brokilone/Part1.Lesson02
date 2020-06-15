package Task16.dao.comment;


import Task16.model.Article;
import Task16.model.Comment;
import Task16.model.User;
import Task16.model.blogException.ArticleNotFoundException;
import Task16.model.blogException.UserNotFoundException;
import Task16.dao.article.ArticleDaoImpl;
import Task16.dao.user.UserDaoImpl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * CommentDaoImpl
 * класс реализует CRUD-операции с объектом Comment
 * <p>
 * created by Ksenya_Ushakova at 01.06.2020
 */
public class CommentDaoImpl implements CommentDao {
    private Connection connection;
    private ArticleDaoImpl articleDao;
    private static final Logger logger = LogManager.getLogger(CommentDaoImpl.class.getName());

    public CommentDaoImpl(Connection connection, ArticleDaoImpl articleDao) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
        this.articleDao = articleDao;
    }

    /**
     * Метод для добавления в БД нового комментария
     *
     * @param comment - комментарий
     * @return id комментария
     * @throws SQLException
     */
    @Override
    public int addComment(Comment comment) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO comment_info " +
                        "VALUES (DEFAULT, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setString(2, comment.getAuthor().getLogin());
            preparedStatement.setInt(3, comment.getSource().getId());
            preparedStatement.executeUpdate();
            connection.commit();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                logger.info("Add new comment {}", comment);
                return generatedKeys.getInt(1);
            } else {
                SQLException e = new SQLException("Add comment to database: fail");
                logger.throwing(Level.ERROR, e);
                throw e;
            }
        }
    }

    /**
     * Получение комментария по id из базы данных
     *
     * @param id - id комментария
     * @return возвращает опционал объекта
     * @throws SQLException
     */
    @Override
    public Optional<Comment> getCommentById(int id) throws SQLException {
        Comment comment = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM comment_info WHERE id = ?");) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            if (resultSet.next()) {
                Article article = articleDao.getById(resultSet.getInt(4))
                        .orElseThrow(ArticleNotFoundException::new);
                comment = new Comment(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        article,
                        new UserDaoImpl(connection).getByLogin(resultSet.getString(3))
                                .orElseThrow(UserNotFoundException::new)
                );
                logger.info("Get comment by id: {}", id);
            }

        }
        return Optional.ofNullable(comment);
    }


    /**
     * Изменение комментария в БД
     *
     * @param comment - комментарий к статье
     * @throws SQLException
     */
    @Override
    public void updateCommentById(Comment comment) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE comment_info SET " +
                             "content = ?, author = ?, source = ? WHERE id = ?")) {
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setString(2, comment.getAuthor().getLogin());
            preparedStatement.setInt(3, comment.getSource().getId());
            preparedStatement.setInt(4, comment.getId());
            preparedStatement.executeUpdate();
            connection.commit();
            logger.info("Update comment: {}", comment);
        }
    }

    /**
     * Удаление комментария по id
     *
     * @param id - id комментария
     * @throws SQLException
     */
    @Override
    public void deleteCommentById(int id) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM comment_info " +
                             "WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            logger.info("Delete comment by id: {}", id);
        }
    }

    /**
     * Получение списка комментариев к статье
     *
     * @param article
     * @return возвращает List
     * @throws SQLException
     */
    @Override
    public List<Comment> getListOfComments(Article article) throws SQLException {
        List<Comment> list = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM comment_info WHERE source = ?")) {

            preparedStatement.setInt(1, article.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String content = resultSet.getString(2);
                String login = resultSet.getString(4);
                User author = new UserDaoImpl(connection).getByLogin(login).orElseThrow(UserNotFoundException::new);
                list.add(new Comment(id, content, article, author));
            }
            logger.info("Get all comments to an article: {}", article);
            return list;
        }
    }

    /**
     * Возвращает список всех комментариев пользователя
     *
     * @param user - пользователь
     * @return list
     * @throws SQLException
     */
    @Override
    public List<Comment> getAllComments(User user) throws SQLException {
        List<Comment> list = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM comment_info WHERE author = ?");) {
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String content = resultSet.getString(2);
                int articleId = resultSet.getInt(4);
                Article article = new ArticleDaoImpl(connection, new UserDaoImpl(connection)).getById(articleId).orElseThrow(ArticleNotFoundException::new);
                list.add(new Comment(id, content, article, user));
            }
            logger.info("Get all comments by user: {}", user);
            return list;
        }
    }

    public ArticleDaoImpl getArticleDao() {
        return articleDao;
    }
}
