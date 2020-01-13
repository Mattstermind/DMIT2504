package ca.nait.mhorvath.chitchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class ReceiveActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
    }

    public void getFromServer()
    {
        BufferedReader in = null;
        TextView textbox = findViewById(R.id.textViewReceive);
        try
        {
            /* Now we create a http client this is a appache library*/
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://youcode.ca/JSONServlet"));
            //now we want to submit the request and place it somewhere
            HttpResponse response = client.execute(request);
            //now we want to pull the data from the response object then pass it through a stream
            //reader, then we will pass it through a buffer reader which will read it one line at
            // a time, then we want to assign the bufferreader to our in object
            in =  new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String strInput = "";

            //Loop through our in object until each message is read
            while((strInput = in.readLine()) != null);
            {
                textbox.append(strInput + "\n");
            }

            //this will close the input stream
            in.close();

        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }

    }
}
