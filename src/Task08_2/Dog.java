package Task08_2;


/**
 * Dog - класс для демонстрации сериализации/десериализации объектов с сылочными полями
 * created by Ksenya_Ushakova at 16.05.2020
 */
public class Dog extends Animal {
    static int count;
    private String name;
    private int age;
    private Person owner;

    public Dog(String name, int age, Person owner, String breed) {
        super(breed);
        this.name = name;
        this.age = age;
        this.owner = owner;
        count++;
    }

    @Override
    public String toString() {
        if (owner == null) {
            return " Dog{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", breed=" + breed +
                    ", owner= null" +
                    '}';
        }
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", breed=" + breed +
                ", owner=" + owner.toString() +
                '}';
    }
}