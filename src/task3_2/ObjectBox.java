package task3_2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ObjectBox
 * класс, хранящий в себе коллекцию Object-ов
 * created by Ksenya_Ushakova at 29.04.2020
 */
public class ObjectBox {
    //НАСТАВНИК
    // самый строгий уровень доступа
    /**
     * коллекция для хранения элементов массива, полученного на вход конструктора
     */
    private Set set = new HashSet();

    /**
     * конструктор принимает на вход массив объектов Object и раскладывает их в HashSet
     * @param objects
     */
    public ObjectBox (Object[] objects) {
        set.addAll(Arrays.asList(objects));
    }

    /**
     * приватный конструктор, принимает на вход коллекцию Set и сохраняет ссылку на нее
     * @param set
     */
    protected ObjectBox (Set set) {
        this.set = set;
    }
    /**
     * метод принимает на вход объект и добавляет его в копию коллекции
     * @param o - добавляемый объект
     * @return возвращает новый объект ObjectBox с обновленной коллекцией
     */
    public ObjectBox addObject(Object o) {
        Set copy = new HashSet(set);
        copy.add(o);
        return new ObjectBox(copy);
    }

    /**
     * метод принимает на вход объект и, если такой объект есть в коллекции, создает копию коллекции,
     * удаляет элемент в копии и возвращает новый объект с обновленной коллекцией
     * @param o - удаляемый объект
     * @return новый объект ObjectBox с обновленной коллекцией, либо текущий объект, если изменения не производились
     */
    public ObjectBox deleteObject(Object o) {
        boolean isPresent = set.contains(o);
        if (isPresent) {
            Set copy = new HashSet(set);
            copy.remove(o);
            return new ObjectBox(copy);
        }
        return this;
    }

    /**
     * Метод выводит содержимое коллекции в строку
     * @return String
     */
    public String dump() {
        return set.toString();
    }

}
