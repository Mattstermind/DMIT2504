package com.example.datashare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RecieveActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);

        Bundle bundle = this.getIntent().getExtras();
        String prefex = bundle.getString("PREFIX");
        String data = bundle.getString("DATA");

        TextView textMessage = findViewById(R.id.text_view_receive);
        textMessage.setText(prefex + data);
    }


}
