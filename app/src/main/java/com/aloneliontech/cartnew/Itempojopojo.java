package com.aloneliontech.cartnew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Itempojopojo extends HashMap<String, String> {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("image")
    @Expose
    private String image;

    public Itempojopojo(String id, String itemName, String price, String qty, String image) {
        this.itemName=itemName;
        this.image=image;
        this.qty=qty;
        this.price=price;
        this.id=id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
