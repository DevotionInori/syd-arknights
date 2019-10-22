package com.syd.arknights;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChouKaFragment extends Fragment {

    private TextView counts;
    private TextView rarity;
    private Button clear;
    private Button record;
    private RecordDataBaseHelper helper;
    private SQLiteDatabase db;
    private List<employee> employees = new ArrayList<employee>();
    private TextView last;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;//mContext 是成员变量，上下文引用
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_main,container,false);
        super.onCreateView(inflater,container,savedInstanceState);
        init(view);

        Button button1 = view.findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext,ChouKaActivity.class);
                in.putExtra("Number",1);
                startActivity(in);
            }
        });
        Button button_ten = view.findViewById(R.id.button_ten);
        button_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext,ChouKaActivity.class);
                in.putExtra("Number",10);
                startActivity(in);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.clear(mContext);
                db.delete("Record",null,null);
                onResume();
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, listEmployee.class);
                in.putExtra("ANS",(Serializable) employees);
                startActivity(in);
            }
        });


        return  view;
    }

    private void init(View view){

        helper = new RecordDataBaseHelper(mContext,"Record",null,1);
        db=helper.getWritableDatabase();
        clear = view.findViewById(R.id.clear);
        counts= view.findViewById(R.id.countsText);
        rarity= view.findViewById(R.id.SixRarityText);
        record=view.findViewById(R.id.viewRecord);
        last=view.findViewById(R.id.lastSix);
        if(!SPUtils.contains(mContext,"Counts"))
            SPUtils.put(mContext,"Counts",0);
        counts.setText(SPUtils.get(mContext,"Counts",0).toString());
        if((int)SPUtils.get(mContext,"Counts",0)-(int)SPUtils.get(mContext,"LastSix",0)<50)
            rarity.setText("2%");
        else{
            int r = ((int)SPUtils.get(mContext,"Counts",0)-(int)SPUtils.get(mContext,"LastSix",0)+1-50)*2+2;
            rarity.setText(""+r+"%");
        }
        Cursor c = db.query("Record",new String[]{"name","rarity","location"},
                null,null,null,null,null);
        last.setText("距离上一次六星："+((int)SPUtils.get(mContext,"Counts",0)-(int)SPUtils.get(mContext,"LastSix",0)));
        c.moveToFirst();
        while(!c.isAfterLast()){
            employees.add(new employee(c.getString(c.getColumnIndex("name")),c.getInt(c.getColumnIndex("rarity")),c.getString(c.getColumnIndex("location"))));
            c.moveToNext();
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        if(!SPUtils.contains(mContext,"Counts"))
            SPUtils.put(mContext,"Counts",0);
        counts.setText(SPUtils.get(mContext,"Counts",0).toString());
        if((int)SPUtils.get(mContext,"Counts",0)-(int)SPUtils.get(mContext,"LastSix",0)<50)
            rarity.setText("2%");
        else{
            int r = ((int)SPUtils.get(mContext,"Counts",0)-(int)SPUtils.get(mContext,"LastSix",0)+1-50)*2+2;
            rarity.setText(""+r+"%");
        }
        Cursor c = db.query("Record",new String[]{"name","rarity","location"},
                null,null,null,null,null);
        last.setText("距离上一次六星："+((int)SPUtils.get(mContext,"Counts",0)-(int)SPUtils.get(mContext,"LastSix",0)));
        c.moveToFirst();
        employees.clear();
        while(!c.isAfterLast()){
            employees.add(new employee(c.getString(c.getColumnIndex("name")),c.getInt(c.getColumnIndex("rarity")),c.getString(c.getColumnIndex("location"))));
            c.moveToNext();
        }
    }

}


