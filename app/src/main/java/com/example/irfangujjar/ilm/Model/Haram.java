package com.example.irfangujjar.ilm.Model;

import java.util.ArrayList;
import java.util.List;

public class Haram {
    public Haram(List<String> haram_ingredients) {
        this.haram_ingredients = haram_ingredients;
    }

    public Haram() {

    }

    List<String> haram_ingredients;

    public List<String> getHaram_ingredients() {
        return haram_ingredients;
    }

    public void setHaram_ingredients(List<String> haram_ingredients) {
        this.haram_ingredients = haram_ingredients;
    }
}
