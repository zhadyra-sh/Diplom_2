package models;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Order {
    private final ArrayList<String> ingredients;

    public Order(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String toJson() {
        return new GsonBuilder().serializeNulls().create().toJson(this);
    }
}
