package com.example.hello;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class FirstlistActivity extends ListActivity implements Runnable {
    Handler handler;
    private static final String TAG = "F";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Thread t=new Thread(this);
        t.start();

        super.onCreate(savedInstanceState);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 9) {
                    Log.i(TAG,"收到");
                    ArrayList<String> list2 = (ArrayList<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(FirstlistActivity.this, android.R.layout.simple_list_item_1, list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
  }

    @Override
    public void run() {
        Log.i(TAG,"run:....");
        List<String> ret =new ArrayList<String>();
        try {
            Document doc= Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"title: "+doc.title());
            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");
            for(Element tr : trs){
                Elements tds = tr.getElementsByTag("td");
                if(tds.size()>0){
                    String str = tds.get(0).text();
                    String val = tds.get(5).text();
                    Log.i(TAG,"run: td1= "+tds.get(0).text() +"=>"+tds.get(5).text());
                    ret.add(str + "=>"+val);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Message msg=handler.obtainMessage(9,ret);
        handler.sendMessage(msg); Log.i(TAG,"run:__");
        MyTask task=new MyTask();
        task.setHandler(handler);
    }
}