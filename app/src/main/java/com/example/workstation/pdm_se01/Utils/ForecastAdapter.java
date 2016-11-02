package com.example.workstation.pdm_se01.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workstation.pdm_se01.DAL.Forecast.Forecast;
import com.example.workstation.pdm_se01.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ForecastAdapter extends ArrayAdapter<Forecast> {

    Context context;
    int layoutResId;
    List<Forecast> data = null;

    public ForecastAdapter(Context context, int layoutResId, List<Forecast> data) {
        super(context, layoutResId, data);
        this.layoutResId = layoutResId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ForecastHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResId, parent, false);

            holder = new ForecastHolder();
            holder.imageIcon = (ImageView)convertView.findViewById(R.id.iconWeather);
            holder.day = (TextView)convertView.findViewById(R.id.day);
            holder.minTemperature = (TextView)convertView.findViewById(R.id.minTemperature);
            holder.windSpeed = (TextView)convertView.findViewById(R.id.windSpeed);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ForecastHolder)convertView.getTag();
        }

        Forecast forecast = data.get(position);
        holder.minTemperature.setText(Double.toString(forecast.getTemp().getMin()));
        holder.windSpeed.setText(Double.toString(forecast.getSpeed()));
        holder.day.setText(Integer.toString(position));
        Picasso
                .with(context)
                .load("http://openweathermap.org/img/w/"+forecast.getWeather().get(0).getIcon()+".png")
                .into(holder.imageIcon);


        return convertView;
    }

    static class ForecastHolder
    {
        ImageView imageIcon;
        TextView day;
        TextView minTemperature;
        TextView windSpeed;
    }
}