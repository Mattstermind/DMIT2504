package com.example.week06;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    static final String TAG = "DisplayActivity";
    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        // we need to put a handle on our text view textView = findViewById(R.id.);
        //now instanstiate the dbManager
        dbManager = new DBManager(this);
        database = dbManager.getReadableDatabase();

    }

    @Override
    protected void onDestroy()
    {
        database.close();
        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        cursor = database.query(DBManager.TABLE_NAME, null, null, null, null, null, DBManager.C_ID + " DESC");
        startManagingCursor(cursor);
        String sender, date, data, output;
        while(cursor.moveToNext())
        {
            sender = cursor.getString(cursor.getColumnIndex(DBManager.C_SENDER));
            date = cursor.getString(cursor.getColumnIndex(DBManager.C_DATE));
            data = cursor.getString(cursor.getColumnIndex(DBManager.C_DATA));

            output = String.format("%s from %s *** %s\n", date, sender, data);
            textView.append(output);
        }
        Log.d(TAG, "in onResume()");
    }
}
