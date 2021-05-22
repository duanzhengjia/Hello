package com.example.hello;

import android.content.ClipData;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTask implements Runnable {
    private  static final String TAG ="MyTask";
    Handler handler;

    public void setHandler(Handler handler){
        this.handler=handler;
    }

    @Override
    public void run() {
        Log.i(TAG,"run:....");
        //List<String> ret =new ArrayList<String>();
        List<HashMap<String,String>> ret= new ArrayList<HashMap<String, String>>();
        try {
            Thread.sleep(3000);
            Document doc= Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"title: "+doc.title());
            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");
            for(Element tr : trs){
                Elements tds = tr.getElementsByTag("td");
                if(tds.size()>0){
                    String str = tds.get(0).text();
                    String val = tds.get(5).text();
                    HashMap map=new HashMap<String,String>();
                    Log.i(TAG,"run: td1="+ str+"=>"+val);
                    //Log.i(TAG,"run: td1= "+tds.get(0).text() +"=>"+tds.get(5).text());
                    //ret.add(str + "=>"+val);
                    map.put("ItemTitle",str);
                    map.put("ItemDetail",val);
                    ret.add(map);
                }
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        Message msg=handler.obtainMessage(9,ret);
        handler.sendMessage(msg);

    }
}