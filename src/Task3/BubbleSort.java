package Task3;

/**
 * BubbleSort
 * created by Ksenya_Ushakova at 25.04.2020
 */
public class BubbleSort implements PersonSort {
    @Override
    public void makeSort(Person[] array, int begin,int end) {
        boolean sorted = false;
        Person temp;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i].compareTo(array[i+1]) > 0) {
                    temp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = temp;
                    sorted = false;
                } else if (array[i].compareTo(array[i+1]) == 0) {
                    try {
                        throw new ComparePersonException(String.format("Warning: duplicate persons: name - %s, age - %d",
                                array[i].getName(), array[i].getAge()));
                    } catch (ComparePersonException e) {
                       // System.err.println(e.getMessage());
                    }
                }
            }
        }
    }
}
