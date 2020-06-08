package Task15.Dao.Article;

import Task15.Dao.User.UserDaoImpl;
import Task15.Model.Article;
import Task15.Model.ArticleAccess;
import Task15.Model.BlogException.ArticleNotFoundException;
import Task15.Model.BlogException.UserNotFoundException;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.Optional;

/**
 * ArticleDaoImpl
 * класс реализует CRUD-операции с объектом Article
 * <p>
 * created by Ksenya_Ushakova at 01.06.2020
 */
public class ArticleDaoImpl implements ArticleDao {
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();
    private UserDaoImpl userDao;

    public ArticleDaoImpl(UserDaoImpl userDao) {
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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO article " +
                             "VALUES (DEFAULT, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setString(3, article.getAuthor().getLogin());
            preparedStatement.setString(4, article.getAccess().toString());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Ошибка записи в базу данных");
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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM article WHERE id = ?");) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                article = new Article(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        userDao.getByLogin(resultSet.getString(4))
                                .orElseThrow(UserNotFoundException::new),
                        ArticleAccess.getByName(resultSet.getString(5))
                );
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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE article SET " +
                             "title = ?, content = ?, author = ?, access_level  = ?" +
                             "WHERE id = ?")) {
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setString(3, article.getAuthor().getLogin());
            preparedStatement.setString(4, article.getAccess().toString());
            preparedStatement.setInt(5, article.getId());
            preparedStatement.executeUpdate();
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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM article " +
                             "WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }



    /**
     * Получение списка логинов пользователей, кому разрешен доступ
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
                throw new ArticleNotFoundException("Unknown access level");
        }
    }

    /**
     * Получение логинов всех зарегистрированных пользователей
     * из БД
     * @return CachedRowSet
     * @throws SQLException
     */

    private CachedRowSet allUsersLogins() throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT login FROM user_info");
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet rowSet = factory.createCachedRowSet();
            rowSet.populate(resultSet);
            return rowSet;
        }
    }

    /**
     * Получение логинов всех пользователей с неотрицательным рейтингом
     *  из БД
     * @return CashedRowSet
     * @throws SQLException
     */
    private CachedRowSet allAuthorsLogins() throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT login FROM user_info WHERE rating >= 0");
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet rowSet = factory.createCachedRowSet();
            rowSet.populate(resultSet);
            return rowSet;
        }
    }
}
