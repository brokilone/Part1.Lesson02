package Task15.Dao.User;

import Task15.Model.Article;
import Task15.Model.Comment;
import Task15.Model.UserInfo.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * UserInfoDao
 * интерфейс CRUD-операций с объектами User (пользователи)
 *
 * created by Ksenya_Ushakova at 31.05.2020
 */
public interface UserDao {
    String addUser(User user) throws SQLException;
    Optional<User> getByLogin(String login) throws SQLException;
    void updateByLogin(User user) throws SQLException;
    void deleteByLogin(String login) throws SQLException;

    boolean isAuthor(User user) throws SQLException;

    List<Article> getAllArticles(User user) throws SQLException;
    List<Comment> getAllComments(User user) throws SQLException;
}
