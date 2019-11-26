package com.example.hereyouare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    /* station */
    String station = "";

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

    /* Linear Layout */
    LinearLayout asc_1;
    LinearLayout asc_2;
    LinearLayout desc_1;
    LinearLayout desc_2;

    /* textview */
    TextView asc_Title1;
    TextView asc_Title2;
    TextView desc_Title1;
    TextView desc_Title2;

    TextView asc_Seat1;
    TextView asc_Seat2;
    TextView desc_Seat1;
    TextView desc_Seat2;

    TextView asc_Arrive1;
    TextView asc_Arrive2;
    TextView desc_Arrive1;
    TextView desc_Arrive2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent(); /*데이터 수신*/

        station = intent.getStringExtra("station"); /*String형*/
        TextView station_tv = (TextView)findViewById(R.id.station);
        station_tv.setText(station);

        /* Linear Layout */
        asc_1 = (LinearLayout)findViewById(R.id.asc_1);
        asc_2 = (LinearLayout)findViewById(R.id.asc_2);
        desc_1 = (LinearLayout)findViewById(R.id.desc_1);
        desc_2 = (LinearLayout)findViewById(R.id.desc_2);

        /* layout에 listener 달기 */
        asc_1.setOnClickListener(this);
        asc_2.setOnClickListener(this);
        desc_1.setOnClickListener(this);
        desc_2.setOnClickListener(this);

        /* textview */
        asc_Title1 = (TextView)findViewById(R.id.ascTitle1);
        asc_Title2 = (TextView)findViewById(R.id.ascTitle2);
        desc_Title1 = (TextView)findViewById(R.id.descTitle1);
        desc_Title2 = (TextView)findViewById(R.id.descTitle2);

        asc_Seat1 = (TextView)findViewById(R.id.ascSeat1);
        asc_Seat2 = (TextView)findViewById(R.id.ascSeat2);
        desc_Seat1 = (TextView)findViewById(R.id.descSeat1);
        desc_Seat2 = (TextView)findViewById(R.id.descSeat2);

        asc_Arrive1 = (TextView)findViewById(R.id.ascArrive1);
        asc_Arrive2 = (TextView)findViewById(R.id.ascArrive2);
        desc_Arrive1 = (TextView)findViewById(R.id.descArrive1);
        desc_Arrive2 = (TextView)findViewById(R.id.descArrive2);


        ArrayList<HashMap> trainInfo = urlConnect2(station);

        int asc_num = trainInfo.get(0).size();
        int desc_num = trainInfo.get(1).size();

        Log.d("connection1", Integer.toString(asc_num));
        Log.d("connection1", Integer.toString(desc_num));

        Train t = (Train)trainInfo.get(0).get("train1");
        Log.d("connection1", t.getNum());
        Log.d("connection1", t.getTitle());
        Log.d("connection1", t.getArrive());
        Log.d("connection1", t.getSeat());



        if (asc_num == 2){ //열차 2대
            Train t1 = (Train)trainInfo.get(0).get("train1");
            Train t2 = (Train)trainInfo.get(0).get("train2");
            ascTitle1 = t1.getTitle();
            ascSeat1 = t1.getSeat();
            ascArrive1 = t1.getArrive();
            ascTitle2 = t2.getTitle();
            ascSeat2 = t2.getSeat();
            ascArrive2 = t2.getArrive();

            asc_Title1.setText(ascTitle1);
            asc_Seat1.setText(ascSeat1);
            asc_Arrive1.setText(ascArrive1);
            asc_Title2.setText(ascTitle2);
            asc_Seat2.setText(ascSeat2);
            asc_Arrive2.setText(ascArrive2);

        }else{  //열차 1대
            Train t1 = (Train)trainInfo.get(0).get("train1");
            ascTitle1 = t1.getTitle();
            ascSeat1 = t1.getSeat();
            ascArrive1 = t1.getArrive();
            asc_Title1.setText(ascTitle1);
            asc_Seat1.setText(ascSeat1);
            asc_Arrive1.setText(ascArrive1);

            asc_2.setVisibility(View.INVISIBLE);
        }

        if (desc_num == 2){ //열차 2대
            Train t1 = (Train)trainInfo.get(1).get("train1");
            Train t2 = (Train)trainInfo.get(1).get("train2");
            descTitle1 = t1.getTitle();
            descSeat1 = t1.getSeat();
            descArrive1 = t1.getArrive();
            descTitle2 = t2.getTitle();
            descSeat2 = t2.getSeat();
            descArrive2 = t2.getArrive();

            desc_Title1.setText(descTitle1);
            desc_Seat1.setText(descSeat1);
            desc_Arrive1.setText(descArrive1);
            desc_Title2.setText(descTitle2);
            desc_Seat2.setText(descSeat2);
            desc_Arrive2.setText(descArrive2);
        }else{  //열차 1대
            Train t1 = (Train)trainInfo.get(1).get("train1");
            descTitle1 = t1.getTitle();
            descSeat1 = t1.getSeat();
            descArrive1 = t1.getArrive();
            desc_Title1.setText(descTitle1);
            desc_Seat1.setText(descSeat1);
            desc_Arrive1.setText(descArrive1);

            desc_2.setVisibility(View.INVISIBLE);
        }
        //Log.d("connection1", trainInfo.get(0).keySet().toString());

    }

    @Override
    public void onClick(View view) {

        //Toast.makeText(getApplicationContext(), "I have no info this time", Toast.LENGTH_SHORT).show();

        Intent intent;
        switch (view.getId()) {
            case R.id.asc_1:
                // Django와 연결
                intent = new Intent(getApplicationContext(), Detail2Activity.class);
                intent.putExtra("station", station);
                intent.putExtra("trainNum", "001001");
                startActivity(intent);
                break;

            case R.id.asc_2:
                // Django와 연결
                intent = new Intent(getApplicationContext(), Detail2Activity.class);
                intent.putExtra("station", station);
                intent.putExtra("trainNum", "001002");
                startActivity(intent);
                break;

            case R.id.desc_1:
                // Django와 연결
                intent = new Intent(getApplicationContext(), Detail2Activity.class);
                intent.putExtra("station", station);
                intent.putExtra("trainNum", "001003");
                startActivity(intent);
                break;

            case R.id.desc_2:
                // Django와 연결
                intent = new Intent(getApplicationContext(), Detail2Activity.class);
                intent.putExtra("station", station);
                intent.putExtra("trainNum", "001004");
                startActivity(intent);
                break;
        }

    }

    public ArrayList<HashMap> urlConnect2(String station){
        ArrayList<HashMap> trainInfo = null;


        RequestHttpURLConnection conn = new RequestHttpURLConnection();
        try {
            trainInfo = conn.execute(station,"a2").get();
            Log.d("connection1", trainInfo.toString());


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return trainInfo;
    }

}
