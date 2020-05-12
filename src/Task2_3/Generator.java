package Task2_3;

import java.util.Random;

/**
 * Generator
 * класс реализует создание объекта Person с рандомными полями name, age, sex
 * @see Person
 * created by Ksenya_Ushakova at 25.04.2020
 */
 class Generator {
    /**
     * Генерация случайной строки
     * @param random - генератор псевдослучайных чисел
     * @param characters - строка символов, используемых для генерации строки
     * @param length - длина генерируемой строки
     * @return возвращает строку
     */
     private static String generateString(Random random, String characters, int length)
     {
         char[] name = new char[length];
         name[0] = Character.toUpperCase(characters.charAt(random.nextInt(characters.length())));
         for (int i = 1; i < length; i++)
         {
             name[i] = characters.charAt(random.nextInt(characters.length()));
         }
         return new String(name);
     }

    /**
     * Создание объекта класса Person  с рандомными именем, возрастом, полом
     * @return Person
     */
    public static Person generateOnePerson() {
        Random random = new Random();
        boolean isMale = random.nextBoolean();
        String characters = "abcdefghijklmnopqrstuvwxyz";
        String name = generateString(random, characters, random.nextInt(5)+3);
        if (isMale) {
            return new Person(name, random.nextInt(101), Sex.MAN);
        }
        return new Person(name, random.nextInt(101), Sex.WOMAN);
    }
}
