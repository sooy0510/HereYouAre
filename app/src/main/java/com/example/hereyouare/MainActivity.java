package com.example.hereyouare;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn;
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
        btn = (Button)findViewById(R.id.btn1);
        btn.setAlpha(0);
        btn.setOnClickListener(this);
        quickBtn = (Button)findViewById(R.id.quick);
        quickBtn.setOnClickListener(this);


        /* Quick Action */
        infoItem 	= new ActionItem(ID_INFO, "Info", getResources().getDrawable(R.drawable.menu_info));
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
                    Toast.makeText(getApplicationContext(), "I have no info this time", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), actionItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
        //by clicking the area outside the dialog.
        quickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(getApplicationContext(), "Dismissed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                btn.setAlpha(1);
                Toast.makeText(this, "나와랏", Toast.LENGTH_SHORT).show();
                //ArrayList<HashMap> jsonList = new ArrayList<>(); //return 해줄 route 정보들

                // Django와 연결
                try {
                    String result = new RequestHttpURLConnection().execute("미금").get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.quick:
                //Intent intent = new Intent(this, ExampleActivity.class);
                //startActivity(intent);
                quickAction.show(view);
                quickAction.setAnimStyle(QuickAction.ANIM_REFLECT);
        }

    }
}
