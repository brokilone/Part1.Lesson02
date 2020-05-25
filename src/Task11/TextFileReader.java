package Task11;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TextFileReader
 * Программа читает текстовый файл, составляет отсортированный по алфавиту список слов,
 * найденных в файле, и сохраняет его в файл-результат. Найденные слова не должны повторяться,
 * регистр не должен учитываться. Одно слово в разных падежах – это разные слова.
 *
 * Задание обновлено в соответствии с ДЗ-11:
 * Перевести одну из предыдущих работ на использование стримов и лямбда-выражений там,
 * где это уместно (возможно, жертвуя производительностью)
 *
 * created by Ksenya_Ushakova at 25.05.2020
 */
public class TextFileReader {

    /**
     * Основной демонстрационный метод
     * @param args в параметры приходит имя файла для чтения, имя файла для записи
     */
    public static void main(String[] args)  {
        //проверка входящих аргументов
        if (args != null && args.length >= 2) {

            List<String> listOfWords = null;
            try {
                listOfWords = getListOfWords(args[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            writeWordsToFile(args[1], listOfWords);

        } else {
            System.out.println("Для работы программы требуется указать в параметрах имена файлов для чтения и записи");
        }
    }

    /**
     * Метод получает stream из строк файла, разделяет их на слова,
     * отбрасывает повторы и сортирует, затем сохраняет в список
     * @param arg - исходный файл
     * @return list
     * @throws IOException
     */
    private static List<String> getListOfWords(String arg) throws IOException {
        Stream<String> stream = Files.lines(Paths.get(arg))
                                       .flatMap(s -> Stream.of(s.split("[\\p{P}\\s\\t\\n\\r]+")));
        List<String> list = stream.distinct()
                            .sorted(String::compareToIgnoreCase)
                            .collect(Collectors.toList());
        return list;
    }

    /**
     * Метод записывает слова из списка в файл
     * @param fileName - имя файла для записи
     * @param data отсортированный список слов
     */
    private static void writeWordsToFile(String fileName, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            //тут, конечно, в отличие от предыдущего метода, число строчек
            //с использованием лямбды только увеличилось,
            //сделано исключительно в целях поупражняться в новой теме :-)
            data.stream().map(s -> s + "\n").forEach(str -> {
                try {
                    writer.write(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


