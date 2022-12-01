package com.panchuk.taxapp.util;

import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.tax.*;

import java.util.HashMap;
import java.util.Map;

public class TaxController {

    private static Map<Integer, TaxType> mapTaxes;

    static {
        mapTaxes = new HashMap<>();
        mapTaxes.put(1, new TaxTypeIncome());
        mapTaxes.put(2, new TaxTypePresent());
        mapTaxes.put(3, new TaxTypeRemuneration());
        mapTaxes.put(4, new TaxTypeSale());
        mapTaxes.put(5, new TaxTypeTransferFromAbroad());
        mapTaxes.put(6, new TaxTypeBenefitsForChildren());
        mapTaxes.put(7, new TaxTypeFinancialAid());

    }

    public static TaxType createTax(int idNumber, int type, double money, String date) {
        if (!mapTaxes.containsKey(type)) {
            System.out.println("There is no such type of tax!");
            return null;
        }
        TaxType tax = mapTaxes.get(type);

        tax.setIdNumber(idNumber);
        tax.setValue(money);
        tax.calculateAmountTax();
        tax.setDatePayment(date);

        return tax;
    }

}
