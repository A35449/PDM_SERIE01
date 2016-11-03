package com.example.workstation.pdm_se01;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workstation.pdm_se01.DAL.Forecast.Forecast;
import com.squareup.picasso.Picasso;

import org.codehaus.jackson.map.ObjectMapper;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        Date date = (Date) i.getSerializableExtra("Date");

        ObjectMapper mapper = new ObjectMapper();
        Forecast forecast = new Forecast();
        try {
            forecast = mapper.readValue(i.getStringExtra("forecastJSON"), Forecast.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillActivity(forecast,date);
    }
    private void fillActivity(Forecast forecast,Date date) {
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        getSupportActionBar().setTitle(sdf.format(date));

        ImageView myIcon = (ImageView) findViewById(R.id.weatherImage);
        Picasso
                .with(getApplicationContext())
                .load("http://openweathermap.org/img/w/"+forecast.getWeather().get(0).getIcon()+".png")
                .into(myIcon);
        TextView minTemperature = (TextView) findViewById(R.id.minTemperatureWeather);
        minTemperature.setText(Double.toString(forecast.getTemp().getMin()));
        TextView maxTemperature = (TextView) findViewById(R.id.maxTemperatureWeather);
        maxTemperature.setText(Double.toString(forecast.getTemp().getMax()));
        TextView windspeed = (TextView) findViewById(R.id.windSpeedWeather);
        windspeed.setText(Double.toString(forecast.getSpeed()));
        TextView pressure = (TextView) findViewById(R.id.pressure);
        pressure.setText(Double.toString(forecast.getPressure()));
        TextView humidity = (TextView) findViewById(R.id.humidity);
        humidity.setText(Double.toString(forecast.getHumidity()));

    }

}
