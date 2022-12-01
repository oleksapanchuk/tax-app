package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypeIncome extends TaxType {
    private static final int TYPE = 1;

    public TaxTypeIncome() {
        super(0, "Податок на дохід з основного та додаткового місць роботи", ProjectConstant.TAX_INCOME);
    }

    public int getType() { return TYPE; }

}
