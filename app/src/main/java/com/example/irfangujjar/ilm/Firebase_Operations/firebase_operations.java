package com.example.irfangujjar.ilm.Firebase_Operations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.irfangujjar.ilm.Halal_sanner;
import com.example.irfangujjar.ilm.Ingredients_info.ingredients_info;
import com.example.irfangujjar.ilm.MainActivity;
import com.example.irfangujjar.ilm.Model.Halal;
import com.example.irfangujjar.ilm.Model.Haram;
import com.example.irfangujjar.ilm.Model.products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;

public class firebase_operations {

    public static void add_products_to_firebase(String barcode, String name, String ingredients, final Context context,CollectionReference products_ref){
        final android.app.AlertDialog waiting_dialog=new SpotsDialog.Builder().setContext(context).build();
        waiting_dialog.setMessage("Adding Product to the System");
        waiting_dialog.setCancelable(false);
        waiting_dialog.show();
        products product=new products(name,barcode, Arrays.asList(ingredients.split(",")));

        products_ref.document(barcode).set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    waiting_dialog.dismiss();
                    Toast.makeText(context,"Product info Added to the System",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                waiting_dialog.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void add_halal_ingredients_to_firebase(String ingredients,CollectionReference halal_ref,Context context){

        Halal h=new Halal(Arrays.asList(ingredients.split(",")));
        for (int i=0;i<h.getHalal_ingredients().size();i++) {
            halal_ref.document("fGVMhkIEu7uXPYKKaGhO").update("halal_ingredients", FieldValue.arrayUnion(h.getHalal_ingredients().get(i)));
        }
        Toast.makeText(context,"Halal Ingredients Added to the System",Toast.LENGTH_LONG).show();
    }
    public static void add_haram_ingredients_to_firebase(String ingredients,CollectionReference haram_ref,Context context){
        Haram h=new Haram(Arrays.asList(ingredients.split(",")));
        for(int i=0;i<h.getHaram_ingredients().size();i++){
            haram_ref.document("m41QMFowL4cNTNOA1Wrk").update("haram_ingredients",FieldValue.arrayUnion(h.getHaram_ingredients().get(i)));
        }
        Toast.makeText(context,"Haram Ingredients Added to the System",Toast.LENGTH_LONG).show();
    }
    public static void compare_ingredients_for_halal_haram(CollectionReference halal_ref, final CollectionReference haram_ref, final List<String> product_ingredients, final Context context){
        final android.app.AlertDialog waiting_dialog=new SpotsDialog.Builder().setContext(context).build();
        waiting_dialog.setMessage("Comparing Ingredients");
        waiting_dialog.setCancelable(false);
        waiting_dialog.show();
        final List<String> halal_ingredients=new ArrayList<>();
        final List<String> haram_ingredients=new ArrayList<>();
      halal_ref.document("fGVMhkIEu7uXPYKKaGhO").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if(task.isSuccessful()){
                DocumentSnapshot snapshot=task.getResult();
                if(snapshot.exists()){
                    Halal h=snapshot.toObject(Halal.class);
                    halal_ingredients.addAll(h.getHalal_ingredients());
                    haram_ref.document("m41QMFowL4cNTNOA1Wrk").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                waiting_dialog.dismiss();
                                DocumentSnapshot snapshot = task.getResult();
                                if (snapshot.exists()) {
                                    Haram h=snapshot.toObject(Haram.class);
                                    haram_ingredients.addAll(h.getHaram_ingredients());
                                    Intent i=new Intent(context, ingredients_info.class);
                                    i.putStringArrayListExtra("product_ingredients", (ArrayList<String>) product_ingredients);
                                    i.putStringArrayListExtra("halal_ingredients",(ArrayList<String>) halal_ingredients);
                                    i.putStringArrayListExtra("haram_ingredients", (ArrayList<String>) haram_ingredients);
                                    context.startActivity(i);
                                    ((Activity)context).finish();

                                } else {
                                    waiting_dialog.dismiss();
                                    Toast.makeText(context, "No Data Found", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            waiting_dialog.dismiss();
                            Toast.makeText(context,e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                }else{
                    waiting_dialog.dismiss();
                    Toast.makeText(context,"No Data Found", Toast.LENGTH_LONG).show();
                }
            }
          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
             waiting_dialog.dismiss();
          }
      });

    }
}
