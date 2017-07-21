package mc.mobilecalculator.presenters;

/**
 * Created by Henry.Oforeh on 2017/07/19.
 */
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import mc.mobilecalculator.helpers.CalculatorHelper;
import mc.mobilecalculator.models.Input;
import mc.mobilecalculator.models.enums.Operator;
import mc.mobilecalculator.views.CalculatorView;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorPresenterTest {

    @Mock
    private CalculatorHelper mCalculatorHelper;

    @Mock
    private CalculatorView mView;

    @Mock
    private Input mPreviousInput;

    @Mock
    private Input mCurrentInput;

    private CalculatorPresenterImpl mPresenter;

    private static final String SHORT_INPUT_A = "8";
    private static final String SHORT_INPUT_B = "5";
    private static final String LONG_INPUT = "38493";
    private static final Operator[] OPERATORS = {Operator.PLUS, Operator.MULTIPLY, Operator.DIVIDE, Operator.MINUS};

    @Before
    public void setupCalculatorPresenter() {
        MockitoAnnotations.initMocks(this);

        when(mCurrentInput.getValue()).thenReturn(Input.DEFAULT_VALUE);
        when(mPreviousInput.getValue()).thenReturn(Input.DEFAULT_VALUE);

        when(mCalculatorHelper.add(any(Input.class), any(Input.class))).thenReturn("0");
        when(mCalculatorHelper.subtract(any(Input.class), any(Input.class))).thenReturn("0");
        when(mCalculatorHelper.multiply(any(Input.class), any(Input.class))).thenReturn("0");
        when(mCalculatorHelper.divide(any(Input.class), any(Input.class))).thenReturn("0");

        mPresenter = new CalculatorPresenterImpl(mCalculatorHelper, mView, mCurrentInput, mPreviousInput);
    }

    @Test
    public void afterInitialization_operatorShouldBeBlank() {
        assertThat("Operator is blank after initialization",
                mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    @Test
    public void initializeApplication_presenterShouldSetDisplayToZero() {
        verify(mView).showInput(Input.DEFAULT_VALUE);
    }

    @Test
    public void userPressDeleteButton_shouldResetCalculator() {
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_B);

        // When the delete ("AC") button is clicked
        mPresenter.clearCalculation();

        // All operands and operators should be removed from the calculation
        verify(mPreviousInput, times(2)).reset();
        verify(mCurrentInput, times(3)).reset();
        assertThat("Operator was reset", mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));

        // Input and operator display should be reset
        verify(mView, times(5)).showInput(anyString());
        verify(mView, times(5)).showOperator(anyString());
    }

    @Test
    public void numbersTyped_shouldBeStoredAsInput() {
        for (int i = 0; i < LONG_INPUT.length(); i++) {
            mPresenter.appendValue(LONG_INPUT.substring(i, i + 1));
        }

        verify(mCurrentInput, times(LONG_INPUT.length())).attachValue(anyString());
        verify(mView, times(LONG_INPUT.length() + 1)).showInput(anyString());
    }

    @Test
    public void operatorsTyped_shouldBeStoredAndDisplayed() {
        for (Operator operator : OPERATORS) {
            mPresenter.appendOperator(operator.toString());
        }

        assertThat("Operator has been stored and updated",
                mPresenter.getOperator(), is(equalTo(OPERATORS[OPERATORS.length - 1])));
        verify(mView, times(OPERATORS.length + 1)).showOperator(anyString());
    }

    @Test
    public void numbersTypedAfterOperator_shouldBeStoredAsNewInput() {
        mPresenter.appendValue(SHORT_INPUT_A);
        when(mCurrentInput.getValue()).thenReturn(SHORT_INPUT_A);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_B);

        verify(mPreviousInput).setValue(SHORT_INPUT_A);
        verify(mCurrentInput).attachValue(SHORT_INPUT_B);
    }

    @Test
    public void secondDistinctOperator_shouldComputePartialCalculation() {
        String result = calculateResult(SHORT_INPUT_A, SHORT_INPUT_B, Operator.PLUS);
        when(mCalculatorHelper.add(any(Input.class), any(Input.class))).thenReturn(result);

        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_B);
        when(mCurrentInput.getValue()).thenReturn(result);
        mPresenter.appendOperator(Operator.DIVIDE.toString());

        verify(mCalculatorHelper).add(any(Input.class), any(Input.class));
        verify(mView, atLeastOnce()).showInput(result);
    }

    @Test
    public void operatorEnteredBeforeFirstInput_shouldSetFirstInputToZero() {
        when(mCurrentInput.getValue()).thenReturn(Input.DEFAULT_VALUE);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_A);

        verify(mPreviousInput).setValue(Input.DEFAULT_VALUE);
    }

    @Test
    public void userEventCompute_shouldExecuteCalculationAndUpdateDisplay() {
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.appendOperator(Operator.MULTIPLY.toString());
        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.performCalculation();

        verify(mCalculatorHelper).multiply(any(Input.class), any(Input.class));
        // Views should have been updated 5 times in total (1 initialization, 4 operations)
        verify(mView, times(5)).showInput(anyString());
        verify(mView, times(5)).showOperator(anyString());
    }

    private String calculateResult(String firstOperand, String secondOperand, Operator operator) {
        switch (operator) {
            case PLUS:
                return Integer.toString(Integer.valueOf(firstOperand)
                        + Integer.valueOf(secondOperand));
            case MINUS:
                return Integer.toString(Integer.valueOf(firstOperand)
                        - Integer.valueOf(secondOperand));
            case MULTIPLY:
                return Integer.toString(Integer.valueOf(firstOperand)
                        * Integer.valueOf(secondOperand));
            case DIVIDE:
                return Integer.toString(Integer.valueOf(firstOperand)
                        / Integer.valueOf(secondOperand));
        }

        return "";
    }



}
