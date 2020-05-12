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
    private Set <Number> set = new HashSet<>();

    /**
     * конструктор принимает на вход массив объектов Number и раскладывает их в HashSet
     * @param array
     */
    public MathBox(Number[] array) {
        set.addAll(Arrays.asList(array));
    }

    /**
     * приватный конструктор, принимает на вход коллекцию Set и сохраняет ссылку на нее
     * @param set
     */
    private MathBox(Set<Number> set) {
        this.set = set;
    }

    /**
     * Метод для получения суммы элементов коллекции
     * @return возвращает сумму значений doublevalue всех элементов коллекции
     */
    public double summator() {
        double sum = 0.0;
        for (Number number : set) {
            sum += number.doubleValue();
        }
        return sum;
    }

    /**
     * Метод производит деление каждого элемента коллекции на делитель, пришедший в параметрах
     * @param divider - делитель, применяемый к каждому элементу коллекции
     * @return новый объект MathBox с обновленной коллекцией
     */
    public MathBox splitter(Number divider) {
        Iterator<Number> iterator = set.iterator();

        Set<Number> copy = new HashSet<>();
        while (iterator.hasNext()){
            Number n = iterator.next();
            copy.add(n.doubleValue()/divider.doubleValue());
        }
        /*можно сделать новый массив Number и сохранять при переборе туда измененные элементы старой
         *коллекции, а потом передать в конструктор. Но в задании нет явного запрета на кол-во конструкторов,
         *поэтому сделан еще один конструктор*/
        return new MathBox(copy);
    }

    /**
     * Метод удаляет из коллекции элемент, совпадающий по значению с поступившим на вход Integer.
     * Если такого значения нет, коллекция не изменяется
     * @param value - значение типа Integer
     * @return Mathbox - возвращает новый объект с обновленной коллекцией, либо возвращает текущий объект, если изменения
     * отсутствуют
     */
    public MathBox integerRemover(Integer value) {
        boolean isPresent = set.contains(value);

        if (isPresent) {
            Set<Number> copy = new HashSet<>(set);
            copy.remove(value);
            return new MathBox(copy);
        }
        return this;
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
