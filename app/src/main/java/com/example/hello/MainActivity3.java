package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    private static final String TAG="score1" ;
    private static final String TAG2="score2" ;
    int score1=0;
    int score2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
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
    private void showB(){
        Log.i(TAG2,"show:score2="+score2);
        TextView showB=findViewById(R.id.score2);
        showB.setText(String.valueOf(score2));
    }
    public void btn3B(View v){
        score2+=3;
        showB();
    }
    public void btn2B(View v){
        score2+=2;
        showB();
    }
    public void btn1B(View v){
        score2+=1;
        showB();
    }
    public void btn0B(View v){
        score2=0;
        showB();
    }
}

//已经上传，，右击MainAcvitiy3.java，Git，Add；退出点击Git，Repository，push