package Task16.model.blogException;

/**
 * AuthorImplementException
 * исключение выбрасывается при попытке получения доступа к функционалу автора у юзера с отрицательным рейтингом
 *
 * created by Ksenya_Ushakova at 02.06.2020
 */
public class AuthorImplementException extends AccessBlogException {
    public AuthorImplementException() {
    }

    public AuthorImplementException(String message) {
        super(message);
    }
}
