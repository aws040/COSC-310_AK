package project;

/**
 * Class containing 4 different sorts, InsertionSort, MergeSort, QuickSort, and HeapSort.
 * @author Alejandro Borroni
 * @version 2026.10.31.10
 */
public class P4_Sorting {
    private long insSortComp = 0;
    private long mergeSortComp = 0;
    private long quickSortComp = 0;
    private long heapSortComp = 0;

    //Getters for the comparisons
    public long getInsSortComp() {
        return insSortComp;
    }

    public long getMergeSortComp() {
        return mergeSortComp;
    }

    public long getQuickSortComp() {
        return quickSortComp;
    }

    public long getHeapSortComp() {
        return heapSortComp;
    }

    /**
     * Gets the range of ints from an array and using the index and the stopping point and then prints them
     * @param index taken as the starting index
     * @param x taken as the ending index
     * @param numArr taken as the array to print out the range of
     */
    public void get5LastnFirst(int index, int x, int[] numArr) {
        if (x >= numArr.length || x < 0 || index >= numArr.length || index < 0) {
            throw new IndexOutOfBoundsException("An index was out of bounds");
        }
        for (int i = index; i < x; i++) {
            System.out.print(numArr[i] + " ");
        }
        System.out.print(numArr[x] + "\n");
    }

    /**
     * Takes an array of ints and sorts them using the insertion sort method
     * @param arr taken as an array of ints
     */
    public void InsertionSort(int[] arr) {
        if (arr == null) return;

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0) {
                insSortComp++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
    }

    /**
     * Takes an array of ints and sorts them using the merge sort method
     * @param arr taken as an array of ints
     */
    public void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * Private method for merge sort
     * @param arr taken as an array of ints
     * @param left the left of the merge sort tree
     * @param right the right of the merge sort tree
     */
    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    /**
     * Private method for merge sort
     * @param arr taken as an array of ints
     * @param left the left of the merge sort tree
     * @param mid the middle of the merge sort tree
     * @param right the right of the merge sort tree
     */
    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        System.arraycopy(arr, left, leftArr, 0, n1);
        for (int j = 0; j < n2; j++) rightArr[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            mergeSortComp++;
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        while (i < n1) arr[k++] = leftArr[i++];
        while (j < n2) arr[k++] = rightArr[j++];
    }

    /**
     * Takes an array of ints and sorts them using the quick sort method
     * @param arr taken as an array of ints
     */
    public void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * Helper method for quick sort
     * @param arr taken as an array of ints
     * @param low taken as the lower point of the array
     * @param high taken as the higher point of the array
     */
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    /**
     * Helper method of quick sort
     * @param arr taken as an array of ints
     * @param low taken as the lower point of the array
     * @param high taken as the higher point of the array
     * @return the pivot point to be used in quick sort
     */
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            quickSortComp++;
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    /**
     * Takes an array of ints and sorts them using the heap sort method
     * @param arr taken as an array of ints
     */
    public void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    /**
     * Helper method of heap sort
     * @param arr taken as an array of ints
     * @param n The active size of the heap
     * @param i taken as the root of the tree in the array
     */
    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n) {
            heapSortComp++;
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }

        if (right < n) {
            heapSortComp++;
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }
}
