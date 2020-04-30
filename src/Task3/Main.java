package Task3;

import java.util.Date;

/**
 * Main
 * Класс иллюстрирует сортировку объектов Person двумя способами
 * @see Person
 * created by Ksenya_Ushakova at 25.04.2020
 */
public class Main {
    public static Person[] allPersons = new Person[1000];//массив для хранения 10000 объектов Person
    public static QuickSort qSort;//хранит ссылку на объект класса быстрой сортировки
    public static InsertionSort inSort;//хранит ссылку на объект класса сортировки вставками

    //создаем объекты классов сортировки
    //заполняем массив сгенерированными объектами Person
    static {
        qSort = new QuickSort();
        inSort = new InsertionSort();
        for (int i = 0; i < allPersons.length; i++) {
            allPersons[i] = Generator.generateOnePerson();
        }
    }

    /**
     * Главный метод, иллюстрирует работу программы
     * @param args
     */
    public static void main(String[] args) {
        Date before = new Date(); //сохраняем текущие дату и время до сортировки
        //inSort.makeSort(allPersons, 0, allPersons.length - 1);
        // опять же закомментированный код. Подумайте как этого избежать с помощью полиморфизма.
        qSort.makeSort(allPersons, 0, allPersons.length - 1);
        Date after = new Date(); //сохраняем текущие дату и время после сортировки

        //выводим в консоль отсортированный массив
        for (Person person : allPersons) {
            System.out.println(person);
        }

        long millis = after.getTime() - before.getTime(); //вычисляем время сортировки в мс
        System.out.println("Время сортировки в миллисекундах составило: " + millis);
    }
}
