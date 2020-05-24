package Task09_1;


import java.io.*;
import java.nio.file.Files;

import java.nio.file.Paths;

/**
 * MyClassLoader - пользовательский загрузчик для загрузки
 * скомпилированного в рантайме класса
 * created by Ksenya_Ushakova at 19.05.2020
 */
public class MyClassLoader extends ClassLoader{
    /**
     * Публичный конструтор
     * @param parent - ClassLoader - родительский загрузчик для делегирования
     */
    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * Метод для загрузки класса
     * Если имя класса совпадает с Task09_1.SomeClass, вызывается переопределенный
     * метод findClass для поиска файла с скомпилированным Java компилятором байт-кодом
     * @param name имя класса
     * @return Class
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if ("Task09_1.SomeClass".equals(name)) {
            return findClass(name);
        }
        return super.loadClass(name);
    }

    /**
     * Метод подменяет  поиск класса для дальнейшей загрузки
     * Считывает из файла с скомпилированным Java компилятором байт-кодом все байты и передает их для загрузки
     * @param name имя класса
     * @return Class
     */
    @Override
    public Class<?> findClass(String name) {

        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get("src\\Task09_1\\SomeClass.class"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defineClass(name, bytes, 0, bytes.length);

    }




}
