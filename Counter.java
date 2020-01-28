public class Counter {

    private int mTotal ;

    public Counter() {
        mTotal = 0;
    }

    public synchronized void increment(int val) {
        mTotal+=val;
    }

    public synchronized int getVal() {
        return mTotal;
    }
}
