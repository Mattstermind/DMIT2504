package ca.nait.oscarreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
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

public class ViewReviews extends ListActivity
{
    public static final String DATE = "Date";
    public static final String REVIEWER = "Reviewer";
    public static final String CATEGORY = "Category";
    public static final String NOMINEE = "Nominee";
    public static final String REVIEW = "Review";

    ArrayList<HashMap<String, String>> reviews = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        //Collect the bundle
        Bundle categoryBundle = this.getIntent().getExtras();
        //String categoryChoice = categoryBundle.toString("CATEGORY");
        //String categoryChoiceTitle = categoryBundle.toString("TITLE");

    }

    private void getFromServer()
    {
        String[] keys = new String[]{DATE, REVIEWER, CATEGORY, NOMINEE, REVIEW};
        int [] ids = new int[]{R.id.row_text_view_date, R.id.row_text_view_reviewer, R.id.row_text_view_category,
                R.id.row_text_view_nominee, R.id.row_text_view_review,  };
        SimpleAdapter adapter = new SimpleAdapter(this, reviews, R.layout.custom_row, keys, ids);
        populateList();
        setListAdapter(adapter);
    }

    public void populateList() {
        BufferedReader in = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            //now we set up a request
            HttpGet request = new HttpGet();
            //JSON parclet
            request.setURI(new URI("http://www.youcode.ca/showReviews.jsp"));
            //now we want to submit the request and place it somewhere
            HttpResponse response = client.execute(request);
            //Now we parse our request
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            while ((line = in.readLine()) != null) {
                HashMap<String, String> tempMap = new HashMap<String, String>();
                //at the beginning of our loop we have already jumped to the first key value pair so
                //we only have to assign it
                tempMap.put(DATE, line);

                //now we read the next line
                line = in.readLine();
                //then assign it
                tempMap.put(REVIEWER, line);

                //we again jump to the next line
                line = in.readLine();
                //then assign it
                tempMap.put(CATEGORY, line);

                //we again jump to the next line
                line = in.readLine();
                //then assign it
                tempMap.put(NOMINEE, line);

                //we again jump to the next line
                line = in.readLine();
                //then assign it
                tempMap.put(REVIEW, line);

                reviews.add(tempMap);
            }
            in.close();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }

    }
}
