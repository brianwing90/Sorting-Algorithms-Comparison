import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * A test suit for showing the differences in effectiveness of three different sorting algorithms.
 *
 * @author Brian Wing and Brandon Lilly
 * @version 1.0
 * @since 04/26/2017
 */
public class SortingAlgorithms extends JFrame implements ActionListener{

    public static void main(String[] args){
        new SortingAlgorithms();
    }

    private static final String VERSION = "v1.0";
    public static final boolean DEBUG = false; // Set to true to display debug messages on the console during runtime.

    private JTextField customInput;
    private JTextField showInput;
    private JTextField showOutput;
    private JTable pastResultsTable;
    private TableModel pastResultsTableModel;
    private JRadioButton randomButton;
    private JRadioButton customButton;
    private JButton sortButton;
    private JTextField showTimeSel;
    private JTextField showTimeIns;
    private JTextField showTimeHeap;
    private JTextField showTimeSelSec;
    private JTextField showTimeInsSec;
    private JTextField showTimeHeapSec;
    private JTextField avgSel;
    private JTextField avgIns;
    private JTextField avgHeap;

    /**
     * Create a new SortingAlgorithm window and populate it with the user interface.
     */
    public SortingAlgorithms(){
        super("SortingAlgorithms - " + VERSION);

        // Populate frame.
        JPanel mainPanel = new JPanel(new GridBagLayout());

        // Input section.
        JPanel input = new JPanel(new GridBagLayout());
        input.setBorder(BorderFactory.createTitledBorder("Input"));
        GridBagConstraints c = new GridBagConstraints();
        ButtonGroup group = new ButtonGroup();
        this.randomButton = new JRadioButton("Generate Random:");
        this.randomButton.setActionCommand("random");
        this.randomButton.setSelected(true);
        this.randomButton.addActionListener(this);
        group.add(this.randomButton);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 7;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(3, 0, 3, 3);
        input.add(this.randomButton, c);
        JLabel label = new JLabel("Length:");
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.ipadx = 20;
        c.anchor = GridBagConstraints.EAST;
        input.add(label, c);
        ButtonGroup subgroup = new ButtonGroup(); // Make this group horizontal.
        JRadioButton button = new JRadioButton("10");
        button.setActionCommand("10");
        button.setSelected(true);
        button.addActionListener(this);
        subgroup.add(button);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.ipadx = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        input.add(button, c);
        button = new JRadioButton("50");
        button.setActionCommand("50");
        button.addActionListener(this);
        subgroup.add(button);
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        input.add(button, c);
        button = new JRadioButton("10^2");
        button.setActionCommand("10^2");
        button.addActionListener(this);
        subgroup.add(button);
        c.gridx = 4;
        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        input.add(button, c);
        button = new JRadioButton("10^3");
        button.setActionCommand("10^3");
        button.addActionListener(this);
        subgroup.add(button);
        c.gridx = 5;
        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        input.add(button, c);
        button = new JRadioButton("10^5");
        button.setActionCommand("10^5");
        button.addActionListener(this);
        subgroup.add(button);
        c.gridx = 6;
        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        input.add(button, c);
        button = new JRadioButton("10^8");
        button.setActionCommand("10^8");
        button.addActionListener(this);
        subgroup.add(button);
        c.gridx = 7;
        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        input.add(button, c);
        this.customButton = new JRadioButton("Custom (CSV):");
        this.customButton.setActionCommand("custom");
        this.customButton.addActionListener(this);
        group.add(this.customButton);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        input.add(this.customButton, c);
        this.customInput = new JTextField();
        this.customInput.setEnabled(false);
        this.customInput.setEditable(false);
        this.customInput.setPreferredSize(new Dimension(440, 20));
        //customInput.addKeyListener(this);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 7;
        c.anchor = GridBagConstraints.EAST;
        input.add(this.customInput, c);
        this.sortButton = new JButton("Perform Sort");
        this.sortButton.setActionCommand("generate");
        this.sortButton.addActionListener(this);
        this.sortButton.setPreferredSize(new Dimension(140, 20)); // Set button width and height.
        this.sortButton.setToolTipText("The time it takes to generate the random array is not counted against the time it takes to sort the array.");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 8;
        c.anchor = GridBagConstraints.NORTH;
        input.add(this.sortButton, c);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.NORTH;
        mainPanel.add(input, c);

        // Results section
        JPanel results = new JPanel(new GridBagLayout());
        results.setBorder(BorderFactory.createTitledBorder("Results"));

        // Input box.
        JPanel showInputArray = new JPanel(new GridBagLayout());
        showInputArray.setBorder(BorderFactory.createTitledBorder("Input Array"));
        this.showInput  = new JTextField();
        this.showInput.setPreferredSize(new Dimension(5000, 20));
        this.showInput.setEditable(false);
        JScrollPane scroll = new JScrollPane(this.showInput);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(382, 40));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTH;
        showInputArray.add(scroll, c);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.anchor = GridBagConstraints.NORTH;
        results.add(showInputArray, c);

