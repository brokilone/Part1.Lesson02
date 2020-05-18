package Task08_2;

/**
 * Person - вспомогательный класс для тестирования сериализации и десериализации
 *
 * created by Ksenya_Ushakova at 15.05.2020
 */
public class Person{
    //все поля приватные, публичных геттеров нет
    private String name;
    private int age;
    private double weight;
    private boolean isMarried;

    /**
     * публичный конструктор
     * @param name
     * @param age
     * @param weight
     */
    public Person(String name, int age, double weight) {

        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    /**
     * публичный конструктор
     * @param name имя
     * @param age возраст
     * @param weight вес
     * @param isMarried женат или нет
     */
    public Person(String name, int age, double weight, boolean isMarried) {

        this.name = name;
        this.age = age;
        this.weight = weight;
        this.isMarried = isMarried;
    }

    /**
     * Метод для строкового представления инфо об объекте
     * @return строку
     */
    public String toString(){
        return String.format("Name: %s, age: %d, weight: %.2f, married: %b", name, age, weight,isMarried);
    }
}
