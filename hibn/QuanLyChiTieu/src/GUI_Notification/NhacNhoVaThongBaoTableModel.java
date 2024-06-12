package GUI_Notification;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import Entity.notifications;

public class NhacNhoVaThongBaoTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] COLUMNS = { "Message", "Date", "Status" };
	private List<notifications> listNotifications;

	public NhacNhoVaThongBaoTableModel(List<notifications> listNotifications) {
		this.listNotifications = listNotifications;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMNS[column];
	}

	@Override
	public int getRowCount() {
		return listNotifications != null ? listNotifications.size() : 0;
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		notifications notification = listNotifications.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return notification.getMessage();
		case 1:
			return notification.getDate();
		case 2:
			return notification.getStatus();
		default:
			return null;
		}
	}
}
