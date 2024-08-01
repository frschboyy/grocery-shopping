package com.example.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class buffStream extends AppCompatActivity {
    private Button list, cart;
    private customAdapter custom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buff_stream);

        list = findViewById(R.id.toListView);
        cart = findViewById(R.id.toCart);

        Intent intent = getIntent();
        HashMap<String, String> data = (HashMap<String, String>) intent.getSerializableExtra("cart");

        StringBuffer myList = new StringBuffer();

        assert data != null;
        for(Map.Entry<String, String> entry : data.entrySet()){
            if(!entry.getValue().isEmpty()){
                myList.append(entry.getKey()).append(" - $").append(entry.getValue()).append('\n');
            }
        }

        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setTitle("RECEIPT !");
        build.setMessage(myList.toString());
        build.show();

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buffStream.this, listView.class);
                intent.putExtra("receipt", new HashMap<>(data));
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