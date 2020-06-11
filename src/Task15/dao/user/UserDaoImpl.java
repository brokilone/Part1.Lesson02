package Task15.dao.user;

import Task15.dao.article.ArticleDaoImpl;
import Task15.model.Article;
import Task15.model.ArticleAccess;
import Task15.model.BlogException.ArticleNotFoundException;
import Task15.model.Comment;
import Task15.model.UserInfo.User;
import Task15.model.BlogException.UserNotFoundException;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserInfoDaoImpl
 * класс реализует CRUD-операции с объектом User
 *
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class UserDaoImpl implements UserDao {
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    /**
     * Добавление пользователя в БД
     * @param user - пользователь
     * @return логин объекта
     * @throws SQLException
     */
    @Override
    public String addUser(User user) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO user_info\n" +
                             " VALUES(?,?,?)");) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getRating());
            preparedStatement.executeUpdate();
            return user.getLogin();
        }
    }

    /**
     * Поиск пользователя в БД по логину
     * @param login логин пользователя
     * @return опционал объекта
     * @throws SQLException
     */
    @Override
    public Optional<User> getByLogin(String login) throws SQLException {
        User user = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM user_info WHERE login = ?");){
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user =  new User(
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
     * @param user - пользователь
     * @throws SQLException
     */
    @Override
    public void updateByLogin(User user) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE user_info SET password = ?," +
                             "rating = ? WHERE login = ?")){
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getRating());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Удаление записи о пользователе в БД по логину
     * @param login
     * @throws SQLException
     */
    @Override
    public void deleteByLogin(String login) throws SQLException {
        try (  Connection connection = connectionManager.getConnection();
               PreparedStatement preparedStatement =
                       connection.prepareStatement("DELETE FROM user_info " +
                               "WHERE login = ?")){

            preparedStatement.setString(1,login);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Проверка доступа к функционалу автора (требуется неотрицательный рейтинг)
     * @param user
     * @return true, если рейтинг неотрицателен
     * @throws SQLException
     */
    @Override
    public boolean isAuthor(User user) throws SQLException {
        try (Connection connection = connectionManager.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT rating FROM user_info WHERE login = ?");
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int rating = resultSet.getInt(1);
                return rating >= 0;
            } else {
                throw new UserNotFoundException();
            }

        }
    }






}
