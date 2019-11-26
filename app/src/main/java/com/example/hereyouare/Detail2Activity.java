package com.example.hereyouare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Detail2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        Intent intent = getIntent(); /*데이터 수신*/

        String station = intent.getStringExtra("station"); /*String형*/
        String trainNum = intent.getStringExtra("trainNum"); /*String형*/

        TextView train_Num = (TextView)findViewById(R.id.trainNum);
        train_Num.setText("열차번호 : "+trainNum);
        //TextView seat_Num = (TextView)findViewById(R.id.seatNum);
        //seat_Num.setText(seatNum);

        JSONObject trainInfo = urlConnect3(station, trainNum);

        Log.d("connection6", trainInfo.toString());

//        try {
//            HashMap train_3 = new HashMap();
//            HashMap train_4 = new HashMap();
//            HashMap train_5 = new HashMap();
//
//            JSONArray obj3 = trainInfo.getJSONObject("trains").getJSONArray("003");
//            JSONArray obj4 = trainInfo.getJSONObject("trains").getJSONArray("004");
//            JSONArray obj5 = trainInfo.getJSONObject("trains").getJSONArray("005");
//
//            for(int i=0; i<obj3.length(); i++){
//                JSONArray seat = obj3.getJSONArray(i);
//                String seat_n = seat.get(0).toString();
//                String seat_f = seat.get(1).toString();
//                train_3.put("seat"+(i+1), trainInfo);
//            }
//
//            for(int i=0; i<obj4.length(); i++){
//                JSONArray seat = obj4.getJSONArray(i);
//                String seat_n = seat.get(0).toString();
//                String seat_f = seat.get(1).toString();
//            }
//
//            for(int i=0; i<obj5.length(); i++){
//                JSONArray seat = obj5.getJSONArray(i);
//                String seat_n = seat.get(0).toString();
//                String seat_f = seat.get(1).toString();
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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
