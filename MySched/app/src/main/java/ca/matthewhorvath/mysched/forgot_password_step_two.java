package ca.matthewhorvath.mysched;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class forgot_password_step_two extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_step_two);
    }

    @Override
    public void onClick(View v) {
        Button nextStep = findViewById(R.id.buttonCheckAnswers);
        Intent intent = new Intent(this, forgot_password_step_three.class);

        /*
         * Make sure to to send the request to the database to ensure that this answers match with
         * the corresponding question, if so take user to next activity to change password         *
         * */
        this.startActivity(intent);


    }
}
