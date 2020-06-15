package Task16.model;


/**
 * Comment
 * класс описывает комментарий к статье в блоге
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

    /**
     * Геттер для id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Сеттер для id
     * @param id - id комментария
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Геттер для текста комментария
     * @return строку текста
     */
    public String getContent() {
        return content;
    }

    /**
     * Геттер для source
     * @return возвращает объект статьи, к которой привязан комментарий
     */
    public Article getSource() {
        return source;
    }

    /**
     * Геттер для author
     * @return возвращает автора комментария
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Сеттер для содержимого комментария
     * @param content - текст комментария
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
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", source=" + source +
                ", author=" + author +
                '}';
    }
}
