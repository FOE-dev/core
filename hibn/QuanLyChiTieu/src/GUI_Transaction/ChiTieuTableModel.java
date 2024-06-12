package GUI_Transaction;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import Entity.categories;
import Entity.transactions;

public class ChiTieuTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] COLUMN_NAMES = { "Danh Mục", "Loại", "Số Tiền", "Ngày", "Mô Tả" };
	private List<transactions> transactionsList;

	public ChiTieuTableModel(List<transactions> transactionsList) {
		this.transactionsList = transactionsList;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public int getRowCount() {
		if (transactionsList != null) {
			return transactionsList.size();
		}
		return 0;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		transactions transaction = transactionsList.get(rowIndex);
		categories category = transaction.getCategories();

		switch (columnIndex) {
		case 0:
			return category.getName();
		case 1:
			return category.getType();
		case 2:
			return transaction.getAmount();
		case 3:
			return transaction.getDate();
		case 4:
			return transaction.getDescription();
		default:
			return null;
		}
	}
}
