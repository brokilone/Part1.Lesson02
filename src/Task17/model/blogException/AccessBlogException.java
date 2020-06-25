package Task17.model.blogException;

/**
 * AccessBlogException
 * created by Ksenya_Ushakova at 05.06.2020
 */
public class AccessBlogException extends RuntimeException{
    public AccessBlogException(){

    }
    public AccessBlogException(String message) {
        super(message);
    }
}
