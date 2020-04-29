package task2;

/**
 * ObjectBoxDemo
 * created by Ksenya_Ushakova at 29.04.2020
 */
public class ObjectBoxDemo {
    public static void main(String[] args) {
        Object[] objects = {"one", "two", "three"};
        ObjectBox box = new ObjectBox(objects);
        box.dump();
        box.addObject("four");
        box.dump();
        box.deleteObject("one");
        box.dump();
        box.deleteObject(15);
        box.dump();
    }
}
