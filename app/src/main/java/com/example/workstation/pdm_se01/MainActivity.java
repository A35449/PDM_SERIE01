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
    TextView txtMain;
    AWA_API AWAInstance;
    NetworkImageView imgView;
    EditText editText;
    Button getWeather;
    Button getForecast;
    Button help;
    static String file_string;
    private static ImageLoader loader;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = (NetworkImageView) findViewById(R.id.imgView);
        txtMain = (TextView) findViewById(R.id.txtMain);
        API api = new API(this.getApplicationContext());
 		initializeInputData();
        Response.Listener<String> repHandler = new Response.Listener<String>(){
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
        Response.ErrorListener errHandler = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                txtMain.setText(error.getMessage());
            }
        };
//        api.getWeather("uk","London",repHandler,errHandler);
        api.getForecast("HongKong",repHandler,errHandler);


  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
      editText = (EditText) findViewById(R.id.LocationInput);
    
       getWeather=(Button) findViewById(R.id.GetWeather);

       getWeather.setOnClickListener( new View.OnClickListener() {
              @Override
               public void  onClick(View v){

                  Editable value =  editText.getText();
                  String [] location = value.toString().split(",");
                    if (location.length<2){
                        Toast.makeText(MainActivity.this,"Location Unavailable",Toast.LENGTH_SHORT).show();
                        return;
                    }
                  String ps = String.format("\"name\":\"%s\",\"country\":\"%s\"",location[0],location[1]);
                    if(file_string.contains(ps)) {
                        String City = location[0];
                        String Country = location[1];
                        //
                    }else {
                        Toast.makeText(MainActivity.this,"Location Unavailable",Toast.LENGTH_SHORT).show();
                        return;
                    }
              }
          });

        getForecast=(Button) findViewById(R.id.GetForecast);

        getForecast.setOnClickListener( new View.OnClickListener() {

            @Override
            public void  onClick(View v){
                Editable value =  editText.getText();
                String [] location = value.toString().split(",");

                String ps = String.format("\"name\":\"%s\",\"country\":\"%s\"",location[0],location[1]);
                if(file_string.contains(ps)) {
                    String City = location[0];
                    String Country = location[1];
                    //ze
                }else {
                    Toast.makeText(MainActivity.this,"Location Unavailable",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        help= (Button)findViewById(R.id.help);
        help.setOnClickListener( new View.OnClickListener() {
            @Override
        
            public void  onClick(View v){

            }
        });







    }



	public void initializeInputData(){
            if(file_string== ""){
            InputStream it=getResources().openRawResource(R.raw.citylist);


            try {
                String file_string = IOUtils.toString(it,"utf-8");

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
