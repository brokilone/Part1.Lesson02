package Task15.Model;

/**
 * CommentInfo
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class CommentInfo {
    private int id;
    private String content;
    private Article source;
    private User author;

    public CommentInfo(int id, String content, Article source, User author) {
        this.id = id;
        this.content = content;
        this.source = source;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article getSource() {
        return source;
    }

    public void setSource(Article source) {
        this.source = source;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
