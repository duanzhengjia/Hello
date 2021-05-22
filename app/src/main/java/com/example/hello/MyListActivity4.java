package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MyListActivity4 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final  String TAG = "MyListActivity4";
    ListView listView;
    //    private Handler handler1;
//    private Handler handler2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list4);
        listView = findViewById(R.id.mylist);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

//        ArrayList<HashMap<String,String>> listItems = new ArrayList<HashMap<String, String>>();
//        for (int i =0; i<10;i++){
//            HashMap<String,String> map = new HashMap<String,String>();
//            map.put("ItemTitle","Rate:"+i);
//            map.put("ItemDetail","detail:"+i);
//            listItems.add(map);
//        }
//
//        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItems,
//                R.layout.list_item,
//                new String[]{"ItemTitle","ItemDetail"},
//                new int[]{R.id.itemTitle,R.id.itemDetail});
//        listView.setAdapter(listItemAdapter);
//        listView.setVisibility(View.VISIBLE);

        listView.setOnItemClickListener(this);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==9){
                    ArrayList<HashMap<String,String>> listItems =(ArrayList<HashMap<String, String>>)msg.obj;
//                    SimpleAdapter adapter = new SimpleAdapter(MyList3Activity.this,listItems,
//                            R.layout.list_item,
//                            new String[]{"ItemTitle","ItemDetail"},
//                            new int[]{R.id.itemTitle,R.id.itemDetail});

                    MyAdapter adapter =new MyAdapter(MyListActivity4.this,R.layout.list_item,listItems);
                    listView.setAdapter(adapter);

                    listView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                }
                super.handleMessage(msg);
            }
        };

        MyTask task = new MyTask();
        task.setHandler(handler);

        Thread t = new Thread(task);
        t.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Object itemAtposition = listView.getItemAtPosition(position);
        HashMap<String,String> map = (HashMap<String, String>)itemAtposition;
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG,"oncreate: titleStr:"+titleStr);
        Log.i(TAG,"oncreate: detailStr:"+detailStr);

        TextView title = (TextView)view.findViewById(R.id.itemTitle);
        TextView detail = (TextView)view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG,"onItemClick:title2="+title2);
        Log.i(TAG,"onItemClick:detail2="+detail2);

//        Message message1 = handler1.obtainMessage(1);
//        message1.obj = title2;
//        handler1.sendMessage(message1);
//        Message message2 = handler2.obtainMessage(2);
//        message2.obj = detail2;
//        handler2.sendMessage(message2);

//        Intent intent=new Intent(MyList3Activity.this,RatePiece.class);
//        intent.setClass(MyList3Activity.this, RatePiece.class);
//        intent.putExtra("title", title2);
//        intent.putExtra("detail", detail2);
//        MyList3Activity.this.startActivity(intent);



    }
}