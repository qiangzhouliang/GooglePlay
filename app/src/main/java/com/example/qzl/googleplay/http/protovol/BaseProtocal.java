package com.example.qzl.googleplay.http.protovol;

import com.example.qzl.googleplay.http.HttpHelper;
import com.example.qzl.googleplay.utils.IOUtils;
import com.example.qzl.googleplay.utils.StringUtils;
import com.example.qzl.googleplay.utils.UIUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 访问网络的基类
 * Created by Qzl on 2016-08-07.
 */
public abstract class BaseProtocal<T> {

    public T getData(int index) {
        //先判断是否有缓存，有的话就加载缓存
        String result = getCache(index);
        System.out.println("result11 : ="+result);
        if (StringUtils.isEmpty(result)) {
            //如果没有缓存或缓存失效，请求服务器
            result = getDataFromServer(index);
        }
        //开始解析
        if (result != null){
            T data = parseData(result);
            return data;
        }
        return null;
    }

    //从网络去获取数据
    //index表示的是从哪个位置开始返回20数据，用于分页
    private String getDataFromServer(int index) {
        //http://www.itheima.com/home?index=0&name=zhangsan&age=18
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" + index + getParams());
        System.out.println("httpResule : = "+httpResult);
        if (httpResult != null) {
            String result = httpResult.getString();
            System.out.println("访问结果：= " + result);
            //写缓存
            if (!StringUtils.isEmpty(result)){
                setCache(index,result);
            }
            return result;
        }
        return null;
    }

    //获取网络链接关键字，子类必须实现
    public abstract String getKey();

    //获取网络链接参数，子类必须实现
    public abstract String getParams();

    //写缓存
    //以url为key，以json为value
    public void setCache(int index, String json) {
        //以url为文件名，以Json为文件内容，保存在本地
        File cacheDir = UIUtils.getContext().getCacheDir();//本应用的缓存文件夹
        //生成缓存文件
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index + getParams());
        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);
            //缓存失效的截止日期
            long deadline = System.currentTimeMillis() + 30 * 60 * 1000;//半个小时有效期
            writer.write(deadline + "\n");//在第一行写入缓存时间,换行
            writer.write(json);//写入json
            writer.flush();//刷新
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }

    public String getCache(int index){
        //以url为文件名，以Json为文件内容
        File cacheDir = UIUtils.getContext().getCacheDir();//本应用的缓存文件夹
        //生成缓存文件
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index + getParams());
        //判断缓存是否存在
        if (cacheFile.exists()){
            BufferedReader bufferedReader = null;
            //判断缓存是否有效
            try {
                bufferedReader = new BufferedReader(new FileReader(cacheFile));
                //读取第一行的有效期
                String deadline = bufferedReader.readLine();
                long deadtime = Long.parseLong(deadline);
                if (System.currentTimeMillis() < deadtime){//当前时间小于截止时间
                    //说明缓存有效
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(bufferedReader);
            }
        }
        return null;
    }

    //解析数据,子类必须实现
    public abstract T parseData(String result);
}
