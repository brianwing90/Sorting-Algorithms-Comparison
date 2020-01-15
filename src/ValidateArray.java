import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by brian on 4/27/17.
 * A wrapper used to validate a custom input array on it's own thread so as not to lock up the GUI while validation is in progress.
 */
public class ValidateArray implements Runnable{

    private  SortingAlgorithms sa;
    private int[] array;
    private String userInput;

    /**
     * Instantiate a ValidateArray with an array given to store the parsed user input in and a {@link String} of user input to parse. All user input should be in CSV form with or without spaces.
     * @param sa The {@link SortingAlgorithms} that called this constructor.
     * @param array The array used to store parsed input.
     * @param userInput The unparsed user input that will be validated.
     */
    public ValidateArray(SortingAlgorithms sa, int[] array, String userInput){
        this.sa = sa;
        this.array = array;
        this.userInput = userInput;
    }

    /**
     * Parse and validate all values in a CSV String and store the validated input in an array. Shows an error dialog if the input is not valid.
     */
    @Override
    public void run() {
        String[] elements = this.userInput.split(",");
        String inputBuilder = "";

        // Validate user input.
        try {
            for (int i = 0; i < elements.length; i++) {
                array[i] = Integer.parseInt(elements[i].trim()); // Attempt to parse an integer value without whitespace from the input element.
                if(i == 0) {
                    inputBuilder = "" + array[i];
                }else{
                    inputBuilder += ", " + array[i];
                }
            }
        }catch(NumberFormatException e1){ // Show a popup error message if invalid input was entered.
            JOptionPane.showMessageDialog(this.sa,
                    "All inputs must be integer values separated by commas.\nExample: 4, 8, 2, 12, 5, 21",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            this.sa.enableSortButton();
            this.sa.printInput("Error");
            return;
        }

        this.sa.printInput(inputBuilder);

        // When done generating the new random array go back and sort it.
        ActionEvent e = new ActionEvent(this, 0, "sort");
        this.sa.actionPerformed(e);
    }
}
