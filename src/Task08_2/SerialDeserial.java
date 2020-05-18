package Task08_2;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * SerialDeserial
 * Необходимо разработать класс, реализующий следующие методы:
 * <p>
 * void serialize (Object object, String file);
 * <p>
 * Object deSerialize(String file);
 * <p>
 * Методы выполняют сериализацию объекта Object в файл file и десериализацию объекта из этого файла.
 * <p>
 * Предусмотреть работу c любыми типами полей (полями могут быть ссылочные типы).
 */

public class SerialDeserial {

    /**
     * Метод выполняет сериализацию объекта Object в файл file
     *
     * @param object объект
     * @param file   файл для сохранения объекта
     */
    public void serialize(Object object, String file) {
        if (object == null) {
            throw new NullPointerException("Объект отсутствует!");
        }
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            serializeObject(object, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод получает класс и поля объекта для дальнейшей сериализации
     *
     * @param object объект
     * @param stream поток ObjectOutputStream
     */
    private void serializeObject(Object object, ObjectOutputStream stream) {
        Class<?> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields(); //поля объекта
        try {
            stream.writeUTF(objectClass.getName()); //записали имя класса
            //записываем значения всех полей
            serializeFields(object, stream, fields);
            //сериализуем поля класса-родителя
            serializeSuper(object, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод рекурсивно сериализует поля объекта в ранее созданный поток для записи
     * @param object объект ссылочного типа
     * @param stream поток для записи
     * @param fields поля объекта
     */
    private void serializeFields(Object object, ObjectOutputStream stream, Field[] fields) {
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers)) {
                continue; //игнорируем статические поля
            }
            field.setAccessible(true);
            try {
                if (!hasFields(field)) {
                    stream.writeObject(field.get(object));
                } else { //иначе сериализуем рекурсивно в уже созданный outputstream
                    if (field.get(object) == null) {
                        stream.writeObject(null);
                    } else {
                        serializeObject(field.get(object), stream);
                    }
                }
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод сериализует публичные поля классов-предков объекта
     * @param object объект
     * @param stream поток для записи
     */
    private void serializeSuper(Object object, ObjectOutputStream stream) {
        Class<?> parentClass = object.getClass().getSuperclass();
        while (true) {
            if (parentClass.getName().equals(Object.class.getName())) { // пока не дойдем до Object
                break;
            }
            Field[] fields = parentClass.getDeclaredFields(); //публичные поля объекта
            //записываем значения всех полей
            serializeFields(object, stream, fields);
            parentClass = parentClass.getSuperclass(); // на уровень выше

        }
    }

    /**
     * Метод проверяет, могут ли поля быть собственные поля
     * @param field поле объекта
     * @return true, если объект не является примитивом, String или оберткой над примитивным типом
     */
    private boolean hasFields(Field field) {
        Class<?> type = field.getType();
        if (type.isPrimitive()) {
            return false;
        }
        String name = type.getName();
        if (name.equals(String.class.getName()) || name.equals(Integer.class.getName()) ||
                name.equals(Character.class.getName()) || name.equals(Boolean.class.getName()) ||
                name.equals(Short.class.getName()) || name.equals(Byte.class.getName()) ||
                name.equals(Float.class.getName()) || name.equals(Double.class.getName()) ||
                name.equals(Long.class.getName())) {
            return false;
        }
        return true;
    }


    /**
     * Метод выполняет десериализацию  объекта из файла.
     *
     * @param file файл с данными объекта
     * @return объект
     */
    public Object deserialize(String file) {
        Object o = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            o = deserializeObject(in);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Метод получет сведения о классе, конструкторе и поляъ объекта и запускает сериализацию
     *
     * @param in - поток ObjectInputStream
     * @return объект
     */
    public Object deserializeObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        Object o = null;
        String className = null;
        try {
            className = in.readUTF();
        } catch (EOFException e) {
            in.readObject();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class clazz = Class.forName(className);//класс объекта
            Constructor[] cons = clazz.getConstructors();//конструкторы объекта
            Class[] params = cons[0].getParameterTypes();//параметры конструктора

            Object[] args = getArgs(params);

            o = clazz.getConstructor(params).newInstance(args);//создаем новый объект с полями по умолчанию

            Field[] fields = clazz.getDeclaredFields();
            deserializeFields(in, o, fields);
            deserializeSuper(o, in);

        } catch (IOException | ClassNotFoundException | NoSuchMethodException |
                InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Метод рекурсивно сериализует поля объекта из ранее созданного потока чтения из файла
     * @param in поток чтения
     * @param o объект
     * @param fields поля объекта
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void deserializeFields(ObjectInputStream in, Object o, Field[] fields) throws IllegalAccessException, IOException, ClassNotFoundException {
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers)) {
                continue;//игнорируем статические поля
            }
            field.setAccessible(true);
            if (!hasFields(field)) {
                field.set(o, in.readObject());
            } else {
                field.set(o, deserializeObject(in));
            }
        }
    }

    /**
     * Вспомогательный метод для установки агрументов конструктора по умолчанию
     * @param params параметры конструктора
     * @return массив объектов
     */
    private Object[] getArgs(Class[] params) {
        Object[] args = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            if (params[i].isPrimitive()) {
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
        return args;
    }

    /**
     * Метод десериализует поля, принадлежащие классам-предкам объекта
     * @param o объект
     * @param in поток для чтения
     */
    private void deserializeSuper(Object o, ObjectInputStream in) {
        Class<?> parentClass = o.getClass().getSuperclass();
        while (true) {
            if (parentClass.getName().equals(Object.class.getName())) {
                break; //пока не дойдем до Object
            }
            Field[] fields = parentClass.getDeclaredFields(); //публичные поля объекта
            try {
                //записываем значения всех полей
               deserializeFields(in,o,fields);
            } catch (IOException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            parentClass = parentClass.getSuperclass(); // на уровень вверх
        }

    }
}

