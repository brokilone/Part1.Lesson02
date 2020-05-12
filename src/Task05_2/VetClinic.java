package Task05_2;

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
        AddPetRequest catRequest = new AddPetRequest("Vasiliy", 3, catOwner, 3.2);

        Person catOwner2 = new Person("Roman", 40, Sex.MAN);
        AddPetRequest catRequest2 = new AddPetRequest("Vasiliy", 4, catOwner2, 3.8);

        Person dogOwner = new Person("Maria", 25, Sex.WOMAN);
        AddPetRequest dogRequest = new AddPetRequest("Tuzik", 1, dogOwner, 5.1);

        Person hamsterOwner = new Person("Natalia", 19, Sex.WOMAN);
        AddPetRequest hamsterRequest = new AddPetRequest("John", 1, hamsterOwner, 0.1);


        try {
            System.out.println("Добавляем кота");
            UUID cat = storage.addPet(catRequest);
            //меняем возраст
            storage.changePetAge(cat, 4);

            System.out.println("Добавляем другого кота");
            UUID cat2 = storage.addPet(catRequest2);

            System.out.println("Добавляем собаку");
            UUID dog = storage.addPet(dogRequest);
            //меняем хозяина
            storage.changePetOwnerInfo(dog,new Person("Oleg", 21, Sex.MAN));

            System.out.println("Добавляем хомяка");
            UUID hamster = storage.addPet(hamsterRequest);
            //меняем вес
            storage.changePetWeight(hamster, 0.15);


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


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
