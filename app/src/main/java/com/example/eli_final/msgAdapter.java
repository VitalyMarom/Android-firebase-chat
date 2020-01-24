package com.example.eli_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class msgAdapter extends ArrayAdapter {

    int imgArr[] = {R.drawable.a1, R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5};

    public msgAdapter(@NonNull Context context, ArrayList<Chat_msg> msgChat) {
        super(context, 0, msgChat);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Chat_msg msg=(Chat_msg)getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_adapter, parent, false);
        }
        // Lookup view for data population
        TextView userName =  convertView.findViewById(R.id.userName);
        TextView message =  convertView.findViewById(R.id.msg);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        // Populate the data into the template view using the data object
        userName.setText(msg.getUser());
        message.setText(msg.getContent());
        imageView.setImageResource(msg.getImg());
        // Return the completed view to render on screen
        return convertView;
    }
}
