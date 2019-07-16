package com.example.irfangujjar.ilm;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.irfangujjar.ilm.Ingredients_info.ingredients_info;
import com.example.irfangujjar.ilm.Model.Halal;
import com.example.irfangujjar.ilm.Model.Haram;
import com.example.irfangujjar.ilm.Model.products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class Halal_sanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    CollectionReference halal_ref;
    CollectionReference haram_ref;
   CollectionReference products_ref;
   List<String> product_ingredients,halal_ingredients,haram_ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        halal_ref=FirebaseFirestore.getInstance().collection("Halal");
        haram_ref=FirebaseFirestore.getInstance().collection("Haram");
        products_ref=FirebaseFirestore.getInstance().collection("Products");
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getApplicationContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        final String myResult = result.getText();
        get_product_info_from_firebase(myResult);

    }
    private void  get_product_info_from_firebase(String barcode){
        final android.app.AlertDialog dialog=new SpotsDialog.Builder().setContext(Halal_sanner.this).build();
        dialog.setMessage("Getting Product Info...");
        dialog.show();
        DocumentReference ref=products_ref.document(barcode);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot=task.getResult();
                if(snapshot.exists()){
                    products p=snapshot.toObject(products.class);
                    product_ingredients=(p.getIngredients());
                    DocumentReference halal_doc=halal_ref.document("fGVMhkIEu7uXPYKKaGhO");
                    halal_doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();
                                if(snapshot.exists()) {
                                    Halal h = snapshot.toObject(Halal.class);
                                    halal_ingredients = h.getHalal_ingredients();

                                    DocumentReference haram_doc=haram_ref.document("m41QMFowL4cNTNOA1Wrk");
                                    haram_doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                dialog.dismiss();
                                                DocumentSnapshot snapshot=task.getResult();
                                                if(snapshot.exists()){
                                                    Haram h=snapshot.toObject(Haram.class);
                                                    haram_ingredients=h.getHaram_ingredients();

                                                    Intent i=new Intent(Halal_sanner.this, ingredients_info.class);
                                    i.putStringArrayListExtra("product_ingredients", (ArrayList<String>) product_ingredients);
                                    i.putStringArrayListExtra("halal_ingredients",(ArrayList<String>) halal_ingredients);
                                    i.putStringArrayListExtra("haram_ingredients", (ArrayList<String>) haram_ingredients);
                                    startActivity(i);
                                    finish();
                                                }
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.dismiss();
                                            Toast.makeText(Halal_sanner.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }else{
                                    dialog.dismiss();
                                    Toast.makeText(Halal_sanner.this,"No Data Found",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(Halal_sanner.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    dialog.dismiss();
                    Toast.makeText(Halal_sanner.this,"No Data Found",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(Halal_sanner.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


}
