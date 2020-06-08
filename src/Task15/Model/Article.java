package Task15.Model;

import Task15.Dao.Article.ArticleDaoImpl;
import Task15.Dao.Comment.CommentDaoImpl;
import Task15.Dao.User.UserDaoImpl;
import Task15.Model.UserInfo.User;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Article
 * класс описывает статью в блоге
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class Article {
    private int id;
    private String title;
    private String content;
    private User author;
    private ArticleAccess access;


    public Article(int id, String title, String content, User author, ArticleAccess access)  {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.access = access;
    }

    /**
     * Геттер id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Сеттер id
     * @param id - id статьи
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Геттер заголовка
     * @return заголовок
     */
    public String getTitle() {
        return title;
    }

    /**
     * Геттер текста статьи
     * @return текст статьи
     */
    public String getContent() {
        return content;
    }

    /**
     * Геттер автора статьи
     * @return автора статьи
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Геттер уровня доступа
     * @return уровень доступа
     */
    public ArticleAccess getAccess() {
        return access;
    }

    /**
     * Сеттер уровня доступа
     * @param access уровень доступа
     */
    public void setAccess(ArticleAccess access) {
        this.access = access;
    }

    /**
     * Сеттер текста статьи
     * @param content текст статьи
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Получение всех комментариев к данной статье
     * @return List
     * @throws SQLException
     */
    public List<Comment> getListOfComments() throws SQLException {
        return new CommentDaoImpl(new ArticleDaoImpl(new UserDaoImpl())).getListOfComments(this);
    }

    /**
     * Строковое представление полей объекта
     * @return
     */
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", access=" + access +
                '}';
    }


    /**
     * Получение пользователей, имеющих доступ к статье, исходя из установленного
     * уровня доступа
     * @return List
     */
    public List<String> getListOfAllowedUsers() throws SQLException {
        List<String> list = new ArrayList<>();
        CachedRowSet rowSet = new ArticleDaoImpl(new UserDaoImpl()).allowedUsers(this);
        while (rowSet.next()){
            list.add(rowSet.getString(1));
        }
        return list;
    }
}
