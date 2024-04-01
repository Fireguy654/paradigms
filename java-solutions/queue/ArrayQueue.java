package queue;

import java.util.Arrays;
import java.util.Objects;

/*
    Model:
        a[1]...a[n] – последовательность элементов
        n – размер очереди
    Inv:
        n >= 0
        forall i 1 <= i <= n: a[i] != null

    Let: immutableSuf(k): forall i 0 <= i < k a'[n' - k] == a[n - k]
    Let: immutablePref(k): forall i 1 <= i <= k a'[k] == a[k]
    Let: immutable: n == n' && forall i 1 <= i <= n a'[i] == a[i]
 */
public class ArrayQueue extends AbstractQueue {
    private Object[] a = new Object[2];
    private int head = 0;

    // Pre: true
    // Неявный конструктор
    // Post: R – корректная очередь. R.n = 0

    @Override
    protected void enqueueImpl(Object elem) {
        ensureCapacity(size() + 1);
        a[(head + size()) % a.length] = elem;
    }

    // Pre: elem != null
    // Post: n' = n + 1 && immutableSuf(n) && a'[1] = elem
    public void push(Object elem) {
        Objects.requireNonNull(elem);
        incSize();
        ensureCapacity(size());
        head = (head - 1 + a.length) % a.length;
        a[head] = elem;
    }

    private void ensureCapacity(int capacity) {
        if (a.length < capacity) {
            Object[] res = new Object[Math.max(capacity, a.length * 2)];
            System.arraycopy(a, head, res, head, Math.min(a.length - head, size()));
            System.arraycopy(a, 0, res, a.length, Math.max(0, head + size() - a.length));
            a = res;
        }
    }

    @Override
    protected Object elementImpl() {
        return a[head];
    }

    // Pre: n >= 1
    // Post: immutable && R = a[n]
    public Object peek() {
        assert size() > 0;
        return a[(head + size() - 1) % a.length];
    }

    @Override
    protected Object dequeueImpl() {
        Object ret = a[head];
        a[head] = null;
        head = (head + 1) % a.length;
        return ret;
    }

    // Pre: n >= 1
    // Post: n' = n - 1 && immutablePref(n') && R = a[n]
    public Object remove() {
        assert size() > 0;
        decSize();
        int size = size();
        Object ret = a[(head + size) % a.length];
        a[(head + size) % a.length] = null;
        return ret;
    }

    @Override
    protected void clearImpl() {
        for (int i = 0; i < size(); i++) {
            a[(head + i) % a.length] = null;
        }
        zeroSize();
    }

    @Override
    protected Queue generateQueue() {
        return new ArrayQueue();
    }

    // Pre: 0 <= ind < n
    // Post: immutable && R = a[ind + 1]
    public Object get(int ind) {
        assert 0 <= ind && ind < size();
        return a[(head + ind) % a.length];
    }

    // Pre: 0 <= ind < n && elem != null
    // Post: n = n' && immutablePref(ind) && immutableSuf(n - ind - 1) && a[ind + 1] = elem
    public void set(int ind, Object elem) {
        Objects.requireNonNull(elem);
        assert 0 <= ind && ind < size();
        a[(head + ind) % a.length] = elem;
    }
}
