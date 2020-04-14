package com.example.citygame.MarkersList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.citygame.R;

import java.util.ArrayList;



public class MarkerListAdapter extends ArrayAdapter<Marker> {
    private Context routeContext;
    private int routeResource;

    public MarkerListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Marker> objects) {
        super(context, resource, objects);
        routeContext = context;
        routeResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String header1 = getItem(position).getTitle();
        String header2 = getItem(position).getLat() + " " + getItem(position).getLon();
        int drawable = R.drawable.ic_game;

        LayoutInflater inflater = LayoutInflater.from(routeContext);
        convertView = inflater.inflate(routeResource,parent, false);

        TextView header1TextView=convertView.findViewById(R.id.header1);
        TextView header2TextView=convertView.findViewById(R.id.header2);
        ImageView imageView=convertView.findViewById(R.id.imageView);
        header1TextView.setText(header1);
        header2TextView.setText(header2);
        imageView.setImageResource(drawable);

        return convertView;
    }
}
