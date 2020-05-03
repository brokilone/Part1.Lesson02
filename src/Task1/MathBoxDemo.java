package Task1;

import java.util.*;

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

        System.out.println("Summator: \n" + box.summator());

        MathBox box2 = box.integerRemover(51);
        System.out.println("Remove value 51: \n" + box2);

        MathBox box3 = box.splitter(2);
        System.out.println("Splitter: \n" + box);

        Number[] nums2 = {4,8,51,11,-4,42,11};
        MathBox box4 = new MathBox(nums2);
        System.out.println("Box equals box4: " + box.equals(box4));

        System.out.println("Add Mathbox objects to Map");
        Map<MathBox, Integer> map = new HashMap<>();
        map.put(box, 1);
        map.put(box2, 2);
        box.splitter(5.0);//box не изменится, метод вернет новый объект, но в демо целях мы не сохраняем ссылку на него
        System.out.println(map.get(box));
    }
}
