package task2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ObjectBox
 * created by Ksenya_Ushakova at 29.04.2020
 */
public class ObjectBox {
    Set set = new HashSet();

    ObjectBox (Object[] objects) {
        set.addAll(Arrays.asList(objects));
    }

    public void addObject(Object o) {
        set.add(o);
    }

    public void deleteObject(Object o) {
        boolean isPresent = set.contains(o);
        if (isPresent) {
            set.remove(o);
        }
    }

    public void dump() {
        System.out.println(set.toString());
    }

}
