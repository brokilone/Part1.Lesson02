package Task15.model.BlogException;

/**
 * NotFoundArticleException
 * исключение генерируется, если в БД не найдена статья по id
 * created by Ksenya_Ushakova at 04.06.2020
 */
public class ArticleNotFoundException extends AccessBlogException{
    public ArticleNotFoundException(){

    }
    public ArticleNotFoundException(String message){
        super(message);
    }

}
