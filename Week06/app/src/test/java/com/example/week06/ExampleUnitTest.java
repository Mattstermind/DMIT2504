package com.example.week06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public static class DMManager extends SQLiteOpenHelper
        {
            static final String TAG = "DBManager";
            //choose a name for your database
            static final String DB_NAME = "ChatterDB";
            static final int DB_VERSION = 1;
            static final String TABLE_NAME = "Chatter";
            //create our db columns
            static final String C_ID = BaseColumns._ID;
            static final String C_DATE = "postDate";
            static final String C_SENDER = "sender";
            static final String C_DATA = "data";



            public DMManager(Context context)
            {
                super(context, DB_NAME, null, DB_VERSION);
            }

            //gets called when no database of this name
            @Override
            public void onCreate(SQLiteDatabase database)
            {
                String sql = "create table "  + TABLE_NAME + "(" + C_ID + "int primary key, "
                        + C_DATA + " text, " + C_SENDER + " text, " + C_DATA+ " text)";
                database.execSQL(sql);
                Log.d(TAG, sql);
            }

            @Override
            public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
            {
                Log.d(TAG, "updating database " + TABLE_NAME)
                database.execSQL("drop table if exists " + TABLE_NAME);
                //explicitly call the override
                onCreate(database);

            }
        }
}