import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Scanner; // Imported for console input

/**
 * ===================================================================
 * Main Demo Class (BookstoreDemo) - UPDATED WITH VIEW QUEUE
 * ===================================================================
 */
public class BookstoreDemo {

    // Global Scanner for user input
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize Data Structures
        BookStack viewedBooks = new BookStack(10); // Stack for history
        OrderQueue newOrders = new OrderQueue(10); // Queue for new orders

        // Pre-populate a "Database" for the SEARCH feature (Must be sorted by ID)
        Order[] allOrders = {
            new Order(1001, "Tuan Anh",   "Shipped",    new String[]{"Java Basics", "OOP Intro"}),
            new Order(1002, "Minh Tu",    "Pending",    new String[]{"Data Structures"}),
            new Order(1003, "Hoang Hai",  "Processing", new String[]{"Algorithms"}),
            new Order(1004, "Bao Chau",   "Delivered",  new String[]{"Moby Dick", "1984"}),
            new Order(1005, "Viet Hoang", "Cancelled",  new String[]{"Clean Code"}),
            new Order(1006, "Thanh Ha",   "Shipped",    new String[]{"Design Patterns"}),
            new Order(1007, "Duc Thang",  "Pending",    new String[]{"C# for Beginners"}),
            new Order(1008, "Ngoc Lan",   "Processing", new String[]{"Web Development"}),
            new Order(1009, "Quoc Bao",   "Delivered",  new String[]{"Docker Guide"}),
            new Order(1010, "Phuong Thao","Shipped",    new String[]{"Microservices"})
        };

