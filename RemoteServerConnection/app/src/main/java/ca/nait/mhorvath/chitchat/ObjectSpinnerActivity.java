package ca.nait.mhorvath.chitchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class ObjectSpinnerActivity extends AppCompatActivity {

    private static final String TAG = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_spinner);
        ArrayList array = new ArrayList();

        populateArray(array);

        MySpinAdapter adapter = new MySpinAdapter(this, android.R.layout.simple_spinner_item, array);

        Spinner spinner = findViewById(R.id.spinner_chatter);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new ItemListener(this));

    }

    public void populateArray(ArrayList chatter) {
        BufferedReader in = null;

        try {
            /* Now we create a http client this is a appache library*/
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/JitterServlet"));
            //now we want to submit the request and place it somewhere
            HttpResponse response = client.execute(request);
            //now we want to pull the data from the response object then pass it through a stream
            //reader, then we will pass it through a buffer reader which will read it one line at
            // a time, then we want to assign the bufferreader to our in object
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";


            //Loop through our in object until each message is read
            while ((line = in.readLine()) != null) {
                Message message = new Message();
                message.setMessageSender(line);

                line = in.readLine();
                message.setMessageContent(line);

                line = in.readLine();
                message.setMessageDate(line);

                chatter.add(message);
            }

            //this will close the input stream
            in.close();

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }




}



class ItemListener implements AdapterView.OnItemSelectedListener
{
    static ObjectSpinnerActivity activity;

    public ItemListener(Context context)
    {
        activity = (ObjectSpinnerActivity)context;
    }
    @Override
    public void onItemSelected(AdapterView<?> spinner, View view, int position, long id)
    {
        Message message = (Message) spinner.getAdapter().getItem(position);

        TextView tvSender = activity.findViewById(R.id.tv_sender);
        TextView tvDate = activity.findViewById(R.id.tv_date);
        TextView tvText = activity.findViewById(R.id.tv_content);

        tvSender.setText(message.getMessageSender());
        tvDate.setText(message.getMessageDate());
        tvText.setText(message.getMessageContent());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        //gets called on a empty list
    }
}