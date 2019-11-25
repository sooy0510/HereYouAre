package com.example.hereyouare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent(); /*데이터 수신*/

        String station = intent.getStringExtra("station"); /*String형*/
        TextView station_tv = (TextView)findViewById(R.id.station);
        station_tv.setText(station);

        urlConnect2(station);

    }

    public void urlConnect2(String station){
        String ascTitle1;
        String ascTitle2;
        String descTitle1;
        String descTitle2;

        String ascSeat1;
        String ascSeat2;
        String descSeat1;
        String descSeat2;

        String ascArrive1;
        String ascArrive2;
        String descArrive1;
        String descArrive2;


        RequestHttpURLConnection conn = new RequestHttpURLConnection();
        try {
            ArrayList<HashMap> trainInfo = conn.execute(station,"a2").get();
//            ascTitle = trainInfo.get(0).get("ascTitle").toString();
//            descTitle = trainInfo.get(0).get("descTitle").toString();
//            seat1 = trainInfo.get(0).get("seat1").toString();
//            seat2 = trainInfo.get(0).get("seat2").toString();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
