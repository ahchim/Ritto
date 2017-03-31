package com.ahchim.android.ritto.navermap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahchim.android.ritto.R;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;

public class Fragment1 extends Fragment {
    private NMapContext mMapContext;
    private NMapLocationManager mMapLocationManager;
    private NMapCompassManager mMapCompassManager;
    private NMapOverlayManager mOverlayManager;
    public NMapView mapView;
    private MapContainerView mMapContainerView;




    private static final String CLIENT_ID = "P8jDQcqVM_RObTUp8XkD";// 애플리케이션 클라이언트 아이디 값
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMapContext =  new NMapContext(super.getActivity());
        mMapContext.onCreate();

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = (NMapView)getView().findViewById(R.id.mapView);

        mMapLocationManager = new NMapLocationManager(getContext());
        mMapLocationManager.setOnLocationChangeListener(new NMapLocationManager.OnLocationChangeListener() {

            //현재 위치 변경시 호출됨. myLocation객체에 변경된 좌표가 전달된다. 현재위치를 계속 탐색하려면 true를 반환.
            @Override
            public boolean onLocationChanged(NMapLocationManager nMapLocationManager, NGeoPoint nGeoPoint) {
                return false;
            }

            //정해진 시간내에 현재위치 탐색실패시 호출된다.
            @Override
            public void onLocationUpdateTimeout(NMapLocationManager nMapLocationManager) {
                Toast.makeText(getContext(), "탐색시간초과!", Toast.LENGTH_SHORT).show();
            }

            //현재위치가 지도상에 표시할 수 있는 범위를 벗어날 때 호출된다.
            @Override
            public void onLocationUnavailableArea(NMapLocationManager nMapLocationManager, NGeoPoint nGeoPoint) {
                Toast.makeText(getContext(), "당신의 위치를 지도에 표기할 수 없습니다!", Toast.LENGTH_SHORT).show();
            }
        });

        mMapCompassManager = new NMapCompassManager(getActivity());
        mMapCompassManager.setOnCompassChangeListener(new NMapCompassManager.OnCompassChangeListener() {
            @Override
            public boolean onSensorChanged(NMapCompassManager nMapCompassManager, float v) {
                // 모니터링을 계속 하려면 true를 반환한다
                return true;
            }
        });


        mapView.setClientId(CLIENT_ID);// 클라이언트 아이디 설정
        mapView.setClickable(true);
        mapView.setFocusable(true);
        mapView.setEnabled(true);
        mapView.setFocusableInTouchMode(true);
        mapView.requestFocus();

        mMapContainerView = new MapContainerView(getContext());
        mMapContainerView.addView(mapView);

        // set the activity content to the map view


        mMapContext.setupMapView(mapView);
    }
    @Override
    public void onStart(){
        super.onStart();
        mMapContext.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }
    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        mMapContext.onDestroy();
        super.onDestroy();
    }



    private class MapContainerView extends ViewGroup {

        public MapContainerView(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            final int width = getWidth();
            final int height = getHeight();
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);
                final int childWidth = view.getMeasuredWidth();
                final int childHeight = view.getMeasuredHeight();
                final int childLeft = (width - childWidth) / 2;
                final int childTop = (height - childHeight) / 2;
                view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            }

            if (changed) {
                mOverlayManager.onSizeChanged(width, height);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            int h = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            int sizeSpecWidth = widthMeasureSpec;
            int sizeSpecHeight = heightMeasureSpec;

            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);

                if (view instanceof NMapView) {
                    if (mapView.isAutoRotateEnabled()) {
                        int diag = (((int)(Math.sqrt(w * w + h * h)) + 1) / 2 * 2);
                        sizeSpecWidth = MeasureSpec.makeMeasureSpec(diag, MeasureSpec.EXACTLY);
                        sizeSpecHeight = sizeSpecWidth;
                    }
                }

                view.measure(sizeSpecWidth, sizeSpecHeight);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}


