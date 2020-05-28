package Task12_1;

import java.util.HashMap;
import java.util.Map;

/**
 * OutOfMemoryHeapDemo
 * Необходимо создать программу, которая продемонстрирует утечку памяти в Java.
 * При этом объекты должны не только создаваться, но и периодически частично удаляться,
 * чтобы GC имел возможность очищать часть памяти.
 * Через некоторое время программа должна завершиться
 * с ошибкой OutOfMemoryError c пометкой Java Heap Space.
 * <p>
 * created by Ksenya_Ushakova at 25.05.2020
 */
public class OutOfMemoryHeapDemo {
    public static final int COUNT = 100_000_000;
    public static final String PREFIX = "DEMO_ELEMENT";
    public static Map<String, OutOfMemoryHeapDemo> map = new HashMap<>();

    /**
     * Метод запускает цикл наполнения map объектами данного класса по ключу
     * Prefix+номер цикла
     * Каждый 100-й объект вызывает удаление объекта с предыдущим ключом
     * @param args
     */
    public static void main(String[] args) {//ключ ограничения heap -Xmx512m
        for (int i = 0; i < COUNT; i++) {
            map.put((PREFIX + i), new OutOfMemoryHeapDemo());
            if (i % 100 == 0) {
                map.remove(PREFIX + (i-1));
            }
        }
    }

}
