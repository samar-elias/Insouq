package com.hudhud.insouqapplication.AppUtils.objectsToSend;

public class UserToSend {
    private String firstName, lastName, phoneNumber, emailAddress, password;

    public UserToSend() {
        this.firstName = "";
        this.lastName = "";
        this.phoneNumber = "";
        this.emailAddress = "";
        this.password = "";
    }

    public UserToSend(String firstName, String lastName, String phoneNumber, String emailAddress, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }
}
