
package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static android.content.ContentValues.TAG;
public class RateListActivity extends ListActivity implements Runnable {
    String data[]={"one","two","three"};
    //自己添加的内容
    Handler handler;
    private int what;
    //private  static final String TAG ="RateListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_rate_list);
        ListView listView=findViewById(R.id.mylist);
        List<String> list1=new ArrayList<String>();
        for (int i=1;i<100;i++){
            list1.add("item"+i);
        }
        ListAdapter adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list1);
        setListAdapter(adapter);

        Thread t=new Thread(this);
        t.start();


        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                //获取网络数据，带回主线程中
                if (msg.what==7){
                    List<String>list2=(List<String>)msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }
    @Override
    public void run() {
        //获取网络数据，带回到主线程中
        List<String> retList=new ArrayList<String>();
        Document doc = null;
        try {
            Thread.sleep(3000);
            doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"run:"+doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table2=tables.get(1); //Elements newsHeadlines = doc.select("#mp-itn b a");
            Elements tds=table2.getElementsByTag("td");
            for (int i=0;i< tds.size();i+=8){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);

                String str=td1.text();
                String val=td2.text();

                Log.i(TAG,"run:"+str+"==>"+val);
                retList.add(str+"==>"+val);
            }
            /*Element doll=doc.select("body > section > div > div > article > table > tbody > tr:nth-child(27) > td:nth-child(6)").first();
            Log.i(TAG,"run:美元="+doll.text());
            float dollarRate = Float.parseFloat(doll.text());
            Element eu=doc.select("body > section > div > div > article > table > tbody > tr:nth-child(9) > td:nth-child(6)").first();
            Log.i(TAG,"run:欧元="+eu.text());
            float euroRate = Float.parseFloat(eu.text());
            Element wo=doc.select("body > section > div > div > article > table > tbody > tr:nth-child(14) > td:nth-child(6)").first();
            Log.i(TAG,"run:韩元="+wo.text());
            float wonRate = Float.parseFloat(wo.text());*/
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        Message msg= handler.obtainMessage(7);
        msg.obj= retList;
        handler.sendMessage(msg);
    }
}