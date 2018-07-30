package com.example.minh.calculatordemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ListIterator;
import java.util.Stack;

public class FragmentCaculator extends Fragment implements View.OnClickListener {
    public static final String MULTIPLY = "*";
    public static final String MINUS = "-";
    public static final String PLUS = "+";
    public static final String DIVIDE = "/";
    public static final String LEFT = "(";
    public static final String RIGHT = ")";
    public static int ONE = 1;
    public static int TWO = 2;
    public static int THREE = 3;
    public static int ZERO = 0;
    private TextView mTxtResult;
    private Button mBtn0;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;
    private Button mBtn6;
    private Button mBtn7;
    private Button mBtn8;
    private Button mBtn9;
    private Button mBtnPlus;
    private Button mBtnEqual;
    private Button mBtnMultiply;
    private Button mBtnAC;
    private Button mBtnMinus;
    private Button mBtnDivide;
    private Button mBtnLeft;
    private Button mBtnRight;
    private Button mBtnDot;
    private String mResult = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        mTxtResult = view.findViewById(R.id.text_result);
        mBtn0 = view.findViewById(R.id.button_0);
        mBtn1 = view.findViewById(R.id.button_1);
        mBtn2 = view.findViewById(R.id.button_2);
        mBtn3 = view.findViewById(R.id.button_3);
        mBtn4 = view.findViewById(R.id.button_4);
        mBtn5 = view.findViewById(R.id.button_5);
        mBtn6 = view.findViewById(R.id.button_6);
        mBtn7 = view.findViewById(R.id.button_7);
        mBtn8 = view.findViewById(R.id.button_8);
        mBtn9 = view.findViewById(R.id.button_9);
        mBtnAC = view.findViewById(R.id.button_ac);
        mBtnPlus = view.findViewById(R.id.button_plus);
        mBtnEqual = view.findViewById(R.id.button_equal);
        mBtnMultiply = view.findViewById(R.id.button_multiply);
        mBtnMinus = view.findViewById(R.id.button_minus);
        mBtnDivide = view.findViewById(R.id.button_divide);
        mBtnLeft = view.findViewById(R.id.button_left);
        mBtnRight = view.findViewById(R.id.button_right);
        mBtnDot = view.findViewById(R.id.button_dot);

        mBtn0.setOnClickListener(this);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mBtn6.setOnClickListener(this);
        mBtn7.setOnClickListener(this);
        mBtn8.setOnClickListener(this);
        mBtn9.setOnClickListener(this);
        mBtnAC.setOnClickListener(this);
        mBtnPlus.setOnClickListener(this);
        mBtnEqual.setOnClickListener(this);
        mBtnMultiply.setOnClickListener(this);
        mBtnMinus.setOnClickListener(this);
        mBtnDivide.setOnClickListener(this);
        mBtnLeft.setOnClickListener(this);
        mBtnRight.setOnClickListener(this);
        mBtnDot.setOnClickListener(this);
        return view;
    }


    public int getPriority(String op) {
        if (op.equals(MULTIPLY) || op.equals(DIVIDE)) {
            return 2;
        }
        if (op.equals(PLUS) || op.equals(MINUS)) {
            return 1;
        }
        if (op.equals(LEFT) || op.equals(RIGHT)) {
            return 0;
        }
        return 3;
    }

    public String convertToPostfix(String expression) {
        //Đổi biểu thức trung tố sang biểu thức hậu tố
        //mListStack dùng để lưu dấu của biểu thức
        String mNumber = "";
        Stack<String> mListStack = new Stack<>();
        Stack<String> mPostfix = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            String mCharacter = String.valueOf(expression.charAt(i));
            int mCheck = getPriority(mCharacter);
            if (mCheck == THREE) {
                mNumber = mNumber + String.valueOf(expression.charAt(i));
                if (i == expression.length() - 1) {
                    mPostfix.add(mNumber);
                }
            } else {
                if (!mNumber.equals("")) {
                    mPostfix.add(mNumber);
                    mNumber = "";
                }
                if (mCheck == TWO || mCheck == ONE) {
                    if (mListStack.size() == ZERO) {
                        mListStack.add(mCharacter);
                    } else {
                        if (mCheck > getPriority(mListStack.peek())) {
                            mListStack.add(mCharacter);
                        } else {
                            String mSign = mListStack.pop();
                            mPostfix.add(mSign);
                            mListStack.add(mCharacter);
                        }
                    }
                }
                if (mCheck == ZERO) {
                    if (mCharacter.equals(LEFT)) {
                        mListStack.add(mCharacter);
                    } else {
                        while (!mListStack.peek().equals(LEFT)) {
                            mPostfix.add(mListStack.pop());
                        }
                        mListStack.pop();
                    }
                }
            }
        }
        while (mListStack.size() != ZERO) {
            mPostfix.add(mListStack.pop());
        }
        //Tính từ biểu thức hậu tố sang so
        String result = Calculated(mPostfix);
        return result;
    }

    public String Calculated(Stack<String> mPostfix) {
        Stack<String> mListStack = new Stack<>();
        ListIterator<String> mIterator = mPostfix.listIterator();
        while (mIterator.hasNext()) {
            String mCharacter = mIterator.next();
            if (getPriority(mCharacter) == THREE) {
                mListStack.add(mCharacter);
            } else {
                float a = Float.parseFloat(mListStack.pop());
                float b = Float.parseFloat(mListStack.pop());
                float c = 0;
                if (mCharacter.equals(PLUS)) {
                    c = b + a;
                } else if (mCharacter.equals(MINUS)) {
                    c = b - a;
                } else if (mCharacter.equals(MULTIPLY)) {
                    c = b * a;
                } else if (mCharacter.equals(DIVIDE)) {
                    c = b / a;
                }
                mListStack.add(String.valueOf(c));
            }
        }
        return mListStack.pop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_0:
                mResult += "0";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_1:
                mResult += "1";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_2:
                mResult += "2";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_3:
                mResult += "3";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_4:
                mResult += "4";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_5:
                mResult += "5";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_6:
                mResult += "6";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_7:
                mResult += "7";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_8:
                mResult += "8";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_9:
                mResult += "9";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_plus:
                mResult += "+";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_minus:
                mResult += "-";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_multiply:
                mResult += "*";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_divide:
                mResult += "/";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_ac:
                mResult = "";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_left:
                mResult += "(";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_right:
                mResult += ")";
                mTxtResult.setText(mResult);
                break;
            case R.id.button_equal:
                String mfinalResult = convertToPostfix(mResult);
                mTxtResult.setText(mfinalResult);
                break;
            case R.id.button_dot:
                mResult += ".";
                mTxtResult.setText(mResult);
                break;
        }
    }
}
