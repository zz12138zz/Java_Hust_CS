package ex2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个线程安全同步队列，模拟多线程环境下的生产者消费者机制
 * 一个生产者线程通过produce方法向队列里产生元素
 * 一个消费者线程通过consume方法从队列里消费元素
 *
 * @param <T> 元素类型
 */
public class SyncQueue2<T> {
    /**
     * 保存队列元素
     */
    private ArrayList<T> list = new ArrayList<>();
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    /**
     * 生产数据
     *
     * @param elements 生产出的元素列表，需要将该列表元素放入队列
     * @throws InterruptedException
     */
    public void produce(List<T> elements) throws InterruptedException {
        lock.lock();
        list.addAll(elements);
        condition.signalAll();
        Thread.sleep(5);
        System.out.print("produce: ");
        for(T e:elements){
            System.out.print(e.toString()+' ');
        }
        System.out.println();
        lock.unlock();
    }

    /**
     * 消费数据
     *
     * @return 从队列中取出的数据
     * @throws InterruptedException
     */
    public List<T> consume() throws InterruptedException {
        lock.lock();
        while(this.list.isEmpty()){
            System.out.println("Wait for produce....");
            condition.await();
        }
        List<T> copy = (List<T>) this.list.clone();
        Thread.sleep(5);
        this.list.clear();
        System.out.print("consume: ");
        for(T e:copy){
            System.out.print(e.toString()+' ');
        }
        System.out.println();
        lock.unlock();
        return copy;
    }

    public static void main(String[] args) throws InterruptedException {
        SyncQueue2<Integer> syncQueue = new SyncQueue2<>();
        Runnable produceTask = () -> {
            for(int j=0;j<10;j++) {
                try {
                    List<Integer> list = new ArrayList<>();
                    int elementsCount = (int) (Math.random() * 10) + 1;
                    for (int i = 0; i < elementsCount; i++) {
                        int r = (int) (Math.random() * 10) + 1;
                        list.add(r);
                    }
                    syncQueue.produce(list);
                    Thread.sleep((int) (Math.random() * 5) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable consumeTask = () -> {
            for(int j=0;j<10;j++) {
                try {
                    List<Integer> list = syncQueue.consume();
                    Thread.sleep((int) (Math.random() * 10) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(produceTask);
        es.execute(consumeTask);
        es.shutdown();
        while (!es.isTerminated()) {
        }
    }
}

