package io.mashup.exit11.ui.fragment;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import io.mashup.exit11.common.Const;
import io.mashup.exit11.util.SharedPreferenceUtil;

/**
 * Created by jonghunlee on 2018. 1. 17..
 */

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private static final String TAG = MapFragment.class.getSimpleName();
    private static final int REQUEST_CHECK_LOCATION_SETTING = 2001;

    private GoogleMap googleMap;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMapAsync(this);
        getCurrentLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.getUiSettings().setTiltGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);

        googleMap.setOnCameraIdleListener(this);

        // 서울 시청
        float lat = SharedPreferenceUtil.getInstance().getFloat(getActivity(), Const.LATITUDE, 37.566692f);
        float lng = SharedPreferenceUtil.getInstance().getFloat(getActivity(), Const.LONGITUDE, 126.978416f);

        // TODO: 2018. 1. 19. show last save location;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 17f));
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1);

        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());

        settingsClient.checkLocationSettings(new LocationSettingsRequest.Builder().addLocationRequest(
                locationRequest).setAlwaysShow(true).build())
                .addOnSuccessListener(getActivity(),
                        response -> fusedLocationClient.requestLocationUpdates(locationRequest,
                                locationCallback,
                                Looper.getMainLooper()))
                .addOnFailureListener(getActivity(), this::settingFail);
    }

    private void showCurrentLocation(Location location) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                location.getLongitude()), 17f));

        SharedPreferenceUtil.getInstance()
                .setFloat(getActivity(), Const.LATITUDE, (float) location.getLatitude());
        SharedPreferenceUtil.getInstance()
                .setFloat(getActivity(), Const.LONGITUDE, (float) location.getLongitude());

        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void settingFail(Exception e) {
        int statusCode = ((ApiException) e).getStatusCode();

        switch (statusCode) {
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    resolvableApiException.startResolutionForResult(getActivity(),
                            REQUEST_CHECK_LOCATION_SETTING);
                }
                catch (IntentSender.SendIntentException sie) {
                    Log.e(TAG, "getCurrentLocation#PendingIntent unable to execute request.", sie);
                }
                break;

        }
    }

    @Override
    public void onCameraIdle() {
        LatLng latLng = googleMap.getCameraPosition().target;
        Log.d(TAG, "onCameraIdle#latLng : " + latLng.toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_LOCATION_SETTING:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        getCurrentLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        getActivity().finish();
                        break;
                }
        }
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            Location location = locationResult.getLastLocation();
            showCurrentLocation(location);
        }
    };
}
