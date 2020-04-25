package Task3;

/**
 * QuickSort
 * created by Ksenya_Ushakova at 25.04.2020
 */
public class QuickSort implements PersonSort {
    static int partition(Person[] array, int begin, int end) {
        int pivot = end;

        int counter = begin;
        for (int i = begin; i < end; i++) {
            if (array[i].compareTo(array[pivot]) < 0) {
                Person temp = array[counter];
                array[counter] = array[i];
                array[i] = temp;
                counter++;
            } else if (array[i].compareTo(array[pivot]) == 0) {
                try {
                    throw new ComparePersonException(String.format("Warning: duplicate persons: name - %s, age - %d",
                                                    array[i].getName(), array[i].getAge()));
                } catch (ComparePersonException e) {
                    //System.err.println(e.getMessage());
                }
            }
        }

        Person temp = array[pivot];
        array[pivot] = array[counter];
        array[counter] = temp;

        return counter;
    }

    public void makeSort(Person[] array, int begin, int end) {
        if (end <= begin) return;
        int pivot = partition(array, begin, end);
        makeSort(array, begin, pivot - 1);
        makeSort(array, pivot + 1, end);
    }


}
