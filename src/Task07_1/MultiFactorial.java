package Task07_1;

import java.math.BigInteger;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.Callable;

/**
 * MultiFactorial
 * Класс используется для вычисления факториала числа
 * created by Ksenya_Ushakova at 14.05.2020
 */
public class MultiFactorial implements Callable {
    private int n;
    private static NavigableMap<Integer, BigInteger> cache = new TreeMap<>();//мапа для хранения ранее вычисленных значений

    /**
     * публичный конструктор принимает на вход целое число для вычисления факториала
     * @param n
     */
    public MultiFactorial(int n) {
        this.n = n;
    }

    /**
     * Метод возвращает результат вычисления факториала числа n
     * Перед вычислением проводится проверка наличия элемента в cache,
     * если факториал числа в cache отсутствует, ищем наибольшее значение ключа, меньше n.
     * Полученный результат сохраняется для дальшейшего использования
     * @return BigInteger - факториал числа n
     * @throws Exception
     */
    @Override
    public BigInteger call() throws Exception {
        BigInteger answer = BigInteger.valueOf(1);
        if (n == 0 || n == 1) {
            return answer;
        }
        int start = 2;
        if (cache.containsKey(n)) {
            return cache.get(n);
        } else {
            if (cache.floorEntry(n) != null) {
                start = cache.floorKey(n);
                answer = cache.get(start);
            }
        }
        for (int i = start; i <= n; i++) {
            answer = answer.multiply(BigInteger.valueOf(i));
        }
        cache.put(n, answer);
        return answer;
    }


}
