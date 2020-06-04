package Task15.Dao.ArticleAccess;

import Task15.Model.UserInfo.User;

import java.util.List;

/**
 * ArticleAccessDao
 * created by Ksenya_Ushakova at 04.06.2020
 */
public interface ArticleAccessDao {
    List<User> getAllAuthors();
    List<User> getAllUsers();
    List<User> getGroupByLogins(String[] logins);
}
