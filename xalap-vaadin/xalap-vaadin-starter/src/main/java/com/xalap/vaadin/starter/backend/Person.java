/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.backend;

import java.time.LocalDate;

public class Person {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Role role;
    private final String email;
    private final boolean randomBoolean;
    private final int randomInteger;
    private final LocalDate lastModified;
    public Person(Long id, String firstName, String lastName, Role role,
                  String email, boolean randomBoolean, int randomInteger,
                  LocalDate lastModified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.randomBoolean = randomBoolean;
        this.randomInteger = randomInteger;
        this.lastModified = lastModified;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getInitials() {
        return (firstName.substring(0, 1) + lastName.substring(0, 1))
                .toUpperCase();
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public boolean getRandomBoolean() {
        return randomBoolean;
    }

    public int getRandomInteger() {
        return randomInteger;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public enum Role {
        DESIGNER, DEVELOPER, MANAGER, TRADER, PAYMENT_HANDLER, ACCOUNTANT
    }

}
