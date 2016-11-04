package com.example.workstation.pdm_se01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by workstation on 03/11/2016.
 */

public class ConnectivityActivity extends AppCompatActivity {
    private static TextView messageBox;
    private static ImageButton refreshBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectivity);
        refreshBtn = (ImageButton)findViewById(R.id.refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        messageBox = (TextView) findViewById(R.id.messageBox);
        messageBox.setText("Sem ligação à rede.");
    }
}