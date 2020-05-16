package Task08_2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * SerialDemo - демонстрация сериализации
 * created by Ksenya_Ushakova at 15.05.2020
 */
public class SerialDemo {
    /**
     * сериализация объекта Dog в файл вместе с сылочным полем Person
     * @param args
     */
    public static void main(String[] args) {
        Person vasya = new Person("Petya", 25, 75);
        Dog sharik = new Dog("Sharik", 4, vasya);
        Path file = Paths.get("dog.bin");
        if (Files.notExists(file)) {
            try {
                Files.createFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SerialDeserial sd = new SerialDeserial();
        sd.serialize(sharik,file.toString());
    }
}