        // Output box.
        JPanel showOutputArray = new JPanel(new GridBagLayout());
        showOutputArray.setBorder(BorderFactory.createTitledBorder("Output Array"));
        this.showOutput  = new JTextField();
        this.showOutput.setPreferredSize(new Dimension(5000, 20));
        this.showOutput.setEditable(false);
        scroll = new JScrollPane(this.showOutput);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(382, 40));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTH;
        showOutputArray.add(scroll, c);
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.NORTH;
        results.add(showOutputArray, c);

        // Time Section.
        JPanel showTime = new JPanel(new GridBagLayout());
        showTime.setBorder(BorderFactory.createTitledBorder("Time"));
        showTime.setPreferredSize(new Dimension(405, 140));
        JPanel panel = new JPanel(new GridBagLayout());
        label = new JLabel("Selection Sort:");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 8, 0);
        c.anchor = GridBagConstraints.WEST;
        panel.add(label, c);
        label = new JLabel("Insertion Sort:");
        c.gridx = 0;
        c.gridy = 1;
        panel.add(label, c);
        label = new JLabel("Heap Sort:");
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 26, 4, 0);
        panel.add(label, c);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.SOUTH;
        showTime.add(panel, c);

        // Results in nanoseconds.
        JPanel showTimeNano = new JPanel(new GridBagLayout());
        showTimeNano.setBorder(BorderFactory.createTitledBorder("Nanoseconds"));
        this.showTimeSel = new JTextField();
        this.showTimeSel.setPreferredSize(new Dimension(160, 20));
        this.showTimeSel.setEditable(false);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(4, 0, 0, 0);
        c.anchor = GridBagConstraints.WEST;
        showTimeNano.add(this.showTimeSel, c);
        this.showTimeIns = new JTextField();
        this.showTimeIns.setPreferredSize(new Dimension(160, 20));
        this.showTimeIns.setEditable(false);
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        showTimeNano.add(this.showTimeIns, c);
        this.showTimeHeap = new JTextField();
        this.showTimeHeap.setPreferredSize(new Dimension(160, 20));
        this.showTimeHeap.setEditable(false);
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        showTimeNano.add(this.showTimeHeap, c);
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        showTime.add(showTimeNano, c);

        // Results in seconds.
        JPanel showTimeSec = new JPanel(new GridBagLayout());
        showTimeSec.setBorder(BorderFactory.createTitledBorder("Seconds"));
        this.showTimeSelSec = new JTextField();
        this.showTimeSelSec.setPreferredSize(new Dimension(80, 20));
        this.showTimeSelSec.setEditable(false);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        showTimeSec.add(this.showTimeSelSec, c);
        this.showTimeInsSec = new JTextField();
        this.showTimeInsSec.setPreferredSize(new Dimension(80, 20));
        this.showTimeInsSec.setEditable(false);
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        showTimeSec.add(this.showTimeInsSec, c);
        this.showTimeHeapSec = new JTextField();
        this.showTimeHeapSec.setPreferredSize(new Dimension(80, 20));
        this.showTimeHeapSec.setEditable(false);
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        showTimeSec.add(this.showTimeHeapSec, c);
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        showTime.add(showTimeSec, c);

        // Add time and results to mainPanel.
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTH;
        results.add(showTime, c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        mainPanel.add(results, c);

        // Past results section.
        JPanel pastResults = new JPanel(new GridBagLayout());
        pastResults.setBorder(BorderFactory.createTitledBorder("Past Results"));
        this.pastResultsTable = new JTable();
        this.pastResultsTableModel = new TableModel();
        this.pastResultsTableModel.addColumn("Selection");
        this.pastResultsTableModel.addColumn("Insertion");
        this.pastResultsTableModel.addColumn("Heapsort");
        this.pastResultsTable.setModel(pastResultsTableModel);
        scroll = new JScrollPane(this.pastResultsTable);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(256, 246));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        pastResults.add(scroll, c);

        label = new JLabel("Avg.:");
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(6, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        pastResults.add(label, c);
        this.avgSel = new JTextField();
        this.avgSel.setPreferredSize(new Dimension(72, 20));
        this.avgSel.setEditable(false);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 0);
        pastResults.add(this.avgSel, c);
        this.avgIns = new JTextField();
        this.avgIns.setPreferredSize(new Dimension(72, 20));
        this.avgIns.setEditable(false);
        c.gridx = 1;
        c.gridy = 2;
        pastResults.add(this.avgIns, c);
        this.avgHeap = new JTextField();
        this.avgHeap.setPreferredSize(new Dimension(72, 20));
        this.avgHeap.setEditable(false);
        c.gridx = 2;
        c.gridy = 2;
        pastResults.add(this.avgHeap, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        mainPanel.add(pastResults, c);

        // Window settings.
        this.add(mainPanel); // Add panel with all elements on it to the JFrame.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null); // Make centered.
        this.setVisible(true);

        // Refresh frame.
        revalidate();
    }

    /**
     * Remove all printed results from the history table, input and output fields, and the second counters.
     */
    private void clearResults(){
        // Clear past results table.
        int i = this.pastResultsTableModel.getRowCount();
        while(i > 0){
            this.pastResultsTableModel.removeRow(i - 1);
            i = this.pastResultsTableModel.getRowCount();
        }

        // Clear average table results.
        this.avgSel.setText("");
        this.avgIns.setText("");
        this.avgHeap.setText("");

        // Clear all current results fields.
        this.showTimeSel.setText("");
        this.showTimeSelSec.setText("");
        this.showTimeIns.setText("");
        this.showTimeInsSec.setText("");
        this.showTimeHeap.setText("");
        this.showTimeHeapSec.setText("");

        // Clear input array.
        this.printInput("");
        this.printOutput("");
    }

    /**
     * Allow the sort button to be clicked again.
     */
    public void enableSortButton(){
        this.sortButton.setEnabled(true);
    }

    /**
     * Show the given {@link String} in the input text field. This method uses a synchronized variable so as to prevent multiple threads from printing at the same time.
     * @param s The {@link String} to show in the text field.
     * @see #printOutput(String)
     */
    public void printInput(String s){
        //Limit the size of the string to be shown to just the first 1000 elements.
        if(s.length() > 4876){
            s = s.substring(0, 4876);
        }

        synchronized (this.showInput) {
            this.showInput.setText(s);
            //this.showOutput.setCaretPosition(0);
        }
    }

    /**
     * Show the given {@link String} in the output text field. This method uses a synchronized variable so as to prevent multiple threads from printing at the same time.
     * @param s The {@link String} to show in the text field.
     * @see #printInput(String)
     */
    public void printOutput(String s){
        //Limit the size of the string to be shown to just the first 1000 elements.
        if(s.length() > 4876){
            s = s.substring(0, 4877);
        }

        synchronized (this.showOutput) {
            this.showOutput.setText(s);
            //this.showOutput.setCaretPosition(0);
        }
    }

    /**
     * Show the Selection Sort run time in the text fields.
     * @param nano The nanoseconds representation of the run time as a {@link Long}.
     * @param sec The seconds representation of the run time given as a {@link String} to allow for formatting.
     * @see DecimalFormat
     */
    public void printTimeSel(long nano, String sec){
        this.showTimeSel.setText("" + nano);
        this.showTimeSelSec.setText(sec);
    }

    /**
     * Show the Insertion Sort run time in the text fields.
     * @param nano The nanoseconds representation of the run time as a {@link Long}.
     * @param sec The seconds representation of the run time given as a {@link String} to allow for formatting.
     * @see DecimalFormat
     */
    public void printTimeIns(long nano, String sec){
        this.showTimeIns.setText("" + nano);
        this.showTimeInsSec.setText(sec);
    }

    /**
     * Show the HeapSort run time in the text fields.
     * @param nano The nanoseconds representation of the run time as a {@link Long}.
     * @param sec The seconds representation of the run time given as a {@link String} to allow for formatting.
     * @see DecimalFormat
     */
    public void printTimeHeap(long nano, String sec){
        this.showTimeHeap.setText("" + nano);
        this.showTimeHeapSec.setText(sec);
    }

    /**
     * Insert the run time results into the past results table. This method uses a synchronized variable so as to prevent multiple threads from changing the table at once.
     * @param pos The column to add the given results to. 0 is the Selection Sort column, 1 is the Insertion Sort column, and 2 is the Heapsort column.
     * @param result The seconds representation of the run time given as a {@link String} to allow for formatting.
     * @see DecimalFormat
     */
    public void addResults(int pos, String result){
        synchronized (this.pastResultsTableModel) {
            int i = this.pastResultsTableModel.getRowCount() - 1; // The address of the last row in the table.
            String sel = this.pastResultsTableModel.getValueAt(i, 0).toString(); // The data for selection sort from the last row.
            String ins = this.pastResultsTableModel.getValueAt(i, 1).toString(); // The data for insertion sort from the last row.
            String heap = this.pastResultsTableModel.getValueAt(i, 2).toString(); // The data for heapsort from the last row.

            // Replace the presumably empty data from the table with the new data.
            switch(pos){
                case 0:
                    sel = result;
                    break;
                case 1:
                    ins = result;
                    break;
                case 2:
                    heap = result;
                    break;
                default:
                    heap = result; // Default to inserting into the heap spot so it is most noticeable if this is done incorrectly.
                    break;
            }
            this.pastResultsTableModel.removeRow(i); // Temporarily remove the row we are editing.
            this.pastResultsTableModel.addRow(new Object[]{sel, ins, heap}); // Reinsert the row with the new data.

            // After adding a new row to the table recalculate the table averages.
            printPastAvg();
        }
    }

    /**
     * Display the averages of the past results columns below the end of the table by adding up each of the entries in each row and dividing by the total number of rows.
     */
    private void printPastAvg(){
        double selAvg = 0.0;
        double insAvg = 0.0;
        double heapAvg = 0.0;

        int i = this.pastResultsTableModel.getRowCount() - 1; // Get the index of the last row.
        while(i >= 0){
            double sel = 0.0;
            double ins = 0.0;
            double heap = 0.0;

            try {
                String s = this.pastResultsTableModel.getValueAt(i, 0).toString(); // The data for selection sort from the current row.
                if(!s.equals("")){ // Ensure that the table data is present already.
                    sel = Double.parseDouble(s);
                }else{
                    sel = 0.0;
                }
                s = this.pastResultsTableModel.getValueAt(i, 1).toString(); // The data for insertion sort from the current row.
                if(!s.equals("")){
                    ins = Double.parseDouble(s);
                }else{
                    ins = 0.0;
                }
                s = this.pastResultsTableModel.getValueAt(i, 2).toString(); // The data for heapsort from the current row.
                if(!s.equals("")){
                    heap = Double.parseDouble(s);
                }else{
                    heap = 0.0;
                }
            }catch(NumberFormatException e){
                if(this.DEBUG){
                    System.out.println("One or more table entries are empty and you are trying to average them.");
                }
            }

            // Sum up the row totals.
            selAvg += sel;
            insAvg += ins;
            heapAvg += heap;

            i--;
        }

        // Get averages from sums.
        i = this.pastResultsTableModel.getRowCount(); // Number of rows to divide by.
        selAvg /= i;
        insAvg /= i;
        heapAvg /= i;

        DecimalFormat df = new DecimalFormat("#.#######");
        String s = df.format(selAvg);
        this.avgSel.setText(s);
        s = df.format(insAvg);
        this.avgIns.setText(s);
        s = df.format(heapAvg);
        this.avgHeap.setText(s);
    }

    private int arrayLength = 10;
    private boolean isRandom = true;
    private int[] array = null;

    /**
     * Performs all of the actions of {@link SortingAlgorithms}, from button presses to radio button selections.
     * @param e The {@link ActionEvent} that triggered the method.
     * @see ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "random":
                this.randomButton.setSelected(true);
                this.customInput.setEnabled(false);
                this.customInput.setEditable(false);
                this.customInput.setText("");
                this.isRandom = true;
                this.sortButton.setToolTipText("The time it takes to generate the random array is not counted against the time it takes to sort the array.");
                break;
            case "custom":
                this.customButton.setSelected(true);
                this.customInput.setEnabled(true);
                this.customInput.setEditable(true);
                this.isRandom = false;
                this.sortButton.setToolTipText("Custom input of more than 100000 integers may cause longer than normal validation times.");
                break;
            case "generate":
                this.sortButton.setEnabled(false); // Prevent multiple sorts at the same time.
                this.pastResultsTableModel.addRow(new Object[]{"", "", ""}); // Create a new blank row in the results table to populate when the sorting is complete.
                String inputBuilder;
                Thread t;

                if(this.isRandom){
                    this.array = new int[arrayLength];

                    if(this.array.length > 1000){
                        this.showInput.setToolTipText("Input arrays longer than 1000 elements only display the first 1000 elements.");
                        this.showOutput.setToolTipText("Output arrays longer than 1000 elements only display the first 1000 elements.");
                    }else{
                        this.showInput.setToolTipText("");
                        this.showOutput.setToolTipText("");
                    }

                    if(this.DEBUG){
                        System.out.println("Generating array of length: " + this.array.length);
                    }
                    inputBuilder = "Generating array of length " + this.array.length + "...";

                    RandomArray ranArray = new RandomArray(this, this.array);
                    t = new Thread(ranArray);
                    t.start();
                }else{
                    String userInput = this.customInput.getText().toString();
                    String[] elements = userInput.split(",");
                    this.array = new int[elements.length];

                    if(this.array.length > 1000){
                        this.showInput.setToolTipText("Input arrays longer than 1000 elements only display the first 1000 elements.");
                        this.showOutput.setToolTipText("Output arrays longer than 1000 elements only display the first 1000 elements.");
                    }else{
                        this.showInput.setToolTipText("");
                        this.showOutput.setToolTipText("");
                    }

                    if(this.DEBUG){
                        System.out.println("Validating array of length: " + this.array.length);
                    }
                    inputBuilder = "Validating array of length " + this.array.length + "...";

                    ValidateArray valArray = new ValidateArray(this, this.array, userInput);
                    t = new Thread(valArray);
                    t.start();
                }

                printInput(inputBuilder);
                printOutput("Limiting output to just the first 1000 sorted elements...");
                break;
            case "sort":
                /* Run all sorts on separate threads so as not to freeze the program during execution. Synchronization
                   does not need to be performed because the only object the threads would share -- the array to be
                   sorted -- is cloned when it is passed in. Each of the threads has their own copy of the object. */
                if(this.DEBUG){
                    System.out.println("Selection Sorting array...");
                }
                this.showTimeSel.setText("Running..."); // Show the sort thread has started on the output field.
                this.showTimeSelSec.setText("Running...");
                new Thread(new SelectionSort(this, this.array)).start();
                if(this.DEBUG){
                    System.out.println("Insertion Sorting array...");
                }
                this.showTimeIns.setText("Running..."); // Show the sort thread has started on the output field.
                this.showTimeInsSec.setText("Running...");
                new Thread(new InsertionSort(this, this.array)).start();
                if(this.DEBUG){
                    System.out.println("Heapsorting array...");
                }
                this.showTimeHeap.setText("Running..."); // Show the sort thread has started on the output field.
                this.showTimeHeapSec.setText("Running...");
                new Thread(new HeapSort(this, this.array)).start();

                this.sortButton.setEnabled(true); // Allow the next sort to be run.
                break;
            case "10":
                this.arrayLength = 10;
                clearResults();
                this.actionPerformed(new ActionEvent(this, 0, "random")); // Make sure the "generate" radio button is pressed when the length button is pressed.
                break;
            case "50":
                this.arrayLength = 50;
                clearResults();
                this.actionPerformed(new ActionEvent(this, 0, "random"));
                break;
            case "10^2":
                this.arrayLength = 100;
                clearResults();
                this.actionPerformed(new ActionEvent(this, 0, "random"));
                break;
            case "10^3":
                this.arrayLength = 1000;
                clearResults();
                this.actionPerformed(new ActionEvent(this, 0, "random"));
                break;
            case "10^5":
                this.arrayLength = 100000;
                clearResults();
                this.actionPerformed(new ActionEvent(this, 0, "random"));
                break;
            case "10^8":
                this.arrayLength = 100000000;
                clearResults();
                this.actionPerformed(new ActionEvent(this, 0, "random"));
                break;
        }
    }
}