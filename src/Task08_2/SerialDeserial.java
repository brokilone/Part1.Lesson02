package Task08_2;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * SerialDeserial
 * Необходимо разработать класс, реализующий следующие методы:
 *
 * void serialize (Object object, String file);
 *
 * Object deSerialize(String file);
 *
 * Методы выполняют сериализацию объекта Object в файл file и десериализацию объекта из этого файла.
 *
 * Предусмотреть работу c любыми типами полей (полями могут быть ссылочные типы).
 */

public class SerialDeserial {
    /**
     * Метод выполняет сериализацию объекта Object в файл file
     * @param object объект
     * @param file файл для сохранения объекта
     */
    public void serialize(Object object, String file) {

        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))){
            serializeField(object, stream);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Метод рекурсивно сериализует поля объекта в ранее созданный поток для записи
     * @param object объект
     * @param stream поток ObjectOutputStream
     */
    private void serializeField(Object object, ObjectOutputStream stream)  {
        Class<?> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields(); //поля объекта
        try {
            stream.writeUTF(objectClass.getName()); //записали имя класса
            //записываем значения всех полей
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType().isPrimitive() || //если поле примитивного типа или String, сериализуем сразу
                        field.getType().getName().equals(String.class.getName())) {
                    stream.writeObject(field.get(object));
                } else { //иначе сериализуем рекурсивно в уже созданный outputstream
                    serializeField(field.get(object), stream);
                }
            }
        } catch (IOException|IllegalAccessException e){
            e.printStackTrace();
        }
    }


    /**
     * Метод выполняет десериализацию  объекта из файла.
     * @param file файл с данными объекта
     * @return объект
     */
    public Object deserialize(String file) {
        Object o = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            //читаем имя класса
            o = deserializeField(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Метод рекурсивно сериализует поля объекта из ранее созданного потока чтения из файла
     * @param in - поток ObjectInputStream
     * @return объект
     */
    public Object deserializeField(ObjectInputStream in) {
        Object o = null;
        try {
            //читаем имя класса
            String className = in.readUTF();

            Class clazz = Class.forName(className);//класс объекта
            Constructor[] cons = clazz.getConstructors();//конструкторы объекта
            Class[] params = cons[0].getParameterTypes();//параметры конструктора

            Object[] args = new Object[params.length];
            for (int i = 0; i < params.length; i++) {

                if(params[i].isPrimitive()) {
                    String name = params[i].getName();
                    if ("boolean".equals(name)) {
                        args[i] = false;
                    } else {
                        args[i] = 0;
                    }
                } else {
                    args[i] = null;
                }

            }

            o = clazz.getConstructor(params).newInstance(args);//создаем новый объект с полями по умолчанию

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType().isPrimitive() || field.getType().getName().equals(String.class.getName())) {
                    field.set(o, in.readObject());
                } else {
                    field.set(o, deserializeField(in));
                }
            }

        } catch (IOException | ClassNotFoundException | NoSuchMethodException |
                InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }



}
