import java.util.Arrays; // Needed for printing the array

public class QuickSort {

    // Counter to track passes across recursive calls
    private static int passCounter;

    public static void sort(String[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        passCounter = 1; // Reset the counter for each new sort
        System.out.println("  Start: " + Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println("  End:   " + Arrays.toString(arr));
    }

    private static void quickSort(String[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            
            // Print the state after a pivot is placed
            System.out.printf("  Pass %d (pivot='%s'): %s\n", passCounter++, arr[pi], Arrays.toString(arr));

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(String[] arr, int low, int high) {
        String pivot = arr[high]; 
        int i = (low - 1); 

        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return (i + 1);
    }

    private static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}