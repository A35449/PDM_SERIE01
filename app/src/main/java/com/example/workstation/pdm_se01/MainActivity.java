package com.example.workstation.pdm_se01;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.workstation.pdm_se01.AWA.API;
import com.example.workstation.pdm_se01.AWA.AWA_API;
import com.example.workstation.pdm_se01.AWA.SingletonRequest;
import com.example.workstation.pdm_se01.DAL.Forecast.Forecast;
import com.example.workstation.pdm_se01.Utils.Converter;
import com.example.workstation.pdm_se01.Utils.ForecastAdapter;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static String file_string;
    TextView txtMain;
    NetworkImageView imgView;
    EditText editText;
    Button getWeather;

    Button help;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = (NetworkImageView) findViewById(R.id.imgView);
        txtMain = (TextView) findViewById(R.id.txtMain);
        final API api = new API(this.getApplicationContext());
        initializeInputData();
        final Response.Listener<String> repHandler = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                com.example.workstation.pdm_se01.DAL.Forecast.Wrapper wrap;
                try{
                    wrap = Converter.convertToForecast(response);
                    fillList(wrap.getList());
                    txtMain.setText(wrap.getList().get(0).getWeather().get(0).getDescription());
                }catch(IOException ex){
                    System.out.print(ex.getMessage());
                }
            }
        };
        final Response.ErrorListener errHandler = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                txtMain.setText(error.getMessage());
            }
        };

        editText = (EditText) findViewById(R.id.LocationInput);

        getWeather=(Button) findViewById(R.id.GetWeather);

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
        ListView lv = (ListView)findViewById(R.id.day_forecast_list);
        ArrayAdapter adapter = new ForecastAdapter(this, R.layout.forecast_item, list);
        lv.setAdapter(adapter);
    }
}
