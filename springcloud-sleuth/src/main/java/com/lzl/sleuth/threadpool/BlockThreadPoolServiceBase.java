package com.lzl.sleuth.threadpool;

import com.alibaba.ttl.TtlRunnable;
import com.lzl.sleuth.util.SpringContextUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author eren.liao
 * @date 2021/12/1 14:45
 */
@Slf4j
public class BlockThreadPoolServiceBase implements BlockThreadPoolService {

    /** 主线程数 */
    @Setter
    private int corePoolSize = 20;

    /** 最大线程数 */
    @Setter
    private int maximumPoolSize = 150;

    /** 线程池维护线程所允许的空闲时间 */
    @Setter
    private int keepAliveTime = 60;

    /** 线程池所使用的队列的大小 */
    @Setter
    private int queueSize = 100;

    /** 是否已被初始化 */
    @Setter
    private boolean inited = false;

    /** 单例延时线程池 */
    private ScheduledExecutorService scheduledExecutorService;

    /** trace跟踪线程池 */
    private LazyTraceThreadPoolTaskExecutor lazyTraceThreadPoolTaskExecutor;

    /**
     * 初始化单例线程池
     */
    public void init() {
        if(inited) {
            return;
        }
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maximumPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueSize);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveTime);
        threadPoolTaskExecutor.setThreadNamePrefix("nssa-Thread-");
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.initialize();

        this.lazyTraceThreadPoolTaskExecutor = new LazyTraceThreadPoolTaskExecutor(SpringContextUtil.getApplicationContext(),threadPoolTaskExecutor);
        inited = true;
    }

    /**
     * 添加任务
     * @param task
     * @return
     */
    @Override
    public Future<?> addTask(Runnable task) {
        if(!inited) {
            init();
        }
        return this.lazyTraceThreadPoolTaskExecutor.submit(TtlRunnable.get(task));
    }

    @Override
    public void stop() {
        lazyTraceThreadPoolTaskExecutor.shutdown();
        if(scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }
    }

    @Override
    public synchronized void loopTask(Runnable task, long interval) {
        loopTask(task, interval, 0);
    }


    @Override
    public void loopTask(Runnable task, long interval, long delay) {
        if(scheduledExecutorService == null) {
            ThreadFactory threadFactory = new ScheduledThreadFactory("schedule-pool-%d-%s");
            scheduledExecutorService = Executors.newScheduledThreadPool(1, threadFactory);
        }
        int minInterval=100;
        if(interval < minInterval) {
            throw new IllegalArgumentException("不允许调度100ms以内的循环任务");
        }
        scheduledExecutorService.scheduleAtFixedRate(TtlRunnable.get(task), delay, interval, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runTasksUntilEnd(List<Runnable> tasks) {
        List<Future<?>> futures = new ArrayList<Future<?>>();

        for(Runnable task : tasks) {
            futures.add(addTask(task));
        }

        for(Future<?> f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                log.warn("", e);
            }
        }
    }

    /**
     * 获取单例线程池实例
     * @return
     */
    protected LazyTraceThreadPoolTaskExecutor getExecutorService() {
        return lazyTraceThreadPoolTaskExecutor;
    }

    /**
     * 动态生成一个定时任务线程池
     */
    static class ScheduledThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        ScheduledThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = String.format(namePrefix, poolNumber.getAndIncrement(), "%d");
        }
        String getThreadName() {
            return String.format(namePrefix,
                    threadNumber.getAndIncrement());
        }
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, getThreadName(), 0);
            if (!t.isDaemon()){
                t.setDaemon(true);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY){
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}

