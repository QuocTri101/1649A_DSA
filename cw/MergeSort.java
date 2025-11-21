import java.util.Arrays; // Needed for printing the array

public class MergeSort {

    // Counter to track passes across recursive calls
    private static int passCounter;

    public static void sort(String[] arr) {
        if (arr == null || arr.length <= 1) {
            return; 
        }
        passCounter = 1; // Reset counter
        String[] temp = new String[arr.length]; 
        System.out.println("  Start: " + Arrays.toString(arr));
        mergeSort(arr, temp, 0, arr.length - 1);
        System.out.println("  End:   " + Arrays.toString(arr));
    }

    private static void mergeSort(String[] arr, String[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            // Divide 
            mergeSort(arr, temp, left, mid);
            mergeSort(arr, temp, mid + 1, right);   
            // Merge
            merge(arr, temp, left, mid, right);
            
            // Print state after each merge
            System.out.printf("  Pass %d (merged %d-%d): %s\n", passCounter++, left, right, Arrays.toString(arr));
        }
    }

    private static void merge(String[] arr, String[] temp, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }

        int i = left;       // Pointer for left half
        int j = mid + 1;    // Pointer for right half
        int k = left;       // Pointer for main array (arr)

        while (i <= mid && j <= right) {
            if (temp[i].compareTo(temp[j]) <= 0) {
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            arr[k] = temp[i];
            k++;
            i++;
        }
    }
}