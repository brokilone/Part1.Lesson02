package Task05_2;

import java.util.Objects;
import java.util.UUID;

/**
 * Pet - класс описывает объект - домашнее животное, хранимый в картотеке
 * created by Ksenya_Ushakova at 30.04.2020
 */
public class Pet {
    private UUID uuid;
    private String name;
    private int age;
    private Person owner;
    private double weight;

    /**
     * публичный конструктор, используется для создания сущности Pet в картотеке на основании запроса
     * @see AddPetRequest
     * @param request
     */
    public Pet(AddPetRequest request) {
        this.uuid = UUID.randomUUID();
        this.name = request.getName();
        this.age = request.getAge();
        this.owner = request.getOwner();
        this.weight = request.getWeight();
    }

    /**
     * публичный геттер
     * @return возвращает UUID - уникальный идентификатор объекта
     */
    public UUID getUuid() {
        return this.uuid;
    }

    /**
     * публичный геттер
     * @return String - имя объекта
     */
    public String getName() {
        return this.name;
    }

    /**
     *  публичный геттер
     * @return возвращает объект Person, характеризующий хозяина питомца
     * @see Person
     */
    public Person getOwner() {
        return this.owner;
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
        return this.weight;
    }

    /**
     * публичный геттер
     * @return int - возрст объекта
     */
    public int getAge(){
        return this.age;
    }
    /**
     * переопределенный метод для удобного вывода в строку инфо об объекте (имя, возраст, имя хозяина, вес)
     * @return строку
     */
    @Override
    public String toString() {
        return String.format("Имя: %s\t возраст: %d \t имя хозяина: %s \t вес: %.2f",name,
                age, owner.getName(), weight);
    }
    /**
     * переопределяем метод для вычисления hashcode исходя из константных полей объекта
     * @return возвращает целочисленный код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid, name,owner, weight, age);
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
        if (this.name != ((Pet) obj).getName()) return false;
        if (this.age != ((Pet) obj).getAge()) return false;
        if (this.owner != ((Pet) obj).getOwner()) return false;
        if (this.weight != ((Pet) obj).getWeight()) return false;

        return true;
    }

}


