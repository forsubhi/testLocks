package sample;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class Bakery   implements Lock {

    final int N; // number of processes using this object

    final boolean[] choosing;
    final int[] number;
    final boolean[] inCS;

    public Bakery(int n) {
        this.N = n;
        choosing = new boolean[N];
        number = new int[N];
        inCS = new boolean[N];
        for (int i = 0; i < N; ++i) {
            choosing[i]     = false;
            number[i] = 0;
            inCS[i] = false;
        }
    }




    @Override
    public void lock() {
        // step1

        int id = ((WorkerThread) Thread.currentThread()).getID();
        choosing[id] = true;
        for (int j = 0; j < N; ++j) {
            if (number[j] > number[id]) number[id] = number[j];
        }
       // randomSleep(500);
        number[id] = number[id] + 1;
        choosing[id]= false;

        // step2
        for (int j = 0; j < N; ++j) {
            while (choosing[j]) {System.out.print("");}; // process j in doorway
            while ((number[j] != 0) &&
                    ((number[j] < number[id]) ||
                            ((number[j] == number[id]) && j < id)))
            {
                System.out.print(""); // busy waiting
            }
        }

        // enter CS
        inCS[id] = true;
    }



    @Override
    public void unlock() {
        int id = ((WorkerThread) Thread.currentThread()).getID();
        for (int i = 0; i < N; ++i) {
            if (i != id && inCS[i]) {
                System.out.println("DATA RACING DETECTED!");
                throw new RuntimeException("DATA RACING DETECTED");
            }
        }

        inCS[id] = false;
        number[id] = 0;
    }





    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
}
