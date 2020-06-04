package Task15.Dao.User;

import Task15.Dao.Article.ArticleDaoImpl;
import Task15.Model.Article;
import Task15.Model.ArticleAccess;
import Task15.Model.UserInfo.Comment;
import Task15.Model.UserInfo.User;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserInfoDaoImpl
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class UserDaoImpl implements UserDao {
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();



    @Override
    public String addUser(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO user_info\n" +
                     " VALUES(?,?,?)");){
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getRating());
            preparedStatement.executeUpdate();
            return user.getLogin();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getByLogin(String login) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM user_info WHERE login = ?");){
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateByLogin(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE user_info SET password = ?," +
                             "rating = ? WHERE login = ?")){
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getRating());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByLogin(String login) {
        try (  Connection connection = connectionManager.getConnection();
               PreparedStatement preparedStatement =
                       connection.prepareStatement("DELETE FROM user_info " +
                               "WHERE login = ?")){

            preparedStatement.setString(1,login);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Article> getAllArticles(User user) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Comment> getAllComments(User user) {
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
                Article article = new ArticleDaoImpl().getById(articleId);
                list.add(new Comment(id,content,article,user));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
