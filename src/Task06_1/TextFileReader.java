package Task06_1;

import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * TextFileReader
 * Программа читает текстовый файл, составляет отсортированный по алфавиту список слов,
 * найденных в файле, и сохраняет его в файл-результат. Найденные слова не должны повторяться,
 * регистр не должен учитываться. Одно слово в разных падежах – это разные слова.
 * created by Ksenya_Ushakova at 07.05.2020
 */
public class TextFileReader {
    /**
     * Основной демонстрационный метод
     *
     * @param args в параметры приходит имя файла для чтения, имя файла для записи
     */
    public static void main(String[] args)  {
        //проверка входящих аргументов
        if (args != null && args.length >= 2) {
            //читаем построчно из файла
            StringBuilder sb = getStringBuilderFromFile(args[0]);
            //складываем слова из StringBuffer в отсортированную коллекцию уникальных слов
            Set<String> words = getWords(sb);
            //пишем в файл
            writeWordsToFile(args[1], words);

        } else {
            System.out.println("Для работы программы требуется указать в параметрах имена файлов для чтения и записи");
        }
    }

    /**
     * Метод читает из файла строки в StringBuffer, преобразуя в нижний регистр
     *
     * @param fileName - имя файла для чтения
     * @return возвращает StringBuffer
     */
    private static StringBuilder getStringBuilderFromFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.ready()) {
                sb.append(reader.readLine().toLowerCase());
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return sb;
    }

    /**
     * Метод разбивает строку на слова, игнорируя пробелы и пунктуацию,
     * и помещает слова в сортированную коллекцию
     * @param sb StringBuffer, содержащий строки, прочитанные из файла
     * @return возвращает отсортированную коллекцию уникальных слов
     */
    private static Set<String> getWords(StringBuilder sb) {
        String[] data = sb.toString().split("[\\p{P} \\t\\n\\r]");
        Set<String> words = new TreeSet<>(Arrays.asList(data));
        return words;
    }

    /**
     * Метод записывает слова из коллекции в файл
     * @param fileName - имя файла для записи
     * @param words отсортированная коллекция слов
     */
    private static void writeWordsToFile(String fileName, Set<String> words) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String word : words) {
                writer.write(word + "\n");
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }


}


