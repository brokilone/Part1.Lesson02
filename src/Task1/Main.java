package Task1;

import java.util.Set;

/**
 * Main
 * created by Ksenya_Ushakova at 27.04.2020
 */
public class Main {
    public static void main(String[] args) {
        Number[] nums = {4,8,51,11,-4,8,42,11};
        MathBox box = new MathBox(nums);



//        System.out.println(box.summator());
//        box.integerRemover(51);
        System.out.println(box.set);
        box.splitter(2);
        System.out.println(box.set);
    }
}
