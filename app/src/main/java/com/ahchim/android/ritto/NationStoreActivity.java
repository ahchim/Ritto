package com.ahchim.android.ritto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class NationStoreActivity extends AppCompatActivity {

    public static int LOCATION_DEPTH_FLAG = 1;
    public static String tempLocation = "";

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nation_store);

        LocationAdapter adapter = new LocationAdapter(this, getAssetsFolderNameList());
        listView = (ListView) findViewById(R.id.location_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.tv_location);
                String locationName = textView.getText().toString();
                getAssetsFolderNameList(locationName);

                LocationAdapter adapter = new LocationAdapter(NationStoreActivity.this, getAssetsFolderNameList(locationName));
                adapter.notifyDataSetChanged();
                listView.removeAllViewsInLayout();
                listView.setAdapter(adapter);

            }
        });

        //Log.e("폴더이름 리스트","======================" + getAssetsFolderNameList("강원"));

    }


    //전지역 맨처음 세팅
    public String[] getAssetsFolderNameList () {
        try {
            String[] fileList = getAssets().list("location");

            for(int i=0; i < fileList.length; i++){
                Log.e("리스트","============" + fileList[i]);
            }
            return fileList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String[] getAssetsFolderNameList (String locationName) {

        switch (LOCATION_DEPTH_FLAG){
            case 1 :
                tempLocation = locationName;
                LOCATION_DEPTH_FLAG = 2;
                try {
                    String[] fileList = getAssets().list("location/" + locationName);

                    for(int i=0; i < fileList.length; i++){
                        Log.e("LOCATION_DEPTH_FLAG = 1","============" + fileList[i]);
                    }
                    return fileList;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 2 :
                try {
                    LOCATION_DEPTH_FLAG = 3;
                    String[] fileList = getAssets().list("location/" + tempLocation +"/"+ locationName);

                    for(int i=0; i < fileList.length; i++){
                        Log.e("LOCATION_DEPTH_FLAG = 2","============" + fileList[i]);
                    }
                    return fileList;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3 :
                break;
        }
        return null;
    }




}
