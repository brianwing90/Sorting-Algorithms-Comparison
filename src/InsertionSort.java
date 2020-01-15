import java.text.DecimalFormat;

/**
 * Written by Brandon Lilly.
 * A wrapper for implementing an Insertion Sorting algorithm.
 */
public class InsertionSort implements Runnable{

    private SortingAlgorithms sa;
    private long runtime;
    private int[] array;

    /**
     * Instantiates InsertionSort with a null array and runtime set to 0.
     * @param sa The {@link SortingAlgorithms} that called this constructor.
     */
    public InsertionSort(SortingAlgorithms sa){
        this.sa = sa;
        this.array = null;
        this.runtime = 0;
    }

    /**
     * Instantiates InsertionSort with the given array and runtime set to 0.
     * @param sa The {@link SortingAlgorithms} that called this constructor.
     * @param inputArray The array to sort.
     */
    public InsertionSort(SortingAlgorithms sa, int[] inputArray){
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
            System.out.println("Insertion Sort complete.");
        }

        // Show results.
        DecimalFormat df = new DecimalFormat("#.#######");
        String timeInSec = df.format((double) this.getRuntime() / 1000000000);
        this.sa.printTimeIns(this.getRuntime(), timeInSec);
        this.sa.addResults(1, timeInSec);
    }


    /**
     * Perform an Insertion Sort on the array.
     * @return - an array of integers sorted by insertion sort
     */
    public int[] sort(){

        long startTime = System.nanoTime();
        for (int i = 0; i < this.array.length; i++) {

            int temp = this.array[i];
            int j;

            //Iterates through the array until finding a value smaller than temp
            for (j = i-1; j >= 0 && temp < this.array[j]; j--) {
                this.array[j + 1] = this.array[j];
            }
            this.array[j + 1] = temp;

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
     */
    public void printArrayContents(){

        for(int i = 0; i < this.array.length; i++){
            System.out.print(this.array[i] + " ");
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
