package com.example.hereyouare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.btn1);
        btn.setAlpha(0);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //btn.setVisibility(View.VISIBLE);
        btn.setAlpha(1);
        Toast.makeText(this, "나와랏", Toast.LENGTH_SHORT).show();
        //ArrayList<HashMap> jsonList = new ArrayList<>(); //return 해줄 route 정보들
        try {
            String result = new RequestHttpURLConnection().execute("미금").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
//
//    public String setConn(String station){
//
//        try{
//            ConnectivityManager conManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo netInfo = conManager.getActiveNetworkInfo();
//
//            if (netInfo != null && netInfo.isConnected()){
//                Log.d("connection", "네트워크 사용가능");
//            }else{
//                Log.d("connection", "네트워크 사용불가능");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
//            String result = new RequestHttpURLConnection().execute("미금").get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return "ss";
//    }

}
