package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypeFinancialAid extends TaxType {

    private static final int TYPE = 7;

    public TaxTypeFinancialAid() {
        super(0, "Податок на матеріальну допомогу", ProjectConstant.TAX_FINANCIAL_AID);
    }

    public int getType() { return TYPE; }
}
