package Task08_2;

/**
 * Dog
 * created by Ksenya_Ushakova at 16.05.2020
 */
public class Dog {
    private String name;
    private int age;
    private Person owner;

    public Dog(String name, int age, Person owner) {
        this.name = name;
        this.age = age;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", owner=" + owner.toString() +
                '}';
    }
}
