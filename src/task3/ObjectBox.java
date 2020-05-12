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
    private Set<T> set = new HashSet<>();

    /**
     * конструктор принимает на вход параметризованный массив и раскладывает их в HashSet
     * @param objects
     */
    public ObjectBox (T[] objects) {
        set.addAll(Arrays.asList(objects));
    }

    /**
     * приватный конструктор, принимает на вход коллекцию Set и сохраняет ссылку на нее
     * @param set
     */
    protected ObjectBox (Set<T> set) {
        this.set = set;
    }

    /**
     * метод принимает на вход объект типа и добавляет его в копию коллекции
     * @param o - добавляемый объект
     * @return возвращает новый объект ObjectBox с обновленной коллекцией
     */
    public ObjectBox<T> addObject(T o) {
        Set<T> copy = new HashSet<>(set);
        copy.add(o);
        return new ObjectBox(copy);
    }
    /**
     * метод принимает на вход объект типа и, если такой объект есть в коллекции, создает копию коллекции,
     * удаляет элемент в копии и возвращает новый объект с обновленной коллекцией
     * @param o - удаляемый объект
     * @return новый объект ObjectBox с обновленной коллекцией, либо текущий объект, если изменения не производились
     */
    public ObjectBox<T> deleteObject(T o) {
        boolean isPresent = set.contains(o);
        if (isPresent) {
            Set<T> copy = new HashSet<>(set);
            copy.remove(o);
            return new ObjectBox<T>(copy);
        }
        return this;
    }

    /**
     * Геттер для использования внутренней коллекции классами-потомками
     * @return возвращает set
     */
    protected Set<T> getSet() {
        return set;
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
