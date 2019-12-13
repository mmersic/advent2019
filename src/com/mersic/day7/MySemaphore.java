package com.mersic.day7;

public class MySemaphore {
    volatile int counter = 0;

    public MySemaphore() {
    }

    //signal data is available
    synchronized void V() {
        synchronized (this) {
            try {
                counter++;
                this.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //wait until data is available
    synchronized void P() {
        synchronized (this) {
            try {
                while (counter == 0) {
                    this.wait();
                }
                counter--;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
