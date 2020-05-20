package Task09_1;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Main
 * <p>
 * Программа с консоли построчно считывает код метода doWork.
 * Код не должен требовать импорта дополнительных классов.
 * После ввода пустой строки считывание прекращается
 * и считанные строки добавляются в тело метода public void doWork() в файле SomeClass.java.
 * Файл SomeClass.java компилируется программой (в рантайме) в файл SomeClass.class.
 * Полученный файл подгружается в программу с помощью кастомного загрузчика
 * Метод, введенный с консоли, исполняется в рантайме
 * <p>
 * <p>
 * created by Ksenya_Ushakova at 19.05.2020
 */
public class Main {
    /**
     * Главный метод, демонстрирующий работу программы
     *
     * @param args
     */
    public static void main(String[] args) {
        //получаем с консоли код метода doWork
        StringBuilder code = getCode();

        Path file = Paths.get("src\\Task09_1\\SomeClass.java");
        Path copy = null;

        try {
            //создаем временный файл для объединения файла с пустым методом
            //и пользовательского кода
            copy = Files.createTempFile(Paths.get("src\\Task09_1"),
                    "copy_", ".tmp");
            writeTempFile(code, file, copy);
            //перезаписываем SomeClass
            writeInJavaFile(file, copy);
            //удаляем временный файл
            Files.delete(copy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();//компилируем
        compiler.run(null, null, null, "src\\Task09_1\\SomeClass.java");

        useCustomClassLoader();//используем кастомный загрузчик для загрузки класса и запуска метода

    }

    /**
     * Метод для получения пользовательского кода метода doWork
     * Для упрощения тестирования выполнена подмена консольного ввода на
     * считывание из файла text.txt
     * @return StringBuilder, содержащий пользовательский код
     */
    private static StringBuilder getCode () {
        System.out.println("Введите текст программы:");
        StringBuilder code = new StringBuilder();
        String input;
        InputStream in = System.in;
        //подмена источника
        try {
            System.setIn(new FileInputStream("src\\Task09_1\\test.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                input = reader.readLine();
                if (input.equals("")) {
                    break;
                }
                code.append(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setIn(in);//возвращаем источник консольного ввода

        return code;
    }

    /**
     * Метод объединяет пользовательский код с телом класса SomeClass во временном файле
     * @param code  StringBuilder - полученный код метода doWork
     * @param file файл SomeClass.java
     * @param copy временный файл
     */
    private static void writeTempFile (StringBuilder code, Path file, Path copy){
        long length = file.toFile().length();
        char[] buffer = new char[(int) length];
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(copy.toFile()));
             BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
            if (reader.ready()) {
                reader.read(buffer);
            }
            writer.write(buffer, 0, 171);
            writer.write(code.toString() + "\n");
            writer.write(buffer, 171, (int) (length - 171));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Метод записывает в файл SomeClass.java из временного файла полный код класса
     * @param file файл SomeClass.java
     * @param copy временный файл
     */
    private static void writeInJavaFile (Path file, Path copy){
            try (BufferedReader reader = new BufferedReader(new FileReader(copy.toFile()));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()))) {
                while (reader.ready()) {
                    writer.write(reader.readLine() + "\n");
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    /**
     * Метод использует кастомный загрузчик для загрузки класса и вызывает на исполнение
     * метод doWork  его инстанса
     */
    private static void useCustomClassLoader() {
        MyClassLoader classLoader = new MyClassLoader(Main.class.getClassLoader());
        try {
            Class<?> cl = classLoader.loadClass("Task09_1.SomeClass");
            Object o = cl.newInstance();
            Method doWork = cl.getMethod("doWork");
            doWork.invoke(o);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    }
