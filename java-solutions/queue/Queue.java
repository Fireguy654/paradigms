package queue;

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
    // :NOTE: cnt?
    // :NOTE: a'[i] = ...
    Let: immutableExceptNth(num): n' = n - cnt && forall (1 <= i <= n && i % num != 0) a'[i - i / num] = a[i]
 */
public interface Queue {
    // Pre: elem != null
    // Post: n' = n + 1 && immutablePref(n) && a'[n'] = elem
    void enqueue(Object elem);

    // Pre: n >= 1
    // Post: immutable && R = a[1]
    Object element();

    // Pre: n >= 1
    // Post: n' = n - 1 && immutableSuf(n') && R = a[1]
    Object dequeue();


    // Pre: true
    // Post: immutable && R = n
    int size();

    // Pre: true
    // Post: immutable && R = (n == 0)
    boolean isEmpty();

    // Pre: true
    // Post: n' = 0
    void clear();

    // Pre: num >= 1
    // Post: immutable && R.n = [n / num] && forall 1 <= i <= R.n R.a[i] == a[i * num]
    Queue getNth(int num);

    // Pre: num >= 1
    // Post: immutableExceptNth(num) && R.n = n / num && forall 1 <= i <= R.n R.a[i] == a[i * num]
    Queue removeNth(int num);

    // Pre: num >= 1
    // Post: immutableExceptNth(num)
    void dropNth(int num);
}
