package Task3;

import java.util.Arrays;
import java.util.Date;

/**
 * Main
 * Класс иллюстрирует сортировку объектов Person двумя способами
 * @see Person
 * created by Ksenya_Ushakova at 25.04.2020
 */
public class Main {
    public static Person[] allPersons = new Person[10000];//массив для хранения 10000 объектов Person
    public static Person[] allPersons_copy;
    public static PersonSort sort;


    //заполняем массивы сгенерированными объектами Person
    static {
        for (int i = 0; i < allPersons.length; i++) {
            allPersons[i] = Generator.generateOnePerson();
        }
        allPersons_copy = Arrays.copyOf(allPersons, allPersons.length);
    }

    /**
     * Главный метод, иллюстрирует работу программы
     * @param args
     */
    public static void main(String[] args) {
        sort = new InsertionSort();
        Date before = new Date(); //сохраняем текущие дату и время до сортировки
        sort.makeSort(allPersons, 0, allPersons.length - 1);//сортировка вставками
        Date after = new Date(); //сохраняем текущие дату и время после сортировки
        long millis = after.getTime() - before.getTime(); //вычисляем время сортировки в мс

        sort = new QuickSort();
        before = new Date(); //сохраняем текущие дату и время до сортировки
        sort.makeSort(allPersons_copy, 0, allPersons.length - 1);//быстрая сортировка
        after = new Date(); //сохраняем текущие дату и время после сортировки
        long millis2 = after.getTime() - before.getTime(); //вычисляем время сортировки в мс


        //выводим в консоль отсортированный массив
        for (Person person : allPersons) {
            System.out.println(person);
        }

        System.out.println("Время сортировки вставками в миллисекундах составило: " + millis);
        System.out.println("Время быстрой сортировки в миллисекундах составило: " + millis2);
    }
}
