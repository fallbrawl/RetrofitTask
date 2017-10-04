package com.attracttest.attractgroup.retrofittask.pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by paul on 03.10.17.
 */

public class GitResponse implements Serializable {

    @SerializedName("total_count")
    private int total;
    private List<Item> items;

    public int getTotal() {
        return total;
    }

    public List<Item> getItems() {
        return items;
    }

    public GitResponse(int total, List<Item> items) {

        this.total = total;
        this.items = items;
    }
}
