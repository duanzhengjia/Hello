package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ConfigActivity4 extends AppCompatActivity {
    private static final String TAG="ConfigActivity4";
    TextView dollarlook,eurolook,wonlook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config4);

        Intent intent=getIntent();
        float dollar1=intent.getFloatExtra("dollar",0.0f);
        float euro1=intent.getFloatExtra("euro",0.0f);
        float won1=intent.getFloatExtra("won",0.0f);

        Log.i(TAG,"onCreate:dollar1="+dollar1);
        Log.i(TAG,"onCreate:euro1="+euro1);
        Log.i(TAG,"onCreate:won1="+won1);
//获取控件
        dollarlook=findViewById(R.id.edit_dollar);
        //获取的汇率放入控件中
        dollarlook.setHint(String.valueOf(dollar1));
        eurolook=findViewById(R.id.edit_euro);
        eurolook.setHint(String.valueOf(euro1));
        wonlook=findViewById(R.id.edit_won);
        wonlook.setHint(String.valueOf(won1));
    }
    public void save(View btn){
        //修改保存
        float newdollar=Float.parseFloat(dollarlook.getText().toString());
        float neweuro=Float.parseFloat(eurolook.getText().toString());
        float newwon=Float.parseFloat(wonlook.getText().toString());

        Log.i(TAG,"save:newdollar="+newdollar);
        Log.i(TAG,"save:neweuro="+neweuro);
        Log.i(TAG,"save:newwonr="+newwon);

        Intent ret=getIntent();
        Bundle bdl=new Bundle();
        bdl.putFloat("new_dollar",newdollar);
        bdl.putFloat("new_euro",neweuro);
        bdl.putFloat("new_won",newwon);
        ret.putExtras(bdl);
        setResult(6,ret);
        finish();
    }

}