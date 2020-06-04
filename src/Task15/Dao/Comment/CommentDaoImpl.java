package Task15.Dao.Comment;

import Task15.Dao.Article.ArticleDaoImpl;
import Task15.Dao.User.UserDaoImpl;
import Task15.Model.UserInfo.Comment;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import java.sql.*;


/**
 * CommentDaoImpl
 * created by Ksenya_Ushakova at 01.06.2020
 */
public class CommentDaoImpl implements CommentDao{
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();
    @Override
    public int addComment(Comment comment) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO comment_info " +
                             "VALUES (DEFAULT, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setString(2, comment.getAuthor().getLogin());
            preparedStatement.setInt(3, comment.getSource().getId());
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
    public Comment getCommentById(int id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM comment_info WHERE id = ?");){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Comment(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        new ArticleDaoImpl().getById(resultSet.getInt(4)),
                        new UserDaoImpl().getByLogin(resultSet.getString(3))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateCommentById(Comment comment) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE comment_info SET " +
                             "content = ?, author = ?, source = ? WHERE id = ?")){
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setString(2, comment.getAuthor().getLogin());
            preparedStatement.setInt(3, comment.getSource().getId());
            preparedStatement.setInt(4,comment.getId());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCommentById(int id) {
        try (Connection connection = connectionManager.getConnection();
               PreparedStatement preparedStatement =
                       connection.prepareStatement("DELETE FROM comment_info " +
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
