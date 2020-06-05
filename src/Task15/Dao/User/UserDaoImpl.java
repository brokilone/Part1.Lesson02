package Task15.Dao.User;

import Task15.Dao.Article.ArticleDaoImpl;
import Task15.Model.Article;
import Task15.Model.ArticleAccess;
import Task15.Model.BlogException.ArticleNotFoundException;
import Task15.Model.UserInfo.Comment;
import Task15.Model.UserInfo.User;
import Task15.Model.BlogException.UserNotFoundException;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * Метод возвращает список всех статей пользователя
     * @param user пользователь
     * @return List
     * @throws SQLException
     */
    @Override
    public List<Article> getAllArticles(User user) throws SQLException {
        List<Article> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM article WHERE author = ?");
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String content = resultSet.getString(3);
                ArticleAccess access = ArticleAccess.getByName(resultSet.getString(5));
                list.add(new Article(id,title,content,user,access));
            }
            return list;
        }
    }

    /**
     * Возвращает список всех комментариев пользователя
     * @param user - пользователь
     * @return list
     * @throws SQLException
     */
    @Override
    public List<Comment> getAllComments(User user) throws SQLException {
        List<Comment> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM comment_info WHERE author = ?");
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String content = resultSet.getString(2);
                int articleId  = resultSet.getInt(4);
                Article article = new ArticleDaoImpl().getById(articleId).orElseThrow(ArticleNotFoundException::new);
                list.add(new Comment(id,content,article,user));
            }
            return list;
        }
    }

    /**
     * Метод получает список всех пользователей с неотрицательным рейтингом
     * @return List
     * @throws SQLException
     */
    @Override
    public List<User> getAllAuthors() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_info WHERE rating >= 0");
            if (resultSet.next()) {
                list.add(new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                ));
            }
            return list;
        }
    }

    /**
     * Метод получает список всех зарегистрированных пользоваталей
     * @return List
     * @throws SQLException
     */
    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_info");
            if (resultSet.next()) {
                list.add(new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                ));
            }
            return list;
        }
    }

    /**
     * Метод получает список всех пользователей согласно указанному перечню логинов
     * @param logins - логины
     * @return List
     * @throws SQLException
     */
    @Override
    public List<User> getGroupByLogins(String[] logins) throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_info WHERE login IN (?)");
            String allLogins = Arrays.toString(logins);
            statement.setString(1, allLogins.substring(1 ,allLogins.length()-1));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                list.add(new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                ));
            }
            return list;
        }
    }


}
