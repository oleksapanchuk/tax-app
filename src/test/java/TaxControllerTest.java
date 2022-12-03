
import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.tax.*;
import com.panchuk.taxapp.util.TaxController;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaxControllerTest {


    @ParameterizedTest
    @MethodSource("testCreateTax")
    void testInputDouble(TaxType tax, int idPayment, int type, double money, String datePayment) {
        assertEquals(tax, TaxController.createTax(idPayment, type, money, datePayment));
    }


    static Stream<Arguments> testCreateTax() {
        TaxType taxType01 = new TaxTypeIncome();
        TaxType taxType02 = new TaxTypePresent();
        TaxType taxType03 = new TaxTypeRemuneration();
        TaxType taxType04 = new TaxTypeSale();
        TaxType taxType05 = new TaxTypeTransferFromAbroad();
        TaxType taxType06 = new TaxTypeBenefitsForChildren();
        TaxType taxType07 = new TaxTypeFinancialAid();

        taxType01.setIdNumber(1);
        taxType01.setValue(1);
        taxType01.calculateAmountTax();
        taxType01.setDatePayment("2000-01-01");

        taxType02.setIdNumber(1);
        taxType02.setValue(1);
        taxType02.calculateAmountTax();
        taxType02.setDatePayment("2000-01-01");

        taxType03.setIdNumber(1);
        taxType03.setValue(1);
        taxType03.calculateAmountTax();
        taxType03.setDatePayment("2000-01-01");

        taxType04.setIdNumber(1);
        taxType04.setValue(1);
        taxType04.calculateAmountTax();
        taxType04.setDatePayment("2000-01-01");

        taxType05.setIdNumber(1);
        taxType05.setValue(1);
        taxType05.calculateAmountTax();
        taxType05.setDatePayment("2000-01-01");

        taxType06.setIdNumber(1);
        taxType06.setValue(1);
        taxType06.calculateAmountTax();
        taxType06.setDatePayment("2000-01-01");

        taxType07.setIdNumber(1);
        taxType07.setValue(1);
        taxType07.calculateAmountTax();
        taxType07.setDatePayment("2000-01-01");

        return Stream.of(
                Arguments.of(taxType01, 1, 1, 1, "2000-01-01"),
                Arguments.of(taxType02, 1, 2, 1, "2000-01-01"),
                Arguments.of(taxType03, 1, 3, 1, "2000-01-01"),
                Arguments.of(taxType04, 1, 4, 1, "2000-01-01"),
                Arguments.of(taxType05, 1, 5, 1, "2000-01-01"),
                Arguments.of(taxType06, 1, 6, 1, "2000-01-01"),
                Arguments.of(taxType07, 1, 7, 1, "2000-01-01")
        );
    }
}
