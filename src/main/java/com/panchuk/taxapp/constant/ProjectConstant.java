package com.panchuk.taxapp.constant;

import java.util.regex.Pattern;

public class ProjectConstant {

    /** SETTINGS DAO */
    public static final String SETTINGS_FILE = "app.properties";
    public static final String CONNECTION_URL = "connection.url";
    public static final String DAO_FACTORY_FQN = "com.panchuk.taxapp.dao.mysql.MysqlDAOFactory";
//    public static final String DAO_FACTORY_FQN = "com.panchuk.tax.dao.xml.XMLDAOFactory";

//    public static final String writeXMLPath = "xml/user.xml";
    public static final String writeXMLPath = "src/main/resources/test_user.xml";   // for testing

    /** TAX MULTIPLIER */
    public static final double TAX_INCOME = 0.49;
    public static final double TAX_REMUNERATION = 0.3;
    public static final double TAX_SALE = 0.2;
    public static final double TAX_PRESENT = 0.15;
    public static final double TAX_TRANSFER_FROM_ABROAD = 0.25;
    public static final double TAX_BENEFITS_FOR_CHILDREN = 0.1;
    public static final double TAX_FINANCIAL_AID = 0.05;


    /** EMOJI */
    public static final String EMO_GREEN_CHECKBOX = "\u2705";
    public static final String EMO_UPDATE_USER = "\u23FA";
    public static final String EMO_BACK_MAIN_MENU = "\u26EA";


    /** TEXT */
    public static final String TEXT_WENT_WRONG = "\u274C Something went wrong!!! Check logger.";



    /** KEYWORDS */
    public static final String FIND_ALL_USERS = "au";
    public static final String FIND_BY_ID = "id-";
    public static final String FIND_TAX_BY_ID_PAYMENT = "ftbin-";

    public static final String FIND_BY_FIRST_NAME = "fn-";
    public static final String FIND_BY_LAST_NAME = "ln-";
    public static final String FIND_BY_EMAIL = "e-";

    public static final String FIND_BY_TYPE = "t-";
    public static final String FIND_BY_RANGE = "r-";

    public static final String SORT_BY_FIRST_NAME = "fns";
    public static final String SORT_BY_LAST_NAME = "lns";
    public static final String SORT_BY_AMOUNT = "s";


    /** Validation patterns */
    public static final Pattern VALID_FIRST_NAME =
            Pattern.compile("[A-ZІА-Я][a-zіа-я]*");
    public static final Pattern VALID_LAST_NAME =
            Pattern.compile("[a-zа-яіІА-ЯA-Z]+([ '-][a-zа-яіА-ЯA-Z]+)*");
    public static final Pattern VALID_EMAIL =
            Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.\\-]+@[a-zA-Z0-9.\\-]+$");
    public static final Pattern VALID_DATE =
            Pattern.compile("^[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
    public static final Pattern VALID_ID_NUMBER =
            Pattern.compile("[0-9]+");
    public static final Pattern VALID_DOUBLE =
            Pattern.compile("^[+]?(([1-9]\\d*)|0)(\\.\\d+)?");


    private ProjectConstant() { }

}
