package task3;


import java.util.HashMap;
import java.util.Map;

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
        ObjectBox<Number> box1 = box.addObject(5);
        ObjectBox<Number> box2 = box.deleteObject(2);
        System.out.println("ObjectBox: \n" + box.dump());
        System.out.println("ObjectBox1: \n" + box1.dump());
        System.out.println("ObjectBox2: \n" + box2.dump());

        MathBox mathBox = new MathBox(nums);
        System.out.println("MathBox: \n" + mathBox.dump());

        System.out.println("Summator :\n" + mathBox.summator());
        MathBox mathBox2 = mathBox.splitter(2);
        System.out.println("Divide by 2 \n" + mathBox2.dump());

        MathBox mathBox3 = mathBox.integerRemover(1);
        System.out.println("Remove 1: " + mathBox3.dump());

        Map<MathBox, Integer> map = new HashMap<>();
        map.put(mathBox, 1);
        map.put(mathBox2, 2);
        mathBox.splitter(5.0);
        System.out.println("From map: " + map.get(mathBox));

        //НАСТАВНИК
        // подумайте как этого избежать
        MathBox mathBox4 = mathBox.addObject(5); //переопределили методы addObject и deleteObject у Mathbox




    }
}
