package Task15.Dao.Comment;

import Task15.Dao.Article.ArticleDao;
import Task15.Dao.User.UserDaoImpl;
import Task15.Model.Article;
import Task15.Model.BlogException.ArticleNotFoundException;
import Task15.Model.Comment;
import Task15.Model.BlogException.UserNotFoundException;
import Task15.Model.UserInfo.User;
import Task15.connection.ConnectionManager;
import Task15.connection.ConnectionManagerJdbcImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    private ArticleDao articleDao;

    public CommentDaoImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

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
                Article article = articleDao.getById(resultSet.getInt(4))
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

    /**
     * Получение списка комментариев к статье
     *
     * @param article
     * @return возвращает List
     * @throws SQLException
     */
    @Override
    public List<Comment> getListOfComments(Article article) throws SQLException {
        List<Comment> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM comment_info WHERE source = ?")) {

            preparedStatement.setInt(1, article.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String content = resultSet.getString(2);
                String login = resultSet.getString(4);
                User author = new UserDaoImpl().getByLogin(login).orElseThrow(UserNotFoundException::new);
                list.add(new Comment(id, content, article, author));
            }
            return list;
        }
    }
}
