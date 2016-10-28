package com.example.workstation.pdm_se01.AWA;

import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.net.URL;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.example.workstation.pdm_se01.AsyncWrapper;
import com.example.workstation.pdm_se01.DAL.Weather;
import com.example.workstation.pdm_se01.DAL.Wrapper;
import java.io.*;
import android.widget.TextView;


public class AWA_API {

    private final String API_KEY = "3b23922cd82d67ca2db1f9d0e189dc9a";

    private final String BASE_URL_CITY = "http://api.openweathermap.org/data/2.5/weather";

    private final String BASE_URL_WEATHER = "https://openweathermap.org/forecast5";


    private AsyncTask<AsyncWrapper,Void,String> worker;

    public  AWA_API(){
        worker = new AsyncTask<AsyncWrapper, Void, String>() {
            TextView txtView;

            @Override
            protected String doInBackground(AsyncWrapper... wrapper) {
                HttpURLConnection con = null;
                try {
                    txtView = wrapper[0]._target;
                    URL url = new URL(wrapper[0]._url);

                    con = (HttpURLConnection) url.openConnection();
                    // optional default is GET
                    con.addRequestProperty("x-api-key",API_KEY);

                    int responseCode = con.getResponseCode();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();

                }
                catch(Exception e) {
                    System.out.println("ERROR :" + e.getMessage());
                    return null;
                }finally{
                    con.disconnect();
                }
            }

            protected void onPostExecute(String response) {
                android.os.Debug.waitForDebugger();
                Weather wea = new Weather();
                if(response == null) {
                    response = "THERE WAS AN ERROR";
                }
                try{
                    byte[] resp_bytes = response.getBytes();
                    ObjectMapper objectMapper = new ObjectMapper();
                    //objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                     Wrapper wrap = objectMapper.readValue(resp_bytes, Wrapper.class);
//                     JsonNode node_arr_wea = predata.get("weather");
//                     JsonNode node_wea = node_arr_wea.get(0);
//                     wea.main = node_wea.get("main").asText();
//                     JsonNode descrip = node_wea.get("description");
//                     wea.description = descrip.asText();
                     txtView.setText(wrap.getWeather().get(0).getMain());

                    return;
                }catch(Exception e){
                    System.out.print("o");
                }
                //progressBar.setVisibility(View.GONE);
                //Log.i("INFO", response);
                //responseView.setText(response);
            }
        };
    }

    public void getWeatherAtCity(String city, String country,AsyncWrapper wrp){
        String prepareURI = String.format(BASE_URL_CITY + "?q=%s,%s&appid=%s}",city,country,API_KEY);
        wrp._url = prepareURI;
        worker.execute(wrp);
    }
}
