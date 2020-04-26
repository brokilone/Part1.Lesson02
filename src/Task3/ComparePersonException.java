package Task3;

/**
 * ComparePersonException
 * Исключение, которое может возникать при сортировке объектов Person
 * created by Ksenya_Ushakova at 25.04.2020
 */
class ComparePersonException extends Exception{

    public ComparePersonException(String message) {
        super(message);
    }
}
