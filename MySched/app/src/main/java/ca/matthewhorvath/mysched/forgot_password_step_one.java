package ca.matthewhorvath.mysched;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class forgot_password_step_one extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_step_one);
    }

    @Override
    public void onClick(View v) {
        Button nextStep = findViewById(R.id.sendToStepTwo);
        Intent intent = new Intent(this, forgot_password_step_two.class);

        /*
        * Make sure to to send the request to the database to ensure that this employeeID exist
        * if so randomly fetch 2 out of the 3 security question and bundle to the next activity
        * */
        startActivity(intent);

    }
}
