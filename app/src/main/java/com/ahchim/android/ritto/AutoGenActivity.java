package com.ahchim.android.ritto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AutoGenActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSelect1, btnSelect2, btnGen;
    public static int REQUEST_CODE_1 = 100;
    public static int REQUEST_CODE_2 = 200;

    ArrayList<Integer> selectedNumber = new ArrayList<>();
    ArrayList<Integer> exceptNumber = new ArrayList<>();
    ArrayList<Integer> generatedNumber;

    ArrayAdapter<?> adapter;

    Ascending ascending;

    LinearLayout ll_inner_container;
    LinearLayout ll_inner_container_except;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("로또 번호 생성");
        setContentView(R.layout.activity_auto_gen);

        ll_inner_container = (LinearLayout) findViewById(R.id.ll_inner_container);
        ll_inner_container_except = (LinearLayout) findViewById(R.id.ll_inner_container_except);

        listView = (ListView) findViewById(R.id.listView);

        btnSelect1 = (Button) findViewById(R.id.btnSelect);
        btnSelect2 = (Button) findViewById(R.id.btnSelect2);
        btnGen = (Button) findViewById(R.id.btnGen1);

        btnSelect1.setOnClickListener(this);
        btnSelect2.setOnClickListener(this);
        btnGen.setOnClickListener(this);

        ascending = new Ascending();

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnSelect :
                intent = new Intent(AutoGenActivity.this, DirectNumSelectActivity.class);
                startActivityForResult(intent, REQUEST_CODE_1);
                break;
            case R.id.btnSelect2 :
                intent = new Intent(AutoGenActivity.this, DirectNumSelectActivity.class);
                startActivityForResult(intent, REQUEST_CODE_2);
                break;
            case R.id.btnGen1 :
                generateRandomNumber(selectedNumber, exceptNumber);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            ll_inner_container.removeAllViewsInLayout();
            selectedNumber = (ArrayList<Integer>) data.getSerializableExtra("result");
            Log.e("잘 받았디?","=================" + selectedNumber);
            Collections.sort(selectedNumber, ascending);
            Log.e("정렬됬디?","=================" + selectedNumber);

            inflateNumber(selectedNumber, requestCode);

        } else if(requestCode == 200 && resultCode == Activity.RESULT_OK) {
            ll_inner_container_except.removeAllViewsInLayout();
            exceptNumber = (ArrayList<Integer>) data.getSerializableExtra("result");
            Log.e("잘 받았디?","=================" + exceptNumber);
            Collections.sort(selectedNumber, ascending);
            Log.e("정렬됬디?","=================" + exceptNumber);

            inflateNumber(exceptNumber, requestCode);
        }


    }

    //포함하고싶은숫자, 제외하고싶은숫자 동적뷰 만들기
    public void inflateNumber(ArrayList<Integer> arrayList, int requestCode ){

        View v = View.inflate(this, R.layout.num_select_list, null);
        LinearLayout ll_container = (LinearLayout) v.findViewById(R.id.ll_select);
        ll_container.setGravity(Gravity.LEFT);

        for(int i=0; i < arrayList.size(); i++){
            TextView lottoNum = new TextView(this);
            lottoNum.setId(arrayList.get(i));
            lottoNum.setText(arrayList.get(i) + "");

            Log.e("어레이리스트 "," 값 ==================" + arrayList);
            Log.e("어레이리스트의 "," 인덱스==================" + arrayList.get(i));

            if(lottoNum.getId() < 11){
                lottoNum.setBackgroundResource(R.mipmap.ball_one);
            } else if(lottoNum.getId() < 21) {
                lottoNum.setBackgroundResource(R.mipmap.ball_two);
            } else if (lottoNum.getId() < 31) {
                lottoNum.setBackgroundResource(R.mipmap.ball_three);
            } else if (lottoNum.getId() < 41) {
                lottoNum.setBackgroundResource(R.mipmap.ball_four);
            } else {
                lottoNum.setBackgroundResource(R.mipmap.ball_five);
            }

            lottoNum.setTextSize(20);
            lottoNum.setTextColor(Color.WHITE);
            lottoNum.setTypeface(Typeface.DEFAULT_BOLD);
            lottoNum.setGravity(Gravity.CENTER);
            lottoNum.setPaintFlags(0);

            ll_container.addView(lottoNum);
        }

        if (requestCode == 100){
            ll_inner_container.setGravity(Gravity.LEFT);
            ll_inner_container.addView(v);
        } else if (requestCode == 200) {
            ll_inner_container_except.setGravity(Gravity.LEFT);
            ll_inner_container_except.addView(v);
        } else {
            listView.addView(v);

//            if(adapter != null){
//                adapter.notifyDataSetChanged();
//            }else {
//                adapter = new ArrayAdapter<Object>(this, R.layout.num_select_list);
//                listView.setAdapter(adapter);
//            }

        }

    }


    public void generateRandomNumber(ArrayList<Integer> selectedNumber, ArrayList<Integer> exceptNumber){
        ArrayList<Integer> ranNumber = new ArrayList<>();
        for(int i=1; i<=45; i++){
            ranNumber.add(i);
        }

        //1부터 45까지의 중복없는 숫자를 랜덤하게 섞고,
        Collections.shuffle(ranNumber);
        Log.e("ranNumber","=================" + ranNumber);
        Log.e("selectedNumber","=================" + selectedNumber);
        Log.e("exceptNumber","=================" + exceptNumber);

        //ranNumber에서 포함하고싶은 숫자 제거
        for(int i=0; i<selectedNumber.size(); i++){
            ranNumber.remove(selectedNumber.get(i));
        }
        Log.e("ranNumber, 포함지움","=================" + ranNumber);

        //ranNumber에서 제외하고싶은 숫자 제거
        for(int i=0; i<exceptNumber.size(); i++){
            ranNumber.remove(exceptNumber.get(i));
        }
        Log.e("ranNumber, 제외지움","=================" + ranNumber);

        generatedNumber = new ArrayList<>();

        //만든수에 포함하고싶은숫자 넣어준다.
        for(int j=0; j<6-selectedNumber.size(); j++){
            generatedNumber.add(j, ranNumber.get(j));
        }
        generatedNumber.addAll(selectedNumber);
        Log.e("generatedNumber","=================" + generatedNumber);

        //그 후 정렬
        Collections.sort(generatedNumber, ascending);
        Log.e("Sorted generatedNumber","=================" + generatedNumber);


        //inflateNumber(generatedNumber, 300);

    }





    //오름차순
    class Ascending implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }

    //내림차순
    class Descending implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    }




}
