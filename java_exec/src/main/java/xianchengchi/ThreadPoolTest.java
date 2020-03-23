package xianchengchi; /**
 * Created on 2020-03-19
 *
 * @author :hao.li
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
//    172.24.50.132

    public static void fixedThreadPoolTest() {
        int nThreads = 3; // 线程池的数量规定为3
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(nThreads);
        for(int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {// 每3秒输出3个
                public void run() {
                    try {
                        System.out.println(index);
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(3000);
                    }catch(InterruptedException  e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void cachedThreadPoolTest() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 1000); // 确保每个线程都能执行完任务
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable(){
                public void run() {
                    System.out.println(index);
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
    }

    public static void scheduledThreadPool() {
        int corePoolSize = 3; // 线程池大小为3
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(corePoolSize);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable(){ // 执行周期性的任务
            public void run() {
                System.out.println("delay 3 seconds");
            }
        }, 1, 3,TimeUnit.SECONDS); // 延迟1s后，每3s执行一次
    }

    public static void singleThreadExecutor() {
        // 创建唯一的线程
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable(){ // 按顺序执行
                public void run() {
                    try{
                        System.out.println(index);
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(2000);
                    }catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        fixedThreadPoolTest();
//        cachedThreadPoolTest();
//        scheduledThreadPool();
        singleThreadExecutor();
    }

}

