package com.example.workstation.pdm_se01;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.workstation.pdm_se01.DAL.Forecast.Forecast;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        ObjectMapper mapper = new ObjectMapper();
        Forecast forecast = new Forecast();
        try {
            forecast = mapper.readValue(i.getStringExtra("forecastJSON"), Forecast.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillActivity(forecast);
    }
    private void fillActivity(Forecast forecast) {

    }

}
