package com.ahchim.android.ritto;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FullScannerFragmentActivity extends BaseScannerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("QR Code Reader");
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_full_scanner_fragment);
        //setupToolbar();
    }
}
