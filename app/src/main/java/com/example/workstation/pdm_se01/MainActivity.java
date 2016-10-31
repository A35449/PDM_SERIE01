package com.example.workstation.pdm_se01;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.workstation.pdm_se01.AWA.API;
import com.example.workstation.pdm_se01.AWA.AWA_API;
import com.example.workstation.pdm_se01.AWA.SingletonRequest;
import com.example.workstation.pdm_se01.DAL.Weather.Wrapper;
import com.example.workstation.pdm_se01.Utils.Converter;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView txtMain;
    AWA_API AWAInstance;
    NetworkImageView imgView;
    private static ImageLoader loader;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = (NetworkImageView) findViewById(R.id.imgView);
        txtMain = (TextView) findViewById(R.id.txtMain);
        API api = new API(this.getApplicationContext());

        Response.Listener<String> repHandler = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                com.example.workstation.pdm_se01.DAL.Forecast.Wrapper wrap;
                try{
                    wrap = Converter.convertToForecast(response);
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


//        RequestQueue queue = SingletonRequest.getInstance(this.getApplicationContext()).initializeRequestQueue();
//        loader = SingletonRequest.getInstance(this.getApplicationContext()).getImageLoader();
//        String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=3b23922cd82d67ca2db1f9d0e189dc9a";
//        final String base_url = "http://api.openweathermap.org";
//        StringRequest req =  new StringRequest(url,repHandler,errHandler);
//        SingletonRequest.getInstance(this).addToRequestQueue(req);
//        List<Bulk> list = Utils.mapBulk(getResources().openRawResource(R.raw.citylist));
//        AWAInstance = new AWA_API();
//        AsyncWrapper wrp = new AsyncWrapper();
//        wrp._target = (TextView) findViewById(R.id.txtMain);
//        AWAInstance.getWeatherAtCity("Lisbon","pt",wrp);
    }
}
