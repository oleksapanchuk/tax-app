package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypeBenefitsForChildren extends TaxType {
    public TaxTypeBenefitsForChildren() {
        super(6,0, "Податок пільг на дітей", ProjectConstant.TAX_BENEFITS_FOR_CHILDREN);
    }
}
