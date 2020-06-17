package Task17.dao.user;


import Task17.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;


import java.sql.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;


/**
 * UserDaoImplTest
 * created by Ksenya_Ushakova at 16.06.2020
 */
@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest {

    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Connection connection;
    @InjectMocks
    private UserDaoImpl userDao;

    private static final String SELECT_FROM_USER_INFO = "SELECT * FROM user_info WHERE login = ?";
    private static final String INSERT_INTO_USER_INFO = "INSERT INTO user_info VALUES(?,?,?)";
    private static final String UPDATE_USER_INFO = "UPDATE user_info SET password = ?, rating = ? WHERE login = ?";
    private static final String DELETE_FROM_USER_INFO = "DELETE FROM user_info WHERE login = ?";
    private static final String SELECT_RATING_FROM_USER_INFO = "SELECT rating FROM user_info WHERE login = ?";


    @Test
    void getByLogin() throws SQLException {
        User user = new User("testUser", "testPass", 0);

        when(connection.prepareStatement(SELECT_FROM_USER_INFO))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(1)).thenReturn("testUser");
        when(resultSet.getString(2)).thenReturn("testPass");
        when(resultSet.getInt(3)).thenReturn(0);

        Optional<User> testUser = userDao.getByLogin("testUser");
        verify(connection, times(1)).prepareStatement(SELECT_FROM_USER_INFO);
        verify(preparedStatement, times(1)).setString(1, user.getLogin());
        verify(preparedStatement, times(1)).executeQuery();
        assertEquals(user, testUser.get());

    }

    @Test
    void addUser() throws SQLException {
        when(connection.prepareStatement(INSERT_INTO_USER_INFO)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        String login = "testUser";
        String pass = "testPass";
        int rating = -10;
        User user = new User(login, pass, rating);
        String addedLogin = userDao.addUser(user);


        verify(connection, times(1)).prepareStatement(INSERT_INTO_USER_INFO);
        verify(preparedStatement, times(1)).setString(1, user.getLogin());
        verify(preparedStatement, times(1)).setString(2, user.getPassword());
        verify(preparedStatement, times(1)).setInt(3, user.getRating());
        verify(preparedStatement, times(1)).executeUpdate();

        assertEquals(addedLogin, user.getLogin());
    }

    @Test
    void updateByLogin() throws SQLException {
        String login = "superUser";
        String pass = "stub";
        int rating = 100;
        User user = new User(login, pass, rating);

        when(connection.prepareStatement(UPDATE_USER_INFO)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        userDao.updateByLogin(user);

        verify(connection, times(1)).prepareStatement(UPDATE_USER_INFO);
        verify(preparedStatement, times(1)).setString(1, user.getPassword());
        verify(preparedStatement, times(1)).setInt(2, user.getRating());
        verify(preparedStatement, times(1)).setString(3, user.getLogin());
        verify(preparedStatement, times(1)).executeUpdate();

    }

    @Test
    void deleteByLogin() throws SQLException {
        String login = "superUser";
        String pass = "stub";
        int rating = 100;
        User user = new User(login, pass, rating);

        when(connection.prepareStatement(DELETE_FROM_USER_INFO)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        userDao.deleteByLogin(login);

        verify(connection, times(1)).prepareStatement(DELETE_FROM_USER_INFO);
        verify(preparedStatement, times(1)).setString(1, user.getLogin());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void isAuthor() throws SQLException {
        String login = "user2";
        String pass = "stub2";
        int rating = -20;
        User user = new User(login, pass, rating);

        when(connection.prepareStatement(SELECT_RATING_FROM_USER_INFO)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(-20);

        boolean isAuthor = userDao.isAuthor(user);
        verify(connection, times(1)).prepareStatement(SELECT_RATING_FROM_USER_INFO);
        verify(preparedStatement, times(1)).setString(1, user.getLogin());
        verify(preparedStatement, times(1)).executeQuery();
        assertEquals(isAuthor, -20 >= 0);
    }


}




