package task3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ObjectBox - параметризованный класс,
 * хранит внутри типизированную коллекцию
 * created by Ksenya_Ushakova at 29.04.2020
 */
public class ObjectBox<T>{
    /**
     * типизированная коллекция для хранения элементов массива, полученного на вход конструктора
     */
    Set<T> set = new HashSet();

    /**
     * конструктор принимает на вход параметризованный массив и раскладывает их в HashSet
     * @param objects
     */
    ObjectBox (T[] objects) {
        set.addAll(Arrays.asList(objects));
    }

    /**
     * метод принимает на вход объект типа и добавляет его в коллекцию
     * @param o - добавляемый объект
     */
    public void addObject(T o) {
        set.add(o);
    }
    /**
     * метод принимает на вход объект типа и, если такой объект есть в коллекции, удаляет его
     * @param o - удаляемый объект
     */
    public void deleteObject(T o) {
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

    /**
     * Метод, используемый для сравнения объекта класса ObjectBox с другим объектом.
     * Сравнение организовано по содержимому коллекции. Перед этим предусмотрена проверка ссылок,
     * указывающих на объекты, проверка на null, проверка, к одному ли классу принадлежат сравниваемые объекты,
     * а также кол-во элементов коллекции
     * @param obj - объект, с которым производится сравнение
     * @return возвращает true, если ссылки указывают на один и тот же объект, либо если объекты
     * содержат одинаковые коллекции
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (hashCode() != obj.hashCode()) return false;

        ObjectBox box = (ObjectBox) obj;
        if (set.size() != box.set.size()) return false;
        for (T t : set) {
            if (!box.set.contains(t)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Определяем как будет вычисляться хэш код для объекта ObjectBox
     * @return целочисленный хэш код объекта
     */
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (T t : set) {
            hashCode = 31 * hashCode + (t == null ? 0 : t.hashCode());
        }
        return hashCode;
    }
}
