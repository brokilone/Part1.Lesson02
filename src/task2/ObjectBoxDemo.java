package task2;

/**
 * ObjectBoxDemo
 * класс демонструрует работу класса ObjectBox
 * created by Ksenya_Ushakova at 29.04.2020
 */
public class ObjectBoxDemo {
    /**
     * Главный метод для демонстрации
     * @param args
     */
    public static void main(String[] args) {
        Object[] objects = {"one", "two", "three"};
        ObjectBox box = new ObjectBox(objects);

        box.addObject("four");

        box.deleteObject("one");

        box.deleteObject(15);

        System.out.println(box.dump());
    }
}
