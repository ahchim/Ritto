package com.ahchim.android.ritto.daummap;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahchim.android.ritto.R;
import com.ahchim.android.ritto.daummap.search.Item;
import com.ahchim.android.ritto.daummap.search.OnFinishSearchListener;
import com.ahchim.android.ritto.daummap.search.Searcher;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.HashMap;
import java.util.List;

public class DaumMapActivity extends AppCompatActivity implements MapView.MapViewEventListener, MapView.CurrentLocationEventListener{

    private static final String DAUM_MAP_API_KEY = "a14b32c93ef72e751b9a37ceea05fd95";
    EditText etSearch;
    Button btnSearch;
    MapView mapView;
    private HashMap<Integer, Item> mTagItemMap = new HashMap<Integer, Item>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_daum_map);

        mapView = new MapView(this);
        mapView.setDaumMapApiKey(DAUM_MAP_API_KEY);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationEventListener(this);

        etSearch = (EditText) findViewById(R.id.etSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearch.getText().toString();
                if(query == null || query.length() == 0){
                    Toast.makeText(getApplicationContext(), "검색어를 입력하세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                hideSoftKeyboard();
                MapPoint.GeoCoordinate geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord();


                double latitude = geoCoordinate.latitude; //위도
                double longtitude = geoCoordinate.longitude; //경도
                int radius = 10000; // 중심좌표부터의 반경거리 특정지역을 중심으로 검색하려고 할 경우 사용한다. meter 단위(0~10000);
                int page = 1; // 페이지번호 (1~3) 한페이지에 15개

                Searcher searcher = new Searcher();
                mapView.removeAllPOIItems(); // 기존 검색결과 삭제

                    searcher.searchKeyword(getApplicationContext(), query, latitude, longtitude, radius, page, DAUM_MAP_API_KEY, new OnFinishSearchListener() {
                        @Override
                        public void onSuccess(List<Item> itemList) {
                            int j = 0;
                            showResult(itemList); // 검색 결과 보여줌
                            Log.e("쇼리절트","=====================================" + j);
                            j++;
                        }

                        @Override
                        public void onFail() {
                            Toast.makeText(getApplicationContext(), "Api키의 제한 트래픽이 초과되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }

    //Create DaumMap Menu Button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem menuItem;
        menuItem = menu.add(0,R.id.current_location,0,"현재위치로");
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        return true;
    }



    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
    }

    private void showResult(List<Item> itemList) {
        MapPointBounds mapPointBounds = new MapPointBounds();

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);

            MapPOIItem poiItem = new MapPOIItem();
            poiItem.setItemName(item.title);
            poiItem.setTag(i);
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(item.latitude, item.longitude);
            poiItem.setMapPoint(mapPoint);
            mapPointBounds.add(mapPoint);

//            poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
//            poiItem.setCustomImageResourceId(R.drawable.map_pin_blue);
//            poiItem.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
//            poiItem.setCustomSelectedImageResourceId(R.drawable.map_pin_red);

            poiItem.setMarkerType(MapPOIItem.MarkerType.BluePin);
            poiItem.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

            poiItem.setCustomImageAutoscale(false);
            poiItem.setCustomImageAnchor(0.5f, 1.0f);

            mapView.addPOIItem(poiItem);
            mTagItemMap.put(poiItem.getTag(), item);
        }

        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));

        MapPOIItem[] poiItems = mapView.getPOIItems();
        if (poiItems.length > 0) {
            mapView.selectPOIItem(poiItems[0], false);
        }
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

        MapPoint myPoint = MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(myPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }
}
