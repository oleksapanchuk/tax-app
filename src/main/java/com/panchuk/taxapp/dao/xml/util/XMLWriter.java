package com.panchuk.taxapp.dao.xml.util;

import com.panchuk.taxapp.constant.XMLConstant;
import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class XMLWriter {

    public static void buildXML(List<User> userList, String path) {

        Document doc = docBuilder();

        // root elements
        Element rootElement = doc.createElement("root");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "user.xsd");
        doc.appendChild(rootElement);


        for (User u : userList) {

            Element user = doc.createElement(XMLConstant.TAG_USER);

            Element userId = doc.createElement(XMLConstant.TAG_ID_USER);
            userId.setTextContent(String.valueOf(u.getId()));
            user.appendChild(userId);

            Element firstName = doc.createElement(XMLConstant.TAG_FIRST_NAME);
            firstName.setTextContent(u.getFirstName());
            user.appendChild(firstName);

            Element lastName = doc.createElement(XMLConstant.TAG_LAST_NAME);
            lastName.setTextContent(u.getLastName());
            user.appendChild(lastName);

            Element sex = doc.createElement(XMLConstant.TAG_SEX);
            sex.setTextContent(u.getSex().name().toLowerCase());
            user.appendChild(sex);

            Element email = doc.createElement(XMLConstant.TAG_EMAIL);
            email.setTextContent(u.getEmail());
            user.appendChild(email);

            Element dateOfBirth = doc.createElement(XMLConstant.TAG_DATE_OF_BIRTH);
            dateOfBirth.setTextContent(u.getDateOfBirth());
            user.appendChild(dateOfBirth);

            user.appendChild(createElementTaxes(doc, u));

            rootElement.appendChild(user);

        }


        try (FileOutputStream file = new FileOutputStream(path)) {

            // print XML to system console
            writeXml(doc, file);

        } catch (IOException | TransformerException e) {
            throw new RuntimeException(e);
        }

    }

    private static Document docBuilder() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            return docBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Error: ", e);
        }
    }

    private static Element createElementTaxes(Document doc, User u) {
        Element taxes = doc.createElement(XMLConstant.TAG_TAXES);

        if (u.getTax() == null) return taxes;

        for (TaxType t : u.getTax()) {

            Element tax = doc.createElement(XMLConstant.TAG_TAX);

            Element idPayment = doc.createElement(XMLConstant.TAG_ID_PAYMENT);
            idPayment.setTextContent(String.valueOf(t.getIdNumber()));
            tax.appendChild(idPayment);

            Element type = doc.createElement(XMLConstant.TAG_TAX_ID);
            type.setTextContent(String.valueOf(t.getType()));
            tax.appendChild(type);

            Element value = doc.createElement(XMLConstant.TAG_VALUE);
            value.setTextContent(String.valueOf(t.getValue()));
            tax.appendChild(value);

            Element amountOfTax = doc.createElement(XMLConstant.TAG_AMOUNT_OF_TAX);
            amountOfTax.setTextContent(String.valueOf(t.getAmountOfTax()));
            tax.appendChild(amountOfTax);

            Element paymentDate = doc.createElement(XMLConstant.TAG_PAYMENT_DATE);
            paymentDate.setTextContent(t.getDatePayment());
            tax.appendChild(paymentDate);

            taxes.appendChild(tax);

        }

        return taxes;
    }

    private static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }

}
