package com.example.citygame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.citygame.Models.GameResponse;
import com.example.citygame.RouteList.RouteApp;

import java.util.ArrayList;


public class GamesListAdapter extends ArrayAdapter<GameApp> {
    private Context routeContext;
    private int routeResource;
    private ArrayList<GameApp> games;

    public GamesListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<GameApp> objects) {
        super(context, resource, objects);
        routeContext = context;
        routeResource=resource;
        games = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String header1 = games.get(position).game.title;
        String header2 = games.get(position).game.score.toString();
        String header3 = "";
        for(String user: games.get(position).game.users_ids){
            header3 += user;
            header3 += ", ";
        }

        if(header3.length() >= 2) {
            header3 = header3.substring(0, header3.length() - 2);
        }
        int drawable = getItem(position).getDrawable();

        LayoutInflater inflater = LayoutInflater.from(routeContext);
        convertView = inflater.inflate(routeResource,parent, false);

        TextView titleOfGameTV = convertView.findViewById(R.id.titleOfGame);
        TextView scoreTV = convertView.findViewById(R.id.scoreText);
        TextView teamTV = convertView.findViewById(R.id.team);
        ImageView imageView = convertView.findViewById(R.id.imageView1);
        titleOfGameTV.setText(header1);
        scoreTV.setText(header2);
        teamTV.setText(header3);
        imageView.setImageResource(drawable);

        return convertView;
    }
}
