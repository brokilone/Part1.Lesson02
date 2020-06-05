package Task15.Dao.Article;

import Task15.Dao.User.UserDaoImpl;
import Task15.Model.Article;
import Task15.Model.ArticleAccess;
import Task15.Model.UserInfo.Comment;
import Task15.Model.UserInfo.User;
import Task15.Model.BlogException.UserNotFoundException;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

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
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    /**
     * Метод для добавления в БД новой статьи
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
                        new UserDaoImpl().getByLogin(resultSet.getString(4))
                                .orElseThrow(UserNotFoundException::new),
                        ArticleAccess.getByName(resultSet.getString(5))
                );
            }

        }
        return Optional.ofNullable(article);
    }

    /**
     * Изменение статьи в БД
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
     * Получение списка комментариев к статье
     * @param article
     * @return возвращает List
     * @throws SQLException
     */
    @Override
    public List<Comment> getListOfComments(Article article) throws SQLException {
        List<Comment> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM comment_info WHERE source = ?")) {

            preparedStatement.setInt(1, article.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String content = resultSet.getString(2);
                String login = resultSet.getString(4);
                User author = new UserDaoImpl().getByLogin(login).orElseThrow(UserNotFoundException::new);
                list.add(new Comment(id, content, article, author));
            }
            return list;
        }
    }
}
