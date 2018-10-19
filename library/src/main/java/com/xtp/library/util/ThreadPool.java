package com.xtp.library.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p/>
 * 如果要自己New Thread()都交给这个线程来处理
 */
public class ThreadPool {

    private ExecutorService pool = Executors.newCachedThreadPool();

    private static ThreadPool instance;

    static class Builder {
        static ThreadPool instance = new ThreadPool();

        public static ThreadPool build() {
            instance.pool = Executors.newCachedThreadPool();
            return instance;
        }
    }

    public static ThreadPool getInstance() {
        if (instance == null)
            instance = Builder.instance;
        return instance;
    }

    private ThreadPool() {
    }

    public void addTask(Runnable task) {
        pool.execute(task);
    }
}
