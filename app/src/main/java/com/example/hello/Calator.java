package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class Calator extends AppCompatActivity  {
    private  static  final String TAG="Calator";
    float rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calator);
        Intent intent=getIntent();
        TextView title=findViewById(R.id.self);
        EditText input=findViewById(R.id.chuandi);
        TextView out=findViewById(R.id.result);

        title.setText(intent.getStringExtra("title"));
        rate = Float.parseFloat(intent.getStringExtra("rate"));
        Log.i(TAG,"onCreate: rate="+rate);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            //获取用户输入数据
                String str=s.toString();
                float r=Float.parseFloat(str)*(100/rate);
                out.setText("result="+r);
            }
        });

    }
    }

