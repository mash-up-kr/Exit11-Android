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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import io.mashup.exit11.R;
import io.mashup.exit11.common.Const;
import io.mashup.exit11.data.model.Party;
import io.mashup.exit11.presenter.map.MapPresenter;
import io.mashup.exit11.presenter.map.MapView;
import io.mashup.exit11.ui.activity.MainActivity;
import io.mashup.exit11.ui.view.PartyInfoView;
import io.mashup.exit11.util.LocationChoiceObserver;
import io.mashup.exit11.util.PxUtil;
import io.mashup.exit11.util.SharedPreferenceUtil;

import static io.mashup.exit11.data.model.Menu.CHICKEN;
import static io.mashup.exit11.data.model.Menu.CHINESE;
import static io.mashup.exit11.data.model.Menu.ETC;
import static io.mashup.exit11.data.model.Menu.HAMBURGER;
import static io.mashup.exit11.data.model.Menu.JAPANESE;
import static io.mashup.exit11.data.model.Menu.KOREAN;
import static io.mashup.exit11.data.model.Menu.PIZZA;
import static io.mashup.exit11.data.model.Menu.PORK_FEET;
import static io.mashup.exit11.data.model.Menu.SNACK_BAR;

/**
 * Created by jonghunlee on 2018. 1. 17..
 */

public class MapFragment extends SupportMapFragment implements MapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraIdleListener {

    private static final String TAG = MapFragment.class.getSimpleName();
    private static final int REQUEST_CHECK_LOCATION_SETTING = 2001;

    @Inject
    SharedPreferenceUtil sharedPreferenceUtil;

    @Inject
    MapPresenter mapPresenter;

    @BindView(R.id.button_choice_location)
    ImageButton btnChoiceLocation;

    @BindView(R.id.view_party_info)
    PartyInfoView partyInfoView;

    private GoogleMap googleMap;

    private List<Marker> markers = new ArrayList<>();

    private FusedLocationProviderClient fusedLocationClient;

    private boolean isChoiceLocation;

    @Override
    public void onCreate(Bundle bundle) {
        AndroidSupportInjection.inject(this);

        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View mapView = super.onCreateView(layoutInflater, viewGroup, bundle);
        FrameLayout layout = new FrameLayout(getActivity());
        layout.addView(mapView,
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

        ImageButton choiceLocationButton = new ImageButton(getContext());
        choiceLocationButton.setId(R.id.button_choice_location);
        choiceLocationButton.setBackground(ContextCompat.getDrawable(getActivity(),
                R.drawable.enrollment_mapmaker));

        FrameLayout.LayoutParams choiceLocationButtonParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        choiceLocationButtonParams.gravity = Gravity.CENTER;

        layout.addView(choiceLocationButton, choiceLocationButtonParams);

        PartyInfoView partyInfoView = new PartyInfoView(getContext());
        partyInfoView.setId(R.id.view_party_info);
        FrameLayout.LayoutParams partyInfoViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        choiceLocationButtonParams.gravity = Gravity.CENTER;

        layout.addView(partyInfoView, partyInfoViewParams);

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        btnChoiceLocation.setVisibility(View.GONE);
        partyInfoView.setVisibility(View.GONE);

        mapPresenter.attachView(this);

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

        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnCameraIdleListener(this);

        // 서울 시청
        float lat = sharedPreferenceUtil.getFloat(Const.LATITUDE, 37.566692f);
        float lng = sharedPreferenceUtil.getFloat(Const.LONGITUDE, 126.978416f);

        LatLng latLng = new LatLng(lat, lng);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f));

        //        getParties(latLng);
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

        saveLocation(location.getLatitude(), location.getLongitude());

        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void saveLocation(double latitude, double longitude) {
        sharedPreferenceUtil.setFloat(Const.LATITUDE, (float) latitude);
        sharedPreferenceUtil.setFloat(Const.LONGITUDE, (float) longitude);
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
        if (isChoiceLocation) {
            return;
        }

        LatLng latLng = googleMap.getCameraPosition().target;
        Log.d(TAG, "onCameraIdle#latLng : " + latLng.toString());

        saveLocation(latLng.latitude, latLng.longitude);

        getParties(latLng);
    }

