package Task1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * MathBox
 * created by Ksenya_Ushakova at 27.04.2020
 */
public class MathBox {
    Set <Number> set = new HashSet<>();
    public MathBox(Number[] array) {
        set.addAll(Arrays.asList(array));
    }

    public double summator() {
        double sum = 0;
        for (Number number : set) {
            sum += number.doubleValue();
        }
        return sum;
    }

    public void splitter(Number divider) {
        Iterator<Number> iterator = set.iterator();
        Set copy = new HashSet();
        while (iterator.hasNext()){
            Number n = iterator.next();
            copy.add(n.doubleValue()/divider.doubleValue());
        }

        set = copy;

    }

    public void integerRemover(Integer value) {
        boolean isPresent = set.contains(value);
        if (isPresent) {
            set.remove(value);
        }
    }
}
