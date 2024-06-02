package com.example.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class recycleView extends AppCompatActivity {
    private Button list, cart, buff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        list = findViewById(R.id.toListView);
        cart = findViewById(R.id.toCart);
        buff = findViewById(R.id.toBufferStream);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(recycleView.this, MainActivity.class);
                startActivity(intent);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(recycleView.this, listView.class);
                startActivity(intent);
            }
        });

        buff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(recycleView.this, buffStream.class);
                startActivity(intent);
            }
        });
    }
}