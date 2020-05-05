package Task05_1;

import java.util.UUID;

/**
 * Pet - класс описывает объект - домашнее животное
 * created by Ksenya_Ushakova at 30.04.2020
 */
public class Pet {
    private final UUID UUID;
    private final String NAME;
    private int age;
    private Person owner;
    private double weight;

    /**
     * публичный конструктор
     * @param name имя
     * @param age возраст
     * @param owner хозяин
     * @see Person
     * @param weight вес
     */
    public Pet(String name, int age, Person owner, double weight) {
        this.UUID = java.util.UUID.randomUUID();
        this.NAME = name;
        this.age = age;
        this.owner = owner;
        this.weight = weight;
    }

    /**
     * публичный геттер
     * @return возвращает UUID - уникальный идентификатор объекта
     */
    public UUID getUUID() {
        return UUID;
    }

    /**
     * публичный геттер
     * @return String - имя объекта
     */
    public String getName() {
        return NAME;
    }

    /**
     *  публичный геттер
     * @return возвращает объект Person, характеризующий хозяина питомца
     * @see Person
     */
    public Person getOwner() {
        return owner;
    }

    /**
     * публичный сеттер
     * @param age - устанавливает поле возраст
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * публичный сеттер
     * @param owner устанавливает поле владелец
     * @see Person
     */
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    /**
     * публичный сеттер
     * @param weight устанавливает поле вес
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * публичный геттер
     * @return double - вес объекта
     */
    public double getWeight() {
        return weight;
    }

    /**
     * переопределенный метод для удобного вывода в строку инфо об объекте (имя, возраст, имя хозяина, вес)
     * @return строку
     */
    @Override
    public String toString() {
        return String.format("Имя: %s\t возраст: %d \t имя хозяина: %s \t вес: %.2f", NAME, age, owner.getName(),weight);
    }

    /**
     * переопределяем метод для вычисления hashcode исходя из константных полей объекта
     * @return возвращает целочисленный код объекта
     */
    @Override
    public int hashCode() {
        int result = UUID.hashCode();
        result = 31 * result + NAME.hashCode();
        return result;
    }

    /**
     * переопределяем метод для сравнения объектов по UUID и имени
     * @param obj объект, с которым производится сравнение
     * @return возвращае true, если объект по ссылке равен this, либо если UUID и имя совпадают
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Pet pet = (Pet) obj;
        if (this.UUID != ((Pet) obj).UUID) return false;
        if (this.NAME != ((Pet) obj).NAME) return false;

        return true;
    }
}
