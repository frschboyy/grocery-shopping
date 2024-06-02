package com.example.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class listView extends AppCompatActivity {
    private Button cart, recycle, buff;
    private ListView list;
    private ArrayList<String> receiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        list = findViewById(R.id.receipt_data);
        receiveData = getIntent().getStringArrayListExtra("cart");

        if (receiveData != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, receiveData);
            list.setAdapter(adapter);
        }

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
                Intent intent = new Intent(listView.this, recycleView.class);
                startActivity(intent);
            }
        });

        buff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listView.this, buffStream.class);
                startActivity(intent);
            }
        });
    }
}