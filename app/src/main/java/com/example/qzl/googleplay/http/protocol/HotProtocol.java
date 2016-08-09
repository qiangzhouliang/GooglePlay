package com.example.qzl.googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * 排行加载数据
 * Created by Qzl on 2016-08-08.
 */
public class HotProtocol extends BaseProtocol<ArrayList<String>> {
    @Override
    public String getKey() {
        return "hot";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<String> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                String keyword = ja.getString(i);
                list.add(keyword);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
