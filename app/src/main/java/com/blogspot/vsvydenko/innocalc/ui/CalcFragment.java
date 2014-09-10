package com.blogspot.vsvydenko.innocalc.ui;

import com.blogspot.vsvydenko.innocalc.R;
import com.blogspot.vsvydenko.innocalc.math.InfToPostConvert;
import com.blogspot.vsvydenko.innocalc.math.ReversePolishNotation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by vsvydenko on 10.09.14.
 */
public class CalcFragment extends Fragment {

    View returnView;

    Button btnCalc;
    Button btnDivide;
    Button btnPower;

    TextView txtPhase1Result;
    TextView txtPhase2Result;
    TextView txtPhase3Result;


    Double calculationResult;

    public CalcFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        returnView = inflater.inflate(R.layout.fragment_calc, container, false);

        return returnView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeContent();
    }

    private void initializeContent() {
        btnCalc = (Button) returnView.findViewById(R.id.btnCalc);
        btnDivide = (Button) returnView.findViewById(R.id.btnDivideByZero);
        btnPower = (Button) returnView.findViewById(R.id.btnPower);

        txtPhase1Result = (TextView) returnView.findViewById(R.id.txtPhase1Result);
        txtPhase2Result = (TextView) returnView.findViewById(R.id.txtPhase2Result);
        txtPhase3Result = (TextView) returnView.findViewById(R.id.txtPhase3Result);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnCalc:
                        calc();
                        break;
                    case R.id.btnDivideByZero:
                        divide(0);
                        break;
                    case R.id.btnPower:
                        pow(2);
                        break;
                }
            }
        };

        btnCalc.setOnClickListener(clickListener);
        btnDivide.setOnClickListener(clickListener);
        btnPower.setOnClickListener(clickListener);

    }

    private void calc() {
        String answer;
        String problem = getActivity().getResources().getString(R.string.math_problem);
        InfToPostConvert infToPostConvert = new InfToPostConvert(problem);

        String postfixNotation = infToPostConvert.doTrans();

        calculationResult = ReversePolishNotation.calcRPN(postfixNotation);

        if (isDoublePositive(calculationResult)) {
            answer = getActivity().getResources()
                    .getString(R.string.result_phase, calculationResult.intValue());
        } else {
            answer = getActivity().getResources().getString(R.string.result_phase,
                    calculationResult);
        }

        txtPhase1Result.setText(answer);

        enablePhaseUI(2);
    }

    private void divide(int divisor) {
        if (divisor == 0) {
            txtPhase2Result.setText("Argument 'divisor' is 0");
            enablePhaseUI(3);
            return;
        }
        double phase2Result = calculationResult / divisor;
        enablePhaseUI(3);
    }

    private void pow(int power) {
        String answer;
        Double result = Math.pow(calculationResult, power);
        if (isDoublePositive(result)) {
            answer = getActivity().getResources()
                    .getString(R.string.result_phase, result.intValue());
        } else {
            answer = getActivity().getResources().getString(R.string.result_phase,
                    result);
        }

        txtPhase3Result.setText(answer);
    }

    private void enablePhaseUI(int phase) {
        switch (phase) {
            case 2:
                btnDivide.setEnabled(true);
                txtPhase2Result.setEnabled(true);
                break;
            case 3:
                btnPower.setEnabled(true);
                txtPhase3Result.setEnabled(true);
                break;
        }
    }

    private boolean isDoublePositive(double value) {
        if ((value == Math.floor(value)) && !Double.isInfinite(value)) {
            return true;
        } else {
            return false;
        }
    }

}
