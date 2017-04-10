package com.ahchim.android.ritto;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;

import com.ahchim.android.ritto.model.SavedNumber;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class GeneratedNumListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Realm realm;
    RealmResults<SavedNumber> results;

    //ArrayList<SavedNumber> searchResult = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("생성 번호 목록");

        setContentView(R.layout.activity_generated_num_list);

        recyclerView = (RecyclerView) findViewById(R.id.recycler1);

        realm = Realm.getDefaultInstance();
        importSaveData();

        GenNumList_Adapter adapter = new GenNumList_Adapter(this, results);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


    }


    public void importSaveData(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results = realm.where(SavedNumber.class).findAll();
                Log.e("렘 검색결과","===========================" + results);

//                for(SavedNumber item : results){
//                    searchResult.add(item);
//                }
//                Log.e("데이터 들어감?","===========================" + searchResult);

            }
        });
    }





}
