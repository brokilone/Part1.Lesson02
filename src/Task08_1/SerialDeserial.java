package Task08_1;

import java.io.*;
import java.lang.reflect.*;

/**
 * SerialDeserial
 * Задание 1. Необходимо разработать класс, реализующий следующие методы:
 * <p>
 * void serialize (Object object, String file);
 * <p>
 * Object deSerialize(String file);
 * <p>
 * Методы выполняют сериализацию объекта Object в файл file
 * и десериализацию объекта из этого файла.
 * Обязательна сериализация и десериализация "плоских" объектов
 * (все поля объекта - примитивы, или String).
 * created by Ksenya_Ushakova at 15.05.2020
 */

public class SerialDeserial {
    /**
     * Метод выполняет сериализацию плоского объекта Object в файл file
     * @param object
     * @param file
     */
    public void serialize(Object object, String file) {
        Class<?> objectClass = object.getClass();

        Field[] fields = objectClass.getDeclaredFields(); //поля объекта

        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));){
            stream.writeUTF(objectClass.getName()); //записали имя класса
            //записываем значения всех полей
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                stream.writeObject(fields[i].get(object));
            }


        } catch (IOException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    /**
     * Метод выполняет десериализацию плоского объекта из файла.
     * @param file
     * @return объект
     */
    public Object deserialize(String file) {
        Object o = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));){
            //читаем имя класса
            String className = in.readUTF();

            Class clazz = Class.forName(className);//класс объекта
            Constructor[] cons = clazz.getConstructors();//конструкторы объекта
            Class[] params = cons[0].getParameterTypes();//параметры конструктора

            Object[] args = new Object[params.length];
            for (int i = 0; i < params.length; i++) {

                if(params[i].isPrimitive()) {
                    String name = params[i].getName();
                    switch (name){
                        case "boolean":
                            args[i] = false;
                            break;
                        default:
                            args[i] = 0;
                            break;
                    }
                } else {
                    args[i] = null;
                }

            }

            o = clazz.getConstructor(params).newInstance(args);//создаем новый объект с полями по умолчанию

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(o,in.readObject());
            }

        } catch (IOException | ClassNotFoundException | NoSuchMethodException |
                InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }
}
