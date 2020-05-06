package Task05_2;

import java.util.Objects;
import java.util.UUID;

/**
 * Pet - класс описывает объект - домашнее животное, хранимый в картотеке
 * created by Ksenya_Ushakova at 30.04.2020
 */
public class Pet extends AddPetRequest{
    private UUID uuid;

    /**
     * публичный конструктор, используется для создания сущности Pet в картотеке на основании запроса
     * @see AddPetRequest
     * @param request
     */
    public Pet(AddPetRequest request) {
        super(request.getName(), request.getAge(), request.getOwner(), request.getWeight());
        this.uuid = UUID.randomUUID();
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
        return super.getName();
    }

    /**
     *  публичный геттер
     * @return возвращает объект Person, характеризующий хозяина питомца
     * @see Person
     */
    public Person getOwner() {
        return super.getOwner();
    }

    /**
     * публичный сеттер
     * @param age - устанавливает поле возраст
     */
    public void setAge(int age) {
        super.setAge(age);
    }

    /**
     * публичный сеттер
     * @param owner устанавливает поле владелец
     * @see Person
     */
    public void setOwner(Person owner) {
        super.setOwner(owner);
    }

    /**
     * публичный сеттер
     * @param weight устанавливает поле вес
     */
    public void setWeight(double weight) {
        super.setWeight(weight);
    }

    /**
     * публичный геттер
     * @return double - вес объекта
     */
    public double getWeight() {
        return super.getWeight();
    }

    /**
     * переопределенный метод для удобного вывода в строку инфо об объекте (имя, возраст, имя хозяина, вес)
     * @return строку
     */
    @Override
    public String toString() {
        return String.format("Имя: %s\t возраст: %d \t имя хозяина: %s \t вес: %.2f",getName(),
                getAge(), getOwner().getName(), getWeight());
    }

    /**
     * переопределяем метод для вычисления hashcode исходя из константных полей объекта
     * @return возвращает целочисленный код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid, getName(),getOwner(), getWeight(), getAge());
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
        if (this.getName() != ((Pet) obj).getName()) return false;
        if (this.getAge() != ((Pet) obj).getAge()) return false;
        if (this.getOwner() != ((Pet) obj).getOwner()) return false;
        if (this.getWeight() != ((Pet) obj).getWeight()) return false;

        return true;
    }

}


