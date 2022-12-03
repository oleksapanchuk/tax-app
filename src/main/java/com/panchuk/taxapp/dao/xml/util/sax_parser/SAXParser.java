package com.panchuk.taxapp.dao.xml.util.sax_parser;

import com.panchuk.taxapp.dao.xml.util.XMLValidation;
import com.panchuk.taxapp.model.User;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SAXParser {

    public List<User> parse(String path) {

        if (!XMLValidation.validateXMLSchema("xml/user.xsd", path)) {
            System.out.println("\n\u274C Xml file is no valid!");
            return null;
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParserHandler handler = new SAXParserHandler();
        javax.xml.parsers.SAXParser parser;

        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("Open sax parser error " + e);
            return null;
        }

        File file = new File(path);
        try {
            parser.parse(file, handler);
        } catch (SAXException e) {
            System.out.println("Sax parsing error " + e);
            return null;
        } catch (IOException e) {
            System.out.println("IO parsing error " + e);
            return null;
        }

        return handler.getUserList();
    }
}
