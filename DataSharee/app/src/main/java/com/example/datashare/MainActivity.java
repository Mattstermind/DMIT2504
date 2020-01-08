package com.example.datashare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fullSend = findViewById(R.id.button_fullsend);
        fullSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        EditText messageBox = findViewById(R.id.message_box);
        String data = messageBox.getText().toString();

        //PopUp
        //Toast.makeText(this, "You entered: " + data, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, RecieveActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("PREFIX", "You typed: ");
        bundle.putString("DATA", data);
        intent.putExtras(bundle);

        this.startActivity(intent);
    }
}
