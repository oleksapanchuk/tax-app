package com.panchuk.taxapp.dao.mysql;

import com.panchuk.taxapp.dao.DAOFactory;
import com.panchuk.taxapp.dao.TaxDAO;

public class MysqlDAOFactory extends DAOFactory {

    private TaxDAO taxDAO;

    @Override
    public TaxDAO getTaxDAO() {
        if (taxDAO == null) {
            taxDAO = new MysqlTaxDAO();
        }
        return taxDAO;
    }
}
