package Task2_3;

/**
 * Person
 * created by Ksenya_Ushakova at 25.04.2020
 */
public class Person implements Comparable {
    private String name; //хранит имя
    private int age; //хранит возраст от 0 до 100
    private Sex sex; //хранит пол

    protected Person(String name, int age, Sex sex) {
        this.name = name;
        if (age >= 0 && age <= 100) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Возраст должен быть в пределах от 0 до 100");
        }
        this.sex = sex;
    }

    /**
     * публичный геттер поля name
     *
     * @return name - значение поля имени
     */
    public String getName() {
        return name;
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
     * публичный геттер поля sex
     *
     * @return sex -  значение поля пола
     */
    public Sex getSex() {
        return sex;
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
        if (this == o) return 0;
        Person p = (Person) o;
        int i = this.getSex().compareTo(p.getSex());
        if (i != 0) return i;
        int j = new Integer(this.getAge()).compareTo(new Integer(p.getAge()));
        if (j != 0) return -j;
        int k = this.getName().compareTo(p.getName());
        if (k == 0) {
            try {
                throw new ComparePersonException(String.format("Warning: duplicate persons: name - %s, age - %d",
                        this.getName(), this.getAge()));
            } catch (ComparePersonException e) {
                System.err.println(e.getMessage());
                System.exit(0);
            }
        }
        return k;
    }
}

