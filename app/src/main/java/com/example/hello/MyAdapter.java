package com.example.hello;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter  extends ArrayAdapter {
    private static  final String TAG="MyAdapter";
    public  MyAdapter(@NonNull Context context, int  resource, @NonNull ArrayList<HashMap<String, String>>list){
        super(context,resource,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View itemView=convertView;
        if(itemView==null){
            itemView=LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Map<String,String> map =(Map<String, String>)getItem(position);
        TextView title =(TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail =(TextView) itemView.findViewById(R.id.itemDetail);


        title.setText(map.get("ItemTitle"));
        detail.setText(map.get("ItemDetail"));

        return itemView;
    }



}
