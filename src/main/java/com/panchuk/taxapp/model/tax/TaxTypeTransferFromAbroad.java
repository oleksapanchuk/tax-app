package com.panchuk.taxapp.model.tax;

import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.model.TaxType;

public class TaxTypeTransferFromAbroad extends TaxType {
    public TaxTypeTransferFromAbroad() {
        super(5, 0, "Податок на перекази з-за кордону", ProjectConstant.TAX_TRANSFER_FROM_ABROAD);
    }

}
