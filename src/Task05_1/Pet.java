package Task05_1;

import java.util.Objects;
import java.util.UUID;

/**
 * Pet - класс описывает объект - домашнее животное
 * created by Ksenya_Ushakova at 30.04.2020
 */
public class Pet {
    private UUID uuid;
    private final String name;
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
        this.name = name;
        this.age = age;
        this.owner = owner;
        this.weight = weight;
    }


    /**
     * публичный геттер
     * @return возвращает UUID - уникальный идентификатор объекта
     */
    public UUID getUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        return this.uuid;
    }

    /**
     * публичный геттер
     * @return String - имя объекта
     */
    public String getName() {
        return name;
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
        return String.format("Имя: %s\t возраст: %d \t имя хозяина: %s \t вес: %.2f", name, age, owner.getName(),weight);
    }

    /**
     * переопределяем метод для вычисления hashcode исходя из константных полей объекта
     * @return возвращает целочисленный код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, age, owner, weight);
    }

    /**
     * переопределяем метод для сравнения объектов
     * @param obj объект, с которым производится сравнение
     * @return возвращае true, если объект по ссылке равен this, либо если поля совпадают
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Pet pet = (Pet) obj;
        if (this.uuid != ((Pet) obj).uuid) return false;
        if (this.name != ((Pet) obj).name) return false;
        if (this.age != ((Pet) obj).age) return false;
        if (this.owner != ((Pet) obj).owner) return false;
        if (this.weight != ((Pet) obj).weight) return false;

        return true;
    }

}


