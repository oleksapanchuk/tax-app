package com.panchuk.taxapp.dao.mysql;

import com.panchuk.taxapp.DAOException;
import com.panchuk.taxapp.constant.ColorConstant;
import com.panchuk.taxapp.constant.DBConstant;
import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.dao.TaxDAO;
import com.panchuk.taxapp.dao.mysql.util.DBCPDataSource;
import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.model.tax.*;
import com.panchuk.taxapp.util.EmailSender;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlTaxDAO implements TaxDAO {

    static final Logger logger = Logger.getLogger(MysqlTaxDAO.class);

    @Override
    public boolean insertUser(User user) {

        logger.info("Starting user insertion " + user);

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(DBConstant.INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getSex().toString());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getDateOfBirth());

            int insertAmount = ps.executeUpdate();

            if (insertAmount > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }

        return false;
    }

    @Override
    public User getUser(int id) throws DAOException {

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(DBConstant.GET_USER_BY_ID)) {
            ps.setInt(1, id);
            ps.execute();
            try (ResultSet rs = ps.getResultSet()) {
                if (rs.next()) {
                    User user = createUser(rs);
                    user.setTax(getUserTaxes(user.getId()));
                    return user;
                }
            }
        } catch (SQLException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }

        return null;
    }

    @Override
    public List<TaxType> getUserTaxes(int id) {

        logger.info("get user taxes");

        List<TaxType> taxList = new ArrayList<>();

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(DBConstant.GET_TAXES_BY_USER_ID)) {

            ps.setInt(1, id);
            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    TaxType tax = createTax(rs);
                    if (tax == null) {
                        System.out.println(ColorConstant.RED + "Something went wrong!!! Check logger.");
                        return null;
                    }
                    taxList.add(tax);
                }
            }
            return taxList;

        } catch (SQLException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }
        return null;
    }

    @Override
    public boolean addTaxForUser(User user, List<TaxType> taxes) {

        logger.info("add tax for user");
        logger.info("user - " + user);
        logger.info("taxes - " + taxes);

        Connection con = null;

        try {

            con = DBCPDataSource.getConnection();

            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(DBConstant.INSERT_TAX);

            for (TaxType tax : taxes) {
                ps.setInt(1, tax.getIdNumber());
                ps.setInt(2, user.getId());
                ps.setInt(3, tax.getType());
                ps.setDouble(4, tax.getValue());
                ps.setDouble(5, tax.getAmountOfTax());
                ps.setString(6, tax.getDatePayment());

                ps.executeUpdate();

                ps.clearParameters();
            }

            con.commit();
            con.setAutoCommit(true);

            return true;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }

        return false;
    }

    @Override
    public boolean updateUser(User user) {

        logger.info("updating user " + user);

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(DBConstant.UPDATE_USER)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getId());

            int amountRows = ps.executeUpdate();

            if (amountRows >= 1) return true;

        } catch (SQLException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }

        return false;
    }

    @Override
    public boolean deleteUser(User user) {

        logger.info("deleting user " + user);

        Connection con = null;
        try {

            con = DBCPDataSource.getConnection();

            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(DBConstant.DELETE_USER);

            ps.setInt(1, user.getId());

            boolean amountRowsUTT = deleteTaxesByUserId(con, user.getId());
            int amountRowsUT = ps.executeUpdate();

            if (amountRowsUT == 1 && amountRowsUTT) {

                con.commit();
                con.setAutoCommit(true);

                return true;
            }


        } catch (SQLException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
            if (con != null) {
                try {
                    con.rollback();
                    return false;
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteUserTax(int idNumberTax) {

        logger.info("deleting users tax");

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(DBConstant.DELETE_TAX)) {

            ps.setInt(1, idNumberTax);

            int amountRows = ps.executeUpdate();

            if (amountRows == 1) return true;

        } catch (SQLException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }

        return false;
    }

    private static boolean deleteTaxesByUserId(Connection con, int id) throws SQLException {

        try (PreparedStatement ps = con.prepareStatement(DBConstant.DELETE_TAXES_BY_USER_ID)) {

            ps.setInt(1, id);

            int amountRows = ps.executeUpdate();

            if (amountRows >= 0) return true;

        } catch (SQLException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }

        return false;
    }

    private User createUser(ResultSet rs) throws SQLException, DAOException {

        logger.info("creating user by resultSet result");

        User user = new User(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                User.Sex.valueOf(rs.getString(4)),
                rs.getString(5),
                rs.getString(6)
        );

        user.setTax(this.getUserTaxes(user.getId()));
        return user;
    }

    private static TaxType createTax(ResultSet rs) throws SQLException {

        TaxType tax = switch (rs.getInt(3)) {
            case 1 -> new TaxTypeIncome();
            case 2 -> new TaxTypePresent();
            case 3 -> new TaxTypeRemuneration();
            case 4 -> new TaxTypeSale();
            case 5 -> new TaxTypeTransferFromAbroad();
            case 6 -> new TaxTypeBenefitsForChildren();
            case 7 -> new TaxTypeFinancialAid();
            default -> null;
        };

        if (tax == null) {
            System.err.println("Incorrect value in column 'tax_id'");
            return null;
        }

        tax.setIdNumber(rs.getInt(1));
        tax.setValue(rs.getDouble(4));
        tax.setAmountOfTax(rs.getDouble(5));
        tax.setDatePayment(rs.getString(6));

        return tax;
    }

    @Override
    public List<TaxType> findTaxByFilter(String query) {

        logger.info("find tax by filter method");

        try (Connection con = DBCPDataSource.getConnection()) {

            if (query.startsWith(ProjectConstant.FIND_TAX_BY_ID_PAYMENT)) {
                return findTaxById(con, Integer.parseInt(query.replace(ProjectConstant.FIND_TAX_BY_ID_PAYMENT, "")));
            }

            if (query.startsWith(ProjectConstant.FIND_BY_TYPE)) {
                int type = Integer.parseInt(query.replace(ProjectConstant.FIND_BY_TYPE, ""));
                boolean isSortByDate = true;
                boolean isAsc = true; // todo

                return findTaxType(con, type, isSortByDate, isAsc);
            }

            if (query.startsWith(ProjectConstant.FIND_BY_RANGE)) {
                query = query.replace(ProjectConstant.FIND_BY_RANGE, "");
                String [] range = query.split("-");
                double start = Double.parseDouble(range[0]);
                double finish = Double.parseDouble(range[1]);

                return findTaxRange(con, start, finish);

            }

            return null;

        } catch (SQLException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }
        return null;
    }


    private List<TaxType> findTaxById(Connection con, int id) throws SQLException {

        logger.info("find tax by id method");

        List<TaxType> taxList = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(DBConstant.GET_TAX_BY_ID_NUMBER)) {

            ps.setInt(1, id);

            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    taxList.add(createTax(rs));
                }
            }
            return taxList;
        }
    }

    private List<TaxType> findTaxType(Connection con, int type, boolean isSortByDate, boolean isAsc) throws SQLException {

        logger.info("find tax info method");

        String query = DBConstant.GET_TAX_BY_TYPE_AND_SORT;

        List<TaxType> taxList = new ArrayList<>();

        if (isSortByDate) query += " order by payment_date";
        else query += "  order by tax_amount";

        if (!isAsc) query += " desc";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, type);

            return getTaxTypes(taxList, ps);
        }
    }

    private List<TaxType> findTaxRange(Connection con, double start, double finish) throws SQLException {

        logger.info("find tax method");

        List<TaxType> taxList = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(DBConstant.GET_TAX_BY_RANGE)) {

            ps.setDouble(1, start);
            ps.setDouble(2, finish);

            return getTaxTypes(taxList, ps);
        }
    }

    private List<TaxType> getTaxTypes(List<TaxType> taxList, PreparedStatement ps) throws SQLException {

        logger.info("get tax type method");

        ps.execute();

        try (ResultSet rs = ps.getResultSet()) {
            while (rs.next()) {
                TaxType tax = createTax(rs);
                if (tax != null) tax.setNameTax(rs.getString(7));
                taxList.add(tax);
            }
        }
        return taxList;
    }

    @Override
    public List<User> findUserByFilter(String query) throws DAOException {

        try (Connection con = DBCPDataSource.getConnection()) {


            if (query.equals(ProjectConstant.FIND_ALL_USERS)) return findAllUsers(con);

            if (query.startsWith(ProjectConstant.FIND_BY_ID)) {
                return findByIdNumber(con, Integer.parseInt(query.replace(ProjectConstant.FIND_BY_ID, "")));
            }

            if (query.startsWith(ProjectConstant.FIND_BY_FIRST_NAME)) {
                return findByString(con, DBConstant.GET_USER_BY_FIRST_NAME, query.replace(ProjectConstant.FIND_BY_FIRST_NAME, ""));
            }

            if (query.startsWith(ProjectConstant.FIND_BY_LAST_NAME)) {
                return findByString(con, DBConstant.GET_USER_BY_LAST_NAME, query.replace(ProjectConstant.FIND_BY_LAST_NAME, ""));
            }

            if (query.startsWith(ProjectConstant.FIND_BY_EMAIL)) {
                return findByString(con, DBConstant.GET_USER_BY_EMAIL, query.replace(ProjectConstant.FIND_BY_EMAIL, ""));
            }

            if (query.startsWith(ProjectConstant.SORT_BY_FIRST_NAME)) {
                return sortUserByString(con, DBConstant.SORT_USER_BY_FIRST_NAME,
                        query.replace(ProjectConstant.SORT_BY_FIRST_NAME, "").equals("a"));
            }

            if (query.startsWith(ProjectConstant.SORT_BY_LAST_NAME)) {
                return sortUserByString(con, DBConstant.SORT_USER_BY_LAST_NAME,
                        query.replace(ProjectConstant.SORT_BY_LAST_NAME, "").equals("a"));
            }

            if (query.startsWith(ProjectConstant.SORT_BY_AMOUNT)) {
                return sortUserByAmount(con,
                        query.replace(ProjectConstant.SORT_BY_AMOUNT, "").equals("a"));
            }

        } catch (SQLException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }
        return null;
    }

    private List<User> findAllUsers(Connection con) throws DAOException {

        List<User> userList = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(DBConstant.GET_ALL_USERS)) {
            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    userList.add(createUser(rs));
                }
            }

            return userList;
        } catch (SQLException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }

        return null;
    }

    private List<User> findByIdNumber(Connection con, int id) {

        List<User> userList = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(DBConstant.GET_USER_BY_ID)) {
            ps.setInt(1, id);

            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    userList.add(createUser(rs));
                }
            }

            return userList;
        } catch (SQLException | DAOException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }
        return null;
    }

    private List<User> findByString(Connection con, String findType, String text)  {

        List<User> userList = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(findType)) {
            ps.setString(1, text + "%");

            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    userList.add(createUser(rs));
                }
            }

            return userList;
        } catch (SQLException | DAOException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }
        return null;
    }

    private List<User> sortUserByString(Connection con, String query, boolean isAsc) {

        logger.info("sorting user by param");

        if (!isAsc) query += " desc";

        List<User> userList = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    userList.add(createUser(rs));
                }
            }

            return userList;
        } catch (SQLException | DAOException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }
        return null;
    }

    private List<User> sortUserByAmount(Connection con, boolean isAsc) {

        logger.info("sorting by total amount of tax");

        String query = DBConstant.SORT_USER_BY_TOTAL_AMOUNT_OF_TAX;
        if (!isAsc) query += " desc";

        List<User> userList = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    User user = createUser(rs);
                    user.setTotalAmountOfTax(rs.getDouble(7));
                    userList.add(user);
                }
            }

            return userList;
        } catch (SQLException | DAOException e) {
            try {
                EmailSender.sendMessage(e.toString());
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.error(e);
            System.out.println("\u26D4 Failed!");
        }
        return null;
    }

}
