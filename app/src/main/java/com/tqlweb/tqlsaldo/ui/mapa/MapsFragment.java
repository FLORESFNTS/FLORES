package com.tqlweb.tqlsaldo.ui.mapa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tqlweb.tqlsaldo.DashBoardMain;
import com.tqlweb.tqlsaldo.R;
import com.tqlweb.tqlsaldo.common.MyApp;
import com.tqlweb.tqlsaldo.retrofit.response.Estacion;
import com.tqlweb.tqlsaldo.ui.saldo.SaldoViewModel;

import java.util.Arrays;
import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = MapsFragment.class.getSimpleName();
    private GoogleMap mapGlobal;
    private CameraPosition cameraPosition;

    // The entry point to the Places API. INSTALA DESDE GRADLE
    private PlacesClient placesClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public boolean locationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    // Keys for storing activity state.
    // [START maps_current_place_state_keys]
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    // [END maps_current_place_state_keys]

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] likelyPlaceNames;
    private String[] likelyPlaceAddresses;
    private List[] likelyPlaceAttributions;
    private LatLng[] likelyPlaceLatLngs;
    private FusedLocationProviderClient fusedLocationClient;
    PermissionListener allpermissionListener;
    public List<Estacion> estacionList;
    SaldoViewModel saldoViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saldoViewModel = new ViewModelProvider(getActivity()).get(SaldoViewModel.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MyApp.getContext());

        if (estacionList == null) {
            saldoViewModel.BuscarEstaciones();
        } else {
            /// si ya esta llena la de estaciones ya no la ejecuta de nuevo
        }

        //REVISA los si tiene prendido el GPS
        if (isGPSProvider(getContext()) == false) {



            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Por favor activa tu localizacion")
                    .setTitle("Sin localizacion");

            builder.setPositiveButton("Vamos!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    try{

                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                    }catch (Exception e){

                    }
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
dialog.show();

        }




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.println(Log.DEBUG, "caminito", "es on create view");
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.println(Log.DEBUG, "caminito", "es on view created");
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            mapGlobal = googleMap;
            Log.println(Log.DEBUG, "caminito", "es onmapready raro");
            LatLng mex1 = new LatLng(19.394019, -99.274970);
            LatLng mex2 = new LatLng(19.309588, -99.254175);

            mapGlobal.addMarker(new MarkerOptions().position(mex1).title("PRUEBA 1"));
            mapGlobal.addMarker(new MarkerOptions().position(mex2).title("PRUEBA 2"));

            // [END map_current_place_set_info_window_adapter]
            // Prompt the user for permission.
