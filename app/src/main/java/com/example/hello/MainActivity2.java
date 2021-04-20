package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
   private static final String TAG="score1" ;
   int score1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
    private void show(){
        Log.i(TAG,"show:score1="+score1);
        TextView show=findViewById(R.id.score1);
        show.setText(String.valueOf(score1));
    }
    public void btn3(View v){
        score1+=3;
        show();
    }
    public void btn2(View v){
        score1+=2;
        show();
    }
    public void btn1(View v){
        score1+=1;
        show();
    }
    public void btn0(View v){
        score1=0;
        show();
    }
}