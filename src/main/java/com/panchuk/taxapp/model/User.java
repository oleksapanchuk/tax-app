package com.panchuk.taxapp.model;

import java.util.List;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private String email;
    private String dateOfBirth;
    private double totalAmountOfTax;
    private List<TaxType> taxes;

    public User() {
    }

    public User(int id, String firstName, String lastName, Sex sex, String email, String dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public double calculateTotalAmountOfTax() {
        double amount = 0;

        if (taxes == null) return amount;

        for (TaxType tax : taxes) {
            if (tax != null)
                amount += tax.getAmountOfTax();
        }
        return amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getTotalAmountOfTax() {
        totalAmountOfTax = calculateTotalAmountOfTax();
        return totalAmountOfTax;
    }

    public void setTotalAmountOfTax(double totalAmountOfTax) {
        this.totalAmountOfTax = totalAmountOfTax;
    }

    public List<TaxType> getTax() {
        return taxes;
    }

    public void setTax(List<TaxType> tax) {
        this.taxes = tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", taxes=" + taxes +
                '}';
    }


    public enum Sex {
        male, female, other;
    }
}
