package com.android.tony.storelist;

public class UsersDatabase {
    private String name, email, dateofbirth, number, gender;


    UsersDatabase(String name, String email, String dateofbirth, String number, String gender) {
        this.name = name;
        this.email = email;
        this.dateofbirth = dateofbirth;
        this.number = number;
        this.gender = gender;

    }

    UsersDatabase() {

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getNumber() {
        return number;
    }

    public String getGender() {
        return gender;
    }

}
