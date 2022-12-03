import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.util.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorTest {

    @ParameterizedTest
    @MethodSource("testInputFirstName")
    void testInputFirstName(String input, boolean expected) {
        assertEquals(expected, Validator.validationString(input, ProjectConstant.VALID_FIRST_NAME));
    }

    @ParameterizedTest
    @MethodSource("testInputLastName")
    void testInputLastName(String input, boolean expected) {
        assertEquals(expected, Validator.validationString(input, ProjectConstant.VALID_LAST_NAME));
    }

    @ParameterizedTest
    @MethodSource("testInputEmail")
    void testInputEmail(String input, boolean expected) {
        assertEquals(expected, Validator.validationString(input, ProjectConstant.VALID_EMAIL));
    }

    @ParameterizedTest
    @MethodSource("testInputDate")
    void testInputDate(String input, boolean expected) {
        assertEquals(expected, Validator.validationString(input, ProjectConstant.VALID_DATE));
    }


    static Stream<Arguments> testInputFirstName() {
        return Stream.of(
                Arguments.of("fGGG", false),
                Arguments.of("Max", true),
                Arguments.of("Oleksandr", true),
                Arguments.of("Надія", true),
                Arguments.of("Оленка", true),
                Arguments.of("Ігнат", true),
                Arguments.of("васьок", false)
        );
    }

    static Stream<Arguments> testInputLastName() {
        return Stream.of(
                Arguments.of("Бурундук", true),
                Arguments.of("Іщук", true),
                Arguments.of("Ящук", true),
                Arguments.of("Panchuk", true),
                Arguments.of("Litanko", true),
                Arguments.of("Gan-Dar", true),
                Arguments.of("D'Ark", true)
        );
    }

    static Stream<Arguments> testInputEmail() {
        return Stream.of(
                Arguments.of("olex4ndr@gmail.com", true),
                Arguments.of("ddd_111_fff@gmail.com", true),
                Arguments.of("sasha@gmail,com", false),
                Arguments.of("sashaara", false)
        );
    }

    static Stream<Arguments> testInputDate() {
        return Stream.of(
                Arguments.of("2000-01-01", true),
                Arguments.of("2023-10-18", true),
                Arguments.of("2022-18-10", false),
                Arguments.of("2022", false),
                Arguments.of("2002-01-32", false),
                Arguments.of("0000/03/12", false),
                Arguments.of("2000-1-01", false)
        );
    }
}
