package org.fzsoft.shoppingmall.utils.concurrent;

import java.util.concurrent.*;

/**
 * @author Boyce 2016年8月17日 下午4:36:28
 */
public class Executors {


    public static ExecutorService newFixedThreadPool(int workerNum, String poolName) {
        return java.util.concurrent.Executors.newFixedThreadPool(workerNum, new DreamThreadFactory(poolName));
    }

    public static ExecutorService newFixedThreadPool(int workerNum, int maxNum, String poolName) {

        return new ThreadPoolExecutor(workerNum, maxNum,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new DreamThreadFactory(poolName));
    }



    public static ExecutorService newFixedThreadPool(int workerNum, int maxNum, String poolName,  BlockingQueue workQueue) {

        return new ThreadPoolExecutor(workerNum, maxNum,
                0L, TimeUnit.MILLISECONDS,
                workQueue,
                new DreamThreadFactory(poolName));
    }


    public static ScheduledExecutorService newScheduledThreadPool(int workerNum, String poolName) {
        return java.util.concurrent.Executors.newScheduledThreadPool(workerNum, new DreamThreadFactory(poolName));
    }

    public static ExecutorService newCachedThreadPool(String poolName) {
        return java.util.concurrent.Executors.newCachedThreadPool(new DreamThreadFactory(poolName));
    }

    public static ExecutorService newSingleThreadExecutor(String poolName) {
        return java.util.concurrent.Executors.newSingleThreadExecutor(new DreamThreadFactory(poolName));
    }


}
