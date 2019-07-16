package com.example.irfangujjar.ilm.Model;

import java.util.List;

public class products {
    public products(String name, String barcode, List<String> ingredients) {
        this.name = name;
        this.barcode = barcode;
        this.ingredients = ingredients;
    }

    String name,barcode;
    List<String> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public products(){

  }





}
