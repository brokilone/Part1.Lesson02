package Task15.Model.BlogException;

/**
 * UserNotFoundException
 * created by Ksenya_Ushakova at 05.06.2020
 */
public class UserNotFoundException extends AccessBlogException{
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
