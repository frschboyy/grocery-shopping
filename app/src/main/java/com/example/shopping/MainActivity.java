package com.example.shopping;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText breadUP, penUP, watchUP, milkUP, breadQ, penQ, watchQ, milkQ;
    private EditText breadGT, penGT, watchGT, milkGT, total, disc, net;
    private EditText focus = null;
    private Button buy;
    private double bread = 0, pen = 0, watch = 0, milk = 0;
    private final HashMap<String, String> myData = new HashMap<>();
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

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
            buy = findViewById(R.id.purchase);
        }
        breadUP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    handleFocusLoss(breadUP, breadQ, breadGT, true);
                else
                    focus = breadUP;
            }
        });
        penUP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    handleFocusLoss(penUP, penQ, penGT, true);
                else
                    focus = penUP;
            }
        });

        watchUP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    handleFocusLoss(watchUP, watchQ, watchGT, true);
                else
                    focus = watchUP;
            }
        });

        milkUP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    handleFocusLoss(milkUP, milkQ, milkGT, true);
                else
                    focus = milkUP;
            }
        });

        breadQ.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    handleFocusLoss(breadUP, breadQ, breadGT, false);
                else
                    focus = breadQ;
            }
        });

        penQ.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    handleFocusLoss(penUP, penQ, penGT, false);
                else
                    focus = penQ;
            }
        });

        watchQ.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    handleFocusLoss(watchUP, watchQ, watchGT, false);
                else
                    focus = watchQ;
            }
        });

        milkQ.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    handleFocusLoss(milkUP, milkQ, milkGT, false);
                else
                    focus = milkQ;
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFocus();
                prepareReceipt();
                Intent intent = new Intent(MainActivity.this, listView.class);
                intent.putExtra("cart", myData);
                if (myData.size() != 5) {
                    Toast.makeText(MainActivity.this, "CART IS EMPTY!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "ITEMS PURCHASED!", Toast.LENGTH_SHORT).show();
                sendSMS(intent);
            }
        });
    }

    private void handleFocusLoss(EditText priceTxt, EditText quantityTxt, EditText totalTxt, boolean isPrice) {
        String priceText = priceTxt.getText().toString();
        String items = quantityTxt.getText().toString();
        if (isPrice && !realNum(priceText) && !priceText.isEmpty()) {
            priceTxt.setText("");
            totalTxt.setText("");
            resetItemTotal(totalTxt);
            agg();
            Toast.makeText(MainActivity.this, "Invalid Price!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isPrice && !realNum(items) && !items.isEmpty()) {
            quantityTxt.setText("");
            totalTxt.setText("");
            resetItemTotal(totalTxt);
            agg();
            Toast.makeText(MainActivity.this, "Invalid Quantity!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (priceText.isEmpty() || items.isEmpty()) {
            totalTxt.setText("");
            resetItemTotal(totalTxt);
        } else {
            double total = getSum(priceText, items, totalTxt);
            setItemTotal(totalTxt, total);
        }
        agg();
    }

    private void setItemTotal(EditText itemTotal, double total) {
        if (itemTotal == breadGT) bread = total;
        else if (itemTotal == penGT) pen = total;
        else if (itemTotal == watchGT) watch = total;
        else milk = total;
    }

    private void resetItemTotal(EditText itemTotal) {
        if (itemTotal == breadGT) bread = 0;
        else if (itemTotal == penGT) pen = 0;
        else if (itemTotal == watchGT) watch = 0;
        else milk = 0;
    }

    private void prepareReceipt() {
        if (bread + milk + pen + watch != 0) myData.put(" ", "RECEIPT!");
        myData.put("Bread", breadGT.getText().toString());
        myData.put("Pen", penGT.getText().toString());
        myData.put("Watch", watchGT.getText().toString());
        myData.put("Milk", milkGT.getText().toString());
    }

    private static double getSum(String itemPrice, String itemAmount, EditText price) {
        double cost = Double.parseDouble(itemPrice);
        double amount = Double.parseDouble(itemAmount);
        double total = cost * amount;
        price.setText(String.valueOf(total));
        return total;
    }

    private void removeFocus() {
        if (focus != null) focus.clearFocus();
    }

    private void agg() {
        double sum = bread + pen + watch + milk;
        double discount = sum * 0.15;
        double netPrice = sum - discount;

        total.setText(String.valueOf(sum));
        disc.setText(String.valueOf(discount));
        net.setText(String.valueOf(netPrice));
    }

    private static boolean realNum(String text) {
        try {
            return !(Double.parseDouble(text) <= 0);
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private void sendSMS(Intent intent) {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setMessage("Enter Phone Number:");

        final EditText phoneNo = new EditText(this);
        phoneNo.setInputType(InputType.TYPE_CLASS_PHONE);
        build.setView(phoneNo);

        build.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder myList = new StringBuilder();
                for (Map.Entry<String, String> entry : myData.entrySet()) {
                    if (!entry.getValue().isEmpty()) {
                        myList.append(entry.getKey()).append(" - $").append(entry.getValue()).append('\n');
                    }
                }
                String phoneNumber = phoneNo.getText().toString();
                // check if permission is granted
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                    return;
                }
                else {
                    // permission already granted, send sms
                    try {
                        SmsManager manage = SmsManager.getDefault();
                        manage.sendTextMessage(phoneNumber, null, myList.toString(), null, null);
                        Toast.makeText(MainActivity.this, "SMS sent!", Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, "SMS failed!", Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                    }
                }
                startActivity(intent);
            }
        });
        build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                startActivity(intent);
            }
        });
        build.show();
    }
}