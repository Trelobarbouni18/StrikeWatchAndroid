package com.nestoras.strikewatch;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class StrikesAdapter extends ArrayAdapter<Strike>{

    private Context aContext;
    private int aResource;
    private static ArrayList<Strike> strikes = new ArrayList<>();

    public StrikesAdapter( Context context, int resource,  ArrayList<Strike> objects) {
        super(context, resource, objects);
        aContext = context;
        aResource = resource;
        strikes = objects;
    }

    public void addStrike(Strike strike) {
        strikes.add(strike);
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        // Get the data item for this position
        Strike strike = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.strike_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.Title);
        TextView tvDate = (TextView) convertView.findViewById(R.id.Date);

        // Populate the data into the template view using the data object
        tvName.setText(strike.getName());
        tvDate.setText(strike.getDate()+" | "+strike.getHour()+" | "+strike.getLocation());
        // Return the completed view to render on screen
        return convertView;
    }
}
