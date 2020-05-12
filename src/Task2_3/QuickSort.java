package Task2_3;

/**
 * QuickSort
 * реализация быстрой сортировки массива Person
 * created by Ksenya_Ushakova at 25.04.2020
 */
class QuickSort implements PersonSort {
    /**
     * реализует метод интерфейса PersonSort
     * @param array массив объектов Person
     * @param leftBorder индекс первого элемента массива, подлежащего сортировке
     * @param rightBorder индекс последнего элемента массива, подлежащего сортировке
     */
    public void makeSort(Person[] array, int leftBorder, int rightBorder) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        Person pivot = array[(leftMarker + rightMarker) / 2]; //опорная точка
        do {
            // Двигаем левый маркер слева направо пока элемент меньше, чем pivot
            while (array[leftMarker].compareTo(pivot) < 0 ) {
                leftMarker++;
            }
            // Двигаем правый маркер, пока элемент больше, чем pivot
            while (array[rightMarker].compareTo(pivot) > 0) {
                rightMarker--;
            }
            // Проверим, не нужно обменять местами элементы, на которые указывают маркеры
            if (leftMarker <= rightMarker) {
                // Левый маркер будет меньше правого только если мы должны выполнить swap
                if (leftMarker < rightMarker) {
                    Person temp = array[leftMarker];
                    array[leftMarker] = array[rightMarker];
                    array[rightMarker] = temp;
                }
                // Сдвигаем маркеры, чтобы получить новые границы
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        // Выполняем рекурсивно для частей
        if (leftMarker < rightBorder) {
            makeSort(array, leftMarker, rightBorder);
        }
        if (leftBorder < rightMarker) {
            makeSort(array, leftBorder, rightMarker);
        }
    }
}
