package com.mersic.day7;

public class IntCodeComputer implements Runnable {
    public static int multOp(int[] data, int ic, int parm1mode, int parm2mode) {
        int p1 = (parm1mode  == 0 ? data[data[ic + 1]] : data[ic + 1]);
        int p2 = (parm2mode == 0 ?  data[data[ic + 2]] : data[ic + 2]);
//        System.out.println("multOp val at: " + data[ic + 3] + "=" + p1 + "*" + p2);

        data[data[ic + 3]] = p2 * p1;
        return ic+4;
    }

    public static int addOp(int[] data, int ic, int parm1mode, int parm2mode) {
        int p1 = (parm1mode  == 0 ? data[data[ic + 1]] : data[ic + 1]);
        int p2 = (parm2mode == 0 ?  data[data[ic + 2]] : data[ic + 2]);
//        System.out.println("addOp val at: " + data[ic + 3] + "=" + p1 + "+" + p2);

        data[data[ic + 3]] = p2 + p1;
        return ic+4;
    }

    public static int readStoreOp(int[] data, int ic, IOHelper ioHelper) {
        int val = Integer.parseInt(ioHelper.read());
//        System.out.println("thread: " + Thread.currentThread().getName() + ". readOp into: " + data[ic + 1] + " val: " + val);
        data[data[ic + 1]] = val;
        return ic + 2;
    }

    public static int printOp(int[] data, int ic, int parm1mode, IOHelper ioHelper) {
        int p1 = (parm1mode == 0 ? data[data[ic + 1]] : data[ic + 1]);
//        System.out.println("thread: " + Thread.currentThread().getName() + ". printOp val: " + p1);
        ioHelper.write(""+p1);
        return ic + 2;
    }

    private static int jumpIfTrue(int[] data, int ic, int parm1mode, int parm2mode) {
        int p1 = (parm1mode  == 0 ? data[data[ic + 1]] : data[ic + 1]);
        int p2 = (parm2mode == 0 ?  data[data[ic + 2]] : data[ic + 2]);
//        System.out.println("jump if true Op val: " + p1 + " " + p2);

        if (p1 != 0) {
            return p2;
        }
        return ic+3;
    }
    private static int jumpIfFalse(int[] data, int ic, int parm1mode, int parm2mode) {
        int p1 = (parm1mode  == 0 ? data[data[ic + 1]] : data[ic + 1]);
        int p2 = (parm2mode == 0 ?  data[data[ic + 2]] : data[ic + 2]);
//        System.out.println("jump if false Op val: " + p1 + " " + p2);

        if (p1 == 0) {
            return p2;
        }
        return ic+3;
    }
    private static int lessThen(int[] data, int ic, int parm1mode, int parm2mode) {
        int p1 = (parm1mode  == 0 ? data[data[ic + 1]] : data[ic + 1]);
        int p2 = (parm2mode == 0 ?  data[data[ic + 2]] : data[ic + 2]);
//        System.out.println("lessThan Op val: " + p1 + " " + p2);

        if (p1 < p2 ) {
            data[data[ic + 3]] = 1;
        } else {
            data[data[ic + 3]] = 0;
        }

        return ic+4;
    }
    private static int equalsOp(int[] data, int ic, int parm1mode, int parm2mode) {
        int p1 = (parm1mode  == 0 ? data[data[ic + 1]] : data[ic + 1]);
        int p2 = (parm2mode == 0 ?  data[data[ic + 2]] : data[ic + 2]);
//        System.out.println("equals Op val: " + p1 + " " + p2);
        if (p1 == p2 ) {
            data[data[ic + 3]] = 1;
        } else {
            data[data[ic + 3]] = 0;
        }

        return ic+4;
    }

    public static boolean compute(int[] data, IOHelper readHelper, IOHelper writeHelper) {
        done: for (int i = 0; i < data.length;) {
            int opcode = data[i];
//            System.out.println("ic= " + i + " opcode=" + opcode);
            int parm3mode = opcode / 10000;
            int parm2mode = (opcode - parm3mode*10000) / 1000;
            int parm1mode = ((opcode - parm3mode*10000)-(parm2mode*1000)) / 100;
//            System.out.println("parm1: "+ parm1mode + " parm2: " + parm2mode + " parm3: "+ parm3mode);
            opcode = opcode % 100;

            switch (opcode) {
                case 1 : { i = addOp(data, i, parm1mode, parm2mode); break; }
                case 2 : { i = multOp(data, i, parm1mode, parm2mode); break; }
                case 3 : { i = readStoreOp(data, i, readHelper); break; }
                case 4 : { i = printOp(data, i, parm1mode, writeHelper); break; }
                case 5 : { i = jumpIfTrue(data, i, parm1mode, parm2mode); break; }
                case 6 : { i = jumpIfFalse(data, i, parm1mode, parm2mode); break; }
                case 7 : { i = lessThen(data, i, parm1mode, parm2mode); break; }
                case 8 : { i = equalsOp(data, i, parm1mode, parm2mode); break; }
                case 99 : break done;
                default: throw new RuntimeException("Error opcode " + opcode + " in position: " + i);
            }
        }
        return true;
    }

    int[] data;
    IOHelper readHelper;
    IOHelper writeHelper;

    IntCodeComputer(int[] data, IOHelper readHelper, IOHelper writeHelper) {
        this.data = data;
        this.readHelper = readHelper;
        this.writeHelper = writeHelper;
    }

    public void run() {
        IntCodeComputer.compute(data, readHelper, writeHelper);
    }
}
