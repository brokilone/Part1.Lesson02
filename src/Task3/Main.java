package Task3;

import java.util.Date;

/**
 * Main
 * created by Ksenya_Ushakova at 25.04.2020
 */
public class Main {
    public static Person[] allPersons = new Person[10000];
    public static QuickSort qSort;
    public static BubbleSort bSort;

    static {
        qSort = new QuickSort();
        bSort = new BubbleSort();
        for (int i = 0; i < allPersons.length; i++) {
            allPersons[i] = Generator.generateOnePerson();
        }
    }

    public static void main(String[] args) {
        Date before = new Date();
        qSort.makeSort(allPersons, 0, allPersons.length - 1);
        //bSort.makeSort(allPersons, 0, allPersons.length - 1);
        Date after = new Date();


        for (Person person : allPersons) {
            System.out.println(person);
        }

        long millis = after.getTime() - before.getTime();
        System.out.println("Время сортировки в миллисекундах составило: " + millis);
    }
}
