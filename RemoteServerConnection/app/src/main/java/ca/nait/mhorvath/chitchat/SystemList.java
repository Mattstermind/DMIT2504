package ca.nait.mhorvath.chitchat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class SystemList extends ListActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFromServer();
    }

    private void getFromServer()
    {
        BufferedReader in = null;
        ArrayList chats = new ArrayList();
        try
        {
            //first we set up the browser
            HttpClient client = new DefaultHttpClient();
            //now we set up a request
            HttpGet request = new HttpGet();
            //JSON parclet
            request.setURI(new URI("http://www.youcode.ca/JSONServlet"));
            //now we want to submit the request and place it somewhere
            HttpResponse response = client.execute(request);
            //Nowe we parse our request
            in =  new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            while((line = in.readLine()) != null)
            {
                chats.add(line);
            }
            in.close();

            ArrayAdapter<ArrayList> adapter =
                    new ArrayAdapter<ArrayList>(this, android.R.layout.simple_list_item_1, chats);

            this.setListAdapter(adapter);

        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();

        }
    }




}
