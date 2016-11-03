package com.example.workstation.pdm_se01.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workstation.pdm_se01.DAL.Forecast.Forecast;
import com.example.workstation.pdm_se01.R;
import com.example.workstation.pdm_se01.WeatherActivity;
import com.squareup.picasso.Picasso;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        final Forecast forecast = data.get(position);
        holder.minTemperature.setText(Double.toString(forecast.getTemp().getMin()));
        holder.minTemperature.append(" ºC");
        holder.windSpeed.setText(Double.toString(forecast.getSpeed()));
        holder.windSpeed.append("m/s");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,position);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        holder.day.setText(sdf.format(date));
        Picasso
                .with(context)
                .load("http://openweathermap.org/img/w/"+forecast.getWeather().get(0).getIcon()+".png")
                .into(holder.imageIcon);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectMapper mapper = new ObjectMapper();
                Intent myIntent = new Intent(context, WeatherActivity.class);
                String forecastJSON = null;
                try {
                    forecastJSON = mapper.writeValueAsString(forecast);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myIntent.putExtra("forecastJSON",forecastJSON); //Optional parameters
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE,position);
                Date date = calendar.getTime();
                myIntent.putExtra("Date",date);
                context.startActivity(myIntent);
            }
        });
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