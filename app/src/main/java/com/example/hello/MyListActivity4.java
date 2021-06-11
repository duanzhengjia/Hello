package com.example.hello;

import android.content.ClipData;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MyListActivity4 extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final  String TAG = "MyListActivity4";
    ListView listView;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list4);
        listView = findViewById(R.id.mylist);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        listView.setOnItemClickListener(this);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==9){
                    ArrayList<HashMap<String,String>> listItems =(ArrayList<HashMap<String, String>>)msg.obj;
                   // adapter =new RateAdapter(MyListActivity4.this,R.layout.list_item,(ArrayList<RateItem>)msg.obj);//方法的不同
                    adapter =new MyAdapter(MyListActivity4.this,R.layout.list_item,(ArrayList<HashMap<String, String>>)msg.obj);
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
        Log.i(TAG,"onItemClick:detail2="+detail2);//去掉前缀
//新添加
          adapter.remove(fileList());
//打开下一界面
        Intent CalatorIntent=new Intent(this,Calator.class);
        CalatorIntent.putExtra("title",titleStr);
        CalatorIntent.putExtra("rate",detailStr);
        startActivity(CalatorIntent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,"onItemLongClick: 长按事件 position="+position);
        MyListActivity4 item= (MyListActivity4) listView.getItemAtPosition(position);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("请确认是否删除当前数据")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG,"onClick: 对话框事件处理");
                        //删除数据项
                        adapter.remove(fileList());
                    }
                }).setNegativeButton("否",null);
                builder.create().show();

                return true;
    }
}