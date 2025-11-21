import java.util.Arrays; // Used for printing arrays
import java.util.EmptyStackException; // Used for our stack
import java.util.NoSuchElementException; // Used for our queue

/**
 * ===================================================================
 * Main Demo Class (BookstoreDemo)
 * ===================================================================
 * This file demonstrates a unified scenario for the bookstore system.
 */
public class BookstoreDemo {

    public static void main(String[] args) {

        System.out.println("--- Online Bookstore Scenario Demo ---");

        // -----------------------------------------------------
        // 1. STACK DEMO (Separate Feature: Recently Viewed)
        // -----------------------------------------------------
        // The Stack (LIFO) scenario is often used for features like "Recently Viewed Items".
        // It operates independently of the main order processing flow.
        System.out.println("\n=================================");
        System.out.println("  Part 1: Stack Feature (Recently Viewed Books)");
        System.out.println("=================================");

        BookStack viewedBooks = new BookStack(5);
        viewedBooks.push("The Hobbit");
        viewedBooks.push("1984");
        System.out.println("Customer just viewed 2 books.");
        System.out.println("Most recently viewed book (peek): " + viewedBooks.peek());
        viewedBooks.push("A Brief History of Time");
        System.out.println("Customer viewed another book. Most recent (peek): " + viewedBooks.peek());


        // -----------------------------------------------------
        // 2. ORDER PROCESSING SCENARIO (Queue -> Sort -> Search)
        // -----------------------------------------------------
        System.out.println("\n=================================");
        System.out.println("  Part 2: Order Processing Flow (FIFO, Sort, Search)");
        System.out.println("=================================");

        // --- STEP 0: Initialize "Database" (allOrders list) ---
        // This is the list of EXISTING orders in the system (must be sorted by ID)
        // to be used for the SEARCH scenario.
        Order[] allOrders = {
            new Order(1001, "Customer A", "Shipped", new String[]{"The Lion"}),
            new Order(1004, "Customer B", "Pending", new String[]{"Moby Dick", "1984"}),
            new Order(1005, "Customer C", "Shipped", new String[]{"The Great Gatsby"}),
            new Order(1012, "Customer D", "Processing", new String[]{"War and Peace"}),
            new Order(1020, "Customer E", "Delivered", new String[]{"The Odyssey"})
        };
        
        // --- STEP 1 (QUEUE): New orders are placed and added to the FIFO queue ---
        System.out.println("\n--- Step 1 (FIFO): New orders are added to the queue ---");
        OrderQueue newOrders = new OrderQueue(10);
        
        // Customers place 2 new orders
        Order order1 = new Order(2001, "Customer F", "Pending", new String[]{"Moby Dick", "A Brief History of Time", "1984"});
        Order order2 = new Order(2002, "Customer G", "Pending", new String[]{"Ulysses", "Don Quixote"});

        newOrders.enqueue(order1);
        newOrders.enqueue(order2);
        System.out.println("Added 2 new orders to the queue.");
        System.out.println("First order waiting for processing (peek): Order #" + newOrders.peek().orderId);

        // --- STEP 2 (DEQUEUE + SORT): Process an order from the queue ---
        System.out.println("\n--- Step 2 (SORT): Process the first order from the queue ---");
        
        // Get the first order from the queue (FIFO)
        Order orderToProcess = newOrders.dequeue();
        
        System.out.println("Now processing Order #" + orderToProcess.orderId);
        System.out.println("  Old status: " + orderToProcess.status);
        
        // SORT SCENARIO: Sort the list of books INSIDE the order
        System.out.println("  Unsorted book list: " + Arrays.toString(orderToProcess.bookList));
        BookstoreAlgorithms.binaryInsertionSort(orderToProcess.bookList);
        System.out.println("  Sorted book list: " + Arrays.toString(orderToProcess.bookList));
        
        // Update the order status (example)
        orderToProcess.status = "Processed";
        System.out.println("  New status: " + orderToProcess.status);
        System.out.println("Next order waiting: Order #" + newOrders.peek().orderId);
        
        // (In a real app, orderToProcess would now be added to 'allOrders')

        // --- STEP 3 (SEARCH): A customer searches for an order ---
        System.out.println("\n--- Step 3 (SEARCH): A customer searches for an order in the DB ---");
        
        int orderIdToFind = 1020; // The customer wants to find order 1020
        System.out.println("Customer is searching for Order ID: " + orderIdToFind + " (in the 'allOrders' database)");
        
        // SEARCH SCENARIO: Binary search on the 'allOrders' array
        Order foundOrder = BookstoreAlgorithms.binarySearchOrders(allOrders, orderIdToFind);
        
        if (foundOrder != null) {
            System.out.println("Order Found!");
            System.out.println("  -> ID: " + foundOrder.orderId);
            System.out.println("  -> Customer: " + foundOrder.customerName);
            System.out.println("  -> Status: " + foundOrder.status);
            System.out.println("  -> Books in order: " + Arrays.toString(foundOrder.bookList));
        } else {
            System.out.println("Order ID " + orderIdToFind + " not found.");
        }
    }
}

