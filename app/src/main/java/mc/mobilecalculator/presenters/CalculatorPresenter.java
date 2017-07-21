package mc.mobilecalculator.presenters;

import mc.mobilecalculator.models.enums.Operator;

/**
 * Created by Henry.Oforeh on 2017/07/18.
 */

public interface CalculatorPresenter {

    void clearCalculation();

    Operator getOperator();

    void appendValue(String value);

    void appendOperator(String operator);

    void performCalculation();
}
