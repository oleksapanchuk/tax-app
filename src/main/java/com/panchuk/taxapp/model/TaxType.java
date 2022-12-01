package com.panchuk.taxapp.model;

import java.util.Objects;

public abstract class TaxType {
    protected int idNumber;
    protected int TYPE;
    protected String nameTax;
    protected double multiplier;
    protected double value;
    protected double amountOfTax;
    protected String datePayment;

    protected TaxType(int idNumber, String nameTax, double multiplier) {
        this.idNumber = idNumber;
        this.nameTax = nameTax;
        this.multiplier = multiplier;
    }

    public void calculateAmountTax() {
        amountOfTax = multiplier * value;
    }


    public int getIdNumber() { return idNumber; }

    public void setIdNumber(int idNumber) { this.idNumber = idNumber; }

    public int getType() { return TYPE; }

    public void setNameTax(String nameTax) { this.nameTax = nameTax; }

    public String getNameTax() {
        return nameTax;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    public double getAmountOfTax() { return amountOfTax; }

    public void setAmountOfTax(double amountOfTax) { this.amountOfTax = amountOfTax; }

    public String getDatePayment() { return datePayment; }

    public void setDatePayment(String datePayment) { this.datePayment = datePayment; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxType taxType = (TaxType) o;
        return idNumber == taxType.idNumber && TYPE == taxType.TYPE && Double.compare(taxType.multiplier, multiplier) == 0 && Double.compare(taxType.value, value) == 0 && Double.compare(taxType.amountOfTax, amountOfTax) == 0 && Objects.equals(nameTax, taxType.nameTax) && Objects.equals(datePayment, taxType.datePayment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber, TYPE, nameTax, multiplier, value, amountOfTax, datePayment);
    }

    @Override
    public String toString() {
        return "TaxType{" +
                "idNumber=" + idNumber +
                ", TYPE=" + TYPE +
                ", value=" + value +
                ", amountOfTax=" + amountOfTax +
                ", datePayment='" + datePayment + '\'' +
                '}';
    }
}
