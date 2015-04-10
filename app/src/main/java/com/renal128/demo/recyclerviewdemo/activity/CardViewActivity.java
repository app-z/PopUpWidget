package com.renal128.demo.recyclerviewdemo.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;

import com.renal128.demo.recyclerviewdemo.R;
import com.renal128.demo.recyclerviewdemo.model.Record;

import java.util.ArrayList;
import java.util.List;


public class CardViewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);

        List<Record> records = new ArrayList<Record>();
        populateRecords(records);

        CardView cardView = (CardView)findViewById(R.id.cardView);



        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


    }

    private void populateRecords(List<Record> records){
        for (int i = 0; i<50; i++){
            Record record = new Record();
            record.setName("Item №" + i);
            record.setType(Record.Type.values()[i%3]);
            records.add(record);
        }
    }

}
