package com.mersic.day11;

import java.util.Arrays;

public class IntCodeComputer implements Runnable {

    static boolean debug = false;

    public long determineValueForWriteParm(long[] data, int ic, int parmMode, int offset) {
        switch (parmMode) {
            case 0: {
                if (debug) System.out.println("parm mode for write, positional, pos is: " + data[ic+offset]);
                return data[ic+offset];
            }
            case 2: {
                if (debug) System.out.println("parm mode for write, relateive, relbase is: " + this.relativeBase + " offset is: " + data[ic+offset] + " dest is: "+ (this.relativeBase + data[ic+offset]));
                return this.relativeBase+data[ic+offset];
            }
            default: {
                throw new RuntimeException(("Invalid parmmode for write: " + parmMode));
            }
        }
    }

    public long determineValueForParmMode(long[] data, int ic, int parmMode, int offset) {
        switch (parmMode) {
            case 0: {
                if (debug) System.out.println("parm mode positional, add: " + ((int)data[ic + offset]) + " val: " + data[(int)data[ic + offset]]);
                return data[(int)data[ic + offset]];
            }
            case 1: {
                if (debug) System.out.println("parm mode immediate, add: " + (ic + offset) + " val: " + data[ic + offset]);
                return data[ic + offset];
            }
            case 2: {
                if (debug) System.out.println("parm mode rel, base: " + this.relativeBase + " offset: " + ((int)data[ic + offset]) + " val: " + data[(int)data[ic + offset]+this.relativeBase]);
                return data[(int)data[ic + offset]+this.relativeBase];
            }
            default: throw new RuntimeException(("Invalid parmMode: " + parmMode));
        }
    }

    public int multOp(long[] data, int ic, int parm1mode, int parm2mode, int parm3mode) {
        long p1 = determineValueForParmMode(data, ic, parm1mode, 1);
        long p2 = determineValueForParmMode(data, ic, parm2mode, 2);
        int p3 = (int)determineValueForWriteParm(data, ic, parm3mode, 3);
        if (debug) System.out.println("multOp pos: " + p3 + " becomes " + p1 + "*" + p2 + " which is: " + (p1*p2));
        data[p3] = p2 * p1;
        return ic+4;
    }

    public int addOp(long[] data, int ic, int parm1mode, int parm2mode, int parm3mode) {
        long p1 = determineValueForParmMode(data, ic, parm1mode, 1);
        long p2 = determineValueForParmMode(data, ic, parm2mode, 2);
        int p3 = (int)determineValueForWriteParm(data, ic, parm3mode, 3);
        if (debug) System.out.println("addOp pos: " + p3 + " becomes " + p1 + "+" + p2 + " which is: " + (p1+p2));

        data[p3] = p2 + p1;
        return ic+4;
    }

    public int readStoreOp(long[] data, int ic, int parmMode, IOHelper ioHelper) {
        int p1 = (int)determineValueForWriteParm(data, ic, parmMode, 1);
        int val = Integer.parseInt(ioHelper.read());
        if (debug) System.out.println("thread: " + Thread.currentThread().getName() + ". readOp into pos: " + p1 + " val: " + val);
        data[p1] = val;
        return ic + 2;
    }

    public int printOp(long[] data, int ic, int parm1mode, IOHelper ioHelper) {
        long p1 = determineValueForParmMode(data, ic, parm1mode, 1);
        if (debug) System.out.println("thread: " + Thread.currentThread().getName() + ". printOp val: " + p1);
        ioHelper.write(""+p1);
        return ic + 2;
    }

