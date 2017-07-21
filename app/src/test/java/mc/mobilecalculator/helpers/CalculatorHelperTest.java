package mc.mobilecalculator.helpers;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import mc.mobilecalculator.models.Input;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Henry.Oforeh on 2017/07/19.
 */

@RunWith(MockitoJUnitRunner.class)
public class CalculatorHelperTest {

    private CalculatorHelper mCalculator;

    @Mock
    private Input mFirstInput;

    @Mock
    private Input mSecondInput;

    private static final Integer INPUT_VALUE_A = 8;
    private static final Integer INPUT_VALUE_B = 5;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mFirstInput.getValue()).thenReturn(Integer.toString(INPUT_VALUE_A));
        when(mSecondInput.getValue()).thenReturn(Integer.toString(INPUT_VALUE_B));

        mCalculator = new CalculatorHelper();
    }

    @Test
    public void testAddition() {
        String expectedResult = Integer.toString(INPUT_VALUE_A + INPUT_VALUE_B);

        assertThat("Addition was executed correctly",
                mCalculator.add(mFirstInput, mSecondInput), is(equalTo(expectedResult)));
    }

    @Test
    public void testSubtraction() {
        String expectedResult = Integer.toString(INPUT_VALUE_A - INPUT_VALUE_B);

        assertThat("Subtraction successful",
                mCalculator.subtract(mFirstInput, mSecondInput), is(equalTo(expectedResult)));
    }

    @Test
    public void testMultiplication() {
        String expectedResult = Integer.toString(INPUT_VALUE_A * INPUT_VALUE_B);

        assertThat("Multiplication successful",
                mCalculator.multiply(mFirstInput, mSecondInput), is(equalTo(expectedResult)));
    }

    @Test
    public void testDivision() {
        // Limit decimal digits as specified in the operand
        double digits = Math.pow(10, Input.MAX_DECIMAL_DIGITS);
        double result = Math.round(((double) INPUT_VALUE_A / (double) INPUT_VALUE_B) * digits) / digits;

        String expectedResult = Double.toString(result);

        assertThat("Division successful",
                mCalculator.divide(mFirstInput, mSecondInput), is(equalTo(expectedResult)));
    }




}
