import javax.swing.table.DefaultTableModel;

/**
 * Created by brian on 4/27/17.
 * A {@link DefaultTableModel} with all cells set to be uneditable.
 */
public class TableModel extends DefaultTableModel{

    /**
     * Override the {@link DefaultTableModel}'s isCellEditable(int row, int column) method to ensure that all cells report back that they are not editable.
     * @param row The row of the cell being queried.
     * @param column The column of the cell being queried.
     * @return Always returns false because all cells are not editable.
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
