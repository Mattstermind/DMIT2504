package ca.nait.mhorvath.chitchat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MySpinAdapter extends ArrayAdapter
{

    public Context context;
    public ArrayList chatter;


    public MySpinAdapter(Context context, int textViewResourceId, ArrayList objects)
    {

        super(context, textViewResourceId, objects);
        this.context = context;
        this.chatter = objects;
    }
    public int getCount()
    {
        return chatter.size();
    }
    public Message getItem(int position)
    {
        return (Message)chatter.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView label = new TextView(context);
        label.setTextColor(Color.MAGENTA);
        Message message = (Message)chatter.get(position);
        String content = message.getMessageContent();
        label.setText(content);

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        ObjectSpinnerActivity activity = (ObjectSpinnerActivity)context;

        LayoutInflater inflater = activity.getLayoutInflater();
        View spinnerRow = inflater.inflate(R.layout.custom_row, null);

        TextView sender = (TextView)spinnerRow.findViewById(R.id.row_text_view_sender);
        TextView date = (TextView)spinnerRow.findViewById(R.id.rowTextViewDate);
        TextView text = (TextView)spinnerRow.findViewById(R.id.rowTextViewMessage);

        Message message = (Message)chatter.get(position);

        sender.setText(message.getMessageSender());
        //sub string is used to manipulate the data
        date.setText(message.getMessageDate());
        text.setText(message.getMessageContent());

        return spinnerRow;
    }
}
