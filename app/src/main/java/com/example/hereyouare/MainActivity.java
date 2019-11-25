package com.example.hereyouare;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn;
    RelativeLayout buttons_layout;
    ArrayList<View> touchables;

    /* Quick Action */
    //action id
    private static final int ID_SEARCH = 3;
    private static final int ID_INFO   = 4;
    private static ActionItem infoItem;
    private static QuickAction quickAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInitialize();
    }

    @Override
    public void onClick(View view) {

        String station = "";
        ArrayList<HashMap> trainInfo = null;
        // Station 선택
        switch (view.getId()){
            case R.id.s1:
                btnInitialize();
                btn = (Button)findViewById(R.id.s1);
                station = "강남";
                btn.setAlpha(1);

                // Django와 연결
                urlConnect1(station, view);
                break;

            case R.id.s2:
                btnInitialize();
                btn = (Button)findViewById(R.id.s2);
                station = "양재";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

            case R.id.s3:
                btnInitialize();
                btn = (Button)findViewById(R.id.s3);
                station = "양재시민의숲";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

            case R.id.s4:
                btnInitialize();
                btn = (Button)findViewById(R.id.s4);
                station = "청계산입구";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

            case R.id.s5:
                btnInitialize();
                btn = (Button)findViewById(R.id.s5);
                station = "판교";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

            case R.id.s6:
                btnInitialize();
                btn = (Button)findViewById(R.id.s6);
                station = "정자";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

            case R.id.s7:
                btnInitialize();
                btn = (Button)findViewById(R.id.s7);
                station = "미금";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

            case R.id.s8:
                btnInitialize();
                btn = (Button)findViewById(R.id.s8);
                station = "동천";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

            case R.id.s9:
                btnInitialize();
                btn = (Button)findViewById(R.id.s9);
                station = "수지구청";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

            case R.id.s10:
                btnInitialize();
                btn = (Button)findViewById(R.id.s10);
                station = "성복";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

            case R.id.s11:
                btnInitialize();
                btn = (Button)findViewById(R.id.s11);
                station = "상현";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

            case R.id.s12:
                btnInitialize();
                btn = (Button)findViewById(R.id.s12);
                station = "광교중앙";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

            case R.id.s13:
                btnInitialize();
                btn = (Button)findViewById(R.id.s13);
                station = "광교";
                btn.setAlpha(1);
                urlConnect1(station, view);
                break;

        }

    }

    public void btnInitialize(){
        // button 모두 alpha(0)으로 만드는 코드
        buttons_layout = (RelativeLayout)findViewById(R.id.buttons_layout); // let's suppouse you have already initialized it
        touchables = buttons_layout.getTouchables();
        for(View touchable : touchables){
            if( touchable instanceof Button ) {
                ((Button) touchable).setAlpha(0);
                ((Button) touchable).setOnClickListener(this);
            }
        }
    }

    public void urlConnect1(String station, View view){
        String ascTitle;
        String descTitle;
        String seat1;
        String seat2;

        RequestHttpURLConnection conn = new RequestHttpURLConnection();
        try {
            ArrayList<HashMap> trainInfo = conn.execute(station,"a1").get();
            ascTitle = trainInfo.get(0).get("ascTitle").toString();
            descTitle = trainInfo.get(0).get("descTitle").toString();
            seat1 = trainInfo.get(0).get("seat1").toString();
            seat2 = trainInfo.get(0).get("seat2").toString();
            addItem(ascTitle, descTitle, seat1, seat2, station);
            quickAction.show(view);
            quickAction.setAnimStyle(QuickAction.ANIM_REFLECT);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void addItem(String ascTitle, String descTitle, String seat1, String seat2, String station){
        String asc = ascTitle + " : " + seat1 + "석 남음";
        String desc = descTitle + " : " + seat2 + "석 남음";

        //역
        final String st = station;
        /* Quick Action */
        infoItem 	= new ActionItem(ID_INFO, asc, desc, getResources().getDrawable(R.drawable.menu_info));
        quickAction = new QuickAction(this, QuickAction.VERTICAL);

        //add action items into QuickAction
        quickAction.addActionItem(infoItem);

        //Set listener for action item clicked
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {
                ActionItem actionItem = quickAction.getActionItem(pos);
                //Log.d("connection","ssssssss"+actionItem.get)

                //here we can filter which action item was clicked with pos or actionId parameter
                if (actionId == ID_SEARCH) {
                    Toast.makeText(getApplicationContext(), "Let's do some search action", Toast.LENGTH_SHORT).show();
                } else if (actionId == ID_INFO) {
                    //Toast.makeText(getApplicationContext(), "I have no info this time", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    // intent에 station 넣어주기
                    intent.putExtra("station",st); /*송신*/
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), actionItem.getAsc_title() + " selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
        //by clicking the area outside the dialog.
        quickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
            @Override
            public void onDismiss() {
                //btn.setAlpha(0);
                btnInitialize();
            }
        });
    }
}
