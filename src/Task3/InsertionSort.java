package Task3;

/**
 * InsertionSort
 * реализация сортировки вставками массива Person
 * created by Ksenya_Ushakova at 26.04.2020
 */
public class InsertionSort implements PersonSort {
    /**
     * реализует метод интерфейса PersonSort
     * @param persons массив объектов класса Person
     * @param begin индекс первого элемента массива, подлежащего сортировке
     * @param end индекс последнего элемента массива, подлежащего сортировке
     */
    @Override
    public void makeSort(Person[] persons, int begin, int end) {
        for (int left = 0; left < persons.length; left++) {
            // Вытаскиваем значение элемента
            Person current = persons[left];
            // Перемещаемся по элементам, которые перед вытащенным элементом
            int i = left - 1;
            for (; i >= 0; i--) {
                // Если вытащили значение меньшее — передвигаем больший элемент дальше
                if (current.compareTo(persons[i]) < 0) {
                    persons[i + 1] = persons[i];
                } else {
                    // Если вытащенный элемент больше — останавливаемся
                    break;
                }
            }
            // В освободившееся место вставляем вытащенное значение
            persons[i + 1] = current;
        }
    }
}
