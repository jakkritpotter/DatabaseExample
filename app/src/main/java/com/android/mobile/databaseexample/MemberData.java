package com.android.mobile.databaseexample;

/**
 * Created by 5N1P3R on 22/2/2560.
 */

public class MemberData {
    private int id;
    private String name;
    private String surname;
    private int age;

    public MemberData(int id,String name,String surname,int age){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }


    public int getAge() {
        return age;
    }

}
