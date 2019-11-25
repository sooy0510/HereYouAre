package com.example.hereyouare;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn;
    Button s1;
    Button quickBtn;

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

        s1 = (Button)findViewById(R.id.s1);
        btn = s1;
        btn.setAlpha(0);
        btn.setOnClickListener(this);

        /*button 모두 alpha(0)으로 만드는 코드
        TableLayout tableLayoutInstance; // let's suppouse you have already initialized it
// blablabla
// example to deactivate all buttons
ArrayList<View> touchables = tableLayoutInstance.getTouchables();
for(View touchable : touchables){
    if( touchable instanceof Button )
        ((Button)touchable).setEnabled(false);
}
        * */


    }

    @Override
    public void onClick(View view) {

        String station = "";
        ArrayList<HashMap> trainInfo = null;
        // Station 선택
        switch (view.getId()){
            case R.id.s1:
                station = "미금";
                btn.setAlpha(1);
                Toast.makeText(this, station, Toast.LENGTH_SHORT).show();

                // Django와 연결
                urlConnect(station, view);
                break;

        }

    }

    public void urlConnect(String station, View view){
        String ascTitle;
        String descTitle;
        String seat1;
        String seat2;

        RequestHttpURLConnection conn = new RequestHttpURLConnection();
        try {
            ArrayList<HashMap> trainInfo = conn.execute(station).get();
            ascTitle = trainInfo.get(0).get("ascTitle").toString();
            descTitle = trainInfo.get(0).get("descTitle").toString();
            seat1 = trainInfo.get(0).get("seat1").toString();
            seat2 = trainInfo.get(0).get("seat2").toString();
            addItem(ascTitle, descTitle, seat1, seat2);
            quickAction.show(view);
            quickAction.setAnimStyle(QuickAction.ANIM_REFLECT);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void addItem(String ascTitle, String descTitle, String seat1, String seat2){
        String asc = ascTitle + " : " + seat1 + "석 남음";
        String desc = descTitle + " : " + seat2 + "석 남음";

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

                //here we can filter which action item was clicked with pos or actionId parameter
                if (actionId == ID_SEARCH) {
                    Toast.makeText(getApplicationContext(), "Let's do some search action", Toast.LENGTH_SHORT).show();
                } else if (actionId == ID_INFO) {
                    //Toast.makeText(getApplicationContext(), "I have no info this time", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
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
                btn.setAlpha(0);
                Toast.makeText(getApplicationContext(), "Dismissed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
