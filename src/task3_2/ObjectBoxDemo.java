
package task3_2;


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

        ObjectBox box2 = box.addObject("four");

        box2 = box2.deleteObject("one");

        System.out.println(box.dump());
        System.out.println(box2.dump());
    }
}
