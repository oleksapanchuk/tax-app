package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypeSale extends TaxType {

    public TaxTypeSale() {
        super(4,0, "Податок на продаж майна", ProjectConstant.TAX_SALE);
    }

}
