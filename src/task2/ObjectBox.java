package task2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ObjectBox
 * класс, хранящий в себе коллекцию Object-ов
 * created by Ksenya_Ushakova at 29.04.2020
 */
public class ObjectBox {
    /**
     * коллекция для хранения элементов массива, полученного на вход конструктора
     */
    Set set = new HashSet();

    /**
     * конструктор принимает на вход массив объектов Object и раскладывает их в HashSet
     * @param objects
     */
    ObjectBox (Object[] objects) {
        set.addAll(Arrays.asList(objects));
    }

    /**
     * метод принимает на вход объект и добавляет его в коллекцию
     * @param o - добавляемый объект
     */
    public void addObject(Object o) {
        set.add(o);
    }

    /**
     * метод принимает на вход объект и, если такой объект есть в коллекции, удаляет его
     * @param o - удаляемый объект
     */
    public void deleteObject(Object o) {
        boolean isPresent = set.contains(o);
        if (isPresent) {
            set.remove(o);
        }
    }

    /**
     * Метод выводит содержимое коллекции в строку
     * @return String
     */
    public String dump() {
        return set.toString();
    }

}
