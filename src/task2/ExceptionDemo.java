package task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * task2.ExceptionDemo
 * Демонстрация генерации исключения
 * при попытке извлечь корень из отрицательного числа;
 * created by Ksenya_Ushakova at 24.04.2020
 */
public class ExceptionDemo {

    static List <Integer> list = new ArrayList<>();//список для сохранения генерируемых чисел
    static int n = 0;//хранит количество чисел, которое будет сгенерировано


    /**
     * Главный метод, иллюстрирующий работу программы.
     * @param args
     */
    public static void main(String[] args) {
        n = setNbyUserInput(); // считываем кол-во с клавиатуры
        generateAndStore(n); //генерируем n  чисел и сохраняем в list
        calculateSqrt(list); //вычисляем квадратный корень из каждого из n чисел
    }

    /**
     * Метод считывает с клавиатуры количество чисел, которое требуется сгенерировать.
     * Если введенные данные не число, появляется сообщение и программа завершается.
     * @return возвращает переменную n, в которую записывается введенное пользователем число
     */
    static int setNbyUserInput () {
        System.out.println("Введите число: ");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            n = Integer.valueOf(reader.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Введенные данные не являются числом!");
        } catch (IOException e) {
            System.err.println("Ошибка ввода вывода");
        }
        return n;
    }

    /**
     * Метод генерирует целые числа в диапазоне от -100 до 100
     * и сохраняет их в список;
     * @param amount - количество генерируемых чисел
     */
    static void generateAndStore(int amount) {
        int i = 0;
        while (i < amount) {
            int k = (int) (Math.random()*(200+1)) - 100;
            list.add(k);
            i++;
        }
    }

    /**
     * Метод вычисляет корень квадратный q каждого числа k из списка,
     * если q*q равно k, выводит k в консоль;
     * перед вычислением корня проводится проверка числа на неотрицательность,
     * если число открицательное, выбрасывается исключение, ловится в catch и печатается сообщение в консоль.
     * @param list - список целых чисел
     */
    static void calculateSqrt(List<Integer> list) {
        for (Integer k : list) {
            try {
                if (k < 0) {
                    throw new Exception(String.format("Невозможно извлечь корень из отрицательного числа: %d", k));
                }
                Double q = Math.sqrt(k);
                if (q.intValue()*q.intValue() == k) {
                    System.out.println(k);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
