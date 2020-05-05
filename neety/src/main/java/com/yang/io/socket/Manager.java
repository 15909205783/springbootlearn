package com.yang.io.socket;

public class Manager extends Employee {
    private Employee secretary;

    public Manager() {
    }

    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        this.secretary = secretary;
    }

    public Employee getSecretary() {
        return secretary;
    }

    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }
}
