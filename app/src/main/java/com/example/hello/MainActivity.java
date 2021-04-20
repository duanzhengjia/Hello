package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText money;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        money=findViewById(R.id.inputmoney);
        result=findViewById(R.id.result);
    }
    public void click(View btn){
        float r=0.0f;
        if(btn.getId()==R.id.dollar){
            r=0.1f;
        }
        else if(btn.getId()==R.id.pound){
            r=0.2f;
        }
        else {
            r=150.3f;
        }
        String str=money.getText().toString();
        if(str!=null&&str.length()>0){
            float re=Float.parseFloat(str)*r;
            result.setText(String.valueOf(re));
        }
        else{
            Toast.makeText(this,"请输入人民币金额",Toast.LENGTH_SHORT).show();
        }
    }

}