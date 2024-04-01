package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    private int size = 0;

    @Override
    public void enqueue(final Object elem) {
        Objects.requireNonNull(elem);
        enqueueImpl(elem);
        size++;
    }

    protected abstract void enqueueImpl(Object elem);

    @Override
    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    @Override
    public Object dequeue() {
        assert size > 0;
        final Object ret = dequeueImpl();
        size--;
        return ret;
    }

    protected abstract Object dequeueImpl();

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        clearImpl();
        size = 0;
    }

    protected abstract void clearImpl();

    protected void incSize() {
        size++;
    }

    protected void decSize() {
        size--;
    }

    protected void zeroSize() {
        size = 0;
    }

    protected abstract Queue generateQueue();

    public Queue cycleNth(final int num, final OperWithNth oper) {
        final Queue res = generateQueue();
        final int size = this.size;
        for (int i = 1; i <= size; i++) {
            if (i % num == 0) {
                // :NOTE: simplify
                if (oper != OperWithNth.DROP) {
                    res.enqueue(element());
                }
                if (oper == OperWithNth.GET) {
                    enqueue(element());
                }
            } else {
                enqueue(element());
            }
            dequeue();
        }
        return res;
    }

    @Override
    public void dropNth(final int num) {
        cycleNth(num, OperWithNth.DROP);
    }


    @Override
    public Queue removeNth(final int num) {
        return cycleNth(num, OperWithNth.REMOVE);
    }

    @Override
    public Queue getNth(final int num) {
        return cycleNth(num, OperWithNth.GET);
    }
}
