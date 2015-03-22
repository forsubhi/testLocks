package sample;

import java.util.concurrent.locks.Lock;

public class WorkerThread extends Thread {
    public static String[] sharedArray = new String[10];
    public String[] localArray = new String[10];
    Lock lock ;
    int ID ;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    WorkerThread(Lock lock)
    {
        this.lock = lock;
    }

    @Override
    public void run() {
        super.run();


        lock.lock();
        System.out.println("Thread "+Thread.currentThread().getId()+ " entered the cs");
        for (int i = 0; i < 3 - 1; i++) {
            sharedArray[i] = "M";
            i++;

            sharedArray[i] = "R";

        }


        System.out.println("Thread "+Thread.currentThread().getId()+ " left the cs");
        lock.unlock();


        for (int i = 0; i < 3 - 1; i++) {
            localArray[i] = "Love";

        }
    }
}
