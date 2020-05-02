package task3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * MathBox
 * класс хранит в себе коллекцию неповторящихся объектов типа Number
 * унаследован от ObjectBox
 * @see ObjectBox
 * created by Ksenya_Ushakova at 27.04.2020
 */
public class MathBox extends ObjectBox<Number>{

    /**
     * конструктор принимает на вход массив объектов Number и раскладывает их в HashSet родителя
     * @param array
     */
    public MathBox(Number[] array) {
        super (array);
    }

    /**
     * Метод для получения суммы элементов коллекции родителя
     * @return возвращает сумму всех элементов коллекции родителя
     */
    public double summator() {
        double sum = 0.0;
        for (Number n : super.set) {
            sum += n.doubleValue();
        }
        return sum;
    }

    /**
     * Метод выполняет поочередное деление всех хранящихся в коллекции элементов на делитель, являющийся аргументом метода.
     * Хранящиеся в объекте данные полностью заменяются результатами деления.
     * @param divider - делитель, участвующий в операциях деления
     */
    public void splitter(Number divider) {
        Iterator<Number> iterator = super.set.iterator();
        Set copy = new HashSet();
        while (iterator.hasNext()){
            Number n = iterator.next();
            copy.add(n.doubleValue()/divider.doubleValue());
        }

        set = copy;

    }

}
