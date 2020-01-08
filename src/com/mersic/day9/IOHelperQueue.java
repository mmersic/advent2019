package com.mersic.day9;

import com.mersic.day9.MySemaphore;

import java.util.ArrayList;
import java.util.List;

public class IOHelperQueue implements IOHelper {

    volatile List<String> queue = new ArrayList<>();

    MySemaphore X;

    public IOHelperQueue(MySemaphore X) {
        this.X = X;
    }

    //Only one writer, does not need to be synchronized
    public void write(String s) {
        synchronized (this) {
            queue.add(0, s);
        }
        X.V();
    }

    //Only one reader, does not need to be synchronized
    public String read() {
        X.P();
        synchronized (this) {
            return queue.remove(queue.size() - 1);
        }
    }
}
