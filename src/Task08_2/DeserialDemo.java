package Task08_2;


/**
 * DeserialDemo - демонстрация десериализации
 * created by Ksenya_Ushakova at 15.05.2020
 */
public class DeserialDemo {
    /**
     * Демонстрация десериализации объекта Dog из файла
     * @param args
     */
    public static void main(String[] args) {
        SerialDeserial sd = new SerialDeserial();
        Dog dog = (Dog) sd.deserialize("dog.bin");
        System.out.println(dog);

    }
}
