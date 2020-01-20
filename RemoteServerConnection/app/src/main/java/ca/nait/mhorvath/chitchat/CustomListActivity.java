package ca.nait.mhorvath.chitchat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomListActivity extends ListActivity
{
    ArrayList<HashMap<String, String>> chatter = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        getFromServer();
    }

    private void getFromServer()
    {
        String[] keys = new String[]{"sender", "text", "myDate" };
        int [] ids = new int[]{R.id.row_text_view_sender, R.id.rowTextViewMessage, R.id.rowTextViewDate};
        SimpleAdapter adapter = new SimpleAdapter(this, chatter, R.layout.custom_row, keys, ids);
        populateList();
        setListAdapter(adapter);
    }

    public void populateList()
    {
        BufferedReader in = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            //now we set up a request
            HttpGet request = new HttpGet();
            //JSON parclet
            request.setURI(new URI("http://www.youcode.ca/JitterServlet"));
            //now we want to submit the request and place it somewhere
            HttpResponse response = client.execute(request);
            //Nowe we parse our request
            in =  new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            while((line = in.readLine()) != null)
            {
                HashMap<String, String> tempMap = new HashMap<String, String>();
                //at the beginning of our loop we have already jumped to the first keyvalue pair so
                //we only have to assign it
                tempMap.put("sender", line);

                //now we read the next line
                line = in.readLine();
                //then assign it
                tempMap.put("text", line);
               //we again jump to the next line
                line = in.readLine();
                //then assign it
                tempMap.put("myDate", line);

                chatter.add(tempMap);
            }
            in.close();
        }
        catch(Exception e)
        {
            Toast.makeText(this,"Error: " + e, Toast.LENGTH_LONG).show();
        }
    }



}
