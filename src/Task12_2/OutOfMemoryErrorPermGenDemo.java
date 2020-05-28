package Task12_2;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * OutOfMemoryErrorPermGenDemo
 * Задание 2.
 * Доработать программу так, чтобы ошибка OutOfMemoryError
 * возникала в Metaspace /Permanent Generation
 *
 * created by Ksenya_Ushakova at 27.05.2020
 */
public class OutOfMemoryErrorPermGenDemo {
    public static final int COUNT = 100_000_000;
    public static List<Person> list = new ArrayList<>();

    /**
     * В методе запускается цикл создания загрузчиков класса и объектов прокси,
     * прокси сохраняются в список
     * @param args
     * @throws MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException {//ключ -XX:MaxMetaspaceSize=64m

        for (int i = 0; i < COUNT; i++) {
            String fileName = "file:" + i + ".jar";
            URL[] classLoadURL = new URL[] {new URL(fileName)};

            URLClassLoader loader = new URLClassLoader(classLoadURL);
            Worker worker = new Worker("Вася", "очень_сложная_задача");
            Class<?>[] interfaces = worker.getClass().getInterfaces();

            Person proxyWorker = (Person) Proxy.newProxyInstance(loader, interfaces, new PersonHandler(worker));
            list.add(proxyWorker);
        }

    }
}
