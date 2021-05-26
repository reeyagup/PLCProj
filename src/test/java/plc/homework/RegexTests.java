package plc.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Contains JUnit tests for {@link Regex}. Test structure for steps 1 & 2 are
 * provided, you must create this yourself for step 3.
 *
 * To run tests, either click the run icon on the left margin, which can be used
 * to run all tests or only a specific test. You should make sure your tests are
 * run through IntelliJ (File > Settings > Build, Execution, Deployment > Build
 * Tools > Gradle > Run tests using <em>IntelliJ IDEA</em>). This ensures the
 * name and inputs for the tests are displayed correctly in the run window.
 */
public class RegexTests {

    /**
     * This is a parameterized test for the {@link Regex#EMAIL} regex. The
     * {@link ParameterizedTest} annotation defines this method as a
     * parameterized test, and {@link MethodSource} tells JUnit to look for the
     * static method {@link #testEmailRegex()}.
     *
     * For personal preference, I include a test name as the first parameter
     * which describes what that test should be testing - this is visible in
     * IntelliJ when running the tests (see above note if not working).
     */
    @ParameterizedTest
    @MethodSource
    public void testEmailRegex(String test, String input, boolean success) {
        test(input, Regex.EMAIL, success);
    }

    /**
     * This is the factory method providing test cases for the parameterized
     * test above - note that it is static, takes no arguments, and has the same
     * name as the test. The {@link Arguments} object contains the arguments for
     * each test to be passed to the function above.
     */
    public static Stream<Arguments> testEmailRegex() {
        return Stream.of(
                Arguments.of("Alphanumeric", "thelegend27@gmail.com", true),
                Arguments.of("UF Domain", "otherdomain@ufl.edu", true),
                Arguments.of("Only Numbers", "123@gmail.com", true),
                Arguments.of("Another Domain", "somename@aol.net", true),
                Arguments.of("Only Has Numbers", "1234@gmail.com", true),
                Arguments.of("No @ or .", "NAME", false),
                Arguments.of("Missing Domain Dot", "missingdot@gmailcom", false),
                Arguments.of("Symbols", "symbols#$%@gmail.com", false),
                Arguments.of("No Dot Com", "name@", false),
                Arguments.of("Missing @", "name.com", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testEvenStringsRegex(String test, String input, boolean success) {
        test(input, Regex.EVEN_STRINGS, success);
    }

    public static Stream<Arguments> testEvenStringsRegex() { //TODO: DONE
        return Stream.of(
                //what has ten letters and starts with gas?
                Arguments.of("10 Characters", "automobile", true),
                Arguments.of("14 Characters", "i<3pancakes10!", true),
                Arguments.of("12 Characters", "ABCDE!@#$%^&", true),
                Arguments.of("18", "HomeSweetHome!ABCD", true),
                Arguments.of("20", "1234567890!@#$%^&*()", true),
                Arguments.of("0 Characters", "", false),
                Arguments.of("9 Characters", "Instagram", false),
                Arguments.of("6 Characters", "6chars", false),
                Arguments.of("9 Characters", "ninelives", false),
                Arguments.of("13 Characters", "i<3pancakes9!", false),
                Arguments.of("25 Characters", "ABCDEFGHIJKLMNOPQRSTUVWXYZ!", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testIntegerListRegex(String test, String input, boolean success) {
        test(input, Regex.INTEGER_LIST, success);
    }

    public static Stream<Arguments> testIntegerListRegex() {
        return Stream.of(
                Arguments.of("Single Element", "[1]", true),
                Arguments.of("Empty List", "[]", true),
                Arguments.of("List With Large Integers", "[100, 155, 1334, 67854,234,5454]", true),
                Arguments.of("Zeros", "[0,0,0,0]", true),
                Arguments.of("Mixed Spaces", "[1,2,3,4, 5]", true),
                Arguments.of("Multiple Elements", "[1,2,3]", true),
                Arguments.of("Missing Brackets", "1,2,3", false),
                Arguments.of("Missing Commas", "[1 2 3]", false),
                Arguments.of("Decimals", "[1.5 , 0.2, 3]" , false),
                Arguments.of("Fractions", "[1/5 , 0/2, 3]" , false),
                Arguments.of("Double Quotes", "[0,, 29]" , false),
                Arguments.of("Non Numerical Symbols", "[@, #, $, r]" , false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testNumberRegex(String test, String input, boolean success) { //TODO: ALMOST DONE
        test(input, Regex.NUMBER, success);
    }

    public static Stream<Arguments> testNumberRegex() { //TODO
        return Stream.of(
                Arguments.of("Single Integer", "-1.0", true),
                Arguments.of("Large Number", "124567890", true),
                Arguments.of("With Decimal Point", "0.5", true),
                Arguments.of("Negative With Decimal Point", "-0.5", true),
                Arguments.of("Trailing Zeroes", "00.500", true),
                Arguments.of("With Decimal Point and Positive Sign", "+100.4", true),
                Arguments.of("Number before decimal point", "0.5", true),
                Arguments.of("Decimal Point with Zero", "100.0", true),
                Arguments.of("Negative Decimal Point without Zero and with Negative", "-1.5", true),
                Arguments.of("No number before decimal point", ".5", false),
                Arguments.of("Two decimal points at the beginning of number", "--0.5", false),
                Arguments.of("Decimal Point without Zero", "1.", false),
                Arguments.of("Positive Symbol Decimal Point without Zero", "+1.", false),
                Arguments.of("Zero without trailing zeros after decimal point ", "0.", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testStringRegex(String test, String input, boolean success) {
        test(input, Regex.STRING, success);
    }

    public static Stream<Arguments> testStringRegex() {
        return Stream.of(
                Arguments.of("Quotes", "\"abc123\"", true),
                Arguments.of("White Spaces", "\" a a a \\\"\"", true),
                Arguments.of("Capital Letters", "\"ABC123\"", true),
                Arguments.of("Slash with t and word after", "\"reeya\\thello\"", true),
                Arguments.of("Slash with b and whitespace", "\"  \\b  \"", true),
                Arguments.of("Slash with '", "\"\\'\"", true),
                Arguments.of("No Slash", "\"Hello, World\"", true),
                Arguments.of("Slash without correct character", "\"some\\z\"", false),
                Arguments.of("Just slash", "\"some\\\"", false),
                Arguments.of("Space and than one character after slash", "\"testing123ABC\\ t\"", false),
                Arguments.of("No second quotes", "\"Hello", false)
                );
    }

    /**
     * Asserts that the input matches the given pattern. This method doesn't do
     * much now, but you will see this concept in future assignments.
     */
    private static void test(String input, Pattern pattern, boolean success) {
        Assertions.assertEquals(success, pattern.matcher(input).matches());
    }

}