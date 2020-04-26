package Task3;

import java.util.Random;

/**
 * Generator
 * класс реализует создание объекта Person с рандомными полями name, age, sex
 * @see Person
 * created by Ksenya_Ushakova at 25.04.2020
 */
 class Generator {
     //вспомогательный массив мужских имен
    private static String[] maleNames = {"Anton", "Andrew", "Anatoliy", "Alex", "Boris", "Ben", "Bob", "Vladimir",
                                        "Valeriy", "Viktor", "Vyacheslav", "Gleb", "Georgiy", "Denis", "Damir",
                                        "Dmitriy", "Egor", "Evgeniy", "Igor", "Ilya", "Innokentiy", "Konstantin",
                                        "Leonard", "Leonid", "Michael", "Mark", "Mike", "Nikita", "Nikolay", "Oleg",
                                        "Petr", "Piter", "Pavel", "Robert", "Radomir", "Stas", "Sergey", "Timur",
                                        "Timofey", "Tamir", "Fedor", "Eduard", "Yaroslav", "Yakov"};

    //вспомогательный массив женских имен
    private static String [] femaleNames = {"Anna", "Alisa", "Alberta", "Alla", "Barbara", "Bella", "Valentina",
                                            "Viktorya", "Vera", "Galina", "Gella", "Diana", "Darina", "Darya",
                                            "Ekaterina", "Elena", "Eva", "Esenya", "Irina", "Iraida", "Isabella",
                                            "Kseniya", "Karina", "Kira", "Kristina", "Larisa", "Lisa","Marina",
                                            "Natalya", "Nina", "Nely", "Olesya", "Oksana", "Olga", "Polina", "Paula",
                                            "Rosa", "Rimma", "Rada", "Ramina", "Tamara", "Tatyana", "Ulyana",
                                            "Elvira", "Yana"};

    /**
     * Создание объекта класса Person  с рандомными именем, возрастом, полом
     * @return Person
     */
    public static Person generateOnePerson() {
        Random random = new Random();
        boolean isMale = random.nextBoolean();
        if (isMale) {
            return Person.createMan(maleNames[random.nextInt(maleNames.length)], random.nextInt(101));
        }
        return Person.createWoman(femaleNames[random.nextInt(femaleNames.length)], random.nextInt(101));
    }
}
