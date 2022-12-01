package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypeBenefitsForChildren extends TaxType {

    private static final int TYPE = 6;

    public TaxTypeBenefitsForChildren() {
        super(0, "Податок пільг на дітей", ProjectConstant.TAX_BENEFITS_FOR_CHILDREN);
    }

    public int getType() { return TYPE; }
}
