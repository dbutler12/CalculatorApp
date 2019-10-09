package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    StateHandler handler = new StateHandler();
    TextView Calculations;
    private Button B1;
    private Button B2;
    private Button B3;
    private Button Bplus;
    private Button B4;
    private Button B5;
    private Button B6;
    private Button Bminus;
    private Button B7;
    private Button B8;
    private Button B9;
    private Button Btimes;
    private Button B0;
    private Button Bdec;
    private Button Bdiv;
    private Button Bequal;
    private Button Bneg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calculations = new TextView(this);
        Calculations = findViewById(R.id.Calculations);

        B1 = findViewById(R.id.B1);
        B1.setOnClickListener(this);
        B2 = findViewById(R.id.B2);
        B2.setOnClickListener(this);
        B3 = findViewById(R.id.B3);
        B3.setOnClickListener(this);
        Bplus = findViewById(R.id.Bplus);
        Bplus.setOnClickListener(this);
        B4 = findViewById(R.id.B4);
        B4.setOnClickListener(this);
        B5 = findViewById(R.id.B5);
        B5.setOnClickListener(this);
        B6 = findViewById(R.id.B6);
        B6.setOnClickListener(this);
        Bminus = findViewById(R.id.Bminus);
        Bminus.setOnClickListener(this);
        B7 = findViewById(R.id.B7);
        B7.setOnClickListener(this);
        B8 = findViewById(R.id.B8);
        B8.setOnClickListener(this);
        B9 = findViewById(R.id.B9);
        B9.setOnClickListener(this);
        Btimes = findViewById(R.id.Btimes);
        Btimes.setOnClickListener(this);
        B0 = findViewById(R.id.B0);
        B0.setOnClickListener(this);
        Bdec = findViewById(R.id.Bdec);
        Bdec.setOnClickListener(this);
        Bdiv = findViewById(R.id.Bdiv);
        Bdiv.setOnClickListener(this);
        Bequal = findViewById(R.id.Bequal);
        Bequal.setOnClickListener(this);
        Bneg = findViewById(R.id.Bneg);
        Bneg.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.B1:
                handler.setBut('1');
                break;
            case R.id.B2:
                handler.setBut('2');
                break;
            case R.id.B3:
                handler.setBut('3');
                break;
            case R.id.Bplus:
                handler.setBut('+');
                break;
            case R.id.B4:
                handler.setBut('4');
                break;
            case R.id.B5:
                handler.setBut('5');
                break;
            case R.id.B6:
                handler.setBut('6');
                break;
            case R.id.Bminus:
                handler.setBut('-');
                break;
            case R.id.B7:
                handler.setBut('7');
                break;
            case R.id.B8:
                handler.setBut('8');
                break;
            case R.id.B9:
                handler.setBut('9');
                break;
            case R.id.Btimes:
                handler.setBut('x');
                break;
            case R.id.B0:
                handler.setBut('0');
                break;
            case R.id.Bdiv:
                handler.setBut('d');
                break;
            case R.id.Bdec:
                handler.setBut('.');
                break;
            case R.id.Bequal:
                handler.setBut('=');
                break;
            case R.id.Bneg:
                handler.setBut('n');
                break;

        }

        handler.handler();
        updateDisplay();
    }

    public void updateDisplay(){
        Calculations.setText(handler.getDisplay());
        System.out.println(handler.getDisplay());
    }
}


