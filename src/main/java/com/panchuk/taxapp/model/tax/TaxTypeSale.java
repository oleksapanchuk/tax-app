package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypeSale extends TaxType {
    private static final int TYPE = 4;

    public TaxTypeSale() {
        super(0, "Податок на продаж майна", ProjectConstant.TAX_SALE);
    }

    public int getType() { return TYPE; }
}
