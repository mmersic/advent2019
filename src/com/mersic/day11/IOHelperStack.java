package com.mersic.day11;

import java.util.Stack;

public class IOHelperStack implements IOHelper {
    volatile Stack<String> stack = new Stack<>();

    MySemaphore X;

    public IOHelperStack(MySemaphore X) {
        this.X = X;
    }

    //Only one writer, does not need to be synchronized
    public void write(String s) {
        synchronized (this) {
            stack.push(s);
        }
        X.V();
    }

    //Only one reader, does not need to be synchronized
    public String read() {
        X.P();
        synchronized (this) {
            return stack.pop();
        }
    }
}
