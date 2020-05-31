package Task15;

import Task15.Dao.User.UserDaoImpl;
import Task15.Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Main
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306",
                "root", "npatfb4psdf");){
            DBManager.renewDataBase(connection);
            UserDaoImpl impl = new UserDaoImpl();
            User user =  impl.getByLogin("experienced_progger");
            System.out.println("Get data about experienced_progger:");
            System.out.println("login: " + user.getLogin());
            System.out.println("password: " + user.getPassword());
            System.out.println("rating: " + user.getRating());

            String login = impl.addUser(new User("unknown_spy", "neverguess", -10));
            System.out.println("Add new user with login: " + login);

            boolean deleteBeginner = impl.deleteByLogin("beginner");
            if (deleteBeginner) {
                System.out.println("Successfully deleted user with login 'beginner'");
            }

            boolean updateSpy = impl.updateByLogin(new User("unknown_spy", "needchange", -60));
            if(updateSpy){
                System.out.println("Successfully updated user with login 'unknown_spy'");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static List<User> getAllUsers(){
        return null;
    }

    public static List<User> getOnlineUsers(){
        return null;
    }

    public static List<User> getAllAuthors(){
        return null;
    }
}
