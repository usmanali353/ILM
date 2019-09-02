package com.example.irfangujjar.ilm.Ingredients_info;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.irfangujjar.ilm.Adapters.expandable_list_adapter;
import com.example.irfangujjar.ilm.Firebase_Operations.firebase_operations;
import com.example.irfangujjar.ilm.Model.Halal;
import com.example.irfangujjar.ilm.Model.products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.irfangujjar.ilm.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ingredients_info extends AppCompatActivity {
String barcode;
List<String> product_ingredients,halal_ingredients,haram_ingredients;
ExpandableListView elv;
List<String> header_list;
HashMap<String,List<String>> child;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        elv=findViewById(R.id.expandable_list);
        header_list=new ArrayList<>();
        child=new HashMap<>();
        header_list.add("Halal Ingredients");
        header_list.add("Haram Ingredients");

        product_ingredients=getIntent().getStringArrayListExtra("product_ingredients");
        halal_ingredients=getIntent().getStringArrayListExtra("halal_ingredients");
        haram_ingredients=getIntent().getStringArrayListExtra("haram_ingredients");
        child.put(header_list.get(0),check_for_halal_ingredients());
        child.put(header_list.get(1),check_for_haram_ingredients());
        elv.setAdapter(new expandable_list_adapter(header_list,child));
        elv.expandGroup(0);
        elv.expandGroup(1);
        String[] haram= check_for_haram_ingredients().toArray(new String[check_for_haram_ingredients().size()]);
        Log.e("haram_size", String.valueOf(haram.length));
        AlertDialog.Builder builder = new AlertDialog.Builder(ingredients_info.this);
        builder.setTitle("This Product is Haram due to");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setItems(haram, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(ingredients_info.this, "Position: " + which + " Value: " + check_for_haram_ingredients().get(which), Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
       //barcode= getIntent().getStringExtra("scanned_barcode");

    }
  private List<String> check_for_halal_ingredients(){

      List<String> temp_ingredients=new ArrayList<>();
      for(String ingredient:halal_ingredients){
        if(product_ingredients.contains(ingredient.toLowerCase())){
            temp_ingredients.add(ingredient);
        }
      }
      return temp_ingredients;
    }
  private List<String> check_for_haram_ingredients(){
      List<String> temp_ingredients=new ArrayList<>();
      for(String ingredient:haram_ingredients){
          if(product_ingredients.contains(ingredient.toLowerCase())){
              temp_ingredients.add(ingredient);
          }
      }

      return temp_ingredients;
  }

}