/**
 * ===================================================================
 * Class 1: BookStack (Stack ADT Implementation - LIFO)
 * ===================================================================
 */
class BookStack {
    private String[] stackArray;
    private int top;
    private int maxSize;

    public BookStack(int size) {
        this.maxSize = size;
        this.stackArray = new String[maxSize];
        this.top = -1;
    }

    public void push(String item) {
        if (isFull()) {
            System.out.println("Stack is full.");
            return;
        }
        stackArray[++top] = item;
    }

    public String pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stackArray[top--];
    }

    public String peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stackArray[top];
    }

    public boolean isEmpty() {
        return (top == -1);
    }

    public boolean isFull() {
        return (top == maxSize - 1);
    }
    
    public int size() {
        return top + 1;
    }
}

/**
 * ===================================================================
 * Class 2: OrderQueue (Queue ADT Implementation - FIFO)
 * ===================================================================
 */
class OrderQueue {
    private Order[] queueArray;
    private int maxSize;
    private int front;      // Index of the front item
    private int rear;       // Index of the last item
    private int currentSize; // Number of items in queue

    public OrderQueue(int size) {
        this.maxSize = size;
        this.queueArray = new Order[maxSize];
        this.front = 0;
        this.rear = -1;
        this.currentSize = 0;
    }

    public void enqueue(Order item) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot enqueue item.");
            return;
        }
        // Wrap around if we reach the end of the array
        if (rear == maxSize - 1) {
            rear = -1;
        }
        queueArray[++rear] = item;
        currentSize++;
    }

    public Order dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        Order itemToDequeue = queueArray[front];
        front++;
        // Wrap around if we reach the end of the array
        if (front == maxSize) {
            front = 0;
        }
        currentSize--;
        return itemToDequeue;
    }

    public Order peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        return queueArray[front];
    }

    public boolean isEmpty() {
        return (currentSize == 0);
    }

    public boolean isFull() {
        return (currentSize == maxSize);
    }
    
    public int size() {
        return currentSize;
    }
}


/**
 * ===================================================================
 * Class 3: Order (Helper Class)
 * *** UPDATED ***
 * ===================================================================
 */
class Order implements Comparable<Order> {
    int orderId;
    String customerName;
    String status;
    String[] bookList; // <-- ADDED THIS LINE

    // *** UPDATED CONSTRUCTOR ***
    public Order(int orderId, String customerName, String status, String[] bookList) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.status = status;
        this.bookList = bookList; // <-- ADDED THIS LINE
    }

    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.orderId, other.orderId);
    }
}

/**
 * ===================================================================
 * Class 4: BookstoreAlgorithms (Sort and Search)
 * (No changes needed)
 * ===================================================================
 */
class BookstoreAlgorithms {

    // --- SORTING ALGORITHM ---
    
    public static void binaryInsertionSort(String[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            String key = arr[i];
            int loc = binarySearchLocation(arr, key, 0, i - 1);
            int j = i - 1;
            while (j >= loc) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static int binarySearchLocation(String[] arr, String key, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = arr[mid].compareTo(key);
            if (cmp == 0) return mid;
            else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return low;
    }
    
    
    // --- SEARCHING ALGORITHM ---
    
    public static Order binarySearchOrders(Order[] sortedOrders, int targetId) {
        int low = 0;
        int high = sortedOrders.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            Order midOrder = sortedOrders[mid];
            
            if (midOrder.orderId == targetId) {
                return midOrder;
            } else if (midOrder.orderId < targetId) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null; 
    }
}