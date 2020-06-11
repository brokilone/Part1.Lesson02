package Task15.model.BlogException;

/**
 * CommentNotFoundException
 * исключение генерируется, если в БД не найден комментарий по id
 * created by Ksenya_Ushakova at 04.06.2020
 */
public class CommentNotFoundException extends AccessBlogException{
    public CommentNotFoundException() {
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
