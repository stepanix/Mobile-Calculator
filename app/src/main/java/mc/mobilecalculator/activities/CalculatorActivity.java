package mc.mobilecalculator.activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mc.mobilecalculator.R;
import mc.mobilecalculator.helpers.CalculatorHelper;
import mc.mobilecalculator.models.Input;
import mc.mobilecalculator.presenters.CalculatorPresenter;
import mc.mobilecalculator.presenters.CalculatorPresenterImpl;
import mc.mobilecalculator.views.CalculatorView;

public class CalculatorActivity extends Activity implements CalculatorView {

    private CalculatorPresenter mPresenter;

    @BindView(R.id.txtv_display_calculation)
    TextView mCalculationView;

    @BindView(R.id.txtv_display_operator)
    TextView mOperatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        ButterKnife.bind(this);

        mPresenter = new CalculatorPresenterImpl(new CalculatorHelper(), this, new Input(), new Input());
    }

    @Override
    public void showInput(String computation) {
        mCalculationView.setText(computation);
    }

    @Override
    public void showOperator(String operator) {
        mOperatorView.setText(operator);
    }

    @OnClick({R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9})
    public void numberButtonClicked(Button button) {
        mPresenter.appendValue((String) button.getText());
    }

    @OnClick({R.id.btn_plus, R.id.btn_minus, R.id.btn_multiply, R.id.btn_divide})
    public void operatorButtonClicked(Button button) {
        mPresenter.appendOperator((String) button.getText());
    }

    @OnClick(R.id.btn_clear)
    public void clearButtonClicked(Button button) {
        mPresenter.clearCalculation();
    }

    @OnClick(R.id.btn_equals)
    public void equalsButtonClicked(Button button) {
        mPresenter.performCalculation();
    }

}
