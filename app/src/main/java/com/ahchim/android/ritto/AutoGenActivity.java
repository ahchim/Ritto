package com.ahchim.android.ritto;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AutoGenActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSelect1, btnSelect2;
    public static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("로또 번호 생성");
        setContentView(R.layout.activity_auto_gen);

        btnSelect1 = (Button) findViewById(R.id.btnSelect);
        btnSelect2 = (Button) findViewById(R.id.btnSelect2);
        btnSelect1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnSelect :
                intent = new Intent(AutoGenActivity.this, DirectNumSelectActivity.class);
                startActivityForResult(intent, REQUEST_CODE); //startActivityforResult로 바꿔야 하겠지?
                break;
            case R.id.btnSelect2 :
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == Activity.RESULT_OK){

        }

    }
}
