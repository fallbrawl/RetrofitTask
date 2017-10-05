package com.attracttest.attractgroup.retrofittask.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by nexus on 02.10.2017.
 */
public class Item extends RealmObject implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("full_name")
    private String fullName;
    private Owner owner;
    @SerializedName("private")
    private Boolean _private;
    @SerializedName("html_url")
    private String htmlUrl;

    public void setId(int id) {
        this.id = id;
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

    public void set_private(Boolean _private) {
        this._private = _private;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @SerializedName("description")
    private String description;
    @SerializedName("fork")
    private boolean fork;
    @SerializedName("url")
    private String url;

    public String getLanguage() {
        return language;
    }

    private String language;

    public Item() {
    }

    public int getId() {
        return id;
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

    public Boolean get_private() {
        return _private;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getFork() {
        return fork;
    }

    public String getUrl() {
        return url;
    }
}
