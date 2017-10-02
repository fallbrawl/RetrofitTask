package com.attracttest.attractgroup.retrofittask;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nexus on 30.09.2017.
 */
public class GitItem {

    private String login;
    private String id;
    private String avatar_url;
    private String url;
    private String followersUrl;
    private String gistsUrl;
    private String starredUrl;
    private String orgUrl;
    private String reposUrl;
    private  boolean isSiteAdmin;

    public GitItem(String login, String id, String avatar_url,
                   String url, String followersUrl, String gistsUrl,
                   String starredUrl, String orgUrl, String reposUrl,
                   boolean isSiteAdmin) {
        this.login = login;
        this.id = id;
        this.avatar_url = avatar_url;
        this.url = url;
        this.followersUrl = followersUrl;
        this.gistsUrl = gistsUrl;
        this.starredUrl = starredUrl;
        this.orgUrl = orgUrl;
        this.reposUrl = reposUrl;
        this.isSiteAdmin = isSiteAdmin;
    }


    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getUrl() {
        return url;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public String getGistsUrl() {
        return gistsUrl;
    }

    public String getStarredUrl() {
        return starredUrl;
    }

    public String getOrgUrl() {
        return orgUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public boolean isSiteAdmin() {
        return isSiteAdmin;
    }
}
