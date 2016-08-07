package com.example.qzl.googleplay.http.protovol;

import com.example.qzl.googleplay.http.HttpHelper;

/**
 * 访问网络的基类
 * Created by Qzl on 2016-08-07.
 */
public abstract class BaseProtocal {

    public void getData(int index) {
        //先判断是否有缓存，有的话就加载缓存
        getDataFromServer(index);
    }

    //从网络去获取数据
    //index表示的是从哪个位置开始返回20数据，用于分页
    private void getDataFromServer(int index) {
        //http://www.itheima.com/home?index=0&name=zhangsan&age=18
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" + index + getParams());
        if (httpResult != null) {
            String result = httpResult.getString();
            System.out.println("访问结果：" + result);
        }
    }

    //获取网络链接关键字，子类必须实现
    public abstract String getKey();

    //获取网络链接参数，子类必须实现
    public abstract String getParams();

}