    private int jumpIfTrue(long[] data, int ic, int parm1mode, int parm2mode) {
        long p1 = determineValueForParmMode(data, ic, parm1mode, 1);
        long p2 = determineValueForParmMode(data, ic, parm2mode, 2);

        if (p1 != 0) {
            if (debug) System.out.println("jump if true Op val: " + p1 + " is jumping to: " + p2);
            return (int)p2;
        }
        if (debug) System.out.println("jump if true Op val: " + p1 + " is not jumping.");
        return ic+3;
    }
    private int jumpIfFalse(long[] data, int ic, int parm1mode, int parm2mode) {
        long p1 = determineValueForParmMode(data, ic, parm1mode, 1);
        long p2 = determineValueForParmMode(data, ic, parm2mode, 2);

        if (p1 == 0) {
            if (debug) System.out.println("jump if false Op val: " + p1 + " is jumping to " + p2);
            return (int)p2;
        }
        if (debug) System.out.println("jump if false Op val: " + p1 + " is not jumping");
        return ic+3;
    }
    private int lessThen(long[] data, int ic, int parm1mode, int parm2mode, int parm3mode) {
        long p1 = determineValueForParmMode(data, ic, parm1mode, 1);
        long p2 = determineValueForParmMode(data, ic, parm2mode, 2);
        int p3 = (int)determineValueForWriteParm(data, ic, parm3mode, 3);

        if (p1 < p2 ) {
            if (debug) System.out.println("lessThan Op val: " + p1 + " is less than " + p2 + " setting pos: "+ p3 + " to 1");
            data[p3] = 1;
        } else {
            if (debug) System.out.println("lessThan Op val: " + p1 + " is not less than " + p2 + " setting pos: "+ p3 + " to 0");
            data[p3] = 0;
        }

        return ic+4;
    }
    private int equalsOp(long[] data, int ic, int parm1mode, int parm2mode, int parm3mode) {
        long p1 = determineValueForParmMode(data, ic, parm1mode, 1);
        long p2 = determineValueForParmMode(data, ic, parm2mode, 2);
        int p3 = (int)determineValueForWriteParm(data, ic, parm3mode, 3);
        if (p1 == p2 ) {
            if (debug) System.out.println("equals Op val: " + p1 + " " + p2 + " setting pos: " + p3 + " to 1");
            data[p3] = 1;
        } else {
            if (debug) System.out.println("equals Op val: " + p1 + " " + p2 + " setting pos: " + p3 + " to 0");
            data[p3] = 0;
        }

        return ic+4;
    }

    private int adjustRelativeBase(long[] data, int ic, int parm1mode) {
        long p1 = determineValueForParmMode(data, ic, parm1mode, 1);
        if (debug) System.out.println("adjustRelativeBase was: " + this.relativeBase + " offset: " + p1 + " newval: " + (this.relativeBase+p1));
        this.relativeBase += p1;
        return ic+2;
    }


    public boolean compute(long[] data, IOHelper readHelper, IOHelper writeHelper) {
        int total_instructions_processed = 0;
        done: for (int i = 0; i < data.length;) {
            total_instructions_processed++;
            int opcode = (int)data[i];
            if (debug) System.out.println("ic= " + i + " opcode=" + opcode);
            int parm3mode = opcode / 10000;
            int parm2mode = (opcode - parm3mode*10000) / 1000;
            int parm1mode = ((opcode - parm3mode*10000)-(parm2mode*1000)) / 100;
            if (debug) System.out.println("parm1: "+ parm1mode + " parm2: " + parm2mode + " parm3: "+ parm3mode);
            opcode = opcode % 100;
            if (parm3mode == 1) {
                throw new RuntimeException("Found parm3 with mode 1 (immediate)");
            }
            switch (opcode) {
                case 1 : { i = addOp(data, i, parm1mode, parm2mode, parm3mode); break; }
                case 2 : { i = multOp(data, i, parm1mode, parm2mode, parm3mode); break; }
                case 3 : { i = readStoreOp(data, i, parm1mode, readHelper); break; }
                case 4 : { i = printOp(data, i, parm1mode, writeHelper); break; }
                case 5 : { i = jumpIfTrue(data, i, parm1mode, parm2mode); break; }
                case 6 : { i = jumpIfFalse(data, i, parm1mode, parm2mode); break; }
                case 7 : { i = lessThen(data, i, parm1mode, parm2mode, parm3mode); break; }
                case 8 : { i = equalsOp(data, i, parm1mode, parm2mode, parm3mode); break; }
                case 9 : { i = adjustRelativeBase(data, i, parm1mode); break; }
                case 99 : break done;
                default: throw new RuntimeException("Error opcode " + opcode + " in position: " + i);
            }
        }
        System.out.println("total instructions processed: " + total_instructions_processed);
        return true;
    }

    long[] data;
    IOHelper readHelper;
    IOHelper writeHelper;
    int relativeBase = 0;

    IntCodeComputer(long[] originalAdata, IOHelper readHelper, IOHelper writeHelper, int additionalMemory) {
        this.data = Arrays.copyOf(originalAdata, originalAdata.length + additionalMemory);
        this.readHelper = readHelper;
        this.writeHelper = writeHelper;
    }

    public void run() {
        this.compute(data, readHelper, writeHelper);
    }
}
