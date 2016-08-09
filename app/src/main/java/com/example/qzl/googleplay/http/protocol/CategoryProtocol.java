package com.example.qzl.googleplay.http.protocol;

import com.example.qzl.googleplay.domian.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 分类模块请求网络数据
 * Created by Qzl on 2016-08-09.
 */
public class CategoryProtocol extends BaseProtocol<ArrayList<CategoryInfo>> {
    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<CategoryInfo> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<CategoryInfo> list = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {//遍历大分类，一共两次
                JSONObject jo1 = ja.getJSONObject(i);
                //初始化标题对象
                if (jo1.has("title")) {//判断是否有title这个字段
                    CategoryInfo titleInfo = new CategoryInfo();
                    titleInfo.title = jo1.getString("title");
                    titleInfo.isTitle = true;
                    list.add(titleInfo);
                }
                //初始化分类对象
                if (jo1.has("infos")) {//小分类
                    JSONArray ja1 = jo1.getJSONArray("infos");
                    for (int j = 0; j < ja1.length(); j++) {
                        JSONObject jo2 = ja1.getJSONObject(j);
                        CategoryInfo info = new CategoryInfo();
                        info.name1 = jo2.getString("name1");
                        info.name2 = jo2.getString("name2");
                        info.name3 = jo2.getString("name3");
                        info.url1 = jo2.getString("url1");
                        info.url2 = jo2.getString("url2");
                        info.url3 = jo2.getString("url3");
                        info.isTitle = false;
                        list.add(info);
                    }
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
