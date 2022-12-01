package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypeRemuneration extends TaxType {

    private static final int TYPE = 3;

    public TaxTypeRemuneration() {
        super(0, "Податок на авторські винагороди", ProjectConstant.TAX_REMUNERATION);
    }

    public int getType() { return TYPE; }
}
