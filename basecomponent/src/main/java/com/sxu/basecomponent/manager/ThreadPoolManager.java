package com.sxu.basecomponent.manager;

import android.os.Process;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*******************************************************************************
 * Description: 线程池
 *
 * Author: Freeman
 *
 * Date: 2017/12/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ThreadPoolManager {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_QUEUE_SIZE = 128;

    private static int coreThreadCount = CPU_COUNT + 1;
    private static int maxThreadCount = CPU_COUNT * 2 + 1;
    private static int threadPriority = Process.THREAD_PRIORITY_BACKGROUND;
    private static Executor executor;

    static {
        init(coreThreadCount, threadPriority);
    }

    public static void init(int _coreThreadCount) {
       init(_coreThreadCount, threadPriority);
    }

    public static void init(int _coreThreadCount, int _threadPriority) {
        if (executor == null) {
            if (_coreThreadCount < 0 || _coreThreadCount > Integer.MAX_VALUE) {
                throw new IllegalStateException();
            }
            coreThreadCount = _coreThreadCount;
            threadPriority = _threadPriority;
            LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue(DEFAULT_QUEUE_SIZE);
            executor = new ThreadPoolExecutor(coreThreadCount, maxThreadCount, 30L, TimeUnit.SECONDS,
                    workQueue, new DefaultThreadFactory(threadPriority));
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private AtomicInteger threadId = new AtomicInteger(1);
        private int threadPriority;
        private String threadNamePrefix;
        private ThreadGroup threadGroup;

        DefaultThreadFactory(int threadPriority) {
            this.threadPriority = threadPriority;
            this.threadNamePrefix = "thread-";
            this.threadGroup = Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(threadGroup, r, threadNamePrefix + threadId.getAndIncrement());
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            thread.setPriority(threadPriority);

            return thread;
        }
    }

    public static void executeTask(Runnable task) {
        if (executor == null) {
            throw new IllegalStateException();
        } else {
            executor.execute(task);
        }
    }
}
