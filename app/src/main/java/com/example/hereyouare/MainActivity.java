package com.example.hereyouare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

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
        String result = RequestHttpURLConnection.setConn("미금");
    }
}
