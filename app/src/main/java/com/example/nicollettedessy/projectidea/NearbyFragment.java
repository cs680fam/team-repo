package com.example.nicollettedessy.projectidea;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nicollettedessy.projectidea.data.entities.GooglePlacesSearchResponse;
import com.example.nicollettedessy.projectidea.data.repositories.GooglePlacesSearchRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

public class NearbyFragment extends Fragment implements OnMapReadyCallback {

    private static final String FOOD_LOCATIONS = "FOOD_LOCATIONS";
    private static final String GOOGLE_NEARBY_PLACES_SEARCH_TYPE = "GOOGLE_NEARBY_PLACES_SEARCH_TYPES";
    private static final String[] ACCESS_LOCATION_PERMISSIONS = { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION };
    private static final int REQUEST_ACCESS_LOCATION = 0;

    private GoogleMap map;
    private String foodLocation;
    private String googleNearbyPlacesSearchType;
    private FusedLocationProviderClient mFusedLocationClient;

    private GooglePlacesSearchRepository googlePlacesSearchRepository = new GooglePlacesSearchRepository();

    private double latitude;
    private double longitude;

    public NearbyFragment() {
    }

    public static NearbyFragment newInstance(String foodLocation, String googleNearbyPlacesSearchType) {
        NearbyFragment fragment = new NearbyFragment();
        Bundle args = new Bundle();
        args.putString(FOOD_LOCATIONS, foodLocation);
        args.putString(GOOGLE_NEARBY_PLACES_SEARCH_TYPE, googleNearbyPlacesSearchType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        tryGetLastLocation();
    }

    private OnCompleteListener<Location> getOnCompleteListener()
    {
        return new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();

                    Log.d("[Location Change]", String.format("Latitude: %f, Longitude: %f", location.getLatitude(), location.getLongitude()));

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    googlePlacesSearchRepository.GetNearByEntitiesBy(getContext(), googleNearbyPlacesSearchType, latitude, longitude, 10000, new Response.Listener<GooglePlacesSearchResponse>() {
                        @Override
                        public void onResponse(GooglePlacesSearchResponse response) {
                            for (GooglePlacesSearchResponse.GooglePlacesSearchResponseResult result : response.results) {
                                map.addMarker(new MarkerOptions().position(new LatLng(result.geometry.location.lat, result.geometry.location.lng)).title(result.name));
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("[Error]", error.getMessage());
                        }
                    });

                    LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                    map.addMarker(new MarkerOptions().position(current).title("Me").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 11));
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            foodLocation = getArguments().getString(FOOD_LOCATIONS);
            googleNearbyPlacesSearchType = getArguments().getString(GOOGLE_NEARBY_PLACES_SEARCH_TYPE);
        }

        View view = inflater.inflate(R.layout.fragment_nearby, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.nearby_map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_ACCESS_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tryGetLastLocation();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void tryGetLastLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), ACCESS_LOCATION_PERMISSIONS,
                    REQUEST_ACCESS_LOCATION);
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(getActivity(), getOnCompleteListener());
    }
}
