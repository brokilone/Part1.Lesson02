package Task15.Dao.Comment;

import Task15.Dao.Article.ArticleDaoImpl;
import Task15.Dao.User.UserDaoImpl;
import Task15.Model.Article;
import Task15.Model.BlogException.ArticleNotFoundException;
import Task15.Model.UserInfo.Comment;
import Task15.Model.BlogException.UserNotFoundException;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import java.sql.*;
import java.util.Optional;


/**
 * CommentDaoImpl
 * класс реализует CRUD-операции с объектом Comment
 * <p>
 * created by Ksenya_Ushakova at 01.06.2020
 */
public class CommentDaoImpl implements CommentDao {
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    /**
     * Метод для добавления в БД нового комментария
     *
     * @param comment - комментарий
     * @return id комментария
     * @throws SQLException
     */
    @Override
    public int addComment(Comment comment) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO comment_info " +
                             "VALUES (DEFAULT, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setString(2, comment.getAuthor().getLogin());
            preparedStatement.setInt(3, comment.getSource().getId());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException();
            }
        }
    }

    /**
     * Получение комментария по id из базы данных
     * @param id - id комментария
     * @return возвращает опционал объекта
     * @throws SQLException
     */
    @Override
    public Optional<Comment> getCommentById(int id) throws SQLException {
        Comment comment = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM comment_info WHERE id = ?");) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Article article = new ArticleDaoImpl().getById(resultSet.getInt(4))
                        .orElseThrow(ArticleNotFoundException::new);
                comment = new Comment(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        article,
                        new UserDaoImpl().getByLogin(resultSet.getString(3))
                        .orElseThrow(UserNotFoundException::new)
                );
            }

        } return Optional.ofNullable(comment);
    }

    /**
     * Изменение комментария в БД
     * @param comment - комментарий к статье
     * @throws SQLException
     */
    @Override
    public void updateCommentById(Comment comment) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE comment_info SET " +
                             "content = ?, author = ?, source = ? WHERE id = ?")) {
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setString(2, comment.getAuthor().getLogin());
            preparedStatement.setInt(3, comment.getSource().getId());
            preparedStatement.setInt(4, comment.getId());
            preparedStatement.executeUpdate();

        }
    }

    /**
     * Удаление комментария по id
     * @param id - id комментария
     * @throws SQLException
     */
    @Override
    public void deleteCommentById(int id) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM comment_info " +
                             "WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        }
    }
}
