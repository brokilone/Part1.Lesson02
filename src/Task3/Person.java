package Task3;

/**
 * Person
 * created by Ksenya_Ushakova at 25.04.2020
 */
public class Person implements Comparable {
    private String name;
    private int age;
    private Sex sex;

    private Person(String name, int age, Sex sex) {
        this.name = name;
        if (age >= 0 && age <= 100) {
            this.age = age;
        } else {
            this.age = 0;
        }
        this.sex = sex;
    }

    public static Person createMan(String name, int age) {
        return new Person(name, age, Sex.MAN);
    }

    public static Person createWoman(String name, int age) {
        return new Person(name, age, Sex.WOMAN);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0 && age <= 100) {
            this.age = age;
        } else {
            this.age = 0;
        }
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return String.format("Person info. Name: %s, age: %d, sex: %s, ", this.name, this.age, this.sex);
    }


    @Override
    public int compareTo(Object o){
        Person p = (Person) o;
        String s = "";
        int i = this.getSex().compareTo(p.getSex());
        if (i != 0) return i;
        int j = new Integer(this.getAge()).compareTo(new Integer(p.getAge()));
        if (j != 0) return -j;
        int k = this.getName().compareTo(p.getName());
        return k;
    }

//    public int compareTo(Object o){
//        Person p = (Person) o;
//        String s = "";
//        int i = this.getSex().compareTo(p.getSex());
//        if (i != 0) return i;
//        int j = new Integer(this.getAge()).compareTo(new Integer(p.getAge()));
//        if (j != 0) return -j;
//        int k = this.getName().compareTo(p.getName());
//        if (k != 0) {
//            return k;
//        } else if (j == 0) {
//            try {
//                throw new ComparePersonException("Warning: duplicate Persons: ");
//            } catch (ComparePersonException e) {
//                s = e.getMessage();
//                return k;
//            }
//            finally {
//                System.out.println(s + ComparePersonException.count);
//            }
//        } else {
//            return 0;
//        }
//    }
}
