package com.panchuk.taxapp.dao;

import com.panchuk.taxapp.DAOException;
import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.User;

import java.util.List;

public interface TaxDAO {

    boolean insertUser(User user) throws DAOException;

    User getUser(int id) throws DAOException;

    List<TaxType> getUserTaxes(int id) throws DAOException;

    boolean addTaxForUser(User user, List<TaxType> taxes) throws DAOException;

    boolean updateUser(User user) throws DAOException;

    boolean deleteUser(User user) throws DAOException;

    boolean deleteUserTax(int idNumberTax) throws DAOException;

    List<TaxType> findTaxByFilter(String query) throws DAOException;

    List<User> findUserByFilter(String query) throws DAOException;

}
