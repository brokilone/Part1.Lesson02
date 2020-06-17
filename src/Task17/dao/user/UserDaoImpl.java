package Task17.dao.user;


import Task15.model.BlogException.UserNotFoundException;
import Task17.dao.article.ArticleDaoImpl;
import Task17.dao.comment.CommentDaoImpl;
import Task17.model.User;
import Task17.model.blogException.AuthorImplementException;
import Task17.model.blogException.CommentNotFoundException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * UserInfoDaoImpl
 * класс реализует CRUD-операции с объектом User
 * <p>
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class UserDaoImpl implements UserDao {
    private Connection connection;

    public UserDaoImpl(Connection connection) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
    }

    /**
     * Добавление пользователя в БД
     *
     * @param user - пользователь
     * @return логин объекта
     * @throws SQLException
     */
    @Override
    public String addUser(User user) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO user_info VALUES(?,?,?)");) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getRating());
            preparedStatement.executeUpdate();
            connection.commit();
            return user.getLogin();
        }
    }

    /**
     * Поиск пользователя в БД по логину
     *
     * @param login логин пользователя
     * @return опционал объекта
     * @throws SQLException
     */
    @Override
    public Optional<User> getByLogin(String login) throws SQLException {
        User user = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM user_info WHERE login = ?");) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                );
            }
            return Optional.ofNullable(user);
        }
    }

    /**
     * Редактирование данных пользователя в БД
     *
     * @param user - пользователь
     * @throws SQLException
     */
    @Override
    public void updateByLogin(User user) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE user_info SET password = ?, rating = ? WHERE login = ?")) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getRating());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.executeUpdate();
            connection.commit();
        }
    }

    /**
     * Удаление записи о пользователе в БД по логину
     *
     * @param login
     * @throws SQLException
     */
    @Override
    public void deleteByLogin(String login) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM user_info WHERE login = ?")) {

            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            connection.commit();
        }
    }

    /**
     * Проверка доступа к функционалу автора (требуется неотрицательный рейтинг)
     *
     * @param user
     * @return true, если рейтинг неотрицателен
     * @throws SQLException
     */
    @Override
    public boolean isAuthor(User user) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT rating FROM user_info WHERE login = ?");) {
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            if (resultSet.next()) {
                int rating = resultSet.getInt(1);
                return rating >= 0;
            } else {
                throw new UserNotFoundException();
            }

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
    public void rateComment(User user, int id, boolean up) throws SQLException {
        User author = new CommentDaoImpl(connection,new ArticleDaoImpl(connection,new UserDaoImpl(connection))).getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Error: comment not found or deleted")).getAuthor();
        if (author.getLogin().equals(user.getLogin())) {//проверка, оценка собственных комментариев недопустима
            AuthorImplementException exception = new AuthorImplementException
                    ("Access denied, it is not possible to manage your own rating");
            throw new AuthorImplementException("Access denied, it is not possible to manage your own rating");

        }
        int value = author.getRating();
        if (up) {
            author.setRating(value + 10);

        } else {
            author.setRating(value - 10);

        }
        updateByLogin(author);
    }


}
