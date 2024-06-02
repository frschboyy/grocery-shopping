package com.example.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText breadUP, penUP, watchUP, milkUP, breadQ, penQ, watchQ, milkQ;
    private EditText breadGT, penGT, watchGT, milkGT, total, disc, net;
    private Button list, recycle, buffer, buy;
    private double bread = 0, pen = 0, watch = 0, milk = 0;
    private ArrayList<String> receiptData = new ArrayList<>();
    private HashMap<String,String> myData = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        // link variables to corresponding activity element by ID
        {
            breadUP = findViewById(R.id.breadPrice);
            penUP = findViewById(R.id.penPrice);
            watchUP = findViewById(R.id.watchPrice);
            milkUP = findViewById(R.id.milkPrice);
            breadQ = findViewById(R.id.breadNum);
            penQ = findViewById(R.id.penNum);
            watchQ = findViewById(R.id.watchNum);
            milkQ = findViewById(R.id.milkNum);
            breadGT = findViewById(R.id.breadTotal);
            penGT = findViewById(R.id.penTotal);
            watchGT = findViewById(R.id.watchTotal);
            milkGT = findViewById(R.id.milkTotal);
            total = findViewById(R.id.total);
            disc = findViewById(R.id.discount);
            net = findViewById(R.id.netPay);
            list = findViewById(R.id.toListView);
            recycle = findViewById(R.id.toRecycleView);
            buffer = findViewById(R.id.toBufferStream);
            buy = findViewById(R.id.purchase);
        }

        // setup onFocusChangeListener on elements

        breadUP.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus)
                    handleFocusLoss(breadUP, breadQ, breadGT, true);
            }
        });

        penUP.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus)
                    handleFocusLoss(penUP, penQ, penGT, true);
            }
        });

        watchUP.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus)
                    handleFocusLoss(watchUP, watchQ, watchGT, true);
            }
        });

        milkUP.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus)
                    handleFocusLoss(milkUP, milkQ, milkGT, true);
            }
        });

        breadQ.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus)
                    handleFocusLoss(breadUP, breadQ, breadGT, false);
            }
        });

        penQ.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus)
                    handleFocusLoss(penUP, penQ, penGT, false);
            }
        });

        watchQ.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus)
                    handleFocusLoss(watchUP, watchQ, watchGT, false);
            }
        });

        milkQ.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus)
                    handleFocusLoss(milkUP, milkQ, milkGT, false);
            }
        });

        // setup onClickListeners for buttons

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data;
                if(bread != 0){
                    data = breadGT.getText().toString();
                    myData.put("Bread",breadGT.getText().toString());
                    receiptData.add(data);
                }
                if(pen != 0){
                    data = penGT.getText().toString();
                    receiptData.add(data);
                    myData.put("Pen",penGT.getText().toString());

                }
                if(watch != 0){
                    data = watchGT.getText().toString();
                    receiptData.add(data);
                    myData.put("Watch",watchGT.getText().toString());
                }
                if(milk != 0){
                    data = milkGT.getText().toString();
                    receiptData.add(data);
                    myData.put("Milk",milkGT.getText().toString());
                }
                Intent intent = new Intent(MainActivity.this, listView.class);
//                intent.putStringArrayListExtra("cart", receiptData);
                intent.putExtra("cart",myData);
                startActivity(intent);
            }
        });

        recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, recycleView.class);
                startActivity(intent);
            }
        });

        buffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, buffStream.class);
                startActivity(intent);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareReceipt();
                if(receiptData.isEmpty()) {
                    Toast.makeText(MainActivity.this, "CART IS EMPTY!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "ITEMS PURCHASED!", Toast.LENGTH_SHORT).show();
                clearCart();
                Intent intent = new Intent(MainActivity.this, listView.class);
                intent.putStringArrayListExtra("cart", receiptData);
                startActivity(intent);
            }
        });
    }

    private void handleFocusLoss(EditText priceTxt, EditText quantityTxt, EditText totalTxt, boolean isPrice){
        String priceText = priceTxt.getText().toString();
        String items = quantityTxt.getText().toString();
        if(isPrice && !realNum(priceText) && !priceText.isEmpty()){
            priceTxt.setText("");
            totalTxt.setText("");
            resetItemTotal(totalTxt);
            agg();
            Toast.makeText(MainActivity.this, "Invalid Price!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isPrice && !realNum(items) && !items.isEmpty()){
            quantityTxt.setText("");
            totalTxt.setText("");
            resetItemTotal(totalTxt);
            agg();
            Toast.makeText(MainActivity.this, "Invalid Quantity!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(priceText.isEmpty() || items.isEmpty()){
            totalTxt.setText("");
            resetItemTotal(totalTxt);
        } else {
            double total = getSum(priceText, items, totalTxt);
            setItemTotal(totalTxt, total);
        }
        agg();
    }

    private void setItemTotal(EditText itemTotal, double total) {
        if(itemTotal.getText() == breadGT.getText())
            bread = total;
        else if(itemTotal.getText() == penGT.getText())
            pen = total;
        else if(itemTotal.getText() == watchGT.getText())
            watch = total;
        else
            milk = total;
    }

    private void resetItemTotal(EditText itemTotal) {
        if(itemTotal.getText() == breadGT.getText())
            bread = 0;
        else if(itemTotal.getText() == penGT.getText())
            pen = 0;
        else if(itemTotal.getText() == watchGT.getText())
            watch = 0;
        else
            milk = 0;
    }

    private void prepareReceipt(){
        receiptData.clear();
        addToReceipt(breadGT);
        addToReceipt(penGT);
        addToReceipt(watchGT);
        addToReceipt(milkGT);
    }

    private void addToReceipt(EditText grandTotal) {
        String data = grandTotal.getText().toString();
        if (!data.isEmpty())
            receiptData.add(data);
    }

    private static double getSum(String itemPrice, String itemAmount, EditText price){
        double cost = Double.parseDouble(itemPrice);
        double amount = Double.parseDouble(itemAmount);
        double total = cost*amount;
        price.setText(String.valueOf(total));
        return total;
    }

    private void agg(){
        double sum = bread + pen + watch + milk;
        double discount =sum*0.15;
        double netPrice = sum-discount;

        total.setText(String.valueOf(sum));
        disc.setText(String.valueOf(discount));
        net.setText(String.valueOf(netPrice));
    }

    private static boolean realNum(String text){
        try {
            return !(Double.parseDouble(text) <= 0);
        } catch (NumberFormatException ex){
            return false;
        }
    }

    private void clearCart(){
        breadUP.setText("");
        breadQ.setText("");
        breadGT.setText("");
        watchUP.setText("");
        watchQ.setText("");
        watchGT.setText("");
        penUP.setText("");
        penQ.setText("");
        penGT.setText("");
        milkUP.setText("");
        milkQ.setText("");
        milkGT.setText("");
        bread = milk = pen = watch = 0;
        agg();
    }

    public String readCart(ArrayList<String> receiptData){
       return "";
    }
}