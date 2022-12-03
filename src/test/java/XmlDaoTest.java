import com.panchuk.taxapp.DAOException;
import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.dao.DAOFactory;
import com.panchuk.taxapp.dao.xml.util.XMLWriter;
import com.panchuk.taxapp.dao.xml.util.sax_parser.SAXParser;
import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.model.tax.TaxTypeIncome;
import com.panchuk.taxapp.util.Validator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XmlDaoTest {

    private static final DAOFactory daoFactory;

    static {
        try {
            DAOFactory.setDAOFactoryFQN(ProjectConstant.DAO_FACTORY_FQN);
            daoFactory = DAOFactory.getInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error is instantiating DAOFactory.", e);
        }
    }

    @BeforeAll
    static void testWriteXMLFile() {

        List<User> userList = new ArrayList<>();
        User user01 = new User(
                1,
                "Mark",
                "Smoliak",
                User.Sex.male,
                "smoliak23@gmail.com",
                "2003-12-18"
        );
        userList.add(user01);
        XMLWriter.buildXML(userList, ProjectConstant.writeXMLPath);
    }

    @AfterAll
    static void testWriteXMLFileEnd() {

        List<User> userList = new ArrayList<>();
        User user01 = new User(
                1,
                "Mark",
                "Smoliak",
                User.Sex.male,
                "smoliak23@gmail.com",
                "2003-12-18"
        );
        userList.add(user01);
        XMLWriter.buildXML(userList, ProjectConstant.writeXMLPath);
    }

    @Test
    void testSAXParser() {

        SAXParser saxParser = new SAXParser();
        List<User> userList = saxParser.parse(ProjectConstant.writeXMLPath);
        User user01 = new User(
                1,
                "Mark",
                "Smoliak",
                User.Sex.male,
                "smoliak23@gmail.com",
                "2003-12-18"
        );
        assertEquals(user01, userList.get(1));
    }



    @Test
    void testAddUser() {
        User user01 = new User(
                1,
                "Mark",
                "Smoliak",
                User.Sex.male,
                "smoliak23@gmail.com",
                "2003-12-18"
        );
        User user02 = new User(
                134,
                "Gran",
                "Barnum",
                User.Sex.male,
                "abrabak@gmail.com",
                "2004-01-12"
        );
        User user01Test;
        User user02Test;
        try {
            daoFactory.getTaxDAO().insertUser(user02);

            user01Test = daoFactory.getTaxDAO().getUser(1);
            user02Test = daoFactory.getTaxDAO().findUserByFilter(ProjectConstant.FIND_ALL_USERS).stream().filter(u -> u.getId() != 1).findFirst().orElse(null);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(user02Test);

        assertEquals(user01, user01Test);
        assertEquals(user02, user02Test);

    }

    @Test
    void testUpdateUser() {

        User user01Test;
        User user02Test;
        try {
            user01Test = daoFactory.getTaxDAO().getUser(1);
            user01Test.setFirstName("Ma");
            user01Test.setLastName("Smo");
            user01Test.setEmail("smoliak23@ukr.net");
            daoFactory.getTaxDAO().updateUser(user01Test);

            user02Test = daoFactory.getTaxDAO().getUser(1);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(user01Test, user02Test);

    }

    @Test
    void testAddDeleteTax() {

        User user01Test;
        TaxType tax01Test;
        TaxType tax01 = new TaxTypeIncome();
        try {

            user01Test = daoFactory.getTaxDAO().getUser(1);

            List<TaxType> taxList = new ArrayList<>();
            tax01.setIdNumber(321);
            tax01.setValue(3423.312);
            tax01.setDatePayment("2002-04-05");
            tax01.calculateAmountTax();
            taxList.add(tax01);

            daoFactory.getTaxDAO().addTaxForUser(user01Test, taxList);

            tax01Test = daoFactory.getTaxDAO().findTaxByFilter(ProjectConstant.FIND_ALL_USERS).stream().findFirst().orElse(null);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(tax01Test);
        assertEquals(tax01.getType(), tax01Test.getType());
        assertEquals(tax01.getIdNumber(), tax01Test.getIdNumber());
        assertEquals(tax01.getAmountOfTax(), tax01Test.getAmountOfTax());
        assertEquals(tax01.getDatePayment(), tax01Test.getDatePayment());


        try {

            daoFactory.getTaxDAO().deleteUserTax(321);

            user01Test = daoFactory.getTaxDAO().getUser(1);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(0, user01Test.getTax().size());

    }


    @ParameterizedTest
    @MethodSource("testValidIdUser")
    void testGetIdUser(Integer input, boolean expected) {
        assertEquals(expected, Validator.validationIdNumber(input, ProjectConstant.VALID_ID_NUMBER));
    }

    static Stream<Arguments> testValidIdUser() {
        return Stream.of(
                Arguments.of(15, false),
                Arguments.of(1, true),
                Arguments.of(-12, false)
        );
    }

    @ParameterizedTest
    @MethodSource("testValidIdTax")
    void testGetIdTax(Integer input, boolean expected) {
        assertEquals(expected, Validator.validationIdNumberForTax(input));
    }

    static Stream<Arguments> testValidIdTax() {
        return Stream.of(
                Arguments.of(321, true),
                Arguments.of(1, true),
                Arguments.of(-12, false)
        );
    }

    @Test
    void testDeleteUser() {
        User user01Test;
        List<User> userList;
        try {
            user01Test = daoFactory.getTaxDAO().getUser(1);
            daoFactory.getTaxDAO().deleteUser(user01Test);

            userList = daoFactory.getTaxDAO().findUserByFilter(ProjectConstant.FIND_ALL_USERS);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(userList);
        assertEquals(1, userList.size());

        List<User> users = new ArrayList<>();
        users.add(user01Test);
        XMLWriter.buildXML(users, ProjectConstant.writeXMLPath);

    }

}
