package Task3;

import java.util.Random;

/**
 * Generator
 * created by Ksenya_Ushakova at 25.04.2020
 */
public class Generator {
    private static String[] maleNames = {"Anton", "Andrew", "Anatoliy", "Alex", "Boris", "Ben", "Bob", "Vladimir",
                                        "Valeriy", "Viktor", "Vyacheslav", "Gleb", "Georgiy", "Denis", "Damir",
                                        "Dmitriy", "Egor", "Evgeniy", "Igor", "Ilya", "Innokentiy", "Konstantin",
                                        "Leonard", "Leonid", "Michael", "Mark", "Mike", "Nikita", "Nikolay", "Oleg",
                                        "Petr", "Piter", "Pavel", "Robert", "Radomir", "Stas", "Sergey", "Timur",
                                        "Timofey", "Tamir", "Fedor", "Eduard", "Yaroslav", "Yakov"};

    private static String [] femaleNames = {"Anna", "Alisa", "Alberta", "Alla", "Barbara", "Bella", "Valentina",
                                            "Viktorya", "Vera", "Galina", "Gella", "Diana", "Darina", "Darya",
                                            "Ekaterina", "Elena", "Eva", "Esenya", "Irina", "Iraida", "Isabella",
                                            "Kseniya", "Karina", "Kira", "Kristina", "Larisa", "Lisa","Marina",
                                            "Natalya", "Nina", "Nely", "Olesya", "Oksana", "Olga", "Polina", "Paula",
                                            "Rosa", "Rimma", "Rada", "Ramina", "Tamara", "Tatyana", "Ulyana",
                                            "Elvira", "Yana"};

    public static Person generateOnePerson() {
        Random random = new Random();
        boolean isMale = random.nextBoolean();
        if (isMale) {
            return Person.createMan(maleNames[random.nextInt(maleNames.length)], random.nextInt(101));
        }
        return Person.createWoman(femaleNames[random.nextInt(femaleNames.length)], random.nextInt(101));
    }
}
