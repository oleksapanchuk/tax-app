package com.panchuk.taxapp.constant;

public class DBConstant {

    /** SELECT scripts */
    public static final String GET_ALL_USERS = "select * from user";
    public static final String GET_USER_BY_ID = "select * from user where id = ?";
    public static final String GET_USER_BY_FIRST_NAME = "select * from user where first_name like ?";
    public static final String GET_USER_BY_LAST_NAME = "select * from user where last_name like ?";
    public static final String GET_USER_BY_EMAIL = "select * from user where email like ?";
    public static final String GET_TAXES_BY_USER_ID = "select * from user_tax where user_id = ?";
    public static final String GET_TAX_BY_ID_NUMBER = "select * from user_tax where id_payment = ?";
    public static final String GET_TAX_BY_TYPE_AND_SORT = "select ut.id_payment, ut.user_id, ut.tax_id, ut.value," +
            " ut.tax_amount, ut.payment_date, concat(u.first_name, \" \", u.last_name) from user_tax ut " +
            "join user u on ut.user_id = u.id where tax_id = ?";
    public static final String GET_TAX_BY_RANGE = "select ut.id_payment, ut.user_id, ut.tax_id, ut.value, ut.tax_amount, ut.payment_date, " +
            "concat(u.first_name, \" \", u.last_name) from user_tax ut join user u on ut.user_id = u.id " +
            "where ut.tax_amount >= ? && ut.tax_amount <= ? order by tax_amount";


    /** INSERT scripts*/
    public static final String INSERT_USER = "insert into user (first_name, last_name, sex, email, date_of_birth) values (?, ?, ?, ?, ?);";
    public static final String INSERT_TAX = "insert into user_tax (id_payment, user_id, tax_id, value, tax_amount, payment_date) values (?, ?, ?, ?, ?, ?)";

    /** DELETE scripts */
    public static final String DELETE_TAXES_BY_USER_ID = "delete from user_tax where user_id = ?";
    public static final String DELETE_USER = "delete from user where id = ?";
    public static final String DELETE_TAX = "delete from user_tax where id_payment = ?";

    /** UPDATE scripts */
    public static final String UPDATE_USER = "update user set first_name = ?, last_name = ?, email = ? where id = ?";


    /** SORT scripts */
    public static final String SORT_USER_BY_FIRST_NAME = "select * from user order by first_name";
    public static final String SORT_USER_BY_LAST_NAME = "select * from user order by last_name";
    public static final String SORT_USER_BY_TOTAL_AMOUNT_OF_TAX = "select u.id, u.first_name, u.last_name, u.sex, u.email, u.date_of_birth, sum(tax_amount) as total_tax_amount " +
            "from user_tax ut join user u on user_id = id group by ut.user_id order by total_tax_amount";



    private DBConstant() { }
}
