package Task15.Model;

import Task15.Dao.Article.ArticleDaoImpl;
import Task15.Model.UserInfo.Comment;
import Task15.Model.UserInfo.User;

import java.util.List;

/**
 * Article
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class Article {
    private int id;
    private String title;
    private String content;
    private User author;
    private ArticleAccess access;

    public Article(int id, String title, User author, ArticleAccess access) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.access = access;
    }

    public Article(int id, String title, String content, User author, ArticleAccess access) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.access = access;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }


    public User getAuthor() {
        return author;
    }


    public ArticleAccess getAccess() {
        return access;
    }

    public void setAccess(ArticleAccess access) {
        this.access = access;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getListOfComments(){
        return new ArticleDaoImpl().getListOfComments(this);
    }

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
}
