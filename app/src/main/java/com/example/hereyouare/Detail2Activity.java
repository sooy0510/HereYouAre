package com.example.hereyouare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Detail2Activity extends AppCompatActivity {

    Button c3_s1;
    Button c3_s2;
    Button c3_s3;
    Button c3_s4;

    Button c4_s1;
    Button c4_s2;

    Button c5_s1;
    Button c5_s2;
    Button c5_s3;
    Button c5_s4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        Intent intent = getIntent(); /*데이터 수신*/

        String station = intent.getStringExtra("station"); /*String형*/
        String trainNum = intent.getStringExtra("trainNum"); /*String형*/

        TextView train_Num = (TextView)findViewById(R.id.trainNum);
        train_Num.setText("열차번호 : "+trainNum);

        ImageView c3_s1 = (ImageView)findViewById(R.id.c3_s1);

        JSONObject trainInfo = urlConnect3(station, trainNum);

        Log.d("connection6", trainInfo.toString());

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.red);
        BitmapDrawable red = new BitmapDrawable(getResources(), icon);

        ImageView seat_img;

        try {

            JSONArray obj3 = trainInfo.getJSONObject("trains").getJSONArray("003");
            JSONArray obj4 = trainInfo.getJSONObject("trains").getJSONArray("004");
            JSONArray obj5 = trainInfo.getJSONObject("trains").getJSONArray("005");

            for(int i=0; i<obj3.length(); i++){
                JSONArray seat = obj3.getJSONArray(i);
                String seat_n = seat.get(0).toString();
                String seat_f = seat.get(1).toString();
                Log.d("connection6", seat_n);
                Log.d("connection6", seat_f);
                if(seat_f.equals("false")){
                    int seat_id = getResources().getIdentifier("c3_s"+(i+1), "id", "com.example.hereyouare");
                    Log.d("connection7", Integer.toString(c3_s1.getId()));
                    Log.d("connection7", Integer.toString(seat_id));
                    seat_img = findViewById(seat_id);
                    seat_img.setBackground(red);

                }
            }

            for(int i=0; i<obj4.length(); i++){
                JSONArray seat = obj4.getJSONArray(i);
                String seat_n = seat.get(0).toString();
                String seat_f = seat.get(1).toString();
                if(seat_f.equals("false")){
                    int seat_id = getResources().getIdentifier("c4_s"+(i+1), "id", "com.example.hereyouare");
                    seat_img = (ImageView) findViewById(seat_id);
                    seat_img.setBackground(red);
                 }
            }

            for(int i=0; i<obj5.length(); i++){
                JSONArray seat = obj5.getJSONArray(i);
                String seat_n = seat.get(0).toString();
                String seat_f = seat.get(1).toString();
                if(seat_f.equals("false")){
                    int seat_id = getResources().getIdentifier("c5_s"+(i+1), "id", "com.example.hereyouare");
                    seat_img = (ImageView) findViewById(seat_id);
                    seat_img = (ImageView) findViewById(seat_id);
                    seat_img.setBackground(red);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject urlConnect3(String station, String trainNum){
       JSONObject trainInfo = null;


        RequestHttpURLConnection2 conn = new RequestHttpURLConnection2();
        try {
            trainInfo = conn.execute(station,"a3", trainNum).get();
            Log.d("connection1", trainInfo.toString());


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return trainInfo;
    }
}
