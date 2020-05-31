package Task15.Dao.User;

import Task15.Model.User;

/**
 * UserInfoDao
 * created by Ksenya_Ushakova at 31.05.2020
 */
public interface UserDao {
    String addUser(User user);
    User getByLogin(String login);
    boolean updateByLogin(User user);
    boolean deleteByLogin(String login);
}
