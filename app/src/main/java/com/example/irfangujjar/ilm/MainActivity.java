package com.example.irfangujjar.ilm;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import dmax.dialog.SpotsDialog;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.irfangujjar.ilm.Firebase_Operations.firebase_operations;
import com.example.irfangujjar.ilm.Mosque_Indicator.mosqueindicator_activity;
import com.example.irfangujjar.ilm.OCR.OCR;
import com.example.irfangujjar.ilm.Scholars.Islamicscholars_activity;
import com.example.irfangujjar.ilm.tajweed.Tajweed_activity;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    public Button btn_taj;
    public Button mosqueindicator_button;
    public Button scholar_btn;
    private Button halal_btn;
    private Button ocr;
    Toolbar toolbar;
    EditText barcode;
    SharedPreferences prefs;
    CollectionReference products_ref,halal_ref,haram_ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        products_ref=FirebaseFirestore.getInstance().collection("Products");
        halal_ref=FirebaseFirestore.getInstance().collection("Halal");
        haram_ref=FirebaseFirestore.getInstance().collection("Haram");
        halal_btn=(Button)findViewById(R.id.halal_button);
        ocr=findViewById(R.id.ocr_button);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(prefs.getString("user_type",null).equals("User")){
            toolbar.setVisibility(View.GONE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open_drawer,R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                 if(menuItem.getItemId()==R.id.add_products){
                    open_add_product_dialog();
                 }else if(menuItem.getItemId()==R.id.add_haram){
                       open_add_haram_ingredients_dialog();
                 }else if(menuItem.getItemId()==R.id.add_hilal){
                     open_add_hilal_ingredients_dialog();
                 }else if(menuItem.getItemId()==R.id.sign_out){
                     FirebaseAuth.getInstance().signOut();
                     startActivity(new Intent(MainActivity.this,Splash.class));
                     finish();
                 }
                return true;
            }
        });
        ocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, OCR.class));
            }
        });
        halal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Halal_sanner.class);
                startActivity(i);
            }
        });

        scholar_btn=(Button)findViewById(R.id.scholar_button);
        scholar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Islamicscholars_activity.class );
                startActivity(i);
            }
        });

        btn_taj = (Button) findViewById(R.id.tajweed_button);
        btn_taj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Tajweed_activity.class);
                startActivity(i);
            }
        });

        mosqueindicator_button = (Button) findViewById(R.id.mosque_button);
        mosqueindicator_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forward_to_google_maps_apologizingly();
               /* Intent i = new Intent(MainActivity.this, mosqueindicator_activity.class);
                startActivity(i);*/
            }
        });
    }
    private void forward_to_google_maps_apologizingly(){
        if(!isLocationEnabled()){
            showAlert();
        }else {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
                SmartLocation.with(MainActivity.this).location().oneFix().start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        Log.e("latitude", String.valueOf(location.getLatitude()));
                        Log.e("longitude", String.valueOf(location.getLongitude()));
                        if (location != null) {
                            Uri gmmIntentUri = Uri.parse("geo:" + location.getLatitude() + "," + location.getLongitude() + "?q=mosques");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    }
                });
        }
    }
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    SmartLocation.with(MainActivity.this).location().oneFix().start(new OnLocationUpdatedListener() {
                        @Override
                        public void onLocationUpdated(Location location) {
                            Log.e("latitude",String.valueOf(location.getLatitude()));
                            Log.e("longitude",String.valueOf(location.getLongitude()));
                            if(location!=null) {
                                Uri gmmIntentUri = Uri.parse("geo:" + location.getLatitude() + "," + location.getLongitude() + "?q=mosques");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);
                            }
                        }
                    });

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
    private void open_add_hilal_ingredients_dialog() {
        AlertDialog.Builder add_hilal_dialog=new AlertDialog.Builder(MainActivity.this);
        add_hilal_dialog.setTitle("Add Halal Ingredients");
        View v=getLayoutInflater().inflate(R.layout.add_hilal_ingredients,null);
        final EditText ingredients=v.findViewById(R.id.ingredients);
        add_hilal_dialog.setView(v);
        add_hilal_dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(ingredients.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter atleast one ingredient",Toast.LENGTH_LONG).show();
                }else{
                    firebase_operations.add_halal_ingredients_to_firebase(ingredients.getText().toString(),halal_ref,MainActivity.this);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void open_add_haram_ingredients_dialog() {
        AlertDialog.Builder add_haram_dialog=new AlertDialog.Builder(MainActivity.this);
        add_haram_dialog.setTitle("Add Haram Ingredients");
        View v=getLayoutInflater().inflate(R.layout.add_hilal_ingredients,null);
        final EditText ingredients=v.findViewById(R.id.ingredients);
        add_haram_dialog.setView(v);
        add_haram_dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(ingredients.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter atleast one ingredient",Toast.LENGTH_LONG).show();
                }else{
                    firebase_operations.add_haram_ingredients_to_firebase(ingredients.getText().toString(),haram_ref,MainActivity.this);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void open_add_product_dialog() {

        AlertDialog.Builder add_product_dialog=new AlertDialog.Builder(MainActivity.this);
        add_product_dialog.setTitle("Add Product");
        View v=getLayoutInflater().inflate(R.layout.add_product_layout,null);
        final EditText name=v.findViewById(R.id.nameBox);
        barcode=v.findViewById(R.id.barcodeBox);
        final EditText ingredients=v.findViewById(R.id.ingredients);
        Button scan=v.findViewById(R.id.scanButton);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainActivity.this).initiateScan();
            }
        });
        add_product_dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(name.getText().toString().isEmpty()||barcode.getText().toString().isEmpty()||ingredients.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please Provide all required information",Toast.LENGTH_LONG).show();
                }else{
                   firebase_operations.add_products_to_firebase(barcode.getText().toString(),name.getText().toString(),ingredients.getText().toString(),MainActivity.this,products_ref);
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setView(v).show();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Alert");
        alert.setMessage("Are you sure you want to exit");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
            }
        });
        alert.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                barcode.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private boolean isLocationEnabled() {
       LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}