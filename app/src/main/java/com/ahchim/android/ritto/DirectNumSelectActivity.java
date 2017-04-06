package com.ahchim.android.ritto;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DirectNumSelectActivity extends AppCompatActivity {

    Button btnSelect;

    int start = 1;
    int limit = 7;
    int a = 1;
    int numSelectCounter = 0;

    int selectedNumberList[] = new int[6];

    LinearLayout linearLayout;
    TextView lottoNum1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("포함할 번호 선택");

        setContentView(R.layout.activity_direct_num_select);

        btnSelect = (Button) findViewById(R.id.btnSelect_direct);

        linearLayout = (LinearLayout) findViewById(R.id.direct_ll);

        viewInit();
    }

    public void viewInit(){
        for(int i=0; i<7; i++){
            View v = View.inflate(this, R.layout.num_select_list, null);
            LinearLayout linearLayout1 = (LinearLayout) v.findViewById(R.id.ll_select);

            switch (a){
                case 1:
                    break;
                case 2 :
                    start = start + 7;
                    limit = limit + 7;
                    break;
                case 3 :
                    start = start + 7;
                    limit = limit + 7;
                    break;
                case 4 :
                    start = start + 7;
                    limit = limit + 7;
                    break;
                case 5 :
                    start = start + 7;
                    limit = limit + 7;
                    break;
                case 6 :
                    start = start + 7;
                    limit = limit + 7;
                    break;
                case 7 :
                    start = start + 7;
                    limit = 45;
                    break;
            }

            for(int j=start; j<=limit; j++){
                lottoNum1 = new TextView(this);
                lottoNum1.setId(j);
                lottoNum1.setBackgroundResource(R.mipmap.ball_unselect);
                lottoNum1.setText(j+"");
                lottoNum1.setTextSize(20);
                lottoNum1.setTextColor(Color.WHITE);
                lottoNum1.setTypeface(Typeface.DEFAULT_BOLD);
                lottoNum1.setGravity(Gravity.CENTER);
                lottoNum1.setPaintFlags(0);

                lottoNum1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(this, ((TextView)v).getText(), Toast.LENGTH_SHORT).show(); -> 이거 토스트 어케 찍냐...
                        Log.e("동적뷰","값=================" + ((TextView)v).getText().toString()); //-> 기가 막힌다. lottoNum1.getText().toString()하면 마지막거의 내용만 출력됨
                        Log.e("동적뷰","아이디값이 뭐니?==================" + v.getId());

                            switch (((TextView)v).getPaintFlags()){
                                case 0 :
                                    if(numSelectCounter < 5) {
                                        if(v.getId() < 11){
                                            v.setBackgroundResource(R.mipmap.ball_one);
                                        } else if(v.getId() < 21) {
                                            v.setBackgroundResource(R.mipmap.ball_two);
                                        } else if (v.getId() < 31) {
                                            v.setBackgroundResource(R.mipmap.ball_three);
                                        } else if (v.getId() < 41) {
                                            v.setBackgroundResource(R.mipmap.ball_four);
                                        } else {
                                            v.setBackgroundResource(R.mipmap.ball_five);
                                        }
                                        ((TextView)v).setPaintFlags(1);
                                        numSelectCounter = numSelectCounter + 1;

                                    }else {
                                        Toast.makeText(DirectNumSelectActivity.this, "5개까지만 선택 가능해요!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;

                                case 1 :
                                    v.setBackgroundResource(R.mipmap.ball_unselect);
                                    ((TextView)v).setPaintFlags(0);
                                    numSelectCounter = numSelectCounter - 1;
                                    break;
                            }
                    }
                });
                Log.e("돌아가니?","===================="+j);
                int count = j;

                if(count%7 == 0){
                    a = a+1;
                    Log.e("a는?","============="+a);
                }
                if(j == 43){
                    linearLayout1.setPadding(105,0,0,0);
                }
                if(j >= 43){
                    linearLayout1.setGravity(Gravity.LEFT);
                }
                linearLayout1.addView(lottoNum1);
            }
            linearLayout.addView(v);
        }

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numSelectCounter == 0){
                    Toast.makeText(DirectNumSelectActivity.this, "번호를 1개 이상 선택해 주십쇼!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent returnIntent = new Intent(DirectNumSelectActivity.this, AutoGenActivity.class);
                    returnIntent.putExtra("result", selectedNumberList);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        });



    }





}
