package com.panchuk.taxapp.dao.xml.util.sax_parser;

import com.panchuk.taxapp.constant.XMLConstant;
import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.model.tax.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SAXParserHandler extends DefaultHandler {

    private final List<User> userList = new ArrayList<>();
    private User user = new User();
    private List<TaxType> taxList = new ArrayList<>();
    private TaxType tax = null;
    private int idPayment;


    private String currentTagName;


    public List<User> getUserList() { return userList; }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        currentTagName = qName;

        switch (qName) {
            case XMLConstant.TAG_USER -> user = new User();
            case XMLConstant.TAG_TAXES -> taxList = new ArrayList<>();
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        currentTagName = null;

        switch (qName) {
            case XMLConstant.TAG_USER -> userList.add(user);
            case XMLConstant.TAG_TAXES -> user.setTax(taxList);
            case XMLConstant.TAG_TAX -> {
                tax.setValue(tax.getAmountOfTax() * tax.getMultiplier());
                taxList.add(tax);
            }
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) {

        if (currentTagName == null) return;

        switch (currentTagName) {
            case XMLConstant.TAG_ID_USER -> user.setId(Integer.parseInt(new String(ch, start, length)));
            case XMLConstant.TAG_FIRST_NAME -> user.setFirstName(new String(ch, start, length));
            case XMLConstant.TAG_LAST_NAME -> user.setLastName(new String(ch, start, length));
            case XMLConstant.TAG_SEX -> {
                User.Sex gender = switch (new String(ch, start, length)) {
                    case "male" -> User.Sex.male;
                    case "female" -> User.Sex.female;
                    default -> User.Sex.other;
                };
                user.setSex(gender);
            }
            case XMLConstant.TAG_EMAIL -> user.setEmail(new String(ch, start, length));
            case XMLConstant.TAG_DATE_OF_BIRTH -> user.setDateOfBirth(new String(ch, start, length));
            case XMLConstant.TAG_TAX_ID -> {
                switch (new String(ch, start, length)) {
                    case "1" -> tax = new TaxTypeIncome();
                    case "2" -> tax = new TaxTypePresent();
                    case "3" -> tax = new TaxTypeRemuneration();
                    case "4" -> tax = new TaxTypeSale();
                    case "5" -> tax = new TaxTypeTransferFromAbroad();
                    case "6" -> tax = new TaxTypeBenefitsForChildren();
                    case "7" -> tax = new TaxTypeFinancialAid();
                }

                tax.setIdNumber(idPayment);
            }
            case XMLConstant.TAG_ID_PAYMENT -> idPayment = Integer.parseInt(new String(ch, start, length));
            case XMLConstant.TAG_VALUE -> tax.setValue(Double.parseDouble(new String(ch, start, length)));
            case XMLConstant.TAG_AMOUNT_OF_TAX -> tax.setAmountOfTax(Double.parseDouble(new String(ch, start, length)));
            case XMLConstant.TAG_PAYMENT_DATE -> tax.setDatePayment(new String(ch, start, length));

        }

    }
}
