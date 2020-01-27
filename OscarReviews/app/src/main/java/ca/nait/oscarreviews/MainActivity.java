package ca.nait.oscarreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void postToServer(String nominee, String review, String category) {
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost("http://www.youcode.ca/Lab01Servlet");
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
            formParameters.add(new BasicNameValuePair("REVIEW", review));
            formParameters.add(new BasicNameValuePair("REVIEWER", "Mhorvath9"));
            formParameters.add(new BasicNameValuePair("NOMINEE", nominee));
            formParameters.add(new BasicNameValuePair("CATEGORY", category));
            formParameters.add(new BasicNameValuePair("PASSWORD", "oscar275"));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParameters);
            form.setEntity((formEntity));
            client.execute(form);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

    public void onClickPostReview(View view) {

        RadioGroup radioGroupCategory = findViewById(R.id.radioGroupCategory);
        EditText nomineeField = findViewById(R.id.editTextNominee);
        EditText reviewField = findViewById(R.id.editTextReview);
        //Validation
        if(radioGroupCategory.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Please select a Category", Toast.LENGTH_LONG).show();
        }
        else if(nomineeField.getText().toString().length()<1)
        {
            Toast.makeText(this, "Please enter a Nominee", Toast.LENGTH_LONG).show();
        }
        else if(reviewField.getText().toString().length()<1)
        {
            Toast.makeText(this, "Please enter a Review", Toast.LENGTH_LONG).show();
        }
        else
        {
            String Nominee = nomineeField.getText().toString();
            String Review = reviewField.getText().toString();
        }

        //Make sure all fields are properly entered
        //then post to server if successful
    }
}
