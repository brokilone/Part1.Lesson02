package Task3;

import java.util.Arrays;
import java.util.Date;

/**
 * Main
 * Класс иллюстрирует сортировку объектов Person двумя способами
 *
 * @see Person
 * created by Ksenya_Ushakova at 25.04.2020
 */
public class Main {
    private static Person[] allPersons = new Person[10000];//массив для хранения 10000 объектов Person
    private static Person[] allPersons_copy;

    //заполняем массивы сгенерированными объектами Person
    static {
        for (int i = 0; i < allPersons.length; i++) {
            allPersons[i] = Generator.generateOnePerson();
        }
        allPersons_copy = Arrays.copyOf(allPersons, allPersons.length);
    }

    /**
     * Главный метод, иллюстрирует работу программы
     *
     * @param args
     */
    public static void main(String[] args) {
        PersonSort sort;
        sort = new InsertionSort();
        long millis = getMillis(sort);
        System.out.println("Время сортировки вставками в миллисекундах составило: " + millis);

        sort = new QuickSort();
        millis = getMillis(sort);
        System.out.println("Время быстрой сортировки в миллисекундах составило: " + millis);

        //выводим в консоль отсортированный массив
        for (Person person : allPersons) {
            System.out.println(person);
        }
    }

    /**
     * Метод вычисляет время сортировки в мс
     *
     * @param sort принимает на вход объект класса сортировки, реализующего интерфейс PersonSort
     * @return возвращает long время сортировки в мс
     */
    private static long getMillis(PersonSort sort) {
        Date before = new Date(); //сохраняем текущие дату и время до сортировки
        sort.makeSort(allPersons, 0, allPersons.length - 1);//сортировка вставками
        Date after = new Date(); //сохраняем текущие дату и время после сортировки
        return after.getTime() - before.getTime();
    }
}
