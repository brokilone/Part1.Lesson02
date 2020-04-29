package Task1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * MathBox
 * класс хранит в себе коллекцию неповторящихся объектов типа Number
 * created by Ksenya_Ushakova at 27.04.2020
 */
public class MathBox {
    /**
     * коллекция для хранения элементов массива, полученного на вход конструктора
     */
    Set <Number> set = new HashSet<>();

    /**
     * конструктор принимает на вход массив объектов Number и раскладывает их в HashSet
     * @param array
     */
    public MathBox(Number[] array) {
        set.addAll(Arrays.asList(array));
    }

    /**
     * Метод для получения суммы элементов коллекции
     * @return возвращает сумму всех элементов коллекции
     */
    public Double summator() {
        Double sum = 0.0;
        for (Number number : set) {
            sum += number.doubleValue();
        }
        return sum;
    }

    /**
     * Метод выполняет поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода.
     * Хранящиеся в объекте данные полностью заменяются результатами деления.
     * @param divider - делитель, участвующий в операциях деления
     */
    public void splitter(Number divider) {
        Iterator<Number> iterator = set.iterator();
        Set copy = new HashSet();
        while (iterator.hasNext()){
            Number n = iterator.next();
            copy.add(n.doubleValue()/divider.doubleValue());
        }

        set = copy;

    }

    /**
     * Метод удаляет из коллекции элемент, совпадающий по значению с поступившим на вход Integer.
     * Если такого значения нет, коллекция не изменяется
     * @param value - значение типа Integer
     */
    public void integerRemover(Integer value) {
        boolean isPresent = set.contains(value);
        if (isPresent) {
            set.remove(value);
        }
    }

    /**
     * Преобразование объекта MathBox в строковое представление.
     * @return возвращает строку, представляющую собой строковое представление содержащейся внутри коллекции
     */
    @Override
    public String toString() {
        return set.toString();
    }

    /**
     * Определяем как будет вычисляться хэш код для объекта MathBox
     * @return целочисленный хэш код объекта
     */
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (Number n : set) {
            hashCode = 31 * hashCode + (n == null ? 0 : n.hashCode());
        }
        return hashCode;
    }

    /**
     * Метод, используемый для сравнения объекта класса MathBox с другим объектом.
     * Сравнение организовано по содержимому коллекции. Перел этим предусмотренна проверка ссылок,
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

        MathBox box = (MathBox) obj;
        if (set.size() != box.set.size()) return false;
        for (Number n : set) {
            if (!box.set.contains(n)) {
                return false;
            }
        }
        return true;
    }
}
