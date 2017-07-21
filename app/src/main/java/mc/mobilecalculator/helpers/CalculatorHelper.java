package mc.mobilecalculator.helpers;

import mc.mobilecalculator.models.Input;

/**
 * Created by Henry.Oforeh on 2017/07/18.
 */

public class CalculatorHelper {

    public String add(Input firstInput, Input secondInput) {
        double result = getValue(firstInput) + getValue(secondInput);
        return formatResult(result);
    }

    public String subtract(Input firstInput, Input secondInput) {
        double result = getValue(firstInput) - getValue(secondInput);
        return formatResult(result);
    }

    public String multiply(Input firstInput, Input secondInput) {
        double result = getValue(firstInput) * getValue(secondInput);
        return formatResult(result);
    }


    public String divide(Input firstInput, Input secondInput) {
        double result = getValue(firstInput) / getValue(secondInput);
        return formatResult(result);
    }

    private double getValue(Input input) {
        return Double.valueOf(input.getValue());
    }

    private String formatResult(Double res) {

        double digits = Math.pow(10, Input.MAX_DECIMAL_DIGITS);
        res = Math.round(res * digits) / digits;

        String result = Double.toString(res);
        String decimals = result.substring(0, result.indexOf("."));
        String fractionals = result.substring(result.indexOf(".") + 1);

        while (fractionals.length() > 0 && fractionals.substring(fractionals.length() - 1).equals("0")) {
            fractionals = fractionals.substring(0, fractionals.length() - 1);
        }

        if (fractionals.length() > 0) {
            return decimals + "." + fractionals;
        } else {
            return decimals;
        }
    }

}
