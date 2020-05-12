package Task05_1;

import java.util.UUID;

/**
 * VetClinic демонстрирует функционал картотеки
 *
 * @see PetStorage
 * created by Ksenya_Ushakova at 30.04.2020
 */
public class VetClinic {
    static PetStorage storage = new PetStorage();

    /**
     * главный метод класса
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //создание объектов Petи помещение в Map
        Person catOwner = new Person("Ivan", 35, Sex.MAN);
        Pet cat = new Pet("Vasiliy", 3, catOwner, 3.2);

        Person catOwner2 = new Person("Roman", 40, Sex.MAN);
        Pet cat2 = new Pet("Vasiliy", 4, catOwner2, 3.8);

        Person dogOwner = new Person("Maria", 25, Sex.WOMAN);
        Pet dog = new Pet("Tuzik", 1, dogOwner, 5.1);

        Person hamsterOwner = new Person("Natalia", 19, Sex.WOMAN);
        Pet hamster = new Pet("John", 1, hamsterOwner, 0.1);

        System.out.println(hamster.toString());
        try {
            System.out.println("Добавляем кота");
            storage.addPet(cat);
            System.out.println("Добавляем другого кота");
            storage.addPet(cat2);
            System.out.println("Добавляем собаку");
            storage.addPet(dog);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        //изменение полей объекта Pet
        storage.changePetAge(cat.getUuid(), 4);
        storage.changePetOwnerInfo(dog.getUuid(), new Person("Oleg", 21, Sex.MAN));
        storage.changePetWeight(hamster.getUuid(), 0.15);

        //поиск по имени
        System.out.println("Поиск бобика");
        System.out.println(storage.findPetByName("Bobik"));
        System.out.println("Поиск Василия");
        System.out.println(storage.findPetByName("Vasiliy"));

        //сортировка
        System.out.println("Сортировка по хозяину");
        storage.sortByOwner();
        System.out.println("Сортировка по кличке");
        storage.sortByPetName();
        System.out.println("Сортировка по весу");
        storage.sortByWeight();


    }
}
