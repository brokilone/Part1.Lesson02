package Task08_1;




/**
 * DeserialDemo - демонстрация десериализации
 * created by Ksenya_Ushakova at 15.05.2020
 */
public class DeserialDemo {
    /**
     * Демонстрация десериализации объекта Person из файла
     * @param args
     */
    public static void main(String[] args) {
        SerialDeserial sd = new SerialDeserial();
        Person person = (Person) sd.deserialize("person.bin");
        System.out.println(person);

    }
}
