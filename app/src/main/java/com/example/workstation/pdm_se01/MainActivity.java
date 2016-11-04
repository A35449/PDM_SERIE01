package com.example.workstation.pdm_se01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.workstation.pdm_se01.AWA.API;
import com.example.workstation.pdm_se01.DAL.Forecast.Forecast;
import com.example.workstation.pdm_se01.Utils.Converter;
import com.example.workstation.pdm_se01.Utils.ForecastAdapter;
import com.example.workstation.pdm_se01.Utils.Utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LIST_INSTANCE_STATE = "ListViewState" ;
    static String file_string;
    private TextView txtMain;
    private EditText editText;
    private Button getWeather;

    private static ListView lv ;
    private static ArrayAdapter adapter;
    private static Parcelable state;
    private static Button aboutUs;

    private static API api;
    private String jsonRequest;
    private Bundle bundle;
    private SharedPreferences shared;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume(){
        super.onResume();
        try{
            if(!Utils.checkConnectivity(getApplicationContext())) throw new Exception();
        }catch(Exception e){
            launchConnectivityErrWindow();
        }
    }


    private void launchConnectivityErrWindow(){
        Intent intent = new Intent(this, ConnectivityActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bundle = savedInstanceState;
        shared =  getSharedPreferences("yawaPref", MODE_PRIVATE);
        lv = (ListView) findViewById(R.id.day_forecast_list);
        editText = (EditText) findViewById(R.id.LocationInput);
        getWeather=(Button) findViewById(R.id.GetWeather);
        aboutUs = (Button) findViewById(R.id.aboutus);

        if(adapter != null){
            lv.setAdapter(adapter);
        }else {
            String savedRequest = getSharedPreferences("yawaPref", MODE_PRIVATE).getString("req", null);
            if(savedRequest != null){
                try {
                    com.example.workstation.pdm_se01.DAL.Forecast.Wrapper wrap = Converter.convertToForecast(savedRequest);
                    fillList(wrap.getList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        api = new API(this.getApplicationContext());

        final Response.Listener<String> repHandler = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                com.example.workstation.pdm_se01.DAL.Forecast.Wrapper wrap;
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("req",response);
                editor.commit();
                try{
                    wrap = Converter.convertToForecast(response);
                    fillList(wrap.getList());
                }catch(IOException ex){
                    System.out.print(ex.getMessage());
                }
            }
        };

        final Response.ErrorListener errHandler = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                System.out.println(error.getMessage());
            }
        };

        getWeather.setOnClickListener( new View.OnClickListener() {
            @Override
            public void  onClick(View v){

                String [] location = editText.getText().toString().split(",");
                if (location.length<2){
                    Toast.makeText(MainActivity.this,"Location Unavailable",Toast.LENGTH_SHORT).show();
                    return;
                }
                String ps = String.format("\"name\":\"%s\",\"country\":\"%s\"",location[0],location[1].toUpperCase());
                if(file_string.contains(ps)) {
                    api.getForecast(location[0],repHandler,errHandler);
                    //
                }else {
                    Toast.makeText(MainActivity.this,"Location Unavailable",Toast.LENGTH_SHORT).show();
                }
            }
        });


        aboutUs.setOnClickListener( new View.OnClickListener() {
            @Override
            public void  onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, AboutActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        initializeInputData();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }
    public void initializeInputData(){
        if(file_string==null){
            InputStream it=getResources().openRawResource(R.raw.citylist);
            try {
                file_string = IOUtils.toString(it,"utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }

    private void fillList(List<Forecast> list) {
        adapter = new ForecastAdapter(this, R.layout.forecast_item, list);
        lv.setAdapter(adapter);
    }
}
