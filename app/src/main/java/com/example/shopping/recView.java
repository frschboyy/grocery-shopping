package com.example.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class recView extends AppCompatActivity {
    private Button list, cart;
    private RecyclerView recycle;
    private customAdapter custom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        recycle = findViewById(R.id.recycle);
        recycle.setLayoutManager(new GridLayoutManager(this, 2));

        Intent intent = getIntent();
        HashMap<String, String> data = (HashMap<String, String>) intent.getSerializableExtra("cart");

        ArrayList<String> myList = new ArrayList<>();

        assert data != null;
        for(Map.Entry<String, String> entry : data.entrySet()){
            if(!entry.getValue().isEmpty()){
                if(entry.getValue().equals("RECEIPT!"))
                    myList.add(entry.getKey() + entry.getValue());
                else
                    myList.add(entry.getKey() + " - $" + entry.getValue());
            }
        }

        custom = new customAdapter(myList);
        recycle.setAdapter(custom);

        list = findViewById(R.id.toListView);
        cart = findViewById(R.id.toCart);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(recView.this, MainActivity.class);
                startActivity(intent);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(recView.this, listView.class);
                intent.putExtra("receipt", new HashMap<>(data));
                startActivity(intent);
            }
        });
    }
}