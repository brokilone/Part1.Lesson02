package Task15.Model;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public ArticleAccess getAccess() {
        return access;
    }

    public void setAccess(ArticleAccess access) {
        this.access = access;
    }
}
