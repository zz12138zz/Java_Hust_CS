package ex3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReusableThread extends Thread{
    private Runnable runTask = null;  //保存接受的线程任务
    private Lock lock=new ReentrantLock();
    private Condition newTask = lock.newCondition();
    private Condition runningTaskFinish= lock.newCondition();

    //只定义不带参数的构造函数
    public ReusableThread(){
        super();
    }

    /**
     * 覆盖Thread类的run方法
     */
    @Override
    public void run() {
        while(true){
            lock.lock();
            try {
            while(runTask==null){
                    newTask.await();
            }
                runTask.run();
                runTask=null;
                runningTaskFinish.signalAll();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    /**
     * 提交新的任务
     * @param task 要提交的任务
     */
    public void submit(Runnable task){
        try {
            lock.lock();
            while (runTask != null) {
                runningTaskFinish.await();
            }
            this.runTask = task;
            newTask.signalAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread " + Thread.currentThread().getId() +
                        ": is running " + toString());
                try { Thread.sleep(200); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            @Override
            public String toString() {
                return "task1";
            }
        };

        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread " + Thread.currentThread().getId() +
                        " is running " + toString());
                try { Thread.sleep(100); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            @Override
            public String toString() {
                return "task2";
            }
        };

        ReusableThread t =new ReusableThread();
        t.start();
        for(int i = 0; i < 5; i++){
            t.submit(task1);
            t.submit(task2);
        }
    }
}

