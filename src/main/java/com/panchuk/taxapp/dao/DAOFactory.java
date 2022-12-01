package com.panchuk.taxapp.dao;

public abstract class DAOFactory {
    private static DAOFactory instance;
    private static String daoFactoryFQN;

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {

            try {
                Class<?> clazz  = Class.forName(DAOFactory.daoFactoryFQN);
                instance = (DAOFactory) clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    protected DAOFactory() { }

    public static void setDAOFactoryFQN(String daoFactoryFQN) {
        instance = null;
        DAOFactory.daoFactoryFQN = daoFactoryFQN;
    }

    public abstract TaxDAO getTaxDAO();
}
