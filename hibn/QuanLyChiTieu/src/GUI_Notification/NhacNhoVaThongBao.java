package GUI_Notification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.toedter.calendar.JDateChooser;
import DAO.NotificationDAO;
import Entity.notifications;
import Entity.users;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NhacNhoVaThongBao extends JPanel implements ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private NhacNhoVaThongBaoTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField txtMessage;
	private JComboBox<String> comboBoxStatus;
	private JDateChooser txtDate;
	private List<notifications> listNotifications;
	private NotificationDAO notificationDAO;
	private users user;
	private int selectedIndex;

	public NhacNhoVaThongBao(users user) {
		this.user = user;
		this.notificationDAO = new NotificationDAO();
		this.listNotifications = new ArrayList<>();
		this.selectedIndex = -1;
		createContent();
		table.getSelectionModel().addListSelectionListener(this);
		loadData();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		selectedIndex = table.getSelectedRow();
		if (selectedIndex != -1) {
			notifications notification = listNotifications.get(selectedIndex);
			txtMessage.setText(notification.getMessage());
			txtDate.setDate(notification.getDate());
			comboBoxStatus.setSelectedItem(notification.getStatus());
		}
	}

	public void loadData() {
		listNotifications = notificationDAO.getAllNotificationsByUserID(user.getUser_id());
		model = new NhacNhoVaThongBaoTableModel(listNotifications);
		table.setModel(model);
	}

	private void refreshData() {
		txtMessage.setText("");
		txtDate.setDate(Calendar.getInstance().getTime());
		comboBoxStatus.setSelectedIndex(-1);
		selectedIndex = -1;
	}

	protected void addNotification() {
		notifications notification = new notifications(txtMessage.getText(),
				new java.sql.Timestamp(txtDate.getDate().getTime()), (String) comboBoxStatus.getSelectedItem(), user);
		notificationDAO.addNotifications(notification);
		loadData();
		refreshData();
	}

	protected void deleteNotification() {
		if (selectedIndex < 0)
			return;
		notificationDAO.deleteNotifications(listNotifications.get(selectedIndex).getNotification_id());
		loadData();
		refreshData();
	}

	protected void updateNotification() {
		if (selectedIndex < 0)
			return;
		notifications notification = listNotifications.get(selectedIndex);
		notification.setMessage(txtMessage.getText());
		notification.setDate(new java.sql.Timestamp(txtDate.getDate().getTime()));
		notification.setStatus((String) comboBoxStatus.getSelectedItem());
		notificationDAO.updateNotification(notification);
		loadData();
		refreshData();
	}

	public void createContent() {
		scrollPane = new JScrollPane();
		setBounds(500, 500, 644, 500);
		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 622, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(6).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE).addGap(0)));

		JLabel lblMessage = new JLabel("Message");
		lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 26));

		txtMessage = new JTextField();
		txtMessage.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtMessage.setColumns(10);

		txtDate = new JDateChooser();

		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNotification();
			}
		});

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteNotification();
			}
		});

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateNotification();
			}
		});

		comboBoxStatus = new JComboBox<>();
		comboBoxStatus.setModel(new DefaultComboBoxModel<>(new String[] { "read", "unread" }));
		comboBoxStatus.setFont(new Font("Tahoma", Font.PLAIN, 26));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(lblMessage, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
						.addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblDate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(
						gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(comboBoxStatus, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtDate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(txtMessage))
				.addGap(49)
				.addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(btnUpdate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addContainerGap(101, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblMessage)
								.addComponent(txtMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAdd))
						.addGap(18)
						.addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblDate)
								.addComponent(txtDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnDelete))
						.addGap(18)
						.addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(btnUpdate)
								.addComponent(lblStatus).addComponent(comboBoxStatus, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}
}
