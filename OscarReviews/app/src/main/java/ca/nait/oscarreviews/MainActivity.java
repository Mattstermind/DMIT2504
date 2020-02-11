package ca.nait.oscarreviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
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
    //Create static strings for our categories
    public static final String CATEGORY_PICTURE = "film";
    public static final String CATEGORY_ACTOR = "actor";
    public static final String CATEGORY_ACTRESS = "actress";
    public static final String CATEGORY_FILM_EDITING = "editing";
    public static final String CATEGORY_VISUAL_EFFECTS = "effects";

    //Create static strings for our preference
    public static final String DEFAULT_URL = "http://www.youcode.ca/Lab01Servlet";
    public static final String DEFAULT_USERNAME = "Mhorvath9";
    public static final String DEFAULT_PASSWORD = "oscar275";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy ourPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(ourPolicy);
        }

    }

    public void postToServer(String nominee, String review, String category) {


        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost(DEFAULT_URL);
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
            formParameters.add(new BasicNameValuePair("REVIEW", review));
            formParameters.add(new BasicNameValuePair("REVIEWER", DEFAULT_USERNAME));
            formParameters.add(new BasicNameValuePair("NOMINEE", nominee));
            formParameters.add(new BasicNameValuePair("CATEGORY", category));
            formParameters.add(new BasicNameValuePair("PASSWORD", DEFAULT_PASSWORD));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParameters);
            form.setEntity((formEntity));
            client.execute(form);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

    public void onClickPostReview(View view) {

        RadioGroup radioGroupCategory = findViewById(R.id.radioGroupCategory);
        EditText nomineeField = findViewById(R.id.editTextNominee);
        EditText reviewField = findViewById(R.id.editTextReview);
        //Validation
        if (radioGroupCategory.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a Category", Toast.LENGTH_LONG).show();
        } else if (nomineeField.getText().toString().length() < 1) {
            Toast.makeText(this, "Please enter a Nominee", Toast.LENGTH_LONG).show();
        } else if (reviewField.getText().toString().length() < 1) {
            Toast.makeText(this, "Please enter a Review", Toast.LENGTH_LONG).show();
        } else {
            // String Category = getSelectedRadioButtonValue(); <--- method
            String Nominee = nomineeField.getText().toString();
            String Review = reviewField.getText().toString();
            //postToServer(Nominee, Review, );
        }

        //Make sure all fields are properly entered
        //then post to server if successful
    }

    //This method is used to obtain the user selection of the radio group.
    public String radioButtonSelection() {
        String categoryChoice = "";
        //get a handle on our radio button
        RadioGroup categoryGroup = findViewById(R.id.radioGroupCategory);

        categoryChoice = (categoryGroup.getCheckedRadioButtonId() == R.id.radioBestPicture) ? CATEGORY_PICTURE :
                (categoryGroup.getCheckedRadioButtonId() == R.id.radioButtonActor) ? CATEGORY_ACTOR
                        : (categoryGroup.getCheckedRadioButtonId() == R.id.radioButtonActress) ? CATEGORY_ACTRESS :
                        (categoryGroup.getCheckedRadioButtonId() == R.id.radioButtonOptionFilmEditing) ? CATEGORY_FILM_EDITING
                                : CATEGORY_VISUAL_EFFECTS;

        return categoryChoice;
    }

    //This method is to bundle out category and ship it over to the other page
    public Bundle radioButtonBundleSelection() {
        String bundleChoice = "";
        //get a handle on our radio button
        RadioGroup categoryGroup = findViewById(R.id.radioGroupCategory);

        bundleChoice = (categoryGroup.getCheckedRadioButtonId() == R.id.radioBestPicture) ? CATEGORY_PICTURE :
                (categoryGroup.getCheckedRadioButtonId() == R.id.radioButtonActor) ? CATEGORY_ACTOR
                        : (categoryGroup.getCheckedRadioButtonId() == R.id.radioButtonActress) ? CATEGORY_ACTRESS :
                        (categoryGroup.getCheckedRadioButtonId() == R.id.radioButtonOptionFilmEditing) ? CATEGORY_FILM_EDITING
                                : CATEGORY_VISUAL_EFFECTS;

        //now Bundle our selection and category title
        RadioButton titleSelected = findViewById(categoryGroup.getCheckedRadioButtonId());
        Bundle categoryBundle = new Bundle();
        categoryBundle.putString("CATEGORY", bundleChoice);
        categoryBundle.putString("TITLE", titleSelected.getText().toString());

        //return our bundle
        return categoryBundle;
    }

    //Menu Methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menuItemPreference:
            {
                Intent intent = new Intent(this, PrefsActivity.class);
                this.startActivity(intent);
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }



}

