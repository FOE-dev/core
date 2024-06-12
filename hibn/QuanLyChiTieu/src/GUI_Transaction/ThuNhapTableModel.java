package GUI_Transaction;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import Entity.transactions;

public class ThuNhapTableModel extends AbstractTableModel  {

	private static final String[] COLUMN = { "Danh Mục", "Loại", "amount ", "date", "description" };
	private List<transactions> listTransactions;

	public ThuNhapTableModel(List<transactions> listTransactions) {
		super();

		this.listTransactions = listTransactions;
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return COLUMN[column];
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		if (listTransactions != null)
			return listTransactions.size();
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return COLUMN.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		transactions transactions = listTransactions.get(row);
		switch (col) {
		case 0:
			return transactions.getCategories().getName();
		case 1:
			return transactions.getCategories().getType();
		case 2:
			return transactions.getAmount();
		case 3:
			return transactions.getDate();
		case 4:
			return transactions.getDescription();
		case 5:
			return transactions.getCategories().getType();
		}

		return null;
	}
}
