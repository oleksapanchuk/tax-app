package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypePresent extends TaxType {
    public TaxTypePresent() {
        super(2, 0, "Податок на подарунки", ProjectConstant.TAX_PRESENT);
    }
}
