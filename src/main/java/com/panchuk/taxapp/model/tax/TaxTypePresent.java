package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypePresent extends TaxType {
    private static final int TYPE = 2;

    public TaxTypePresent() {
        super(0, "Податок на подарунки", ProjectConstant.TAX_PRESENT);
    }

    public int getType() { return TYPE; }
}
