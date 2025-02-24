package lockfreedatastructure;

import java.util.concurrent.atomic.AtomicReference;

// the concept is CAS
public class LockFreeStack<T> {
    private final AtomicReference<Node<T>> head = new AtomicReference<>();

    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        do {
            newNode.next = head.get();
        } while (!head.compareAndSet(newNode.next, newNode));
    }

    public T pop() {
        Node<T> oldHead;
        do {
            oldHead = head.get();
            if (oldHead == null) {
                return null; // Stack is empty
            }
        } while (!head.compareAndSet(oldHead, oldHead.next));
        return oldHead.value;
    }
}

class Node<T> {
    T value;
    Node<T> next;

    Node(T value) {
        this.value = value;
    }
}