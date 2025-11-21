import java.util.Arrays; // Needed for printing the array

public class InsertionSort {
    
    public static void sort(String[] arr) {
        int n = arr.length;
        // Print the starting state
        System.out.println("  Start: " + Arrays.toString(arr));

        for (int i = 1; i < n; i++) {
            String key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j]; 
                j--;
            }
            arr[j + 1] = key;
            
            // Print the array after each pass
            System.out.printf("  Pass %d (key='%s'): %s\n", i, key, Arrays.toString(arr));
        }
        // Print the final state
        System.out.println("  End:   " + Arrays.toString(arr));
    }
}