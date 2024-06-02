package com.example.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class buffStream extends AppCompatActivity {
    private Button list, recycle, cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buff_stream);

        list = findViewById(R.id.toListView);
        recycle = findViewById(R.id.toRecycleView);
        cart = findViewById(R.id.toCart);

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buffStream.this, listView.class);
                startActivity(intent);
            }
        });

        recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buffStream.this, recycleView.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buffStream.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}