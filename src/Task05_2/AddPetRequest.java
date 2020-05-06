package Task05_2;


/**
 * AddPetRequest - класс описывает запрос на добавление животного в картотеку
 * created by Ksenya_Ushakova at 06.05.2020
 */
public class AddPetRequest {
    private String name;
    private int age;
    private Person owner;
    private double weight;

    /**
     * публичный конструктор для формирования запроса
     * @param name   имя
     * @param age    возраст
     * @param owner  хозяин
     * @param weight вес
     * @see Person
     */
    public AddPetRequest(String name, int age, Person owner, double weight) {
        this.name = name;
        this.age = age;
        this.owner = owner;
        this.weight = weight;
    }
    /**
     * публичный геттер
     * @return String - имя объекта
     */
    public String getName() {
        return name;
    }
    /**
     * публичный геттер
     * @return int - возраст объекта
     */
    public int getAge() {
        return age;
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
     * публичный геттер
     * @return double - вес объекта
     */
    public double getWeight() {
        return weight;
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
}
