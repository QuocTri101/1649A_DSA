import java.util.Arrays; // Needed for printing the array

public class BinaryInsertionSort {

    private static int binarySearchLocation(String[] arr, String key, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = arr[mid].compareTo(key);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) { 
                low = mid + 1;
            } else { 
                high = mid - 1;
            }
        }
        return low;
    }

    public static void sort(String[] arr) {
        int n = arr.length;
        // Print the starting state
        System.out.println("  Start: " + Arrays.toString(arr));

        for (int i = 1; i < n; i++) {
            String key = arr[i];

            int loc = binarySearchLocation(arr, key, 0, i - 1);
            int j = i - 1;
            while (j >= loc) {
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