package Task16.dao.user;

import Task16.model.User;

import java.sql.SQLException;
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
    void rateComment(User user, int id, boolean up) throws SQLException;
    boolean isAuthor(User user) throws SQLException;

}
