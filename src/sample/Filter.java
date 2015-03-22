package sample;

import sun.awt.windows.ThemeReader;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class Filter implements Lock {

    int n = 0;
    int[] level;
    int[] victim;

    public Filter(int n) {
        this.n = n;
        level = new int[n];
        victim = new int[n]; // use 1..n-1
        for (int i = 0; i < n; i++) {
            level[i] = 0;
            victim[i]=0;
        }
    }

    public void lock() {
        int me =    ((WorkerThread) Thread.currentThread()).getID();
        for (int i = 1; i < n; i++) {
            level[me] = i;
            victim[i] = me;

            // (∃k != me) (level[k] >= i && victim[i] == me)
            // while (ConflictExist(me,i));
                for (int k = 1; k < n; k++) {
                    while((k != me)&&(level[k] >= i && victim[i] == me)){
                        // why this is important ?!
                        System.out.print("");
                    };

                }

        }
    }



    // TODO : see why it is wrong
    private boolean ConflictExist(int me, int i) {

        for (int k = 1; k < n; k++) {
            if (k != me && level[k] >= i && victim[i] == me) {
                return true;

            }
        }
        return false;
    }

    public void unlock() {
       /* for(int i =0 ;i<level.length;i++)
        {
            System.out.println("level " + i + "=" + level[i]);
            System.out.println("victim " + i + "=" + victim[i]);
        }*/
        int me = (int) ((WorkerThread) Thread.currentThread()).getID();
        level[me] = 0;

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
