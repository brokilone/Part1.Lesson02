package Task12_2;

/**
 * Worker
 * класс описывает работника
 * created by Ksenya_Ushakova at 27.05.2020
 */
public class Worker implements Person {

    private String name;
    private String task;

    /**
     * публичный интерфейс
     * @param name
     * @param task
     */
    public Worker(String name, String task) {
        this.name = name;
        this.task = task;
    }

    /**
     * Метод переопределяет метод интерфейса Person
     * Выводит в консоль инфо о работнике (имя и задача)
     */
    @Override
    public void introduce() {
        System.out.println("Меня зовут " + this.name + ", я выполняю задачу: " + task);
    }


}
