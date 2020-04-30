package task1;

/**
 * task1.HelloWorld
 * В классе моделируется ряд исключительных ситуаций: выход за пределы массива, попытка обращения к null-ссылке,
 * попытка деления на ноль.
 *
 * created by Ksenya_Ushakova at 23.04.2020
 */
public class HelloWorld {

    static int[] numbers = {24,15,-7,11,0};
    static String testString = null;

    /**
     * Демонстрация генерации исключений
     * @param args
     */
    public static void main(String[] args) {
        // закомментированный код - дело не благородное. Подумайте, как можно его избежать.
        //моделирует выброс исключения ArrayIndexOutOfBoundsException
        //testArrayIndexOutOfBoundExc(numbers);
        //моделирует выброс исключения NullPointerException
        //testNPE(testString,"");
        //моделирует выброс исключения ArithmeticException
        testAnotherException();
    }

    /**
     * Метод моделирует выброс исключения ArrayIndexOutOfBoundsException;
     * unchecked исключение в сигнатуре метода указано для наглядности.
     * @param numbers - массив целочисленных значений
     * @throws ArrayIndexOutOfBoundsException
     */
    public static void testArrayIndexOutOfBoundExc(int[] numbers) throws ArrayIndexOutOfBoundsException {
        for (int i = 0; i < 6; i++) {
            System.out.println(numbers[i]);
        }
    }

    /**
     * Метод сравнивает две строки с помощью equals, моделирует выброс исключения NullPointerException;
     * unchecked исключение в сигнатуре метода указано для наглядности.
     * @param s1 - первая строка
     * @param s2 - вторая строка
     * @return возвращает true только тогда, когда s1.equals(s2);
     * @throws NullPointerException
     */
    public static boolean testNPE(String s1, String s2) throws NullPointerException{
        return s1.equals(s2);
    }

    /**
     * Метод моделирует выброс исключения ArithmeticException;
     * unchecked исключение в сигнатуре метода указано для наглядности.
     * в методе объявляются 2 целочисленных массива -  числители и знаменатели;
     * в связи с присутствием в масcиве знаменателей нуля моделируется выброс исключения.
     * @throws ArithmeticException
     */
    public static void testAnotherException() throws ArithmeticException{
        int [] numer = {4,2,5,4};
        int[] denom = {2,1,0,2};
        for (int i = 0; i < numer.length; i++) {
            if (numer[i] % denom[i] == 0) {
                System.out.printf("%d : %d = %d, остаток от деления отсутствует\n", numer[i], denom[i], numer[i] / denom[i]);
            } else {
                System.out.printf("%d : %d = %d, остаток от деления равен %d\n", numer[i], denom[i], numer[i] / denom[i], numer[i] % denom[i]);
            }
        }
    }

}

