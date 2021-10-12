package com.example.calculatorjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.calculatorjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
// boolean 반환타입으로 첫 번재 숫자를 입력하는지, 두 번재 숫자를 입력하는지 체크하기
    boolean isFirstInput = true;
    boolean isOperatorClick = false; // 연산자가 눌렸는지 확인하기 위한 변수 (처음에는 눌리지 않았으니 false를 설정)
    double inputNumber = 0;
//  입력된 숫자를 연산하는 변수, resultview에 출력할 수 있도록 하는 변수
    double resultNumber = 0;
    String operator = "=";
    String lastoperator = "+";

//  view 바인딩 선언하기
//  ActivityMainBinding이란 <layout> 태그로 선언된 XML을 위해 자동으로 만들어지는
//  결국에는 activity_main + Binding << 따라서 xml파일명이 변동되면 변하게 됨
    ActivityMainBinding activityMainBinding;

    // activity 클래스를 상속받으며서 반드시 onCreate() 함수를 오버라이딩 함
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(activityMainBinding.getRoot()); // 바인딩을 등록
        // getRood() -> layout을 반환시키는 메소드
        // setContentView 함수의 역할 view를 인자로 가짐, XML 레이아웃 리소스를 ID에 해당하는 파일을
        // 파싱하여 뷰를 생성한다. 뷰의 속성을 지정하고 뷰 간의 상하관계에 맞추어서 배치를 하도록 함.
        // 이와 같은 과정을 하나의 전개 infalte라고 부른다.
    }
//  출력만 필요하기 때문에 return 값은 필요 없음
//  메소드를 생성했으면 해당 메소드를 사용할 곳에 등록해주기
    public void numButtonClick(View view) {
        Button button = findViewById(view.getId()); // 눌린 버튼에 대한 객체를 받아온 것
        String getButtonText = button.getText().toString(); // 해당 객체의 text 값을 그대로 뽑아올 수 있음 toString - 문자열로 받아오기

        String getButtonText2 = view.getTag().toString(); // xml 에서 tag를 지정한 다음, 해당 tag를 그대로 불러올 수 있음
//      tag를 활용하여서 받아갈 수 있는 유용함? = 객체를 받아온 것 자체만으로도 메모리 할당량이 높아짐, 다만 tag는 그 자체의 값을 받아오기 때문에
//      메로리적인 측면에서 훨씬 절약적임을 알 수 있음

        if(isFirstInput) {
            activityMainBinding.resultTextView.setText(getButtonText);
            isFirstInput = false; // false처리를 해주지 않으면 계속 true를 반환하여 1이 append되지 않음
            if(operator.equals("=")) {
                activityMainBinding.mathTextView.setText(null); // text를 초기화하는 방법 "" or null
                isOperatorClick = false;
            }
        }else {
            if(activityMainBinding.resultTextView.getText().toString().equals("0")) {
                Toast.makeText(this, "o으로 시작되는 숫자는 없습니다.", Toast.LENGTH_SHORT).show();
                isFirstInput = true; // true로 바꾸고 새롭게 input이 가능하도록
            } else {
                activityMainBinding.resultTextView.append(getButtonText); // 1번 버튼을 눌렀을 때 1이 append되도록
            }
        }
    }

//  아래 메소드는 버튼이 눌리고, append되는 원리
    public void num1Click (View view) { // view는 UI의 버튼들을 가르킴
        if(isFirstInput) {
            activityMainBinding.resultTextView.setText("1");
            activityMainBinding.mathTextView.setText("1");
            isFirstInput = false; // false처리를 해주지 않으면 계속 true를 반환하여 1이 append되지 않음
        }else {
            activityMainBinding.resultTextView.append("1"); // 1번 버튼을 눌렀을 때 1이 append되도록
            activityMainBinding.mathTextView.append("1");
        }

    }
