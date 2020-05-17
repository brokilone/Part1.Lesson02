package Task07_1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
        List<BigInteger> answers = new ArrayList<>();//
        long start = System.currentTimeMillis();
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < values.length; i++) {
            Future<BigInteger> task = es.submit(new MultiFactorial(values[i]));
            try {
                answers.add(task.get());
            } catch (InterruptedException |ExecutionException e) {
                e.printStackTrace();
            }
        }
        es.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("Результаты");
        for (int i = 0; i < answers.size(); i++) {
            System.out.printf("Факториал для числа %d равен %s\n", values[i], answers.get(i).toString());
        }
        System.out.println("Time remaining: " + (end - start));
    }

}
