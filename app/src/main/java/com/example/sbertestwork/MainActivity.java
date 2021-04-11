package com.example.sbertestwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sbertestwork.models.Weather;
import com.example.sbertestwork.viewmodels.MainViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    private TextView temperature;
    private TextView windSpeed;
    private static final int ACCESS_FINE_LOCATION_PERMISSION_REQUEST = 123;
    String permission = Manifest.permission.ACCESS_FINE_LOCATION;
    private boolean isAccessFineLocationGranted;
    private MainViewModel viewModel;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperature = findViewById(R.id.temperature);
        windSpeed = findViewById(R.id.wind_speed);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if(viewModel.getData() ==null){
            checkLocationPermission();
        } else{
            getData();
        }

    }


    private void checkLocationPermission(){
        if (EasyPermissions.hasPermissions(this, permission)) {
            Log.d("Test", " EasyPermissions.hasPermissions true ");
            if(viewModel.getData()==null)
                getCurrentLocation();

        } else {
            EasyPermissions.requestPermissions(this,
                    "Для получения погоды в вашем регионе приложения нужно ваше местоположение",
                    ACCESS_FINE_LOCATION_PERMISSION_REQUEST,
                    permission);
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {

                    @Override
                    public void onSuccess(Location location) {
                        Log.d("Test", " getLocation onSuccess ");
                        if (location != null) {
                            viewModel.getMyLocationWeather(location.getLongitude(), location.getLatitude());
                            getData();
                        }
                    }
                });
    }


    private void getData(){

        viewModel.getData().observe(this, new Observer<Weather>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Weather weather) {
                temperature.setText(String.valueOf(weather.getTemperature()));
                windSpeed.setText("Ветер "+ weather.getWindSpeed() +" м/с");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setQueryHint("Введите город на английском");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                viewModel.getCityWeather(query);
                getData();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(isAccessFineLocationGranted)
            getCurrentLocation();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {}

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Для просмотра погоды в вашем регионе приложению необходимо ваше местоположение!", Toast.LENGTH_SHORT);
        toast.show();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}

    @Override
    public void onRationaleAccepted(int requestCode) {
        Log.d("Test", " onRationaleAccepted "+requestCode);
        if(requestCode==ACCESS_FINE_LOCATION_PERMISSION_REQUEST)
            isAccessFineLocationGranted=true;
    }

    @Override
    public void onRationaleDenied(int requestCode) {}
}