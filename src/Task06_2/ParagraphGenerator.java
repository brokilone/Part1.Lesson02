package Task06_2;

import java.util.Random;

/**
 * ParagraphGenerator Generator
 * класс для генерации абзаца текста по правилам:
 * В одном абзаце 1<=n3<=20 предложений.
 * Предложение состоит из 1<=n1<=15 слов.
 * В предложении после произвольных слов могут находиться запятые.
 * Слово состоит из 1<=n2<=15 латинских букв
 * Слова разделены одним пробелом
 * Предложение начинается с заглавной буквы
 * Предложение заканчивается (.|!|?)+" "
 * В конце абзаца стоит разрыв строки и перенос каретки.
 * Есть массив слов 1<=n4<=1000
 * Есть вероятность probability вхождения одного из слов этого массива
 * в следующее предложение (1/probability).
 *
 * created by Ksenya_Ushakova at 10.05.2020
 */
public class ParagraphGenerator {
    private Random random;
    private String[] words;


    /**
     * Публичный конструктор:
     * в конструкторе создается объект класса Random,
     * генерируется длина массива библиотеки от 1 до 1000,
     * затем массив заполняется случайными словами
     */
    public ParagraphGenerator(String[] words){
        random = new Random();
        this.words = words;
    }

    /**
     * Метод генерирует один текстовый абзац
     * @param size - максимальный размер абзаца
     * @param probability - вероятность вхождения слова из массива-библиотеки
     *                      в каждое предложение абзаца в процентах
     * @return возвращает строку, представляющую собой один текстовый обзац
     */
    public String generateOneParagraph(int size, int probability) {
        int sentenceAmount = random.nextInt(20)+1; // кол-во предложений в абзаце
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentenceAmount-1; i++) {
            //предложения, кроме последнего, завершаются разделяющим пробелом
            sb.append(generateOneSentence(probability) + " ");
        }
        //последнее предложение завершается разрывом строки и переносом каретки
        sb.append(generateOneSentence(probability) + "\r\n");
        //если длина абзаца превысила заданный максимальный размер, обрезаем

        if (sb.length() > size) {
            String text = fitToSize(size, sb);
            return text;
        }
        return sb.toString();
    }

    private String fitToSize(int size, StringBuilder sb) {
        String text = sb.substring(0,size);
        char end = text.charAt(text.length() - 1);
        if (Character.isLetter(end)){
            text = text.substring(0,text.length()-1) + endOfSentence();
        }
        return text;
    }

    /**
     * Метод генерирует одно текстовое предложение
     * @param probability - вероятность вхождения слова из массива-библиотеки
     *                      в предложение в процентах
     * @return возвращает строку, представляющуюю собой одно предложение
     */
    private String generateOneSentence(int probability){

        int length = random.nextInt(15)+1; // кол-во слов в предложении
        //определяем позицию слова в предложении из массива библиотеки
        int position = position(needFromLibrary(probability),length);

        StringBuilder sb = new StringBuilder();
        //слово из массива либо генерация нового, завершается пробелом
        for (int i = 0; i < length; i++) {
            if (i == position) {
                String libword = words[random.nextInt(words.length)];
                sb.append(libword + " ");
            } else {
                sb.append(generateOneWordWithComma() + " ");
            }
        }

        String sentence = sb.toString().trim();//обрезаем последний пробел
        //устанавливаем начало с заглавной буквы, обрезаем запятую и добавляем ./?/!
        if (sentence.endsWith(",")) {
            return capitalize(sentence.substring(0,sentence.length()-1) + endOfSentence());
        }
        return capitalize(sentence + endOfSentence());
    }

    /**
     * Возвращает символ в конце предложения
     *
     * @return равновероятно возвращает '.' или '?' или '!'
     */
    private char endOfSentence(){
        int choice = random.nextInt(3);

        switch (choice) {
            case 0:
                return '.';
            case 1:
                return '?';
            case 2:
                return '!';
            default:
                return '.';
        }
    }

    /**
     * Возвращает переданную строку с заглавной буквы
     * @param s - строка для преобразования
     * @return String
     */
    private String capitalize (String s){
        char fst = Character.toUpperCase(s.charAt(0));
        s = fst + s.substring(1);
        return s;
    }

    /**
     * Метод для определения необходимости извлечь слово из библиотеки,
     * исходя из заданной пользователем вероятности
     * @param probability - принимает на вход пользовательское значение в процентах
     * @return возвращает true, если сгенерированное число от 1 до 100 меньше заданной вероятности
     */
    private boolean needFromLibrary(int probability) {
        int i = random.nextInt(100);
        return i < probability;
    }

    /**
     * Метод определяет на какой позиции в предложении окажется слово из библиотеки
     * @param needFromArray принимает на вход булево значение, извлекать ли слово из библиотеки
     * @param length длина предложения
     * @return возвращает позицию слова, либо -1, если needFromArray равен false
     */
    private int position(boolean needFromArray, int length) {
        if (needFromArray) {
            return random.nextInt(length);
        }
        return -1;
    }

    /**
     * Метод генерирует случайное слово
     * @return возвращает случайное слово в строковом представлении
     */
    private String generateOneWord() {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        int length = random.nextInt(15)+1; //кол-во символов в слове
        char[] word = new char[length];
        for (int i = 0; i < length; i++)
        {
            word[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(word);
    }

    /**
     * Определяет, будет ли после слова запятая и, при необходимости, конкатенирует ее
     * @return слово с запятой или без
     */
    private String generateOneWordWithComma(){
        String word = generateOneWord();
        //установим вероятность запятой после слова = 20%;
        boolean hasComma = random.nextInt(10) < 2;
        if (hasComma) {
            return word + ","; //добавляем запятую после слова
        }
        return word;
    }

}
