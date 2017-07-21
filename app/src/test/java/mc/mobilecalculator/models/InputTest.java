package mc.mobilecalculator.models;

/**
 * Created by Henry.Oforeh on 2017/07/19.
 */

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class InputTest {

    private Input mInput;

    private static final String VALUE = "356";

    @Before
    public void setUp() {
        mInput = new Input();
    }

    @Test
    public void defaultValue_shouldBeZero() {
        assertThat("Default value was zero", mInput.getValue(), is(equalTo(Input.DEFAULT_VALUE)));
    }

    @Test
    public void appendValue_shouldAttachInputVariables() {
        appendValues(VALUE);

        assertThat("Values were stored in the input", mInput.getValue(), is(equalTo(VALUE)));
    }

    @Test
    public void reset_shouldSetValueToZero() {
        appendValues(VALUE);
        mInput.reset();

        assertThat("value is zero", mInput.getValue(), is(equalTo(Input.DEFAULT_VALUE)));
    }

    @Test
    public void setValue_shouldReplaceValue() {
        String value = "12345";
        appendValues(VALUE);
        mInput.setValue(value);

        assertThat("Value replaced by setValue()", mInput.getValue(), is(equalTo(value)));
    }




    private void appendValues(String value) {
        for (int i = 0; i < value.length(); i++) {
            mInput.attachValue(value.substring(i, i + 1));
        }
    }
}
