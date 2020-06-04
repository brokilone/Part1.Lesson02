package Task15.Dao.User;

import Task15.Model.Article;
import Task15.Model.UserInfo.Comment;
import Task15.Model.UserInfo.User;

import java.util.List;

/**
 * UserInfoDao
 * created by Ksenya_Ushakova at 31.05.2020
 */
public interface UserDao {
    String addUser(User user);
    User getByLogin(String login);
    boolean updateByLogin(User user);
    boolean deleteByLogin(String login);

    List<Article> getAllArticles(User user);
    List<Comment> getAllComments(User user);
}
