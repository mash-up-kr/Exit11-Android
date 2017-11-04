package io.mashup.exit11.ui.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import io.mashup.exit11.R;
import io.mashup.exit11.Util.PermissionUtils;
import io.mashup.exit11.common.MapApiConst;

public class MapFragment extends Fragment
        implements MapView.MapViewEventListener,
        MapView.POIItemEventListener,
        MapView.OpenAPIKeyAuthenticationResultListener {

    static final String TAG = MapFragment.class.getName();

    private MapPOIItem mCustomMarker;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapView mapView = new MapView(getActivity());
        ViewGroup viewGroup = view.findViewById(R.id.map_view);
        mapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        viewGroup.addView(mapView);
    }

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int i, String s) {
        Log.i("TEST", String.format("Open API Key Authentication Result : code=%d, message=%s", i, s));
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        Log.i(TAG, "onMapViewInitialized");
// 현재 위치 보여주기 전에
        if(PermissionUtils.checkPermissions(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                }
            }, 1000);
        } else{
            Toast.makeText(getActivity(), "권한을 허용해주셔야 현재 위치가 조회가능합니다.", Toast.LENGTH_SHORT).show();
        }

        MyCustomTestBalloonAdapter customTestBalloonAdapter = new MyCustomTestBalloonAdapter();
        mapView.setCalloutBalloonAdapter(customTestBalloonAdapter);

    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);

    }

    private boolean isGetPermission() {

        return false;
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
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        // 말꼬리표 눌렀을때 해당하는 이벤트
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem,
                                                 MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }


    // CalloutBalloonAdapter 인터페이스 구현
    class MyCustomTestBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public MyCustomTestBalloonAdapter() {
            mCalloutBalloon = View.inflate(getActivity(), R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {

            return null;
        }
    }

    /**
     * 커스텀 마커 만드는 함수
     */
    private MapPOIItem createCustomMarker(MapView mapView, MapPoint point, String name) {

        mCustomMarker = new MapPOIItem();
        mCustomMarker.setItemName(name);
        mCustomMarker.setTag(1);
        mCustomMarker.setMapPoint(point);
        mCustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        mCustomMarker.setCustomImageResourceId(R.drawable.custom_marker_red);
        mCustomMarker.setCustomImageAutoscale(false);
        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f);

        mapView.addPOIItem(mCustomMarker);
        mapView.selectPOIItem(mCustomMarker, true);
        mapView.setMapCenterPoint(point, false);
        return mCustomMarker;
    }

    private void removeMapView(MapView mapView) {
        mapView.removeAllPOIItems();
    }

    private void showAll(MapView mapView, MapPoint point) {
        int padding = 20;
        float minZoomLevel = 1;
        float maxZoomLevel = 10;
        MapPointBounds bounds = new MapPointBounds(point, point);
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, padding, minZoomLevel, maxZoomLevel));
    }


}
