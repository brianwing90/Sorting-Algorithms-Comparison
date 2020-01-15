import java.text.DecimalFormat;

/**
 * Written by Brandon Lilly.
 * A wrapper for implementing an Insertion Sorting algorithm.
 */
public class SelectionSort implements Runnable{

    private SortingAlgorithms sa;
    private long runtime;
    private int[] array;

    /**
     * Instantiate SelectionSort with a null array and run time set to 0.
     * @param sa The {@link SortingAlgorithms} that called this constructor.
     */
    public SelectionSort(SortingAlgorithms sa){
        this.sa = sa;
        this.array = null;
        this.runtime = 0;
    }

    /**
     * Instantiate SelectionSort with a given array and run time set to 0.
     * @param sa The {@link SortingAlgorithms} that called this constructor.
     * @param inputArray The array to sort.
     */
    public SelectionSort(SortingAlgorithms sa, int[] inputArray){
        this.sa = sa;
        this.array = inputArray.clone();
        this.runtime = 0;
    }

    /**
     * Sort the given array in a new {@link Thread} and report back with the results.
     */
    @Override
    public void run(){
        sort();

        if(this.sa.DEBUG){
            System.out.println("Selection Sort complete.");
        }

        // Show results.
        DecimalFormat df = new DecimalFormat("#.#######");
        String timeInSec = df.format((double) this.getRuntime() / 1000000000);
        this.sa.printTimeSel(this.getRuntime(), timeInSec);
        this.sa.addResults(0, timeInSec);
    }

    /**
     * selectionSort - Selection sorts this objects array of integers
     *
     * @return           - Outputs sorted array of integers
     */
    public int[] sort(){

        long startTime = System.nanoTime();
        for(int i = 0; i < this.array.length;i++){

            int index = i;

            //The following for loop iterates through the array searching for a value smaller than what is located at i
            for (int j = i + 1; j < this.array.length; j++) {

                //If the value at j is smaller than i, the new index value becomes j
                if (this.array[j] < this.array[index]) {
                    index = j;
                }
            }

            //After determining the smaller value, the value at j takes the place of i
            int smaller = this.array[index];
            this.array[index] = this.array[i];
            this.array[i] = smaller;

            //Debug code - prints the current state of the array after each iteration
             /*
             System.out.print("Iteration " + (i+1) + ":   ");
             for(int x = 0; x < this.array.length; x++){
                 System.out.print(this.array[x] + " ");
             }
             System.out.print("\n");
                */
        }
        long endTime = System.nanoTime();
        this.runtime = endTime - startTime;

        return this.array;
    }

    /**
     * printArrayContents - Prints every element of the specified array on one line
     * @param inputArray  - the array to be printed
     */
    public static void printArrayContents(int[] inputArray){

        for(int i = 0; i < inputArray.length; i++){
            System.out.print(inputArray[i] + " ");
        }
    }

    /**
     * Sets a new integer array for sorting.
     * @param array The new integer array to sort.
     */
    public void setArray(int[] array){
        this.array = array;
    }

    /**
     * Returns the runtime of the last sorting processes in nanoseconds. This returns 0 if no sorting
     * operation has been performed. Use {@link #getRuntimeMS()} to get the runtime in milliseconds.
     * @return The runtime in nanoseconds.
     * @see #getRuntimeMS()
     */
    public long getRuntime(){
        return this.runtime;
    }

    /**
     * Returns the runtime of the last sorting operation in milliseconds. This returns 0 if no sorting
     * operation has been performed.
     * @return The runtime in milliseconds.
     */
    public long getRuntimeMS(){
        return this.getRuntime() / 1000000;
    }

}
