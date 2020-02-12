package ca.nait.oscarreviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
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


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener
{
    SharedPreferences preferences;
    View mainView;

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

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);

        mainView = findViewById(R.id.linear_layout_main);
        String bgColor = preferences.getString(getResources().getString(R.string.pref_key_background_color), "#660000");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }

    public void postToServer(String nominee, String review, String category)
    {
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost("http://www.youcode.ca/Lab01Servlet");
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
            formParameters.add(new BasicNameValuePair("REVIEW", review));
            formParameters.add(new BasicNameValuePair("REVIEWER", "gerry"));
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
        //Make sure all fields are properly entered
        //then post to server if successful
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
            String Category = radioButtonSelection();
            String Nominee = nomineeField.getText().toString();
            String Review = reviewField.getText().toString();
            //post to server
            postToServer(Nominee, Review, Category);

            Intent intent = new Intent(this, ViewReviews.class);
            intent.putExtras(radioButtonBundleSelection());
            startActivity(intent);
        }
    }

    //This method is used to obtain the user selection of the radio group.
    public String radioButtonSelection()
    {
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
    public Bundle radioButtonBundleSelection()
    {
        String bundleChoice = "";
        //get a handle on our radio button
        RadioGroup categoryGroup = findViewById(R.id.radioGroupCategory);

        //grab what radio our user has selected
        bundleChoice = (categoryGroup.getCheckedRadioButtonId() == R.id.radioBestPicture) ? CATEGORY_PICTURE :
                (categoryGroup.getCheckedRadioButtonId() == R.id.radioButtonActor) ? CATEGORY_ACTOR
                        : (categoryGroup.getCheckedRadioButtonId() == R.id.radioButtonActress) ? CATEGORY_ACTRESS :
                        (categoryGroup.getCheckedRadioButtonId() == R.id.radioButtonOptionFilmEditing) ? CATEGORY_FILM_EDITING
                                : CATEGORY_VISUAL_EFFECTS;

        //now Bundle our selection and category title
        RadioButton titleSelected = findViewById(categoryGroup.getCheckedRadioButtonId());
        //create a new bundle instance
        Bundle categoryBundle = new Bundle();
        //bundle the category value
        categoryBundle.putString("CATEGORY", bundleChoice);
        //bundle the category name
        categoryBundle.putString("TITLE", titleSelected.getText().toString());

        //return our bundle
        return categoryBundle;
    }

    //Menu Methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //true  - means its been taken care of

        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menuItemViewReviews:
            {
                Intent intent = new Intent(this, ViewReviews.class);
                intent.putExtras(radioButtonBundleSelection());
                startActivity(intent);
                break;
            }
            case R.id.menuItemPreference:
            {
                Intent intent = new Intent(this, PrefsActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String bgColor = preferences.getString(getResources().getString(R.string.pref_key_background_color), "#660000");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }

}

