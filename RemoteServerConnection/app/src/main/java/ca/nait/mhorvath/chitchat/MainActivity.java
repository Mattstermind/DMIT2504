package ca.nait.mhorvath.chitchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = findViewById(R.id.buttonSend);
        Button viewButton = findViewById(R.id.buttonView);

        sendButton.setOnClickListener(this);
        viewButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.buttonSend:
            {

                EditText messageBox = findViewById(R.id.editTextSend);
                String data = messageBox.getText().toString();
                Toast toast = Toast.makeText(this, "You entered: " + data, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 50);
                toast.show();
                break;
            }
            case R.id.buttonView:
            {
                Intent intent = new Intent(this, ReceiveActivity.class);
                startActivity(intent);
                break;
            }

        }

    }
}
