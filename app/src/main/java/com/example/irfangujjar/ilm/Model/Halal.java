package com.example.irfangujjar.ilm.Model;
import java.util.List;

public class Halal {
    List<String> halal_ingredients;

    public List<String> getHalal_ingredients() {
        return halal_ingredients;
    }

    public void setHalal_ingredients(List<String> halal_ingredients) {
        this.halal_ingredients = halal_ingredients;
    }

    public Halal() {

    }

    public Halal(List<String> halal_ingredients) {
        this.halal_ingredients = halal_ingredients;
    }

}
