package com.panchuk.taxapp.dao.xml;


import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.dao.TaxDAO;
import com.panchuk.taxapp.dao.xml.util.XMLWriter;
import com.panchuk.taxapp.dao.xml.util.sax_parser.SAXParser;
import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.User;
import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class XMLTaxDAO implements TaxDAO {

    static final Logger logger = Logger.getLogger(Main.class);

    @Override
    public boolean insertUser(User user) {
        logger.info("Starting insert user " + user);
        List<User> userList = findUserByFilter(ProjectConstant.FIND_ALL_USERS);
        user.setId(generateUserId(userList));
        userList.add(user);

        XMLWriter.buildXML(userList, ProjectConstant.writeXMLPath);

        logger.info("User added");
        return true;
    }

    @Override
    public User getUser(int id) {

        List<User> userList = findUserByFilter(ProjectConstant.FIND_ALL_USERS);

        for (User user : userList) {
            if (user.getId() == id) {
                logger.info("User" + user);
                return user;
            }
        }

        return null;
    }

    @Override
    public List<TaxType> getUserTaxes(int id) {
        return getUser(id).getTax();
    }

    @Override
    public boolean addTaxForUser(User user, List<TaxType> taxes) {
        List<User> userList = findUserByFilter(ProjectConstant.FIND_ALL_USERS);
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).equals(user)) {
                List<TaxType> taxesList = userList.get(i).getTax();
                taxesList.addAll(taxes);
                userList.get(i).setTax(taxesList);
                XMLWriter.buildXML(userList, ProjectConstant.writeXMLPath);
                logger.info("User taxes added" + user);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean updateUser(User user) {
        List<User> userList = findUserByFilter(ProjectConstant.FIND_ALL_USERS);
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == user.getId()) {
                user.setTax(userList.get(i).getTax());
                userList.remove(i);
                userList.add(user);
                XMLWriter.buildXML(userList, ProjectConstant.writeXMLPath);
                logger.info("User updated " + user);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        List<User> userList = findUserByFilter(ProjectConstant.FIND_ALL_USERS);
        userList.remove(user);

        XMLWriter.buildXML(userList, ProjectConstant.writeXMLPath);

        logger.info("User deleted " + user);

        return true;
    }

    @Override
    public boolean deleteUserTax(int idNumberTax) {
        List<User> userList = findUserByFilter(ProjectConstant.FIND_ALL_USERS);
        for (User u : userList) {
            List<TaxType> userTaxes = u.getTax();
            for (TaxType tt : userTaxes) {
                if (tt.getIdNumber() == idNumberTax) {
                    userTaxes.remove(tt);
                    u.setTax(userTaxes);
                    XMLWriter.buildXML(userList, ProjectConstant.writeXMLPath);
                    logger.info("Tax deleted" + tt);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<TaxType> findTaxByFilter(String query) {
        List<User> userList = findUserByFilter(ProjectConstant.FIND_ALL_USERS);
        List<TaxType> taxList = new ArrayList<>();
        for (User u : userList) {
            taxList.addAll(u.getTax());
        }

        if (query.equals(ProjectConstant.FIND_ALL_USERS)) return taxList;

        if (query.startsWith(ProjectConstant.FIND_TAX_BY_ID_PAYMENT)) {
            int idPayment = Integer.parseInt(query.replace(ProjectConstant.FIND_TAX_BY_ID_PAYMENT, ""));
            return taxList.stream()
                    .filter(taxType -> taxType.getIdNumber() == idPayment)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        if (query.startsWith(ProjectConstant.FIND_BY_TYPE)) {
            int type = Integer.parseInt(query.replace(ProjectConstant.FIND_BY_TYPE, ""));
            return taxList.stream()
                    .filter(taxType -> taxType.getType() == type)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        if (query.startsWith(ProjectConstant.FIND_BY_RANGE)) {
            query = query.replace(ProjectConstant.FIND_BY_RANGE, "");
            String [] range = query.split("-");
            double start = Double.parseDouble(range[0]);
            double finish = Double.parseDouble(range[1]);

            return taxList.stream()
                    .filter(taxType -> taxType.getAmountOfTax() > start && taxType.getAmountOfTax() < finish)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        return null;
    }

    @Override
    public List<User> findUserByFilter(String query) {

        SAXParser saxParser = new SAXParser();

        List<User> userList = saxParser.parse(ProjectConstant.writeXMLPath);

        if (query.equals(ProjectConstant.FIND_ALL_USERS)) return userList;

        if (query.startsWith(ProjectConstant.FIND_BY_ID)) {
            int id = Integer.parseInt(query.replace(ProjectConstant.FIND_BY_ID, ""));
            return userList.stream().filter(user -> user.getId() == id).collect(Collectors.toCollection(ArrayList::new));
        }

        if (query.startsWith(ProjectConstant.FIND_BY_FIRST_NAME)) {
            String firstName = query.replace(ProjectConstant.FIND_BY_FIRST_NAME, "");
            return userList.stream()
                    .filter(u -> u.getFirstName().toLowerCase().startsWith(firstName.trim().toLowerCase()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        if (query.startsWith(ProjectConstant.FIND_BY_LAST_NAME)) {
            String lastName = query.replace(ProjectConstant.FIND_BY_LAST_NAME, "");
            return userList.stream()
                    .filter(u -> u.getLastName().toLowerCase().startsWith(lastName.trim().toLowerCase()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        if (query.startsWith(ProjectConstant.FIND_BY_EMAIL)) {
            String email = query.replace(ProjectConstant.FIND_BY_EMAIL, "");
            return userList.stream()
                    .filter(u -> u.getEmail().toLowerCase().startsWith(email.trim().toLowerCase()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        if (query.startsWith(ProjectConstant.SORT_BY_FIRST_NAME)) {
            String order = query.replace(ProjectConstant.SORT_BY_FIRST_NAME, "");
            if (order.equals("a")) {
                return userList.stream()
                        .sorted(Comparator.comparing(User::getFirstName))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            return userList.stream()
                    .sorted(Comparator.comparing(User::getFirstName).reversed())
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        if (query.startsWith(ProjectConstant.SORT_BY_LAST_NAME)) {
            String order = query.replace(ProjectConstant.SORT_BY_LAST_NAME, "");
            if (order.equals("a")) {
                return userList.stream()
                        .sorted(Comparator.comparing(User::getLastName))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            return userList.stream()
                    .sorted(Comparator.comparing(User::getLastName).reversed())
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        if (query.startsWith(ProjectConstant.SORT_BY_AMOUNT)) {
            String order = query.replace(ProjectConstant.SORT_BY_AMOUNT, "");
            if (order.equals("a")) {
                return userList.stream()
                        .sorted(Comparator.comparing(User::calculateTotalAmountOfTax))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            return userList.stream()
                    .sorted(Comparator.comparing(User::calculateTotalAmountOfTax).reversed())
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        return userList;
    }

    private int generateUserId(List<User> userList) {
        Random random = new Random();
        int idUser;
        boolean isOk;
        while (true) {
            idUser = random.nextInt(1, 1_000_000);
            isOk = true;
            for (User u : userList) {
                if (u.getId() == idUser) {
                    isOk = false;
                    break;
                }
            }
            if (isOk) {
                logger.info("User id generated = " + idUser);
                return idUser;
            }
        }
    }
}
