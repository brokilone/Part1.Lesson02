package Task15.Model.UserInfo;

import Task15.Dao.Article.ArticleDao;
import Task15.Dao.Article.ArticleDaoImpl;
import Task15.Dao.Comment.CommentDao;
import Task15.Dao.Comment.CommentDaoImpl;
import Task15.Dao.User.UserDao;
import Task15.Dao.User.UserDaoImpl;
import Task15.Model.Article;
import Task15.Model.ArticleAccess;

import java.util.List;

/**
 * UserInfo
 * created by Ksenya_Ushakova at 31.05.2020
 */
public class User implements Author, Commentator {
    private String login;
    private String password;
    private int rating;

    public User(String login, String password, int rating) {
        this.login = login;
        this.password = password;
        this.rating = rating;
    }

    public String getLogin() {
        return login;
    }


    public String getPassword() {
        return password;
    }


    public int getRating() {
        return rating;
    }


    @Override
    public int writeArticle(String title, String content, ArticleAccess access) {
        try {
            if (isAuthor()) {
                Article article = new Article(0, title, content, this, access);
                int id = new ArticleDaoImpl().addArticle(article);
                article.setId(id);
                return id;
            }
            else throw new AuthorImplementException("A positive rating is required to access author features");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return -1;
    }
    @Override
    public int writeArticle(String title, String content, ArticleAccess access, String[] logins) {
        try {
            if (isAuthor()) {
                if (access != ArticleAccess.AVAILABLE_TO_LIST) {
                    return writeArticle(title, content, access);
                }
                Article article = new Article(0, title, content, this, access);
                int id = new ArticleDaoImpl().addArticle(article);
                article.setId(id);
                access.setList(logins);
                return id;
            }
            else throw new AuthorImplementException("A positive rating is required to access author features");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean editArticle(int id, String content, ArticleAccess access) {
        try {
            if (isAuthor()) {
                ArticleDao articleDao = new ArticleDaoImpl();
                Article article = articleDao.getById(id);
                article.setContent(content);
                article.setAccess(access);
                return articleDao.updateById(article);
            } else throw new AuthorImplementException("A positive rating is required to access author features");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteArticle(int id) {
        return new ArticleDaoImpl().deleteById(id);
    }

    @Override
    public List<Article> getAllArticles() {
        UserDao userDao = new UserDaoImpl();
        return userDao.getAllArticles(this);
    }

    @Override
    public int writeComment(Article article, String content) {
        Comment comment = new Comment(0, content, article, this);
        int id = new CommentDaoImpl().addComment(comment);
        comment.setId(id);
        return id;
    }

    @Override
    public boolean editComment(int id, String content) {
        CommentDao commentDao = new CommentDaoImpl();
        Comment comment = commentDao.getCommentById(id);
        comment.setContent(content);
        return commentDao.updateCommentById(comment);
    }

    @Override
    public boolean deleteComment(int id) {
        return new CommentDaoImpl().deleteCommentById(id);
    }

    @Override
    public void rateComment(int id, boolean up) {
        User author = new CommentDaoImpl().getCommentById(id).getAuthor();
        int value = author.getRating();
        if (up) {
            author.setRating(value + 10);
        } else {
            author.setRating(value - 10);
        }
    }

    @Override
    public List<Comment> getAllComments() {
        UserDao userDao = new UserDaoImpl();
        return userDao.getAllComments(this);
    }

    private void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isAuthor(){
        return (this.rating >= 0);
    }

    private boolean changeLogin(String newLogin){
        this.login = newLogin;
        return new UserDaoImpl().updateByLogin(this);
    }

    private boolean changePassword(String newPassword){
        this.password = newPassword;
        return new UserDaoImpl().updateByLogin(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", rating=" + rating +
                '}';
    }
}
