package com.example.irfangujjar.ilm.OCR;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.irfangujjar.ilm.Firebase_Operations.firebase_operations;
import com.example.irfangujjar.ilm.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.googlecode.leptonica.android.Skew;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class background_task_for_tesseract extends AsyncTask<Bitmap,Void,String> {
    String DATA_PATH = Environment.getExternalStorageDirectory() + "/ilm_ocr/";
    public static final String lang = "eng";
    ArrayList<Rect> rectArrayList=new ArrayList<>();
    Bitmap bit = null;
    Context context;
     ArrayList<String> recognized_text;
    private CollectionReference haram_ref,halal_ref;
    public background_task_for_tesseract(Context context) {
        this.context = context;
        this.recognized_text=new ArrayList<>();
        haram_ref= FirebaseFirestore.getInstance().collection("Haram");
        halal_ref=FirebaseFirestore.getInstance().collection("Halal");
    }

    @Override
    protected String doInBackground(Bitmap... bitmaps) {
        bit = bitmaps[0];
        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(DATA_PATH,lang);
        baseApi.setImage(bit);
        Log.e("skew_angle",String.valueOf(Skew.findSkew(baseApi.getThresholdedImage())));
        rectArrayList = baseApi.getWords().getBoxRects();
        String recognized_text = baseApi.getUTF8Text();
        baseApi.end();
        return recognized_text;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (s.length() > 0 && bit != null && rectArrayList.size() > 0) {
           recognized_text.addAll(Arrays.asList(s.split("\\r?\\n")));
        }
          for(String str:recognized_text){
              Log.e("recognized_text",str);
          }
        AlertDialog.Builder imagedilog = new AlertDialog.Builder(context);
        imagedilog.setTitle("Preview");
        //custom_imageview imageview = new custom_imageview(context, bit, rectArrayList);
        View v = LayoutInflater.from(context).inflate(R.layout.preview_layout, null);
        ImageView img = (ImageView) v.findViewById(R.id.img);
        Bitmap bitm = Bitmap.createBitmap(bit.getWidth(), bit.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitm);
        canvas.drawBitmap(bit, 0, 0, null);
        for (int i = 0; i < rectArrayList.size(); i++) {
            Paint p = new Paint();
            p.setAlpha(R.color.colorPrimary);
            p.setStyle(Paint.Style.STROKE);
            p.setColor(context.getResources().getColor(R.color.colorPrimary));
            p.setStrokeWidth(3);
            // p.setStrokeWidth();
            canvas.drawRect(rectArrayList.get(i).left, rectArrayList.get(i).top, rectArrayList.get(i).right, rectArrayList.get(i).bottom, p);
        }
        // imageview.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageBitmap(bitm);
        imagedilog.setView(v);
        imagedilog.setCancelable(false);
        imagedilog.setPositiveButton("Check", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebase_operations.compare_ingredients_for_halal_haram(halal_ref, haram_ref,recognized_text, context);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        imagedilog.show();

    }
}
