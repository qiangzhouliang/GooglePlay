package com.example.qzl.googleplay.manager;

import java.util.ArrayList;

/**
 * 下载管理
 * 未下载- 等待下载 - 正在下载 - 暂停下载 - 下载失败 - 下载成功
 * DownloadManager : 被观察者，有责任通知所有的观察者状态和进度发生变化
 * Created by Qzl on 2016-08-11.
 */
public class DownloadManager {

    public static final int STATE_UNDO = 1;//没下载
    public static final int STATE_WATIING = 2;//等待下载
    public static final int STATE_DOWNLOADING = 3;//正在下载
    public static final int STATE_PAUSE = 4;//暂停
    public static final int STATE_ERROR = 5;//下载失败
    public static final int STATE_SUCCESS = 6;//下载成功
    private static DownloadManager mDownloadManager = new DownloadManager();

    // 4 声明观察者集合
    private ArrayList<DownloadObserver> mObservers = new ArrayList<>();
    private DownloadManager() {
        
    }

    public static DownloadManager getInstance() {
        return mDownloadManager;
    }

    //2 注册观察者
    public void registerObserver(DownloadObserver observer){
        if (observer != null && !mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    //3 注销观察者
    public void unregisterObserver(DownloadObserver observer){
        if (observer != null && mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    //5 通知下载状态发生变化
    public void notifyDownloadStateChanged(){
        for (DownloadObserver observer : mObservers) {
            observer.onDownloadStateChanged();
        }
    }

    //6 通知下载进度发生变化
    public void notifyDownloadProgressChanged(){
        for (DownloadObserver observer : mObservers) {
            observer.onDownloadProgressChanged();
        }
    }
    /**
     * 1 声明观察者接口
     *
     */
    public interface DownloadObserver{

        //下载状态发生变化
        public void onDownloadStateChanged();

        //下载进度发生变化
        public void onDownloadProgressChanged();
    }
}
