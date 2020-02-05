package ca.nait.threaderrr;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class chatter extends Service {

    static final String TAG = "ChatterService";
    static final int DELAY = 10000;
    public static boolean bRun = false;
    private chatReader reader = null;

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        return null;
    }
    //This will create our thread
    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG ,"in onCreate" );
        reader = new chatReader();
    }

    //Its what gets the thread rolling
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG ,"in onStartCommand" );
        bRun = true;
        reader.start();
        return START_STICKY;
    }

    //Kill the thread when you are done using it
    @Override
    public void onDestroy()
    {
        bRun = false;
        reader.interrupt();
        reader = null;
        Log.d(TAG ,"in onDestroy" );
        super.onDestroy();
    }


    private class chatReader extends Thread {


        public chatReader()
        {
            //normally a predefined string
            super("ChatterReader");
            Log.d(TAG ,"Thread instantiated" );
        }

        public void run()
        {
            chatter parent = chatter.this;
            while(bRun == true)
            {
                try
                {
                    Log.d(TAG ,"Reader executed one cycle" );
                    Thread.sleep(DELAY);
                }
                catch(InterruptedException ie)
                {
                    parent.bRun = false;
                }
            }
        }

        public void getFromServer()
        {
            BufferedReader in = null;
            try
            {

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI("http://www.youcode.ca/JSONServlet"));
                HttpResponse response = client.execute(request);
                in =  new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String strInput = "";



                //Loop through our in object until each message is read
                while((strInput = in.readLine()) != null)
                {
                    Log.d(TAG , strInput);
                }

                //this will close the input stream
                in.close();

            }
            catch(Exception e)
            {
                Log.d(TAG , "Throw exception" + e);;
            }

        }

    }
}
