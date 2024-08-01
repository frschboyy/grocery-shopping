package com.example.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.*;


public class listView extends AppCompatActivity {
    private Button cart, recycle, buff;
    private ListView list;
    private HashMap<String,String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        list = findViewById(R.id.receipt_data);
        Intent intent = getIntent();
        data = (HashMap<String, String>) intent.getSerializableExtra("cart");

        ArrayList<String> myList = new ArrayList<>();
        if (data != null){
            for(Map.Entry<String,String> entry : data.entrySet()){
                if(!entry.getValue().isEmpty()){
                    if(entry.getValue().equals("RECEIPT!"))
                        myList.add(entry.getKey() + entry.getValue());
                    else
                        myList.add(entry.getKey() + " - $" + entry.getValue());
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        list.setAdapter(adapter);


        cart = findViewById(R.id.toCart);
        recycle = findViewById(R.id.toRecycleView);
        buff = findViewById(R.id.toBufferStream);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listView.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listView.this, recView.class);
                HashMap<String,String> data2 = data;
                data2.remove(" ");
                intent.putExtra("cart",new HashMap<>(data2));
                startActivity(intent);
            }
        });

        buff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listView.this, buffStream.class);
                intent.putExtra("cart", new HashMap<>(data));
                startActivity(intent);
            }
        });
    }
}