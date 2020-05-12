package task3_3;

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
public class MathBox extends ObjectBox<Number> {

    /**
     * конструктор принимает на вход массив объектов Number и раскладывает их в HashSet родителя
     * @param array
     */
    public MathBox(Number[] array) {
        super (array);
    }

    /**
     * приватный конструктор, принимает на вход коллекцию Set и сохраняет ссылку на нее
     * @param set
     */
    private MathBox(Set<Number> set) {
        super(set);
    }

    /**
     * еще один конструктор для получения MathBox из ObjectBox параметризированного по Number
     * @param box объект ObjectBox
     * @param set коллекция Number
     */
    private MathBox (ObjectBox<Number> box, Set<Number> set){
        super(set);
    }

    /**
     * Метод для получения суммы элементов коллекции родителя
     * @return возвращает сумму всех элементов коллекции родителя
     */
    public double summator() {
        double sum = 0.0;
        for (Number n : super.getSet()) {
            sum += n.doubleValue();
        }
        return sum;
    }

    /**
     * Метод производит деление каждого элемента коллекции на делитель, пришедший в параметрах
     * @param divider - делитель, применяемый к каждому элементу коллекции
     * @return новый объект MathBox с обновленной коллекцией
     */
    public MathBox splitter(Number divider) {
        Iterator<Number> iterator = super.getSet().iterator();
        Set<Number> copy = new HashSet<>();
        while (iterator.hasNext()){
            Number n = iterator.next();
            copy.add(n.doubleValue()/divider.doubleValue());
        }
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
        boolean isPresent = super.getSet().contains(value);
        /*здесь также можно было новую коллекцию привести к массиву, закастить и передать в публичный конструктор,
         * но решила работать через приватный конструктор, принимающий Set
         */
        if (isPresent) {
            Set<Number> copy = new HashSet<>(super.getSet());
            copy.remove(value);
            return new MathBox(copy);
        }
        return this;
    }

    /**
     * Переопределенный метод для добавления элемента в коллекцию
     * @param o - добавляемый объект
     * @return возвращает новый объект MathBox
     */
    @Override
    public MathBox addObject(Number o) {
        ObjectBox<Number> box = super.addObject(o);
        return new MathBox(box,box.getSet());
    }

    /**
     * Переопределенный метод для удаления элемента из коллекции
     * @param o - удаляемый объект
     * @return возвращает новый объект MathBox
     */
    @Override
    public MathBox deleteObject(Number o) {
        ObjectBox<Number> box = super.deleteObject(o);
        return new MathBox(box,box.getSet());
    }
}
