package com.ceshi.study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @ClassName: ThreadTest
 * @Author: shenyafei
 * @Date: 2021/1/21
 * @Desc
 **/
public class ThreadTest {
    public static void main(String[] args) throws Exception{
       /*
        //第一种 继承Thread 类
        ExThread exThread = new ExThread();
        exThread.start();*/

        //第2种 实现Runnable接口
       /* Thread thread = new Thread(new ImpRunnable());
        thread.start();*/

        //第3种 使用Callable和Future创建线程
        //Future实现Runnble的run()方法，里面调用Callable的call()方法
        /*CallThread callThread = new CallThread();
        FutureTask futureTask = new FutureTask(callThread);
        Thread fuThread = new Thread(futureTask);
        fuThread.start();
        //使用Future的get()方法获取返回值
        System.out.println(futureTask.get());*/

        //第4种，利用线程池创建线程
        /***
         * 目前通过Executors类静态方法中自带的有5种线程池
         * 这些线程底层都是用ThreadPoolExecutor类创建线程
         * public ThreadPoolExecutor(int corePoolSize,
         *                               int maximumPoolSize,
         *                               long keepAliveTime,
         *                               TimeUnit unit,
         *                               BlockingQueue<Runnable> workQueue) {
         *         this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
         *              Executors.defaultThreadFactory(), defaultHandler);
         *     }
         *      private static final RejectedExecutionHandler defaultHandler =
         *         new AbortPolicy();
         * 1.newFixedThreadPool 固定线程数，核心线程数=最大线程数，空闲线程存活时间为0，使用linkedBlockingQueue队列（存储int最大值长度）
         * 2.newCachedThreadPool 0核心线程数，最大线程数=int最大值，空闲线程存活时间为60秒，使用SynchronousQueue队列
         * 3.newScheduledThreadPool
         * 4.newSingleThreadExecutor
         * 5.newWorkStealingPool
         */

        ExecutorService exe1 = Executors.newFixedThreadPool(5);
        //固定数目线程池 new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>())
        //LinkedBlockingQueue：链表，容量为Int最大值

        ExecutorService exe2 = Executors.newCachedThreadPool();
        //可缓存的线程池 new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>())

        ScheduledExecutorService exe3 = Executors.newScheduledThreadPool(5);//ScheduledThreadPoolExecutor类创建线程，实际是 extends ThreadPoolExecutor
        //new ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,new DelayedWorkQueue())

        ExecutorService exe4 = Executors.newSingleThreadExecutor();//ThreadPoolExecutor
        //new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()))


        ExecutorService exe5 = Executors.newWorkStealingPool();//ForkJoinPool类创建线程

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //new ScheduledThreadPoolExecutor(1)


    }
}
