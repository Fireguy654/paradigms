package queue;

public class MyQueueTest {
    public static void main(String[] args) {
        testModule();
        testADT();
        testOOP();
    }


    private static void testModule() {
        System.out.println("----Module----");
        for (int i = 0; i < 6; i++) {
            ArrayQueueModule.enqueue(i + "smth");
        }
        System.out.println(ArrayQueueModule.size());
        System.out.println(ArrayQueueModule.element());
        System.out.println(ArrayQueueModule.dequeue());
        ArrayQueueModule.push("Hi");
        ArrayQueueModule.push("Friend");
        System.out.println(ArrayQueueModule.peek());
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.remove());
        }
        for (int i = 0; i < 15; i++) {
            ArrayQueueModule.enqueue(i);
        }
        System.out.println(ArrayQueueModule.get(13));
        ArrayQueueModule.set(13, 52);
        System.out.println(ArrayQueueModule.get(13));
        System.out.println(ArrayQueueModule.size());
        System.out.println(ArrayQueueModule.isEmpty());
        ArrayQueueModule.clear();
        System.out.println(ArrayQueueModule.isEmpty());
    }

    private static void testADT() {
        System.out.println("----ADT----");
        ArrayQueueADT queue = ArrayQueueADT.create();
        for (int i = 0; i < 6; i++) {
            ArrayQueueADT.enqueue(queue,i + "smth");
        }
        System.out.println(ArrayQueueADT.size(queue));
        System.out.println(ArrayQueueADT.element(queue));
        System.out.println(ArrayQueueADT.dequeue(queue));
        ArrayQueueADT.push(queue, "Hi");
        ArrayQueueADT.push(queue, "Friend");
        System.out.println(ArrayQueueADT.peek(queue));
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(ArrayQueueADT.remove(queue));
        }
        for (int i = 0; i < 15; i++) {
            ArrayQueueADT.enqueue(queue, i);
        }
        System.out.println(ArrayQueueADT.get(queue,13));
        ArrayQueueADT.set(queue,13, 52);
        System.out.println(ArrayQueueADT.get(queue,13));
        System.out.println(ArrayQueueADT.size(queue));
        System.out.println(ArrayQueueADT.isEmpty(queue));
        ArrayQueueADT.clear(queue);
        System.out.println(ArrayQueueADT.isEmpty(queue));
    }

    private static void testOOP() {
        System.out.println("----OOP----");
        ArrayQueue queue = new ArrayQueue();
        for (int i = 0; i < 6; i++) {
            queue.enqueue(i + "smth");
        }
        System.out.println(queue.size());
        System.out.println(queue.element());
        System.out.println(queue.dequeue());
        queue.push("Hi");
        queue.push("Friend");
        System.out.println(queue.peek());
        while (!queue.isEmpty()) {
            System.out.println(queue.remove());
        }
        for (int i = 0; i < 15; i++) {
            queue.enqueue(i);
        }
        System.out.println(queue.get(13));
        queue.set(13, 52);
        System.out.println(queue.get(13));
        System.out.println(queue.size());
        System.out.println(queue.isEmpty());
        queue.clear();
        System.out.println(queue.isEmpty());
    }
}
