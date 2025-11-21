public class BubbleSort {
    public static void sort(String[] arr) {
        int n = arr.length;
        boolean swapped;
        
        // Outer loop: Iterate through all elements
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            // Inner loop: Push the largest element to the end
            // (n - i - 1) because the last i elements are already sorted
            for (int j = 0; j < n - i - 1; j++) {
                // Compare two strings using compareTo
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    // Swap arr[j] and arr[j+1]
                    String temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            
            // Optimization: If no elements were swapped in the inner loop,
            // the array is already sorted -> Stop early.
            if (!swapped) break;
        }
    }
}
