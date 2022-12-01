package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypeRemuneration extends TaxType {

    public TaxTypeRemuneration() {
        super(3,0, "Податок на авторські винагороди", ProjectConstant.TAX_REMUNERATION);
    }

}
