package mc.mobilecalculator.models;



/**
 * Created by Henry.Oforeh on 2017/07/18.
 */

public class Input {

    public static final String DEFAULT_VALUE = "0";
    public static final Integer MAX_LENGTH = 10;
    public static final int MAX_DECIMAL_DIGITS = 1;

    private String mValue = DEFAULT_VALUE;

    public String getValue() {
        return mValue;
    }

    public void setValue(String mValue) {
        this.mValue = mValue;
    }

    public void reset() {
        mValue = DEFAULT_VALUE;
    }

    public void attachValue(String value) {
        if (mValue.equals(DEFAULT_VALUE)) {
            mValue = value;
        } else {
            mValue += value;
        }
    }

}
