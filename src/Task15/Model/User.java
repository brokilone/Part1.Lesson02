package Task15.Model;

/**
 * UserInfo
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class User {
    private String login;
    private String password;
    private int rating;

    public User(String login, String password, int rating) {
        this.login = login;
        this.password = password;
        this.rating = rating;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
