package com.example.qzl.googleplay.http.protovol;

import com.example.qzl.googleplay.domian.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 应用网络请求
 * Created by Qzl on 2016-08-08.
 */
public class AppProtocol extends BaseProtocol<ArrayList<AppInfo>> {
    @Override
    public String getKey() {
        return "app";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<AppInfo> list = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo1 = ja.getJSONObject(i);
                AppInfo info = new AppInfo();
                info.des = jo1.getString("des");
                info.downloadUrl = jo1.getString("downloadUrl");
                info.iconUrl = jo1.getString("iconUrl");
                info.id = jo1.getString("id");
                info.name = jo1.getString("name");
                info.packageName = jo1.getString("packageName");
                info.size = jo1.getLong("size");
                info.stars = (float) jo1.getDouble("stars");
                list.add(info);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
