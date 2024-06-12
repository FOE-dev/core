package GUI_Categories;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import Entity.categories;

public class DanhMucTableModel extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = { "Name", "Type" };
	private List<categories> listCategories;

	public DanhMucTableModel(List<categories> listCategories) {
		this.listCategories = listCategories;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public int getRowCount() {
		return (listCategories != null) ? listCategories.size() : 0;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		categories category = listCategories.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return category.getName();
		case 1:
			return category.getType();
		default:
			return null;
		}
	}
}
