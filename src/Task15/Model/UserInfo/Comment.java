package Task15.Model.UserInfo;

import Task15.Model.Article;

/**
 * CommentInfo
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class Comment {
    private int id;
    private String content;
    private Article source;
    private User author;

    public Comment(int id, String content, Article source, User author) {
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

    public Article getSource() {
        return source;
    }

    public User getAuthor() {
        return author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", source=" + source +
                ", author=" + author +
                '}';
    }
}
