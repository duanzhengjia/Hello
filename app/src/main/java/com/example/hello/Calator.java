package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class Calator extends AppCompatActivity  {
    TextView titlepiece,resultpiece;
    EditText inputpiece;
    float rate;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calator);
        Intent intent=getIntent();
        title= intent.getStringExtra("title");
        titlepiece=findViewById(R.id.self);
        titlepiece.setText(title);
    }
    public void jisuan(View piece){
        Intent intent=getIntent();
        String rate1=intent.getStringExtra("detail");
        rate=Float.parseFloat(String.valueOf(rate1));
        inputpiece=findViewById(R.id.chuandi);
        float input =Float.parseFloat(inputpiece.getText().toString());
        float result=input*rate;
        resultpiece=findViewById(R.id.button3);
        resultpiece.setText(String.valueOf(result));
    }
}
