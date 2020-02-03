package ca.nait.mhorvath.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener
{
    SharedPreferences preferences;
    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy ourPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(ourPolicy);
        }

        Button sendButton = findViewById(R.id.buttonSend);
        Button viewButton = findViewById(R.id.buttonView);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);

        mainView = findViewById(R.id.linear_layout_main);
        String bgColor = preferences.getString(getResources().getString(R.string.pref_key_main_bg_color), "#660000");
        mainView.setBackgroundColor(Color.parseColor(bgColor));

        sendButton.setOnClickListener(this);
        viewButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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
            case R.id.menuItemTextViewList:
            {
                Intent intent = new Intent(this, ReceiveActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menuItemTextSystemList:
            {
                Intent intent = new Intent(this, SystemList.class);
                this.startActivity(intent);
                   break;
            }
            case R.id.menuItemTextCustomListActivity:
            {
                Intent intent = new Intent(this, CustomListActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menuItemPreferences:
            {
                Intent intent = new Intent(this, PrefsActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menuItemSpinnerObject:
            {
                Intent intent = new Intent(this, ObjectSpinnerActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
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
                postToServer(data);
                messageBox.setText("");
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

    public void postToServer(String message)
    {
        String userName = preferences.getString(getResources().getString(R.string.pref_key_user_name), "Unknown");
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
            formParameters.add(new BasicNameValuePair("REVIEW", message));
            formParameters.add(new BasicNameValuePair("USERNAME", "oscar275"));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParameters);
            //Now we are going to set the entity
            form.setEntity((formEntity));
            client.execute(form);

        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String bgColor = preferences.getString(getResources().getString(R.string.pref_key_main_bg_color), "#660000");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }
}
