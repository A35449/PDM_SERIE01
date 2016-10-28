package com.example.workstation.pdm_se01;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.workstation.pdm_se01.AWA.AWA_API;
import com.example.workstation.pdm_se01.DAL.Bulk;
import com.example.workstation.pdm_se01.Utils.Utils;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    TextView txtMain;
    AWA_API AWAInstance;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //List<Bulk> list = Utils.mapBulk(getResources().openRawResource(R.raw.citylist));
        AWAInstance = new AWA_API();
        AsyncWrapper wrp = new AsyncWrapper();
        wrp._target = (TextView) findViewById(R.id.txtMain);
        AWAInstance.getWeatherAtCity("Lisbon","pt",wrp);
    }
}
