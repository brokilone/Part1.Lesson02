package Task05_1;

import java.util.*;

/**
 * PetStorage класс определяет хранение данных об объектах Pet  в картотеке, хранение организовано в HashMap
 * created by Ksenya_Ushakova at 30.04.2020
 */
public class PetStorage {
    Map<UUID, Pet> petMap;

    /**
     * публичный конструктор
     */
    public PetStorage() {
        this.petMap = new HashMap<>();
    }

    /**
     * метод добавляет новый объект Pet в ассоциативный массив
     *
     * @param pet - объект класса Pet
     * @throws Exception выбрасывает исключение при попытке добавить уже имеющееся в списке животное
     * @see Pet
     */
    public UUID addPet(Pet pet) throws Exception {
        UUID uuid = pet.getUuid();
        if (!petMap.containsKey(uuid)) {
            petMap.put(uuid, pet);
        } else {
            throw new Exception("Животное с таким идентификатором уже есть в картотеке!");
        }
        return uuid;
    }

    /**
     * Метод для изменения данных о возрасте питомца
     *
     * @param uuid   идентификатор объекта Pet
     * @param newAge новое значение поля age объекта Pet
     * @return возвращает true, если элемент найден в мапе по идентификатору и данные успешно заменены
     * @see Pet
     */
    public Pet changePetAge(UUID uuid, int newAge) throws Exception {
        if (petMap.containsKey(uuid)) {
            Pet pet = petMap.get(uuid);
            pet.setAge(newAge);
            return pet;
        } else {
            throw new Exception("Идентификатор не найден!");
        }
    }

    /**
     * Метод для изменения данных о весе питомца
     *
     * @param uuid      идентификатор объекта Pet
     * @param newWeight новое значение поля weight объекта Pet
     * @return возвращает true, если элемент найден в мапе по идентификатору и данные успешно заменены
     * @see Pet
     */
    public Pet changePetWeight(UUID uuid, double newWeight) throws Exception {
        if (petMap.containsKey(uuid)) {
            Pet pet = petMap.get(uuid);
            pet.setWeight(newWeight);
            return pet;
        } else {
            throw new Exception("Идентификатор не найден!");
        }
    }

    /**
     * Метод для изменения данных о хозяине питомца
     *
     * @param uuid  идентификатор объекта Pet
     * @param owner новое значение поля owner объекта Pet
     * @return возвращает true, если элемент найден в мапе по идентификатору и данные успешно заменены
     * @see Pet
     * @see Person
     */
    public Pet changePetOwnerInfo(UUID uuid, Person owner) throws Exception {
        if (petMap.containsKey(uuid)) {
            Pet pet = petMap.get(uuid);
            pet.setOwner(owner);
            return pet;
        } else {
            throw new Exception("Идентификатор не найден!");
        }
    }

    /**
     * Метод для поиска в ассоциативном массиве по имени питомца
     *
     * @param name принимает на вход имя объекта Pet
     * @return возвращает список всех питомцев с таким именем или пустой список, если совпадений не найдено
     * @see Pet
     */
    public List<Pet> findPetByName(String name) {
        List<Pet> pets = new ArrayList<>();
        for (Map.Entry<UUID, Pet> petEntry : petMap.entrySet()) {
            if (petEntry.getValue().getName().equals(name)) {
                pets.add(petEntry.getValue());
            }
        }
        return pets;
    }

    /**
     * Метод для сортировки словаря по кличке питомца (поле имя объекта Pet)
     *
     * @see Pet
     */
    public void sortByPetName() {
        TreeSet<Pet> set = new TreeSet<>(new Comparator<Pet>() {
            @Override
            public int compare(Pet o1, Pet o2) {
                int i = o1.getName().compareTo(o2.getName());
                if (i != 0) return i;
                return o1.getUuid().compareTo(o2.getUuid());
            }
        });
        set.addAll(petMap.values());
        for (Pet pet : set) {
            System.out.println(pet);
        }

    }

    /**
     * Метод для сортировки словаря по имени хозяина Person объекта Pet
     *
     * @see Pet
     * @see Person
     */
    public void sortByOwner() {
        TreeSet<Pet> set = new TreeSet<>(new Comparator<Pet>() {
            @Override
            public int compare(Pet o1, Pet o2) {
                int i = o1.getOwner().compareTo(o2.getOwner());
                if (i != 0) return i;
                return o1.getUuid().compareTo(o2.getUuid());
            }
        });
        set.addAll(petMap.values());
        for (Pet pet : set) {
            System.out.println(pet);
        }
    }

    /**
     * Метод для сортировки словаря по весу питомца (поле вес объекта Pet)
     *
     * @see Pet
     */
    public void sortByWeight() {
        TreeSet<Pet> set = new TreeSet<>(new Comparator<Pet>() {
            @Override
            public int compare(Pet o1, Pet o2) {
                int i = Double.compare(o1.getWeight(), o2.getWeight());
                if (i != 0) return i;
                return o1.getUuid().compareTo(o2.getUuid());
            }
        });
        set.addAll(petMap.values());
        for (Pet pet : set) {
            System.out.println(pet);
        }
    }
}
