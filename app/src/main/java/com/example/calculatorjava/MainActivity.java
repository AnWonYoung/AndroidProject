package com.example.calculatorjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.calculatorjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
//  첫 번재 숫자를 입력하는지, 두 번재 숫자를 입력하는지 체크하기
    boolean isFirstInput = true;
//  view 바인딩 선언하기
//  ActivityMainBinding이란 <layout> 태그로 선언된 XML을 위해 자동으로 만들어지는
//  결국에는 activity_main + Binding << 따라서 xml파일명이 변동되면 변하게 됨
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(activityMainBinding.getRoot());
        // getRoot() 레이아웃 뷰를 반환
    }
}