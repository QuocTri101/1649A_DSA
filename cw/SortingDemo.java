import java.util.Arrays;
import java.util.Collections; // Required for shuffling
import java.util.List;       // Required for shuffling

public class SortingDemo {

    // A larger, static "master list" of 50 books
    private static final String[] MASTER_BOOK_LIST = {
        "The Hobbit", "A Brief History of Time", "1984", "Moby Dick", "Pride and Prejudice",
        "The Great Gatsby", "War and Peace", "Don Quixote", "Ulysses", "The Catcher in the Rye",
        "To Kill a Mockingbird", "Brave New World", "The Lord of the Rings", "Fahrenheit 451", "Jane Eyre",
        "Crime and Punishment", "Wuthering Heights", "Anna Karenina", "The Adventures of Huckleberry Finn", "Catch-22",
        "One Hundred Years of Solitude", "The Sound and the Fury", "Dracula", "Frankenstein", "The Odyssey",

        "Alice's Adventures in Wonderland", "Treasure Island", "The Scarlet Letter", "Gulliver's Travels", "A Tale of Two Cities",
        "Little Women", "Great Expectations", "Oliver Twist", "The Three Musketeers", "The Call of the Wild",
        "20,000 Leagues Under the Sea", "The Picture of Dorian Gray", "The Grapes of Wrath", "The Count of Monte Cristo", "The Old Man and the Sea",
        "The Lion, the Witch, and the Wardrobe", "Anne of Green Gables", "Lord of the Flies", "The Stranger", "Gone with the Wind",
        "Of Mice and Men", "White Fang", "The Jungle", "Persuasion", "Candide"
    };

    public static void main(String[] args) {
        
        // Create a List from the master array.
        List<String> bookList = Arrays.asList(MASTER_BOOK_LIST);

        // --- 1. Test Binary Insertion Sort ---
        System.out.println("--- Testing Binary Insertion Sort ---");
        Collections.shuffle(bookList); // Shuffle the list
        String[] books1 = bookList.toArray(new String[0]); // Copy to a new array
        
        long startTime1 = System.nanoTime();
        BinaryInsertionSort.sort(books1);
        long endTime1 = System.nanoTime();
        
        printSortResult("Binary Insertion", books1, (endTime1 - startTime1));

        // --- 2. Test Standard Insertion Sort ---
        System.out.println("--- Testing Standard Insertion Sort ---");
        Collections.shuffle(bookList); // Re-shuffle for fairness
        String[] books2 = bookList.toArray(new String[0]);
        
        long startTime2 = System.nanoTime();
        InsertionSort.sort(books2);
        long endTime2 = System.nanoTime();
        
        printSortResult("Standard Insertion", books2, (endTime2 - startTime2));
        
        // --- 3. Test Merge Sort ---
        System.out.println("--- Testing Merge Sort ---");
        Collections.shuffle(bookList); // Re-shuffle for fairness
        String[] books3 = bookList.toArray(new String[0]);
        
        long startTime3 = System.nanoTime();
        MergeSort.sort(books3);
        long endTime3 = System.nanoTime();
        
        printSortResult("Merge Sort", books3, (endTime3 - startTime3));

        // --- 4. Test QuickSort ---
        System.out.println("--- Testing QuickSort ---");
        Collections.shuffle(bookList); // Re-shuffle for fairness
        String[] books4 = bookList.toArray(new String[0]);
        
        long startTime4 = System.nanoTime();
        QuickSort.sort(books4);
        long endTime4 = System.nanoTime();
        
        printSortResult("QuickSort", books4, (endTime4 - startTime4));
    
       // --- 5. Test Bubble Sort (NEWLY ADDED) ---
        System.out.println("--- 5. Testing Bubble Sort ---");
        Collections.shuffle(bookList); // Shuffle again
        String[] books5 = bookList.toArray(new String[0]);
        
        long startTime5 = System.nanoTime();
        BubbleSort.sort(books5); // Calling the new BubbleSort class
        long endTime5 = System.nanoTime();
        
        printSortResult("Bubble Sort", books5, (endTime5 - startTime5));
    
           // --- 6. Test Selection Sort (NEWLY ADDED) ---
        System.out.println("--- 6. Testing Selection Sort ---");
        Collections.shuffle(bookList); // Shuffle again
        String[] books6 = bookList.toArray(new String[0]);
        
        long startTime6 = System.nanoTime();
        SelectionSort.sort(books6); 
        long endTime6 = System.nanoTime();
        
        printSortResult("Selection Sort", books6, (endTime6 - startTime6));
    }
    /**
     * Helper method to print a clean summary of the sort result.
     * It shows the first few and the last element to prove it's sorted
     * without flooding the console.
     */
    private static void printSortResult(String algorithmName, String[] arr, long duration) {
        System.out.print("Sorted Snippet: [");
        // Print the first 5 elements (or fewer if the array is small)
        for (int i = 0; i < Math.min(arr.length, 5); i++) {
            System.out.print(arr[i] + (i < Math.min(arr.length, 5) - 1 ? ", " : ""));
        }
        
        // Add ellipsis and the last element if the array is larger than 5
        if (arr.length > 5) {
            System.out.print(", ..., " + arr[arr.length - 1]);
        }
        System.out.println("]");
        System.out.println("Algorithm: " + algorithmName);
        System.out.println("Runtime: " + duration + " nanoseconds");
        System.out.println(); // Add a blank line
    }
}