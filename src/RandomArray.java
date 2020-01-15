import java.awt.event.ActionEvent;
import java.util.Random;

/**
 * Created by brian on 4/27/17.
 * Fills a given array with random integers between 0 and 999.
 */
public class RandomArray implements Runnable{

    private  SortingAlgorithms sa;
    private int[] array;

    /**
     * Instantiates a new RandomArray with a given array to fill with random integers.
     * @param sa The {@link SortingAlgorithms} that called this constructor.
     * @param array The array to fill with random integers.
     */
    public RandomArray(SortingAlgorithms sa, int[] array){
        this.sa = sa;
        this.array = array;
    }

    /**
     * Fill the array specified in the constructor with random integers between 0 and 999.
     */
    @Override
    public void run() {
        Random random = new Random();
        String inputBuilder = "";

        // Generate random numbers and show the input array.
        for(int i = 0; i < array.length; i++){
            array[i] = random.nextInt(999);
            if(i == 0) {
                inputBuilder = "" + array[i];
            }else{
                inputBuilder += ", " + array[i];
            }
        }

        this.sa.printInput(inputBuilder);

        // When done generating the new random array go back and sort it.
        ActionEvent e = new ActionEvent(this, 0, "sort");
        this.sa.actionPerformed(e);
    }
}