package com.example.hello;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.number.NumberRangeFormatter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements Runnable{
    private static final String TAG ="MainActivity";
    EditText money;
    TextView result;
    float dollarRate=0.0f;
    float euroRate=0.1266f;
    float wonRate=170.2708f;
    Handler handler;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        money=findViewById(R.id.inputmoney);
        result=findViewById(R.id.result);
        //获取页面数据
        SharedPreferences sharedPreferences = getSharedPreferences("rate", Activity.MODE_PRIVATE);
        dollarRate=sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate=sharedPreferences.getFloat("euro_rate",0.1266f);
        wonRate=sharedPreferences.getFloat("won_rate",170.2708f);
        String updateStr=sharedPreferences.getString("update_str","");
        Log.i(TAG,"onCreate:dollar="+dollarRate);
        Log.i(TAG,"onCreate:updateStr=" + updateStr);

        //获取当前日期
        LocalDate today=LocalDate.now();
        //比较日期
        if (updateStr.equals(today.toString())){
            Log.i(TAG,"onCreate:日期相等，不从网络获取数据");
        }else{
            //开启线程
            Thread t=new Thread(this);
            t.start();
        }
        //创建handler对象
        handler= new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                if(msg.what==7){
                    String val=(String)msg.obj;
                    Log.i(TAG,"handleMessage:val="+val);
                    Toast.makeText(MainActivity.this,"数据已更新",Toast.LENGTH_SHORT).show();
                    //result.setText(val);

                    SharedPreferences sp=getSharedPreferences("rate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putFloat("dollar_rate",dollarRate);
                    editor.putFloat("euro_rate",euroRate);
                    editor.putFloat("won_rate",wonRate);
                    //存入当前日期
                    editor.putString("update_str",today.toString());
                    editor.apply();
                    Log.i(TAG,"handleMessage:日期已更新"+today);
                }
                super.handleMessage(msg);
            }
        };
    }
    public void click(View btn){
        float r=0.0f;
        if(btn.getId()==R.id.dollar){
            r=dollarRate;
        }
        else if(btn.getId()==R.id.euro){
            r=euroRate;
        }
        else {
            r=wonRate;
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
    public void openConfig(View btn){
        Intent config = show();
        //startActivity(config);
        startActivityForResult(config,3);
    }
//菜单内容
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }
//菜单控件内容
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
            Intent config = show();
            //startActivity(config);
            startActivityForResult(config,3);
        }
        return super.onOptionsItemSelected(item);
    //return true;
    }
    private Intent show() {
        Log.i(TAG,"openConfig:");
        Intent config=new Intent(this, ConfigActivity4.class);
        config.putExtra("dollar",dollarRate);
        config.putExtra("euro",euroRate);
        config.putExtra("won",wonRate);

        Log.i(TAG,"openConfig:dollarRate="+dollarRate);
        Log.i(TAG,"openConfig:euroRate="+euroRate);
        Log.i(TAG,"openConfig:wonRate="+wonRate);
        return config;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==3&&resultCode==6){
            Bundle bundle=data.getExtras();
            dollarRate=bundle.getFloat("new_dollar",0.1f);
            euroRate=bundle.getFloat("new_euro",0.1f);
            wonRate=bundle.getFloat("new_won",0.1f);

            SharedPreferences sp=getSharedPreferences("rate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);
            editor.apply();
            
            Log.i(TAG,"onActivityResult:dollarRate="+dollarRate);
            Log.i(TAG,"onActivityResult:euroRate="+euroRate );
            Log.i(TAG,"onActivityResult:wonRate="+wonRate);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //操作jsoup解析
    @Override
    public void run() {
        Log.i(TAG,"run:.....");
        //耗时操作
        URL url=null;
       /* try {
            url=new URL("https://www.usd-cny.com/icbc.htm");
            HttpsURLConnection http=(HttpsURLConnection)url.openConnection();
            InputStream in =http.getInputStream();
            String html= inputStream2String(in);
            Log.i(TAG,"run:html="+html);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        Log.i(TAG,"run: after 3s");
        */

        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"run:"+doc.title());
            Element table = doc.getElementsByTag("table").first();
            Elements trs=table.getElementsByTag("tr"); //Elements newsHeadlines = doc.select("#mp-itn b a");
            for (Element tr : trs) {//log("%s\n\t%s", headline.attr("title"), headline.absUrl("href"));
                Elements tds = tr.getElementsByTag("td");
                if (tds.size() > 0) {
                    String str = tds.get(0).text();//第一个
                    String val = tds.get(5).text();//第六个
                    // String vala = tds.get(4).text();

                    Log.i(TAG, "run:td1=" + tds.get(0).text()+"==>"+tds.get(5).text());//多写了一个等号
                }
            }
            Element doll=doc.select("body > section > div > div > article > table > tbody > tr:nth-child(27) > td:nth-child(6)").first();
            Log.i(TAG,"run:美元="+doll.text());
            dollarRate=Float.parseFloat(doll.text());
            Element eu=doc.select("body > section > div > div > article > table > tbody > tr:nth-child(9) > td:nth-child(6)").first();
            Log.i(TAG,"run:欧元="+eu.text());
            euroRate=Float.parseFloat(eu.text());
            Element wo=doc.select("body > section > div > div > article > table > tbody > tr:nth-child(14) > td:nth-child(6)").first();
            Log.i(TAG,"run:韩元="+wo.text());
            wonRate=Float.parseFloat(wo.text());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回消息到主线程
        Message msg= handler.obtainMessage(7);
        msg.obj="Hello from run()";
        handler.sendMessage(msg);
    }
    private String inputStream2String(InputStream inputStream)
        throws IOException{
        final int bufferSize=1024;
        final char[]buffer=new char[bufferSize];
        final StringBuilder out=new StringBuilder();
        Reader in=new InputStreamReader(inputStream,"gb2312");
        while (true){
            int rsz=in.read(buffer,0,buffer.length);
            if (rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return  out.toString();
    }
    public  void setHandler(Handler handler){
        this.handler=handler;
    }
}
