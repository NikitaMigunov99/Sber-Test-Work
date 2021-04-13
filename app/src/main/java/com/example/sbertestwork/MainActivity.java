package com.example.sbertestwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sbertestwork.data.Repository;
import com.example.sbertestwork.data.local.DatabaseSource;
import com.example.sbertestwork.data.remote.WeatherRemoteSource;
import com.example.sbertestwork.models.Weather;
import com.example.sbertestwork.viewmodels.MainViewModel;
import com.example.sbertestwork.viewmodels.MainViewModelFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import javax.inject.Inject;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    private TextView temperature;
    private TextView windSpeed;
    private TextView city;
    private Button button;
    private static final int ACCESS_FINE_LOCATION_PERMISSION_REQUEST = 123;
    private String permission = Manifest.permission.ACCESS_FINE_LOCATION;
    private MainViewModel viewModel;
    private FusedLocationProviderClient fusedLocationClient;
    @Inject
    MainViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MainApplication)getApplication()).getAppComponent().inject(this);

        city= findViewById(R.id.city);
        temperature = findViewById(R.id.temperature);
        windSpeed = findViewById(R.id.wind_speed);
        button= findViewById(R.id.local_weather);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        if(viewModel.getData() ==null){
            checkLocationPermission();
        } else{
            getData();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", " get my location temperature ");
                //checkLocationPermission();
                if(viewModel.isAccessFineLocationGranted()) {
                    getCurrentLocationWeather();
                }
            }
        });
    }


    private void checkLocationPermission(){
        if (EasyPermissions.hasPermissions(this, permission)) {
            Log.d("Test", " EasyPermissions.hasPermissions true ");
            viewModel.setAccessFineLocationGranted(true);
            if(viewModel.getData()==null)
                getCurrentLocationWeather();

        } else {
            EasyPermissions.requestPermissions(this,
                    getResources().getString(R.string.location_permission),
                    ACCESS_FINE_LOCATION_PERMISSION_REQUEST,
                    permission);
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocationWeather() {
        if(isInternetOn()){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {

                    @Override
                    public void onSuccess(Location location) {
                        Log.d("Test", " getLocation onSuccess ");
                        if (location != null) {
                            viewModel.getMyLocationWeather(location.getLongitude(), location.getLatitude());
                            getData();
                            saveDataIntoDataBase();
                        }
                    }
                });
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.check_internet),
                    Toast.LENGTH_SHORT);
            toast.show();
            viewModel.getLocalData(); //get last record from db
            getData();
        }
    }


    private void saveDataIntoDataBase(){
        viewModel.getData().observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                viewModel.saveIntoDataBase(weather);
            }
        });
    }


    private void getData(){
        viewModel.getData().observe(this, new Observer<Weather>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Weather weather) {

                String temperatureValue= String.valueOf(weather.getTemperature());
                if(weather.getTemperature()>0)
                    temperature.setText(getResources().getString(R.string.temperature)+" +"
                            +temperatureValue);

                else if(weather.getTemperature()<0){
                    temperature.setText(getResources().getString(R.string.temperature)+" -"
                            +temperatureValue);
                }else {
                    temperature.setText(getResources().getString(R.string.temperature)+" "
                            +temperatureValue);
                }

                windSpeed.setText(getResources().getString(R.string.wind)+" "+
                        weather.getWindSpeed() +" "+
                        getResources().getString(R.string.speed));
                city.setText(weather.getCity());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setQueryHint(getResources().getString(R.string.input_city));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(isInternetOn()) {
                    viewModel.getCityWeather(query);
                    getData();
                    saveDataIntoDataBase();
                }else {Toast toast = Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.check_internet),
                        Toast.LENGTH_SHORT);
                toast.show();
                }

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
        if(viewModel.isAccessFineLocationGranted())
            getCurrentLocationWeather();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {}

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast toast = Toast.makeText(getApplicationContext(),
                getResources().getString(R.string.location_permission),
                Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}

    @Override
    public void onRationaleAccepted(int requestCode) {
        Log.d("Test", " onRationaleAccepted "+requestCode);
        if(requestCode==ACCESS_FINE_LOCATION_PERMISSION_REQUEST)
           viewModel.setAccessFineLocationGranted(true);
    }

    @Override
    public void onRationaleDenied(int requestCode) {}

    private boolean isInternetOn() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}