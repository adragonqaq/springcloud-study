package com.lzl.sleuth.threadpool;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author eren.liao
 * @date 2021/12/1 14:44
 */
public interface  BlockThreadPoolService {

    /**
     * 向线程池中添加任务
     * @param task
     * @return 任务(必须实现Runnable接口)
     */
    Future<?> addTask(Runnable task);

    /**
     * 异步执行一批任务，直到任务执行完成
     * @param task
     */
    void runTasksUntilEnd(List<Runnable> task);

    /**
     * 向线程池中添加循环运行的任务
     * @param task 任务(必须实现Runnable接口)
     * @param interval 时间间隔，单位毫秒
     */
    void loopTask(Runnable task, long interval);

    /**
     * 向线程池中添加循环运行的任务
     * @param task 任务(必须实现Runnable接口)
     * @param interval 时间间隔，单位毫秒
     * @param delay 延迟执行的时间，单位毫秒，表示任务在delay ms后开始被定时调度
     */
    void loopTask(Runnable task, long interval, long delay);

    /**
     * 停止线程池
     */
    public void stop();

}
