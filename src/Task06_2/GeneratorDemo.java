package Task06_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * GeneratorDemo - демонстрационный класс для генерации текстовых файлов
 * created by Ksenya_Ushakova at 11.05.2020
 */
public class GeneratorDemo {

    /**
     * главный метод
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String path = "";
        int n = 0;
        int size = 0;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Укажите путь к папке: ");
            path = reader.readLine();
            System.out.println("Укажите количество файлов");
            n = Integer.parseInt(reader.readLine());
            System.out.println("Укажите размер файла");
            size = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Library library = new Library();
        FileGenerator gen = new FileGenerator(library.getWords(), 100);
        gen.getFiles(path,n,size);
    }
}
