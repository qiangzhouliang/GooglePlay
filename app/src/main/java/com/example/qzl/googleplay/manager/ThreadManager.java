package com.example.qzl.googleplay.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理器(单例模式)
 * Created by Qzl on 2016-08-11.
 */
public class ThreadManager {

    private static ThreadPool mThreadPool;
    private static ThreadPoolExecutor sExecutor;

    //拿到一个线程池
    public static ThreadPool getThreadPool(){
        if (mThreadPool == null){
            synchronized (ThreadManager.class){
                if (mThreadPool == null){
                    //获取cup数量
                    int cpuCount = Runtime.getRuntime().availableProcessors();
                    System.out.println("cupCount :cpu个数： "+cpuCount);
                    //int threadCount = cpuCount * 2 + 1;//线程个数（同时最多运行的线程）
                    int threadCount = 10;

                    mThreadPool = new ThreadPool(threadCount,threadCount,1L);
                }
            }
        }
        return mThreadPool;
    }

    //线程池
    public static class ThreadPool{

        private int corePoolSize;//核心线程数
        private int maximumPoolSize;//最大线程数
        private long keepAliveTime;//休息时间

        public ThreadPool(int corePoolSize,int maximumPoolSize,long keepAliveTime){
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable r){
            //线程池的执行者
            //参一：核心线程数 参二：最大线程数 参三：线程休眠时间，参四：时间单位，参五：线程队列，参六：生产线程的工厂，参七：线程异常处理策略
            if (sExecutor == null) {
                sExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
            }
            //执行一个Runnable对象，具体运行时机，线程池说了算
            sExecutor.execute(r);
        }
    }
}
