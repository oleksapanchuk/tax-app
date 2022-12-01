package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypeFinancialAid extends TaxType {

    public TaxTypeFinancialAid() {
        super(7,0, "Податок на матеріальну допомогу", ProjectConstant.TAX_FINANCIAL_AID);
    }

}
