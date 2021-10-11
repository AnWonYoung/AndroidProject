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
//  입력된 숫자를 연산하는 변수, resultview에 출력할 수 있도록 하는 변수
    double resultNumber = 0;
    String operator = "+";

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
            activityMainBinding.resultTextView.setText(getButtonText); //
            isFirstInput = false; // false처리를 해주지 않으면 계속 true를 반환하여 1이 append되지 않음
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

    public void operatorClick (View view) {
        double inputNumber = Double.parseDouble(activityMainBinding.resultTextView.getText().toString()); // double형의 객체로 바꾸는 메소드

        if(operator.equals("+")) {
            resultNumber = resultNumber + inputNumber;
        }else if(operator.equals("-")) {
            resultNumber = resultNumber - inputNumber;
        }else if(operator.equals("X")) {
            resultNumber = resultNumber * inputNumber;
        }else if(operator.equals("/")) {
            resultNumber = resultNumber / inputNumber;
        }

        activityMainBinding.resultTextView.setText(String.valueOf(resultNumber)); // resultNumber는 double형이니 변환해주고 사용
        isFirstInput = true; // result에 반영 후 새로 input을 받아야 하니 true를 반환하도록
        operator = view.getTag().toString(); // operator에 각 tag에 등록한 값을 넣기
        activityMainBinding.mathTextView.append(inputNumber + " " + operator + " ");
    }
// AC 버튼
    public void allClearButtonClick(View view) {
        activityMainBinding.resultTextView.setText("0"); // 초기화
        activityMainBinding.mathTextView.setText("");
        resultNumber = 0;
        operator = "+";
        isFirstInput = true; // 처음부터 다시 입력할 수 있도록
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

    // 소수점 포인트 처리 메소드
    // 1. 기본적으로 소수점이 append될 수 있도록 처리 (여기서 0 뒤에도 소수점이 찍힐 수 있도록 0 + 태그 메소드를 입력하기)
    // 2. 실행 후 예외 발생 처리하기
    // 2-1. 연속적으로 소수점 입력이 불가능 하도록
    // 2-1-1. 현재 소수점을 가지고 있는지 체크 후, 가지고 있다면 Toast를 띄우고 || 가지고 있지 않다면 append를 띄우도록 하기

    // 필요한 메소드를 먼저 생성한 다음, xml파일로 이동하여 onclick 잊지 말고 먼저 걸어주기
    // 값이 필요할 때는

    public void pointButtonclick2 (View view) {
        if(isFirstInput) {
            activityMainBinding.resultTextView.setText("0" + view.getTag().toString());
            isFirstInput = false;
        }else  {
            if(activityMainBinding.resultTextView.getText().toString().contains(".")) { // contains = 해당 문자열(.)을 이미 가지고 있는지
                Toast.makeText(this , "연속 소수점은 입력이 불가능합니다.", Toast.LENGTH_SHORT).show();
            }else  {
                activityMainBinding.resultTextView.append(view.getTag().toString()); // .이 없을 시에는 append로 소수점을 추가해줄 수 있도록 처리
            }
        }
    }
}