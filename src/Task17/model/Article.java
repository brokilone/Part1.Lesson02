package Task17.model;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id &&
                title.equals(article.title) &&
                content.equals(article.content) &&
                author.equals(article.author) &&
                access == article.access;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, author, access);
    }
}
