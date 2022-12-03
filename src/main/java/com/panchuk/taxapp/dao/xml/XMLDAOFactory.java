package com.panchuk.taxapp.dao.xml;

import com.panchuk.taxapp.dao.DAOFactory;
import com.panchuk.taxapp.dao.TaxDAO;

public class XMLDAOFactory extends DAOFactory {

    private TaxDAO taxDAO;

    @Override
    public TaxDAO getTaxDAO() {
        if (taxDAO == null) {
            taxDAO = new XMLTaxDAO();
        }
        return taxDAO;
    }
}
