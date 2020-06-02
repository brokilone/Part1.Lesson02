package Task15.Dao.Article;

import Task15.Dao.User.UserDaoImpl;
import Task15.Model.Article;
import Task15.Model.Comment;
import Task15.Model.User;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import java.sql.*;

/**
 * ArticleDaoImpl
 * created by Ksenya_Ushakova at 01.06.2020
 */
public class ArticleDaoImpl implements ArticleDao{
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    @Override
    public int addArticle(Article article) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO article " +
                             "VALUES (DEFAULT, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setString(3, article.getAuthor().getLogin());
            preparedStatement.setString(4, article.getAccess().toString());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Article getById(int id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM article WHERE id = ?");){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Article(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        new UserDaoImpl().getByLogin(resultSet.getString(4)),
                        Article.ArticleAccess.getByName(resultSet.getString(5))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateById(Article article) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE article SET " +
                             "title = ?, content = ?, author = ?, access_level  = ?" +
                             "WHERE id = ?")){
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setString(3, article.getAuthor().getLogin());
            preparedStatement.setString(4,article.getAccess().toString());
            preparedStatement.setInt(5,article.getId());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM article " +
                             "WHERE id = ?")){

            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
