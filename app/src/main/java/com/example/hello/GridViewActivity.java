package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView gridview=findViewById(R.id.gridview);
        List<String> list1 =new ArrayList<String>();
        for (int i=1;i<100;i++){
            //list1.add("item"+i);
        }
        ListAdapter adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list1);

        gridview.setAdapter(adapter);
        gridview.setEmptyView(findViewById(R.id.nodata));
    }
}