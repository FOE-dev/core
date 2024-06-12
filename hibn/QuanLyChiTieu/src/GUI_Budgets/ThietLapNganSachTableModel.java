package GUI_Budgets;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import Entity.budgets;

public class ThietLapNganSachTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"Danh Muc", "Loai", "Amount", "Start Date", "End Date"};
    private List<budgets> budgetsList;

    public ThietLapNganSachTableModel(List<budgets> budgetsList) {
        this.budgetsList = budgetsList;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public int getRowCount() {
        return budgetsList != null ? budgetsList.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (budgetsList == null || rowIndex >= budgetsList.size()) {
            return null;
        }

        budgets budget = budgetsList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return budget.getCategories().getName();
            case 1:
                return budget.getCategories().getType();
            case 2:
                return budget.getAmount();
            case 3:
                return budget.getStart_date();
            case 4:
                return budget.getEnd_date();
            default:
                return null;
        }
    }
}
