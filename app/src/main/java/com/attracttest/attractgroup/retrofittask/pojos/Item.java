package com.attracttest.attractgroup.retrofittask.pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nexus on 02.10.2017.
 */
public class Item extends RealmObject implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    private int _id;
    @SerializedName("name")
    private String name;
    @SerializedName("full_name")
    private String fullName;
    private Owner owner;
    @SerializedName("private")
    private boolean _private;
    @SerializedName("html_url")
    private String htmlUrl;

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void set_private(boolean _private) {
        this._private = _private;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setForky(boolean forky) {
        this.forky = forky;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @SerializedName("description")
    private String description;
    @SerializedName("forky")
    private boolean forky;
    @SerializedName("url")
    private String url;

    public String getLanguage() {
        return language;
    }

    private String language;

    public Item() {
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public Owner getOwner() {
        return owner;
    }

    public boolean get_private() {
        return _private;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public boolean getForky() {
        return forky;
    }

    public String getUrl() {
        return url;
    }
}
