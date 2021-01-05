package com.aloneliontech.cartnew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class google {
    @SerializedName("items")
    @Expose
    private List<Itempojopojo> items = null;

    public List<Itempojopojo> getItempojopojos() {
        return items;
    }

    public void setItempojopojos(List<Itempojopojo> items) {
        this.items = items;
    }
}
