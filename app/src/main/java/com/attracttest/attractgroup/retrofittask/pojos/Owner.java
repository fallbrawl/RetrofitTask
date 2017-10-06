package com.attracttest.attractgroup.retrofittask.pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by nexus on 02.10.2017.
 */
public class Owner extends RealmObject implements Serializable {

    private String login;
    private int wowid;
    @SerializedName("avatar_url")
    private String avatarUrl;
    private String url;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Owner() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getWowid() {
        return wowid;
    }

    public void setWowid(int wowid) {
        this.wowid = wowid;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
