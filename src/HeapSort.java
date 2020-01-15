import java.text.DecimalFormat;

/**
 * Written by Brian Wing.
 * A wrapper for implementing a Heapsorting algorithm.
 */
public class HeapSort implements Runnable{

    private SortingAlgorithms sa;
    private int[] array;
    private int n;
    private long runtime;
    private int count;

    /**
     * Instantiates HeapSort with runtime and iteration counts set to zero.
     * @param sa The {@link SortingAlgorithms} that called this constructor.
     */
    public HeapSort(SortingAlgorithms sa){
        this.sa = sa;
        this.runtime = 0;
        this.count = 0;
    }

    /**
     * Instantiates HeapSort with runtime and iteration counts set to zero and the array to be sorted as given. The given array is copied so as to allow for sorting without changing the original array.
     * @param sa The {@link SortingAlgorithms} that called this constructor.
     * @param array The array to sort.
     */
    public HeapSort(SortingAlgorithms sa, int[] array){
        this.sa = sa;
        this.runtime = 0;
        this.count = 0;
        this.array = array.clone();
    }

    /**
     * Sort the given array in a new {@link Thread} and report back with the results.
     */
    @Override
    public void run(){
        sort();

        if(this.sa.DEBUG){
            System.out.println("Heapsort complete.");
        }

        // Print results.
        DecimalFormat df = new DecimalFormat("#.#######");
        String timeInSec = df.format((double) this.getRuntime() / 1000000000);
        this.sa.printTimeHeap(this.getRuntime(), timeInSec);
        this.sa.addResults(2, timeInSec);
        this.sa.printOutput(this.toString()); // Do this last so as not to hold up adding totals to the results table.
    }

    /**
     * Turn the given array into a heap.
     * @param a The array to turn into a heap.
     */
    private void heapify(int[] a){
        n = a.length - 1;
        for(int i=n/2; i >= 0; i--){
            maxHeap(a,i);
        }
    }

    private void maxHeap(int[] a, int i){
        int left = 2 * i;
        int right = 2 * i + 1;
        int largest;

        if(left <= n && a[left] > a[i]){
            largest = left;
        }
        else{
            largest = i;
        }

        if(right <= n && a[right] > a[largest]){
            largest = right;
        }

        if(largest != i){
            swap(i,largest);
            maxHeap(a, largest);
        }
    }

    /**
     * Switches the places of two given integers in the heap.
     * @param i Parent element to switch with a child.
     * @param j Child element to switch with a parent.
     */
    private void swap(int i, int j){
        int t = this.array[i];
        this.array[i] = this.array[j];
        this.array[j] = t;
        this.count++;
    }

    /**
     * Perform heapsort on {@link #array}.
     */
    public int[] sort(){
        this.count = 0; // Reset count
        heapify(this.array); // Create the heap.

        // Sort the heap.
        long startTime = System.nanoTime();
        for(int i=n;i>0;i--){
            swap(0, i);
            n=n-1;
            maxHeap(this.array, 0);
            //this.count++;
        }
        long endTime = System.nanoTime();
        this.runtime = (endTime - startTime);

        return this.array;
    }

    /**
     * Returns the last sort run time in milliseconds.
     * @return The sort time in milliseconds.
     */
    public long getRuntimeMS(){
        return this.runtime / 1000000;
    }

    /**
     * Returns the last sort run time in nanoseconds.
     * @return The sort time in nanoseconds.
     */
    public long getRuntime(){
        return this.runtime;
    }

    /**
     * Returns the number of times elements were {@link #swap(int, int) swapped} for the last sort.
     * @return The iterations of the sort.
     */
    public int getCount(){
        return this.count;
    }

    /**
     * Returns a delimited {@link String} with commas separating each array element.
     * @return A String representation of the array.
     */
    @Override
    public String toString(){
        String s = "" + this.array[0];

        for(int i = 1; i < this.array.length; i++){
            s += ", " + this.array[i];
        }

        return s;
    }
}
