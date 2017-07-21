package mc.mobilecalculator.presenters;

import mc.mobilecalculator.helpers.CalculatorHelper;
import mc.mobilecalculator.models.Input;
import mc.mobilecalculator.models.enums.Operator;
import mc.mobilecalculator.views.CalculatorView;

/**
 * Created by Henry.Oforeh on 2017/07/18.
 */

public class CalculatorPresenterImpl implements CalculatorPresenter {

    private CalculatorHelper mCalculatorHelper;
    private CalculatorView mCalculatorView;

    private Input mCurrentInput;
    private Input mPreviousInput;
    private Operator mOperator;
    private boolean hasLastInputOperator;
    private boolean hasLastInputEquals;

    public CalculatorPresenterImpl(CalculatorHelper mCalculatorHelper,
                                   CalculatorView mCalculatorView,
                                   Input mCurrentInput,
                                   Input mPreviousInput) {
        this.mCalculatorHelper = mCalculatorHelper;
        this.mCalculatorView = mCalculatorView;

        this.mCurrentInput = mCurrentInput;
        this.mPreviousInput = mPreviousInput;
        resetCalculator();
        updateDisplay();
    }

    @Override
    public void clearCalculation() {
        resetCalculator();
        updateDisplay();
    }

    @Override
    public Operator getOperator() {
        return mOperator;
    }

    @Override
    public void appendValue(String value) {
        if (hasLastInputOperator) {
            mPreviousInput.setValue(mCurrentInput.getValue());
            mCurrentInput.reset();
        } else if (hasLastInputEquals) {
            resetCalculator();
        }

        if (mCurrentInput.getValue().length() < Input.MAX_LENGTH) {
            mCurrentInput.attachValue(value);
            hasLastInputOperator = false;
            hasLastInputEquals = false;
            updateDisplay();
        }
    }

    @Override
    public void appendOperator(String operator) {

            if (mOperator != Operator.EMPTY && !hasLastInputOperator) {
                performCalculation();
            }

            mOperator = Operator.getOperator(operator);
            hasLastInputOperator = true;
            updateDisplay();
        }

    @Override
    public void performCalculation() {
        String result = "";

        switch (mOperator) {
            case PLUS:
                result = mCalculatorHelper.add(mPreviousInput, mCurrentInput);
                break;
            case MINUS:
                result = mCalculatorHelper.subtract(mPreviousInput, mCurrentInput);
                break;
            case MULTIPLY:
                result = mCalculatorHelper.multiply(mPreviousInput, mCurrentInput);
                break;
            case DIVIDE:
                result = mCalculatorHelper.divide(mPreviousInput, mCurrentInput);
                break;
            default:
                result = mCurrentInput.getValue();
        }

        mCurrentInput.setValue(result);

        mPreviousInput.reset();
        mOperator = Operator.EMPTY;
        hasLastInputEquals = true;
        updateDisplay();
    }

    private void resetCalculator() {
        mCurrentInput.reset();
        mPreviousInput.reset();
        hasLastInputEquals = false;
        hasLastInputOperator = false;

        mOperator = Operator.EMPTY;
    }

    private void updateDisplay() {
        mCalculatorView.showInput(mCurrentInput.getValue());
        mCalculatorView.showOperator(mOperator.toString());
    }
}
