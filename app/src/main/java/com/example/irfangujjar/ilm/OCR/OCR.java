package com.example.irfangujjar.ilm.OCR;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.irfangujjar.ilm.Firebase_Operations.firebase_operations;
import com.example.irfangujjar.ilm.MainActivity;
import com.example.irfangujjar.ilm.R;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.example.irfangujjar.ilm.OCR.background_task_for_tesseract.lang;

public class OCR extends Activity {
    String DATA_PATH=Environment.getExternalStorageDirectory()+"/ilm_ocr/";
    public static final String lang = "eng";
    String resulturi;
    ImageView image;
    Bitmap bitmap;
    Button scan;
    CollectionReference haram_ref,halal_ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        haram_ref= FirebaseFirestore.getInstance().collection("Haram");
        halal_ref=FirebaseFirestore.getInstance().collection("Halal");
        image=findViewById(R.id.ocr_image);
        scan=findViewById(R.id.scan_btn);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(OCR.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(OCR.this, new String[]{Manifest.permission.CAMERA}, 2000);
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(OCR.this);
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(OCR.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions(OCR.this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 3000);
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                if(bitmap!=null) {
                    create_directory_in_External_storage();
                    copy_file_to_device();
                    new background_task_for_tesseract(OCR.this).execute(bitmap);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            resulturi = result.getUri().getPath();
            bitmap = BitmapFactory.decodeFile(resulturi);
            image.setImageBitmap(bitmap);
        }
    }
    public void create_directory_in_External_storage()  {
        String folder_main = "ilm_ocr";

        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            if( f.mkdirs()){
                Toast.makeText(OCR.this,"Directory Created Sucessfully",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(OCR.this,"Directory already Exist",Toast.LENGTH_LONG).show();
            //Log.e("make_dir","Directory already Exist");
        }
        File f1 = new File(Environment.getExternalStorageDirectory() + "/" + folder_main, "tessdata");
        if (!f1.exists()) {
            if( f1.mkdirs()){
                Log.e("make_sub_directory","Sub Directory created sucessfully");
            }
        }else{
            Log.e("make_sub_directory","Sub Directory already exists");
        }
    }
    public void copy_file_to_device() {
        if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
            try {
                AssetManager assetManager = getAssets();
                InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
                //GZIPInputStream gin = new GZIPInputStream(in);
                OutputStream out = new FileOutputStream(DATA_PATH
                        + "tessdata/" + lang + ".traineddata");

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                //while ((lenf = gin.read(buff)) > 0) {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                //gin.close();
                out.close();

                //Log.v(TAG, "Copied " + lang + " traineddata");
            } catch (IOException e) {
                //Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
            }

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("grant_results",String.valueOf(grantResults.length));
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(OCR.this);
                }
                return;
            }
            case 3000:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(bitmap!=null) {
                        create_directory_in_External_storage();
                        copy_file_to_device();
                        new background_task_for_tesseract(OCR.this).execute(bitmap);
                    }
                }
        }
    }

}
