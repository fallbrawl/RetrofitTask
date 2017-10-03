//package com.attracttest.attractgroup.retrofittask;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.attracttest.attractgroup.retrofittask.pojos.GitItemsList;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.concurrent.ExecutionException;
//
///**
// * Created by nexus on 30.09.2017.
// */
//public class JsonUtils {
//    private static ArrayList<GitItemsList> gitItemsListProfileList;
//
//    public static ArrayList<GitItemsList> extractFeatureFromJson(String GitProfileString) throws ExecutionException, InterruptedException {
//
//        ArrayList<GitItemsList> gitItemsListProfileList = new ArrayList<>();
//
//        // If the JSON string is empty or null, then return early.
//        if (TextUtils.isEmpty(GitProfileString)) {
//            Log.e("staty", "Json string is empty!!!");
//            return null;
//        }
//
//        try {
//            Log.e("staty", "parsing is started!");
//            JSONObject gitProfilesJsonObject = new JSONObject(GitProfileString);
//            JSONArray gitProfilesJsonArray = gitProfilesJsonObject.getJSONArray("items");
//
//            // If there are results in the profiles array
//            if (gitProfilesJsonArray.length() > 0) {
//                for (int i = 0; i < gitProfilesJsonArray.length(); i++) {
//
//                    // Extract out the owners's profile JSON
//                    JSONObject gitProfile = gitProfilesJsonArray.getJSONObject(i);
//                    JSONObject gitOwnersProfile = gitProfile.getJSONObject("owner");
//
//
//                    //Profile owner's parameters:
//                    String id = gitOwnersProfile.getString("id");
//
//                    String login = gitOwnersProfile.getString("login");
//                    String avatarUrl = gitOwnersProfile.getString("avatar_url");
//                    String url = gitOwnersProfile.getString("url");
//                    String followersUrl = gitOwnersProfile.getString("followers_url");
//                    String gistsUrl = gitOwnersProfile.getString("gists_url");
//                    String starredUrl = gitOwnersProfile.getString("starred_url");
//                    String orgUrl = gitOwnersProfile.getString("organizations_url");
//                    String reposUrl = gitOwnersProfile.getString("repos_url");
//                    Boolean isAdmin = gitOwnersProfile.getBoolean("site_admin");
//
//                    //Constructing a full string for debuggin' purposes:
////                    String logDebugString = String.format("%s \n %s \n %s \n %s \n %s ",
////                            name, image, description, reportDate, id);
////
////                    Log.e(LOG_TAG, logDebugString);
//
//                    // Create a new {@link GitItemsList} object
//                    gitItemsListProfileList.add(new GitItemsList(login, id, avatarUrl, url, followersUrl, gistsUrl,
//                            starredUrl, orgUrl, reposUrl, isAdmin));
//                }
//            }
//            Log.e("staty", "size " + gitItemsListProfileList.size());
//            return gitItemsListProfileList;
//
//        } catch (JSONException e) {
//            Log.v("staty","\n \n \n" +GitProfileString + "\n \n \n");
//            Log.e("staty", "Problem parsing the GitProfile JSON results", e);
//            return gitItemsListProfileList;
//        }
//    }
//}
