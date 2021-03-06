package Task16.dao.article;


import Task16.model.Article;
import Task16.model.ArticleAccess;
import Task16.model.User;
import Task16.model.blogException.ArticleNotFoundException;
import Task16.model.blogException.UserNotFoundException;
import Task16.dao.user.UserDaoImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ArticleDaoImpl
 * класс реализует CRUD-операции с объектом Article
 * <p>
 * created by Ksenya_Ushakova at 01.06.2020
 */
public class ArticleDaoImpl implements ArticleDao {


    private static final Logger logger = LogManager.getLogger(ArticleDaoImpl.class.getName());
    private UserDaoImpl userDao;
    private Connection connection;

    public ArticleDaoImpl(Connection connection, UserDaoImpl userDao) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
        this.userDao = userDao;
    }

    /**
     * Метод для добавления в БД новой статьи
     *
     * @param article
     * @return int - id статьи
     * @throws SQLException
     */
    @Override
    public int addArticle(Article article) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO article " +
                        "VALUES (DEFAULT, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setString(3, article.getAuthor().getLogin());
            preparedStatement.setString(4, article.getAccess().toString());
            preparedStatement.executeUpdate();
            connection.commit();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                logger.info("Add new article: {}", article);
                return generatedKeys.getInt(1);
            } else {
                SQLException e = new SQLException("Add article to database: fail");
                logger.throwing(Level.ERROR, e);
                throw e;
            }
        }
    }

    /**
     * Получение статьи из БД по id
     *
     * @param id - id статьи
     * @return опционал объекта Article
     * @throws SQLException
     */
    @Override
    public Optional<Article> getById(int id) throws SQLException {
        Article article = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM article WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            if (resultSet.next()) {
                article = new Article(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        userDao.getByLogin(resultSet.getString(4))
                                .orElseThrow(UserNotFoundException::new),
                        ArticleAccess.getByName(resultSet.getString(5))
                );
                logger.info("Get article by id: {}", id);
            }

        }
        return Optional.ofNullable(article);
    }

    /**
     * Изменение статьи в БД
     *
     * @param article статья
     * @throws SQLException
     */
    @Override
    public void updateById(Article article) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE article SET " +
                             "title = ?, content = ?, author = ?, access_level  = ?" +
                             "WHERE id = ?")) {
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setString(3, article.getAuthor().getLogin());
            preparedStatement.setString(4, article.getAccess().toString());
            preparedStatement.setInt(5, article.getId());
            preparedStatement.executeUpdate();
            connection.commit();
            logger.info("Update article: {}", article);
        }
    }

    /**
     * Удаление статьи по id из БД
     *
     * @param id - id статьи
     * @throws SQLException
     */
    @Override
    public void deleteById(int id) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM article " +
                             "WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            logger.info("Delete article by id: {}", id);
        }
    }


    /**
     * Получение списка логинов пользователей, кому разрешен доступ
     *
     * @param article статья
     * @return CashedRowSet
     * @throws SQLException
     */
    @Override
    public CachedRowSet allowedUsers(Article article) throws SQLException {
        switch (article.getAccess()) {
            case OPEN:
                return allUsersLogins();
            case AVAILABLE_TO_AUTHORS:
                return allAuthorsLogins();
            default:
                ArticleNotFoundException e = new ArticleNotFoundException("Unknown access level");
                logger.throwing(Level.ERROR, e);
                throw e;
        }
    }

    /**
     * Получение логинов всех зарегистрированных пользователей
     * из БД
     *
     * @return CachedRowSet
     * @throws SQLException
     */

    private CachedRowSet allUsersLogins() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT login FROM user_info");
        connection.commit();
        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet rowSet = factory.createCachedRowSet();
        rowSet.populate(resultSet);
        logger.info("Get all users logins");
        return rowSet;
    }

    /**
     * Получение логинов всех пользователей с неотрицательным рейтингом
     * из БД
     *
     * @return CashedRowSet
     * @throws SQLException
     */
    private CachedRowSet allAuthorsLogins() throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT login FROM user_info WHERE rating >= 0");
        connection.commit();
        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet rowSet = factory.createCachedRowSet();
        rowSet.populate(resultSet);
        logger.info("Get all authors logins");

        return rowSet;

    }

    /**
     * Метод возвращает список всех статей пользователя
     *
     * @param user пользователь
     * @return List
     * @throws SQLException
     */
    @Override
    public List<Article> getAllArticles(User user) throws SQLException {
        List<Article> list = new ArrayList<>();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM article WHERE author = ?")) {
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String content = resultSet.getString(3);
                ArticleAccess access = ArticleAccess.getByName(resultSet.getString(5));
                list.add(new Article(id, title, content, user, access));
            }
            logger.info("Get all articles by user: {}", user);
            return list;
        }

    }

    public UserDaoImpl getUserDao() {
        return userDao;
    }
}
