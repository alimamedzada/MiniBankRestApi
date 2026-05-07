package com.company.MiniBankByUsingSpring.customers.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {

    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "surname")
    private String surname;
    @Basic(optional = false)
    @Column(name = "aze_id")
    private String azeid;
    @Basic(optional = false)
    @Column(name = "age")
    private int age;

    public Person() {
    }

    public Person(String name, String surname, int age, String azeID) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.azeid = azeID;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAzeid() {
        return azeid;
    }

    public void setAzeid(String azeid) {
        this.azeid = azeid;
    }

    @Override
    public String toString() {
        return "name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", azeID='" + azeid + '\''
                + ", age=" + age;
    }
}