//VTE AMO
            getLocationPermission();
            // [END_EXCLUDE]

            // Get the current location of the device and set the position of the map.
            getDeviceLocation();

            // Turn on the My Location layer and the related control on the map.
            updateLocationUI();

            //ubica y colorea estaciones desde tql web
            LoadNewEstaciones();
        }
    };


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.println(Log.DEBUG, "caminito", "es saveinstance");
        if (mapGlobal != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mapGlobal.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onResume() {

        Log.println(Log.DEBUG, "caminito", "es on resume");


        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapGlobal = googleMap;
        Log.println(Log.DEBUG, "caminito", "es onmapready");
    }


    private void LoadNewEstaciones() {
        Log.println(Log.DEBUG, "caminito", "en load new estaciones");
        //observar cuando recibimos una lista de datos
        //saldoViewModel = new ViewModelProvider(getActivity()).get(SaldoViewModel.class);
        saldoViewModel.getEstaciones().observe(getActivity(), new Observer<List<Estacion>>() {
            @Override
            public void onChanged(List<Estacion> estacions) {

                estacionList = estacions;//ya tenemos la lista , se supone

            }
        });
        try {
            LatLng cordenadas;
            for (Estacion est : estacionList) {
                try {
                    cordenadas = new LatLng(Double.parseDouble(est.getLatitud()), Double.parseDouble(est.getLongitud()));
                    mapGlobal.addMarker(new MarkerOptions()
                            .position(cordenadas)
                            //.snippet("Population: 4,137,400")
                            .title(est.getRazon()));
                } catch (Exception d) {
                    Log.println(Log.ERROR, "error", "" + d);
                }
            }
        } catch (Exception e) {
            Log.println(Log.ERROR, "error", "" + e);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.println(Log.DEBUG, "caminito", "es permisos");
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }


    private void getDeviceLocation() {
        Log.println(Log.ERROR, "caminito", "entro a GDL");
        try {
            if (locationPermissionGranted) {
                Log.println(Log.ERROR, "caminito", "granted");
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();


                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            Log.println(Log.ERROR, "caminito", "task bien");
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mapGlobal.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                            }else{
                                Log.println(Log.ERROR, "caminito", "last location es null");
                            }
                        } else {
                            Log.println(Log.ERROR, "caminito", "task mal");
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mapGlobal.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mapGlobal.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


    public void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */


        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;


        } else {
            locationPermissionGranted = false;
            checkPermissions();
        }
    }


    public void getLocationPermission(Activity context) {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */


        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;


        } else {
            locationPermissionGranted = false;
            checkPermissions(context);
        }
    }

    private void checkPermissions() {

        PermissionListener dialogOnDeniedPermisionListener = DialogOnDeniedPermissionListener.Builder.withContext(getActivity())
                .withTitle("Permiso de localizacion")
                .withMessage("Sin los permisos de localizacion no podremos ubicarte en el mapa")
                .withButtonText("Aceptar")
                .withIcon(R.mipmap.ic_launcher)
                .build();

        allpermissionListener = new CompositePermissionListener((PermissionListener) getActivity(), dialogOnDeniedPermisionListener);


        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(allpermissionListener)
                .check();


    }

    private void checkPermissions(Activity context) {

        PermissionListener dialogOnDeniedPermisionListener = DialogOnDeniedPermissionListener.Builder.withContext(context)
                .withTitle("Permiso de localizacion")
                .withMessage("Sin los permisos de localizacion no podremos ubicarte en el mapa")
                .withButtonText("Aceptar")
                .withIcon(R.mipmap.ic_launcher)
                .build();

        allpermissionListener = new CompositePermissionListener((PermissionListener) context, dialogOnDeniedPermisionListener);


        Dexter.withActivity(context).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(allpermissionListener)
                .check();


    }

    private void showCurrentPlace() {
        if (mapGlobal == null) {
            return;
        }

        if (locationPermissionGranted) {
            // Use fields to define the data types to return.
            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                    Place.Field.LAT_LNG);

            // Use the builder to create a FindCurrentPlaceRequest.
            FindCurrentPlaceRequest request =
                    FindCurrentPlaceRequest.newInstance(placeFields);

            // Get the likely places - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            @SuppressWarnings("MissingPermission") final Task<FindCurrentPlaceResponse> placeResult =
                    placesClient.findCurrentPlace(request);
            placeResult.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
                @Override
                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        FindCurrentPlaceResponse likelyPlaces = task.getResult();

                        // Set the count, handling cases where less than 5 entries are returned.
                        int count;
                        if (likelyPlaces.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
                            count = likelyPlaces.getPlaceLikelihoods().size();
                        } else {
                            count = M_MAX_ENTRIES;
                        }

                        int i = 0;
                        likelyPlaceNames = new String[count];
                        likelyPlaceAddresses = new String[count];
                        likelyPlaceAttributions = new List[count];
                        likelyPlaceLatLngs = new LatLng[count];

                        for (PlaceLikelihood placeLikelihood : likelyPlaces.getPlaceLikelihoods()) {
                            // Build a list of likely places to show the user.
                            likelyPlaceNames[i] = placeLikelihood.getPlace().getName();
                            likelyPlaceAddresses[i] = placeLikelihood.getPlace().getAddress();
                            likelyPlaceAttributions[i] = placeLikelihood.getPlace()
                                    .getAttributions();
                            likelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();

                            i++;
                            if (i > (count - 1)) {
                                break;
                            }
                        }

                        // Show a dialog offering the user the list of likely places, and add a
                        // marker at the selected place.
                        MapsFragment.this.openPlacesDialog();
                    } else {
                        Log.e(TAG, "Exception: %s", task.getException());
                    }
                }
            });
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");

            // Add a default marker, because the user hasn't selected a place.
            mapGlobal.addMarker(new MarkerOptions()
                    .title("AQUI VA ALGO")
                    .position(defaultLocation)
                    .snippet("va otra cosa"));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }
    // [END maps_current_place_show_current_place]


    private void openPlacesDialog() {
        // Ask the user to choose the place where they are now.
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // The "which" argument contains the position of the selected item.
                LatLng markerLatLng = likelyPlaceLatLngs[which];
                String markerSnippet = likelyPlaceAddresses[which];
                if (likelyPlaceAttributions[which] != null) {
                    markerSnippet = markerSnippet + "\n" + likelyPlaceAttributions[which];
                }

                // Add a marker for the selected place, with an info window
                // showing information about that place.
                mapGlobal.addMarker(new MarkerOptions()
                        .title(likelyPlaceNames[which])
                        .position(markerLatLng)
                        .snippet(markerSnippet));

                // Position the map's camera at the location of the marker.
                mapGlobal.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
                        DEFAULT_ZOOM));
            }
        };

        // Display the dialog.
        AlertDialog dialog = new AlertDialog.Builder(MyApp.getContext())
                .setTitle("PICK PLACE VA ALGO")
                .setItems(likelyPlaceNames, listener)
                .show();
    }
    // [END maps_current_place_open_places_dialog]

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    // [START maps_current_place_update_location_ui]
    private void updateLocationUI() {
        if (mapGlobal == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mapGlobal.setMyLocationEnabled(true);
                mapGlobal.getUiSettings().setMyLocationButtonEnabled(true);
/*
                lastKnownLocation = mapGlobal.getMyLocation();

                mapGlobal.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lastKnownLocation.getLatitude(),
                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));*/

            } else {
                mapGlobal.setMyLocationEnabled(false);
                mapGlobal.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }


        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    // [END maps_current_place_update_location_ui]


    public boolean isGPSProvider(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean isNetowrkProvider(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



}