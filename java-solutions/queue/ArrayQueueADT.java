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

    Let: immutableSuf(cur, k): forall i 0 <= i < k cur.a'[cur.n' - k] == cur.a[cur.n - k]
    Let: immutablePref(cur, k): forall i 1 <= i <= k cur.a'[k] == cur.a[k]
    Let: immutable(cur): cur.n == cur.n' && forall i 1 <= i <= cur.n cur.a'[i] == cur.a[i]
 */

public class ArrayQueueADT {
    private Object[] a = new Object[2];
    private int size = 0;
    private int head = 0;

    // Pre: true
    // Post: R – корректная очередь. R.n = 0 
    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }
   
    // Pre: elem != null && cur != null
    // Post: cur.n' = cur.n + 1 && immutablePref(cur, cur.n) && cur.a'[cur.n'] = elem
    public static void enqueue(ArrayQueueADT cur, Object elem) {
        Objects.requireNonNull(cur);
        Objects.requireNonNull(elem);
        ensureCapacity(cur, cur.size + 1);
        cur.a[(cur.head + cur.size++) % cur.a.length] = elem;
    }

    // Pre: elem != null && cur != null
    // Post: cur.n' = cur.n + 1 && immutableSuf(cur, cur.n) && cur.a'[1] = elem
    public static void push(ArrayQueueADT cur, Object elem) {
        Objects.requireNonNull(cur);
        Objects.requireNonNull(elem);
        ensureCapacity(cur, ++cur.size);
        cur.head = (cur.head - 1 + cur.a.length) % cur.a.length;
        cur.a[cur.head] = elem;
    }

    private static void ensureCapacity(ArrayQueueADT cur, int capacity) {
        if (cur.a.length < capacity) {
            Object[] res = new Object[Math.max(capacity, cur.a.length * 2)];
            System.arraycopy(cur.a, cur.head, res, cur.head, Math.min(cur.a.length - cur.head, cur.size));
            System.arraycopy(cur.a, 0, res, cur.a.length, Math.max(0, cur.head + cur.size - cur.a.length));
            cur.a = res;
        }
    }

    // Pre: cur.n >= 1 && cur != null
    // Post: immutable(cur) && R = cur.a[1]
    public static Object element(ArrayQueueADT cur) {
        Objects.requireNonNull(cur);
        assert cur.size > 0;
        return cur.a[cur.head];
    }

    // Pre: cur.n >= 1 && cur != null
    // Post: immutable(cur) && R = cur.a[cur.n]
    public static Object peek(ArrayQueueADT cur) {
        Objects.requireNonNull(cur);
        assert cur.size > 0;
        return cur.a[(cur.head + cur.size - 1) % cur.a.length];
    }

    // Pre: cur.n >= 1 && cur != null
    // Post: cur.n' = cur.n - 1 && immutableSuf(cur, cur.n') && R = a[1]
    public static Object dequeue(ArrayQueueADT cur) {
        Objects.requireNonNull(cur);
        assert cur.size > 0;
        Object ret = cur.a[cur.head];
        cur.a[cur.head] = null;
        cur.head = (cur.head + 1) % cur.a.length;
        cur.size--;
        return ret;
    }

    // Pre: cur.n >= 1 && cur != null
    // Post: cur.n' = cur.n - 1 && immutablePref(cur, cur.n') && R = cur.a[cur.n]
    public static Object remove(ArrayQueueADT cur) {
        Objects.requireNonNull(cur);
        assert cur.size > 0;
        cur.size--;
        Object ret = cur.a[(cur.head + cur.size) % cur.a.length];
        cur.a[(cur.head + cur.size) % cur.a.length] = null;
        return ret;
    }

    // Pre: cur != null
    // Post: immutable(cur) && R = cur.n
    public static int size(ArrayQueueADT cur) {
        Objects.requireNonNull(cur);
        return cur.size;
    }

    // Pre: cur != null
    // Post: immutable(cur) && R = (cur.n == 0)
    public static boolean isEmpty(ArrayQueueADT cur) {
        Objects.requireNonNull(cur);
        return cur.size == 0;
    }

    // Pre: cur != null
    // Post: cur.n' = 0
    public static void clear(ArrayQueueADT cur) {
        Objects.requireNonNull(cur);
        for (int i = 0; i < cur.size; i++) {
            cur.a[(cur.head + i) % cur.a.length] = null;
        }
        cur.size = 0;
    }

    // Pre: 0 <= ind < cur.n && cur != null
    // Post: immutable(cur) && R = cur.a[ind + 1]
    public static Object get(ArrayQueueADT cur, int ind) {
        Objects.requireNonNull(cur);
        assert 0 <= ind && ind < cur.size;
        return cur.a[(cur.head + ind) % cur.a.length];
    }

    // Pre: 0 <= ind < cur.n && elem != null && cur != null
    // Post: cur.n = cur.n' && immutablePref(cur, ind) && immutableSuf(cur, cur.n - ind - 1) && cur.a[ind + 1] = elem
    public static void set(ArrayQueueADT cur, int ind, Object elem) {
        Objects.requireNonNull(cur);
        Objects.requireNonNull(elem);
        assert 0 <= ind && ind < cur.size;
        cur.a[(cur.head + ind) % cur.a.length] = elem;
    }
}
