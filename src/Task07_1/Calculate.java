package Task07_1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Calculate
 * Дан массив случайных чисел. Написать программу для вычисления факториалов всех элементов массива.
 * Использовать пул потоков для решения задачи.
 * created by Ksenya_Ushakova at 14.05.2020
 */
public class Calculate {

    /**
     * главный метод для демонстрации работы программы
     * @param args
     */
    public static void main(String[] args) {
        //массив чисел
        int[] values = {40,55,120,80,3,11,55,40, 500, 1250, 500, 1250,1250,1250, 15000, 15000, 40};
        Map<Integer, Future<BigInteger>> results = new HashMap<>();
        long start = System.currentTimeMillis();
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < values.length; i++) {
            Future<BigInteger> task = es.submit(new MultiFactorial(values[i]));
            results.put(values[i],task);
        }
        es.shutdown();
        long end = System.currentTimeMillis();

        for (Map.Entry<Integer, Future<BigInteger>> entry : results.entrySet()) {
            try {
                System.out.printf("Факториал для числа %d равен %s\n",
                        entry.getKey(), entry.getValue().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        System.out.println("Time remaining: " + (end - start));
    }

}