    private void getParties(LatLng latLng) {
        mapPresenter.getParties(latLng.latitude, latLng.longitude);
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        Log.e(TAG, "showErrorMessage#" + throwable.getMessage(), throwable);
    }

    @Override
    public void resultParties(List<Party> parties) {
        for (Party party : parties) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(party.getLatitude(), party.getLongitude()));

            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmap(party.getFoodId())));
            Marker marker = googleMap.addMarker(markerOptions);
            marker.setTag(party);

            markers.add(marker);
        }
    }

    private Bitmap getMarkerBitmap(int foodId) {
        Drawable drawable = null;
        switch (foodId) {
            case PIZZA:
                drawable = getResources().getDrawable(R.drawable.menu_pizza);
                break;
            case HAMBURGER:
                drawable = getResources().getDrawable(R.drawable.menu_hamburger);
                break;
            case CHICKEN:
                drawable = getResources().getDrawable(R.drawable.menu_chicken);
                break;
            case CHINESE:
                drawable = getResources().getDrawable(R.drawable.menu_chinese);
                break;
            case KOREAN:
                drawable = getResources().getDrawable(R.drawable.menu_korean);
                break;
            case SNACK_BAR:
                drawable = getResources().getDrawable(R.drawable.menu_snackbar);
                break;
            case PORK_FEET:
                drawable = getResources().getDrawable(R.drawable.menu_porkfeet);
                break;
            case JAPANESE:
                drawable = getResources().getDrawable(R.drawable.menu_japanese);
                break;
            case ETC:
                drawable = getResources().getDrawable(R.drawable.menu_etc);
                break;
        }

        if (drawable == null) {
            return null;
        }

        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();

        int size = (int) PxUtil.convertDpToPx(50f);

        return Bitmap.createScaledBitmap(bitmap, size, size, false);
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

    public void setLocationChoiceMode() {
        showAllMarker(false);

        isChoiceLocation = true;
        btnChoiceLocation.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_choice_location)
    void OnClickChoiceLocation() {
        btnChoiceLocation.setVisibility(View.GONE);

        LatLng latLng = googleMap.getCameraPosition().target;
        String address = getLocationAddress(latLng.latitude, latLng.longitude);

        LocationChoiceObserver.getInstance().send(address);

        isChoiceLocation = false;

        showAllMarker(true);
    }

    private void showAllMarker(boolean isShow) {
        for (Marker marker : markers) {
            marker.setVisible(isShow);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Party party = (Party) marker.getTag();

        Log.d(TAG, "onMarkerClick#party id : " + party.getPartyId());

        partyInfoView.setVisibility(View.VISIBLE);
        partyInfoView.setPartyInfo(party);

        ((MainActivity) getActivity()).showAddPartyView(false);

        return true;       // 마커의 기본 동작 방지
    }

    private String getLocationAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        String choiceAddress = "";
        try {
            Address address = geocoder.getFromLocation(latitude, longitude, 1).get(0);
            Log.d(TAG, "OnClickChoiceLocation#address : " + address.toString());
            choiceAddress = address.getAddressLine(0);

            //                String s1 = address.get(0).getCountryName();        // 국가명
            //                String s2 = address.get(0).getAdminArea();          // 시
            //                String s3 = address.get(0).getLocality();           // 구 메인, "성남시 중원구" 인 경우 "성남시"가 들어감
            //                String s4 = address.get(0).getSubLocality();        // 구 서브데이터  "성남시 중원구" 인 경우 "중원구"가 들어감
            //                String s5 = address.get(0).getThoroughfare();             // 동
            //                String s6 = address.get(0).getSubThoroughfare());         // 번지
            //                String s7 = address.get(0).getFeatureName()());          // 번지
            //                String s8 = address.get(0).getAddressLine(0).toString(); // 국가명 시 군 구 동 번지
        }
        catch (IOException e) {
            Log.e(TAG, "OnClickChoiceLocation#getFromLocation", e);
        }

        return choiceAddress;
    }
}
