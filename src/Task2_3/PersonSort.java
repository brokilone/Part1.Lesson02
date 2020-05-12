package Task2_3;

/**
 * PersonSort
 * интерфейс для сортировки объектов класса Person
 * created by Ksenya_Ushakova at 25.04.2020
 */
public interface PersonSort {
    /**
     *
     * @param persons массив объектов класса Person
     * @param begin индекс первого элемента массива, подлежащего сортировке
     * @param end индекс последнего элемента массива, подлежащего сортировке
     */
    void makeSort(Person[] persons, int begin, int end);
}
