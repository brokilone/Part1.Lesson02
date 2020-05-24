package Task06_2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * FileGenerator
 * Генератор текстовых файлов.
 *
 * created by Ksenya_Ushakova at 10.05.2020
 */
public class FileGenerator {
    private String[] words;
    private int probability;

    public FileGenerator(String[] words, int probability) {
        this.words = words;
        this.probability = probability;
    }
    /**
     * Метод создает n файлов размером size в каталоге path.
     * @param path каталог для создания файлов
     * @param n - количество файлов
     * @param size размер файла в байтах
     * @throws IOException
     */
    public void getFiles(String path, int n, int size) throws IOException {
        Path pathname = Paths.get(path);
        if (!Files.isDirectory(pathname)) {
            Files.createDirectory(pathname);
        }
        for (int i = 0; i < n; i++) {
            getOneTextFile(pathname,i,size);
        }
    }

    /**
     * Метод создает один текстовый файл в директории path размером size
     * @param path каталог для создания файла
     * @param n - порядковый номер
     * @param size - размер файла в байтах
     * @throws IOException
     */
    private void getOneTextFile(Path path, int n, int size) throws IOException {
        Path file = Paths.get(path.toString(), "file" + n + ".txt");
        if (Files.notExists(file)) {
            Files.createFile(file);
        }
        writeInFile(size, file.toFile());
    }

    /**
     * Метод записывает в файл сгенерированный текст
     * @param size - объем текста
     * @param file файл
     */
    private void writeInFile(int size, File file) {
        ParagraphGenerator gen = new ParagraphGenerator(words);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            int length = size; //максимальная длина параграфа
            int currentlength = 0;//текущий размер файла
            while (currentlength < size) {
                String par = gen.generateOneParagraph(length, probability);
                writer.write(par);
                currentlength += par.length();
                length = size - currentlength;
                writer.flush();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }



}
