package Task15.Model;

import Task15.Main;

import java.util.ArrayList;
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

    /**
     * AccessLevel
     * created by Ksenya_Ushakova at 31.05.2020
     */
    public enum ArticleAccess {
        OPEN(Main.getAllUsers()), AVAILABLE_TO_LIST(new ArrayList<>()),
        AVAILABLE_TO_AUTHORIZED_USERS(Main.getOnlineUsers()),
        AVAILABLE_TO_AUTHORS(Main.getAllAuthors());

        private List<User> list;

        ArticleAccess( List<User> list) {
            this.list = list;
        }


        @Override
        public String toString() {
            switch (this) {
                case OPEN:
                    return "open";
                case AVAILABLE_TO_LIST:
                    return "avaiable to list";
                case AVAILABLE_TO_AUTHORS:
                    return "available to authors";
                case AVAILABLE_TO_AUTHORIZED_USERS:
                    return "available to authorized users";
            }
            return null;
        }

        public static ArticleAccess getByName(String name){
            switch (name) {
                case "open":
                    return OPEN;
                case "avaiable to list":
                    return AVAILABLE_TO_LIST;
                case "available to authors":
                    return AVAILABLE_TO_AUTHORS;
                case "available to authorized users":
                    return AVAILABLE_TO_AUTHORIZED_USERS;
            }
            return null;
        }
    }
}
