package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head = null;
    private Node tail = null;

    @Override
    protected void enqueueImpl(final Object elem) {
        final Node newNode = new Node(elem);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }

    @Override
    protected Object elementImpl() {
        return head.val;
    }

    @Override
    protected Object dequeueImpl() {
        final Object ret = head.val;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return ret;
    }

    @Override
    protected void clearImpl() {
        head = null;
        tail = null;
    }

    @Override
    protected Queue generateQueue() {
        return new LinkedQueue();
    }

    private static class Node {
        // :NOTE: access
        public final Object val;
        public Node next;

        public Node(final Object value, final Node next) {
            this.val = value;
            this.next = next;
        }

        public Node(final Object value) {
            this(value, null);
        }
    }
}
