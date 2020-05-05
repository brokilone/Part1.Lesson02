package Task05_1;

/**
 * Person класс описывает человека-хозяина домашнего питомца
 * created by Ksenya_Ushakova at 30.04.2020
 */
public class Person implements Comparable<Person>{
    private String name;
    private int age;
    private Sex sex;

    /**
     * публичный конструктор
     * @param name имя
     * @param age возраст
     * @param sex пол
     */
    public Person(String name, int age, Sex sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    /**
     * публичный геттер
     * @return String - имя объекта
     */
    public String getName() {
        return name;
    }

    /**
     * метод для сортировки объектов по имени
     * @param o - объект, с которым производится сравнение
     * @return возвращает целое число
     */
    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.getName());
    }
}

/**
 * Вспомогательный enum-класс для указания пола объекта Person
 */
 enum Sex {
    MAN,
    WOMAN
}
