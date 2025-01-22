package specclasses;

public class LinkedList {

    private static class Node {

        int data;

        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;

    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public void add(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public int remove() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        int removedData = head.data;
        head = head.next;
        size--;
        return removedData;
    }
}
