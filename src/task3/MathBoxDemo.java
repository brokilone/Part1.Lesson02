package task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main
 * класс демонструрует работу класса MathBox
 * created by Ksenya_Ushakova at 27.04.2020
 */
public class MathBoxDemo {
    /**
     * Главный метод для демонстрации
     * @param args
     */
    public static void main(String[] args) {
        /**
         * Создается три объекта класса MathBox, каждый принмиает на вход свой массив Number
         */
        Number[] nums = {4,8,51,11,-4,8,42,11};
        MathBox box = new MathBox(nums);
        Number[] nums2 = {10,12,16,0};
        MathBox box2 = new MathBox(nums2);
        Number[] nums3 = {4,8,51,11,-4,42};
        MathBox box3 = new MathBox(nums3);

        System.out.println("Summator:");
        double sum = box.summator();
        System.out.println(sum);
        box.deleteObject(51);
        System.out.println("After deleting");
        box.dump();
        box.splitter(2);
        System.out.println("After dividing");
        box.dump();

        List<MathBox> list = new ArrayList<MathBox>();
        list.add(box);
        list.add(box2);
        System.out.println("Inside list");
        for (MathBox mathBox : list) {
            mathBox.dump();
        }
        System.out.println("Equals or not");
        System.out.println(box.equals(box2));//TODO
        System.out.println(box.equals(box3));

        Map<String, MathBox> map = new HashMap<>();
        map.put("first", box);
        map.put("second", box2);
        map.put("third", box3);
        System.out.println("Inside map");
        for (Map.Entry<String, MathBox> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        ObjectBox objectBox = box;
        //box2 = objectBox;
    }
}
