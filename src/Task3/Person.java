package Task3;

/**
 * Person
 * created by Ksenya_Ushakova at 25.04.2020
 */
public class Person implements Comparable {
    private String name; //хранит имя
    private int age; //хранит возраст от 0 до 100
    private Sex sex; //хранит пол

    private Person(String name, int age, Sex sex) {
        this.name = name;
        if (age >= 0 && age <= 100) {
            this.age = age;
        } else {
            this.age = 0;
        }
        this.sex = sex;
    }

    /**
     * Создаем мужчину
     *
     * @param name имя
     * @param age  возраст
     * @return возвращает объект класса Person с заданнами именем и возрастом, пол мужской
     */
    public static Person createMan(String name, int age) {
        return new Person(name, age, Sex.MAN);
    }

    /**
     * Создаем женщину
     *
     * @param name имя
     * @param age  возраст
     * @return возвращает объект класса Person с заданнами именем и возрастом, пол женский
     */
    public static Person createWoman(String name, int age) {
        return new Person(name, age, Sex.WOMAN);
    }

    /**
     * публичный сеттер поля name
     *
     * @return name - значение поля имени
     */
    public String getName() {
        return name;
    }

    /**
     * публичный сеттер поля name
     *
     * @param name принимает в качестве аргумента строку - имя объекта
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * публичный геттер поля age
     *
     * @return age - значение поля возраста
     */
    public int getAge() {
        return age;
    }

    /**
     * публичный сеттер поля age
     *
     * @param age принимает в качестве аргумента int - возраст объекта
     */
    public void setAge(int age) {
        if (age >= 0 && age <= 100) {
            this.age = age;
        } else {
            this.age = 0;
        }
    }

    /**
     * публичный геттер поля sex
     *
     * @return sex -  значение поля пола
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * публичный сеттер поля sex
     *
     * @param sex принимает в качестве аргумента объект класса Sex - пол объекта
     * @see Sex
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * переопределенный метод для вывода информации об объекте и его полях
     *
     * @return String возвращает строку
     */
    @Override
    public String toString() {
        return String.format("Person info. Name: %s, age: %d, sex: %s, ", this.name, this.age, this.sex);
    }

    /**
     * Переопределенный метод для реализации интерфейса Comparable
     * позволяет сравнивать объект по трем полям: пол, возраст, имя
     *
     * @param o - объект, с которым происходит сравнение
     * @return возвращает число
     */
    @Override
    public int compareTo(Object o) {
        Person p = (Person) o;
        String s = "";
        int i = this.getSex().compareTo(p.getSex());
        if (i != 0) return i;
        int j = new Integer(this.getAge()).compareTo(new Integer(p.getAge()));
        if (j != 0) return -j;
        int k = this.getName().compareTo(p.getName());
        if (k == 0 && j == 0) {
            try {
                throw new ComparePersonException(String.format("Warning: duplicate persons: name - %s, age - %d",
                        this.getName(), this.getAge()));
            } catch (ComparePersonException e) {
                System.err.println(e.getMessage());
                return k;
            }
        }
        return k;
    }
}