// 사칙연산 메소드
    public void operatorClick (View view) {
        isOperatorClick = true; // 연산자가 클릭됨
        lastoperator = view.getTag().toString();
        if(isFirstInput) { // 연산자를 클릭한 뒤 또 클릭했을 때 뒤에 눌린 연산자로 바뀌도록
            if(operator.equals("=")) { // 연속으로 연산할 때 result값이 mathtextView로 이동하여 더하기되어짐
                operator = view.getTag().toString();
                resultNumber = Double.parseDouble(activityMainBinding.resultTextView.getText().toString());
                activityMainBinding.mathTextView.setText(resultNumber + " " + operator + " ");
            } else {
                operator = view.getTag().toString(); // 현재 눌린 연산자를 저장
                String getMathText = activityMainBinding.mathTextView.getText().toString(); // 문자열을 저장
                String subString = getMathText.substring(0, getMathText.length() - 2); // mathText 문자열을 삭제, 0번째부터 mathText의 총 길이에서부터 -2까지 삭제한다
                // -2의 이유? 함께 설정해준 공백 + 연산자
                activityMainBinding.mathTextView.setText(subString); // 자른 문자열 저장
                activityMainBinding.mathTextView.append(operator + " "); // 뒤에 눌린 연산자 저장 + 공백
            }
        }else {
            inputNumber = Double.parseDouble(activityMainBinding.resultTextView.getText().toString()); // double형의 객체로 바꾸는 메소드

            resultNumber = calculator(resultNumber, inputNumber, operator);

            activityMainBinding.resultTextView.setText(String.valueOf(resultNumber)); // resultNumber는 double형이니 변환해주고 사용
            isFirstInput = true; // result에 반영 후 새로 input을 받아야 하니 true를 반환하도록
            operator = view.getTag().toString(); // operator에 각 tag에 등록한 값을 넣기
            activityMainBinding.mathTextView.append(inputNumber + " " + operator + " ");
        }
    }

    public void equalsButtonClick (View view) {
        if(isFirstInput) { // =이 연속적으로 눌리는 것을 방지
            if(isOperatorClick) {
                activityMainBinding.mathTextView.setText(resultNumber + " " + lastoperator + " " + inputNumber + " =");
                resultNumber = calculator(resultNumber, inputNumber, lastoperator);
                activityMainBinding.resultTextView.setText(String.valueOf(resultNumber));
            }
        } else {
            inputNumber = Double.parseDouble(activityMainBinding.resultTextView.getText().toString()); // double형의 객체로 바꾸는 메소드

            resultNumber = calculator(resultNumber, inputNumber, operator);
            activityMainBinding.resultTextView.setText(String.valueOf(resultNumber)); // resultNumber는 double형이니 변환해주고 사용
            isFirstInput = true; // result에 반영 후 새로 input을 받아야 하니 true를 반환하도록
            operator = view.getTag().toString(); // operator에 각 tag에 등록한 값을 넣기
            activityMainBinding.mathTextView.append(inputNumber + " " + operator + " ");
        }
    }
// 사칙연산 기능 구현
    private double calculator(double resultNumber, double inputNumber, String operator) {
        switch (operator) {
            case "=" : // = 추가로 초기값도 =이 되어야 함
                resultNumber = inputNumber;
                break;
            case "+" :
                resultNumber = resultNumber + inputNumber;
                break;
            case "-" :
                resultNumber = resultNumber - inputNumber;
                break;
            case "X" :
                resultNumber = resultNumber * inputNumber;
                break;
            case "/" :
                resultNumber = resultNumber / inputNumber;
                break;
        }

        return resultNumber;
    }


// AC 버튼
// 선언했던 변수들 모두 초기화하기
    public void allClearButtonClick(View view) {
        activityMainBinding.resultTextView.setText("0"); // 초기화
        activityMainBinding.mathTextView.setText("");
        resultNumber = 0;
        operator = "=";
        isFirstInput = true; // 처음부터 다시 입력할 수 있도록
        isOperatorClick = false;
    }

    // 소수점이 append 될 수 있도록
    // . 이 여러번 찍히지 않도록
    // 0 뒤에도 소수점이 찍힐 수 있도록

    public void pointButtonClick (View view) {
        if(isFirstInput) {
            activityMainBinding.resultTextView.setText("0" + view.getTag().toString());
            isFirstInput = false;
        }else {
            if(activityMainBinding.resultTextView.getText().toString().contains(".")) { // contains = 문자열이 이미 있는지 확인하는 메소드
                Toast.makeText(this, "연속 소수점은 불가능 합니다.", Toast.LENGTH_SHORT).show();
            } else {
                activityMainBinding.resultTextView.append(view.getTag().toString());
            }
        }
    }
}