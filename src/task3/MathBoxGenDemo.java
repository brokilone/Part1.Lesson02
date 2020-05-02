package task3;


/**
 * Main
 * класс демонструрует работу класса MathBox, унаследованного от типизированного класса ObjectBox
 * created by Ksenya_Ushakova at 27.04.2020
 */
public class MathBoxGenDemo {
    /**
     * Главный метод для демонстрации
     * @param args
     */
    public static void main(String[] args) {
        Number[] nums = {1,2,3,4};
        ObjectBox<Number> box = new ObjectBox<>(nums);
        box.addObject(5);
        box.deleteObject(2);
        System.out.println("ObjectBox: \n" + box.dump());

        MathBox mathBox = new MathBox(new Number[0]);
        mathBox.addObject(5);
        mathBox.addObject(7.0);
        mathBox.addObject(15);
        System.out.println("MathBox: ");
        System.out.println("Summator :\n" + mathBox.summator());
        mathBox.splitter(2);
        System.out.println("Divide by 2 \n" + mathBox.dump());


        mathBox = (MathBox) box; //попытка закастить и положить ObjectBox в MathBox приведет к ClassCatException



    }
}
