package Task05_1;

import java.util.*;

/**
 * PetStorage класс определяет хранение данных об объектах Pet  в картотеке, хранение организовано в HashMap
 * created by Ksenya_Ushakova at 30.04.2020
 */
public class PetStorage {
    Map <UUID, Pet> petMap;

    /**
     * публичный конструктор
     */
    public PetStorage() {
        this.petMap = new HashMap<>();
    }

    /**
     * метод добавляет новый объект Pet в ассоциативный массив
     * @param pet - объект класса Pet
     * @see Pet
     * @throws Exception выбрасывает исключение при попытке добавить уже имеющееся в списке животное
     */
    public void addPet(Pet pet) throws Exception {
        if (!petMap.containsKey(pet.getUUID())) {
            petMap.put(pet.getUUID(),pet);
        } else {
            throw new Exception("Животное с таким идентификатором уже есть в картотеке!");
        }
    }

    /**
     * Метод для изменения данных о возрасте питомца
     * @param uuid идентификатор объекта Pet
     * @param newAge новое значение поля age объекта Pet
     * @see Pet
     * @return возвращает true, если элемент найден в мапе по идентификатору и данные успешно заменены
     */
    public boolean changePetAge(UUID uuid, int newAge){
        if (petMap.containsKey(uuid)) {
            Pet pet = petMap.get(uuid);
            pet.setAge(newAge);
            petMap.put(uuid,pet);
            return true;
        } else {
            System.out.println("Проверьте правильность введенного идентификатора");
            return false;
        }
    }
    /**
     * Метод для изменения данных о весе питомца
     * @param uuid идентификатор объекта Pet
     * @param newWeight новое значение поля weight объекта Pet
     * @see Pet
     * @return возвращает true, если элемент найден в мапе по идентификатору и данные успешно заменены
     */
    public boolean changePetWeight(UUID uuid, double newWeight){
        if (petMap.containsKey(uuid)) {
            Pet pet = petMap.get(uuid);
            pet.setWeight(newWeight);
            petMap.put(uuid,pet);
            return true;
        } else {
            System.out.println("Проверьте правильность введенного идентификатора");
            return false;
        }
    }

    /**
     * Метод для изменения данных о хозяине питомца
     * @param uuid идентификатор объекта Pet
     * @param owner новое значение поля owner объекта Pet
     * @see Pet
     * @see Person
     * @return возвращает true, если элемент найден в мапе по идентификатору и данные успешно заменены
     */
    public boolean changePetOwnerInfo(UUID uuid, Person owner){
        if (petMap.containsKey(uuid)) {
            Pet pet = petMap.get(uuid);
            pet.setOwner(owner);
            petMap.put(uuid,pet);
            return true;
        } else {
            System.out.println("Проверьте правильность введенного идентификатора");
            return false;
        }
    }

    /**
     * Метод для поиска в ассоциативном массиве по имени питомца
     * @param name принимает на вход имя объекта Pet
     * @see Pet
     * @return возвращает список всех питомцев с таким именем или пустой список, если совпадений не найдено
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
     * @see Pet
     */
    public void sortByPetName() {
        TreeMap<UUID,Pet> map = new TreeMap<> (new Comparator<UUID>() {
            @Override
            public int compare(UUID uuid1, UUID uuid2) {
                Pet pet1 = petMap.get(uuid1);
                Pet pet2 = petMap.get(uuid2);
                int i = pet1.getName().compareTo(pet2.getName());
                if (i != 0) return i;
                return uuid1.compareTo(uuid2);
            }
        });
        map.putAll(petMap);
        for (Map.Entry<UUID, Pet> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }
    /**
     * Метод для сортировки словаря по имени хозяина Person объекта Pet
     * @see Pet
     * @see Person
     */
    public void sortByOwner() {
        TreeMap<UUID,Pet> map = new TreeMap<> (new Comparator<UUID>() {
            @Override
            public int compare(UUID uuid1, UUID uuid2) {
                Pet pet1 = petMap.get(uuid1);
                Pet pet2 = petMap.get(uuid2);
                int i = pet1.getOwner().compareTo(pet2.getOwner());
                if (i != 0) return i;
                return uuid1.compareTo(uuid2);
            }
        });
        map.putAll(petMap);
        for (Map.Entry<UUID, Pet> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }

    /**
     * Метод для сортировки словаря по весу питомца (поле вес объекта Pet)
     * @see Pet
     */
    public void sortByWeight() {
        TreeMap<UUID,Pet> map = new TreeMap<> (new Comparator<UUID>() {
            @Override
            public int compare(UUID uuid1, UUID uuid2) {
                Pet pet1 = petMap.get(uuid1);
                Pet pet2 = petMap.get(uuid2);
                int i = Double.compare(pet1.getWeight(), pet2.getWeight());
                if (i != 0) return i;
                return uuid1.compareTo(uuid2);
            }
        });
        map.putAll(petMap);

        for (Map.Entry<UUID, Pet> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }
}
