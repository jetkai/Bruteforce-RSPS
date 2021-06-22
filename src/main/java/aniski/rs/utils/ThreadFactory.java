package aniski.rs.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * AniskiConsole
 * Created by Kai on 23/03/2016.
 */
public class ThreadFactory implements java.util.concurrent.ThreadFactory {

    private final List<Thread> threads = new ArrayList<>();

    private Thread lastThread;

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        threads.add(t);
        t.setUncaughtExceptionHandler((t1, e) -> System.out.println("[ALERT] ONE OF THE BOT THREADS HAVE CRASHED"));
        return t;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void stopThread(Thread thread) {
        if(thread.isAlive()) {
            try {
                thread.stop();
            } catch (Exception e) {
                //
            }
        }
    }

    public void stopThreads() {
        for (Thread t : threads) {
            if (t.isAlive()) {
                try {
                    t.stop();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    public Thread getLastThread() {
        return lastThread;
    }
}