        while (true) {
            System.out.println("\n===========================================");
            System.out.println("    ONLINE BOOKSTORE SYSTEM (DEMO)");
            System.out.println("===========================================");
            System.out.println("1. [Stack] View a Book (Add to Recently Viewed)");
            System.out.println("2. [Stack] Check Most Recently Viewed Book");
            System.out.println("3. [Queue] Place New Order (Add to Queue)");
            System.out.println("4. [Sort]  Process Order (Dequeue & Sort Books)");
            System.out.println("5. [Search] Search Order in Database");
            System.out.println("0. Exit");
            System.out.print(">> Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("(!) Error: Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    // STACK: PUSH
                    System.out.print("Enter the book title you are viewing: ");
                    String bookTitle = scanner.nextLine();
                    if (!bookTitle.trim().isEmpty()) {
                        viewedBooks.push(bookTitle);
                        System.out.println("-> '" + bookTitle + "' added to history.");
                    }
                    break;

                case 2:
                    // STACK: PEEK
                    try {
                        System.out.println("-> Most recently viewed: " + viewedBooks.peek());
                    } catch (EmptyStackException e) {
                        System.out.println("(!) History is empty.");
                    }
                    break;

                case 3:
                    // QUEUE: ENQUEUE
                    System.out.println("--- New Order Entry ---");
                    try {
                        System.out.print("Enter Order ID (integer, e.g., 2001): ");
                        int id = Integer.parseInt(scanner.nextLine());
                        
                        System.out.print("Enter Customer Name: ");
                        String name = scanner.nextLine();
                        
                        System.out.print("Enter Books (comma separated, e.g., Book A, Book B): ");
                        String booksInput = scanner.nextLine();
                        String[] books = booksInput.split(",");
                        for(int i=0; i<books.length; i++) books[i] = books[i].trim();

                        Order newOrder = new Order(id, name, "Pending", books);
                        newOrders.enqueue(newOrder);
                        
                        System.out.println("-> Order #" + id + " added to the processing queue.");
                        
                        // --- NEW: View the queue immediately to confirm ---
                        newOrders.viewQueue();
                        // ------------------------------------------------

                    } catch (NumberFormatException e) {
                        System.out.println("(!) Error: Order ID must be a number.");
                    }
                    break;

                case 4:
                    // QUEUE: DEQUEUE + SORT
                    try {
                        System.out.println("--- Processing Next Order ---");
                        Order processingOrder = newOrders.dequeue();
                        
                        System.out.println("Processing Order: #" + processingOrder.orderId + " (" + processingOrder.customerName + ")");
                        System.out.println("Unsorted Books: " + Arrays.toString(processingOrder.bookList));
                        
                        // Apply Sorting Algorithm
                        BookstoreAlgorithms.binaryInsertionSort(processingOrder.bookList);
                        
                        System.out.println("SORTED Books:   " + Arrays.toString(processingOrder.bookList));
                        processingOrder.status = "Processed";
                        System.out.println("-> Status updated to: Processed");
                        
                    } catch (NoSuchElementException e) {
                        System.out.println("(!) Queue is empty. No orders to process.");
                    }
                    break;

                case 5:
                    // SEARCH
                    System.out.println("--- Search Database (Available IDs: 1001 to 1010) ---");
                    // Show Index List for reference
                    for(int i=0; i<allOrders.length; i++) {
                         System.out.println("[" + i + "] ID: " + allOrders[i].orderId + " | Name: " + allOrders[i].customerName);
                    }
                    System.out.println("-----------------------------------------------------");

                    System.out.print("Enter Order ID to search: ");
                    try {
                        int searchId = Integer.parseInt(scanner.nextLine());
                        Order found = BookstoreAlgorithms.binarySearchOrders(allOrders, searchId);
                        
                        if (found != null) {
                            System.out.println("\n[ORDER FOUND]");
                            System.out.println("- ID: " + found.orderId);
                            System.out.println("- Customer: " + found.customerName);
                            System.out.println("- Status: " + found.status);
                            System.out.println("- Books: " + Arrays.toString(found.bookList));
                        } else {
                            System.out.println("(!) Order ID " + searchId + " not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("(!) ID must be a number.");
                    }
                    break;

                case 0:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

/**
 * ===================================================================
 * HELPER CLASSES (ADTs and Algorithms)
 * ===================================================================
 */

// --- Class 1: BookStack (Keep logic same) ---
class BookStack {
    private String[] stackArray;
    private int top;
    private int maxSize;
    public BookStack(int size) { this.maxSize = size; this.stackArray = new String[maxSize]; this.top = -1; }
    public void push(String item) { if (isFull()) { System.out.println("Stack is full."); return; } stackArray[++top] = item; }
    public String pop() { if (isEmpty()) throw new EmptyStackException(); return stackArray[top--]; }
    public String peek() { if (isEmpty()) throw new EmptyStackException(); return stackArray[top]; }
    public boolean isEmpty() { return (top == -1); }
    public boolean isFull() { return (top == maxSize - 1); }
}

// --- Class 2: OrderQueue (UPDATED with viewQueue) ---
class OrderQueue {
    private Order[] queueArray;
    private int maxSize, front, rear, currentSize;

    public OrderQueue(int size) { 
        this.maxSize = size; 
        this.queueArray = new Order[maxSize]; 
        this.front = 0; 
        this.rear = -1; 
        this.currentSize = 0; 
    }

    public void enqueue(Order item) { 
        if (isFull()) { System.out.println("(!) Queue is full."); return; } 
        if (rear == maxSize - 1) rear = -1; 
        queueArray[++rear] = item; 
        currentSize++; 
    }

    public Order dequeue() { 
        if (isEmpty()) throw new NoSuchElementException(); 
        Order temp = queueArray[front++]; 
        if (front == maxSize) front = 0; 
        currentSize--; 
        return temp; 
    }

    public Order peek() { 
        if (isEmpty()) throw new NoSuchElementException(); 
        return queueArray[front]; 
    }

    public boolean isEmpty() { return (currentSize == 0); }
    public boolean isFull() { return (currentSize == maxSize); }

    // --- NEW: Print the Queue ---
    public void viewQueue() {
        if (isEmpty()) {
            System.out.println("   (Queue is currently empty)");
            return;
        }
        System.out.println("   [CURRENT QUEUE STATUS]");
        
        int tempFront = front;
        for (int i = 0; i < currentSize; i++) {
            Order o = queueArray[tempFront];
            System.out.println("   + Pos " + (i+1) + ": Order #" + o.orderId + " (" + o.customerName + ")");
            
            tempFront++;
            if (tempFront == maxSize) tempFront = 0;
        }
        System.out.println("   ----------------------");
    }
}

// --- Class 3: Order ---
class Order implements Comparable<Order> {
    int orderId; String customerName; String status; String[] bookList;
    public Order(int id, String name, String st, String[] bList) { this.orderId = id; this.customerName = name; this.status = st; this.bookList = bList; }
    @Override public int compareTo(Order other) { return Integer.compare(this.orderId, other.orderId); }
}

// --- Class 4: BookstoreAlgorithms ---
class BookstoreAlgorithms {
    public static void binaryInsertionSort(String[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            String key = arr[i];
            int loc = binarySearchLocation(arr, key, 0, i - 1);
            int j = i - 1;
            while (j >= loc) { arr[j + 1] = arr[j]; j--; }
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
    public static Order binarySearchOrders(Order[] sortedOrders, int targetId) {
        int low = 0, high = sortedOrders.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (sortedOrders[mid].orderId == targetId) return sortedOrders[mid];
            else if (sortedOrders[mid].orderId < targetId) low = mid + 1;
            else high = mid - 1;
        }
        return null; 
    }
}