package Task15.Dao.ArticleAccess;

import Task15.Model.UserInfo.User;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ArticleAccessDaoImpl
 * created by Ksenya_Ushakova at 04.06.2020
 */
public class ArticleAccessDaoImpl implements ArticleAccessDao{
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    @Override
    public List<User> getAllAuthors() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getGroupByLogins(String[] logins) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
