package Task12_2;

/**
 * PersonHandler
 * Перехватчик вызовов для объекта Person
 * created by Ksenya_Ushakova at 27.05.2020
 */
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PersonHandler implements InvocationHandler {

    private Person person;

    public PersonHandler(Person person) {
        this.person = person;
    }

    /**
     * Метод перехватывает вызовы к объекту person и добавляет свое поведение
     * @param  - прокси объекта person
     * @param method вызываемый метод
     * @param args аргументы метода
     * @return возвращает вызов оригинального метода
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Привет! Я перехватчик");
        return method.invoke(person, args);
    }
}