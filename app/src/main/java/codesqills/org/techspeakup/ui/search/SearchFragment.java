package codesqills.org.techspeakup.ui.search;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.followersdetails.FollowersDetailsActivity;
import codesqills.org.techspeakup.ui.followersdetails.FollowersDetailsContract;
import codesqills.org.techspeakup.ui.map.MapActivity;
import codesqills.org.techspeakup.ui.map.MapContract;
import codesqills.org.techspeakup.ui.speakerprofile.SpeakerProfileActivity;

/**
 * Created by kamalshree on 11/16/2018.
 */

public class SearchFragment extends Fragment implements SearchContract.View, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private static final String TAG = "SearchFragment";

    private Bundle extras;
    private SearchContract.Presenter mPresenter;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 2.0f;

    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    List<User> userList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();

    private Marker m;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);
        PresenterInjector.injectSearchPresenter(this);
        extras = getActivity().getIntent().getExtras();
        mPresenter.start(extras);
        getLocationPermission();
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();


                            for (int i = 0; i < userList.size(); i++) {
                                m = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(userList.get(i).getLatitude(), userList.get(i).getLongitude()))
                                        .title(userList.get(i).getName())
                                        .snippet(userList.get(i).getJob())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                m.setTag(userList.get(i).getKey());
                                markerList.add(m);

                            }

                            mMap.setOnInfoWindowClickListener(SearchFragment.this);

                            for (Marker m : markerList) {
                                moveCamera(new LatLng(m.getPosition().latitude, m.getPosition().longitude),
                                        DEFAULT_ZOOM, m.getTitle(), m.getSnippet());
                            }
                            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                                @Override
                                public View getInfoWindow(Marker arg0) {
                                    return null;
                                }

                                @Override
                                public View getInfoContents(Marker marker) {

                                    LinearLayout info = new LinearLayout(getContext());
                                    info.setOrientation(LinearLayout.VERTICAL);

                                    TextView title = new TextView(getContext());
                                    title.setTextColor(getResources().getColor(R.color.colorText));
                                    title.setBackground(getResources().getDrawable(R.color.colorPrimary));
                                    title.setGravity(Gravity.CENTER);
                                    title.setTypeface(null, Typeface.BOLD);
                                    title.setText(marker.getTitle());

                                    TextView snippet = new TextView(getContext());
                                    snippet.setTextColor(getResources().getColor(R.color.colorAccent));
                                    snippet.setText(marker.getSnippet());

                                    info.addView(title);
                                    info.addView(snippet);
                                    return info;
                                }
                            });

                        } else {
                            Toast.makeText(getContext(), "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title, String job) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(job);
        mMap.addMarker(options);


    }


    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(SearchFragment.this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    @Override
    public void loadUserDetails(List<User> users) {
        userList = users;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String userKey = (String) marker.getTag();
        if (userKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            Intent ProfileIntent = new Intent(getActivity(), SpeakerProfileActivity.class);
            startActivity(ProfileIntent);
        } else {
            Intent followersDetailsIntent = new Intent(getActivity(), FollowersDetailsActivity.class);
            followersDetailsIntent.putExtra(FollowersDetailsContract.KEY_FOLLOWERS_ID, userKey);
            startActivity(followersDetailsIntent);
            this.getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.anim_nothing);
        }

    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
