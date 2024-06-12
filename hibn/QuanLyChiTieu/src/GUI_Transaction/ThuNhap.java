package GUI_Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;

import DAO.TransactionsDAO;
import Entity.categories;
import Entity.transactions;
import Entity.users;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JCalendar;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class ThuNhap extends JPanel implements ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private ThuNhapTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField txtAmount;
	private JTextField txtDescription;
	private JDateChooser txtDate;

	private List<categories> listCategories = new ArrayList<categories>();
	private List<transactions> listThuNhap = new ArrayList<transactions>();
	private TransactionsDAO transactionsDAO = new TransactionsDAO();
	private users user;
	private int index = -1;
	private JComboBox comboBoxDanhMuc;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JCalendar calendarThuNhap;

	/**
	 * Create the panel.
	 */
	public ThuNhap(users user, List<categories> listCategories) {

		this.listCategories = listCategories;
		this.user = user;
		createContent();
		table.getSelectionModel().addListSelectionListener(this);
		loadData();
		loadDataComboBox();
	}

	public void loadData() {
		listThuNhap.clear(); // Xóa dữ liệu cũ trước khi thêm mới

		for (categories category : this.listCategories) {
			List<transactions> newListTran = transactionsDAO.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			listThuNhap.addAll(newListTran);
		}
		model = new ThuNhapTableModel(listThuNhap);
		table.setModel(model);
	}

	private void loadDataComboBox() {
		// TODO Auto-generated method stub
		DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<String>();
		for (categories ca : this.listCategories) {
			boxModel.addElement(ca.getName());
		}
		comboBoxDanhMuc.setModel(boxModel);
	}

	private void refreshData() {
		// TODO Auto-generated method stub
		txtAmount.setText("");
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate = calendar.getTime();
		txtDate.setDate(currentDate);
		txtDescription.setText("");
		index = -1;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		index = table.getSelectedRow();
		if (index != -1) {

			transactions tran = listThuNhap.get(index);
			txtAmount.setText(Double.toString(tran.getAmount()));
			txtDate.setDate(tran.getDate());
			txtDescription.setText(tran.getDescription());
			comboBoxDanhMuc.setSelectedIndex(tran.getCategories().getCategory_id());
		}
	}

	protected void updateThuNhap() {
		// TODO Auto-generated method stub
		transactions tran = listThuNhap.get(index);
		tran.setAmount(Double.parseDouble(txtAmount.getText()));
		tran.setDate(new Date(txtDate.getDate().getTime()));
		tran.setDescription(txtDescription.getText());
		transactionsDAO.updateTransactions(tran);
		loadData();
		refreshData();

	}

	protected void deleteThuNhap() {
		// TODO Auto-generated method stub
		if (index < 0)
			return;
		transactionsDAO.deleteTransactions(listThuNhap.get(index).getTransaction_id());
		loadData();
		refreshData();

	}

	protected void addThuNhap() {
		// TODO Auto-generated method stub
		categories ca = new categories();
		for (categories categories : this.listCategories) {
			if (categories.getName().equals(comboBoxDanhMuc.getSelectedItem()))
				ca = categories;
		}
		transactions tran = new transactions(Double.parseDouble(txtAmount.getText()),
				new Date(txtDate.getDate().getTime()), txtDescription.getText(), user, ca);
		transactionsDAO.addTransactions(tran);
		loadData();
		refreshData();

	}

	public void createContent() {

		scrollPane = new JScrollPane();
		setBounds(500, 500, 1001, 634);

		table = new JTable();
		scrollPane.setViewportView(table);

		JScrollPane scrollPane = new JScrollPane();

		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 610,
										Short.MAX_VALUE)
								.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE).addContainerGap()));

		JLabel lblNewLabel = new JLabel("Amount");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblNewLabel_1 = new JLabel("Date");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblNewLabel_1_1 = new JLabel("Description");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 26));

		txtAmount = new JTextField();
		txtAmount.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtAmount.setColumns(10);

		txtDescription = new JTextField();
		txtDescription.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtDescription.setColumns(10);

		txtDate = new JDateChooser();

		JButton btnADD = new JButton("Add");
		btnADD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				addThuNhap();
			}
		});
		btnADD.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnDELETE = new JButton("Delete");
		btnDELETE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				deleteThuNhap();
			}
		});
		btnDELETE.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnUPDATE = new JButton("Update");
		btnUPDATE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				updateThuNhap();
			}
		});
		btnUPDATE.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Danh Mục");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 26));

		comboBoxDanhMuc = new JComboBox();
		comboBoxDanhMuc.setFont(new Font("Tahoma", Font.PLAIN, 26));

		calendarThuNhap = new JCalendar();

		JRadioButton rdbtnDay = new JRadioButton("Day");
		buttonGroup.add(rdbtnDay);
		rdbtnDay.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JRadioButton rdbtnMonth = new JRadioButton("Month");
		buttonGroup.add(rdbtnMonth);
		rdbtnMonth.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JRadioButton rdbtnYear = new JRadioButton("Year");
		buttonGroup.add(rdbtnYear);
		rdbtnYear.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JButton btnbaocao = new JButton("Báo Cáo Chi Tiết");
		btnbaocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnDay.isSelected())
					loadDataDay();
				if (rdbtnMonth.isSelected())
					loadDataMonth();
				if (rdbtnYear.isSelected())
					loadDataYear();
			}

		});
		btnbaocao.setFont(new Font("Tahoma", Font.PLAIN, 20));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_1_1_1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtDate, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addComponent(txtAmount)
								.addComponent(comboBoxDanhMuc, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
				.addGap(18)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnADD, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnUPDATE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnDELETE, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnbaocao, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnDay, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnMonth, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnYear, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(calendarThuNhap, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
				.addGap(8)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(23).addGroup(gl_panel
								.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
								.addComponent(txtAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnADD)).addGap(15)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup().addComponent(lblNewLabel_1)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_1_1).addComponent(txtDescription,
																GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
														.addGroup(gl_panel.createSequentialGroup().addGap(16)
																.addComponent(lblNewLabel_1_1_1_1,
																		GroupLayout.PREFERRED_SIZE, 25,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_panel.createSequentialGroup()
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(comboBoxDanhMuc,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))))
										.addGroup(gl_panel
												.createSequentialGroup().addGap(20).addComponent(btnDELETE).addGap(18)
												.addComponent(btnUPDATE))))
						.addGroup(gl_panel.createSequentialGroup().addGap(27).addGroup(gl_panel
								.createParallelGroup(Alignment.LEADING)
								.addComponent(calendarThuNhap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(
												btnbaocao, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addGap(11)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createSequentialGroup().addComponent(rdbtnDay)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(rdbtnMonth))
												.addComponent(txtDate, GroupLayout.PREFERRED_SIZE, 28,
														GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(rdbtnYear)))))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		table = new JTable();
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}

	protected void loadDataYear() {
		// TODO Auto-generated method stub
		listThuNhap.clear(); // Xóa dữ liệu cũ trước khi thêm mới

		for (categories category : this.listCategories) {
			List<transactions> newListTran = transactionsDAO.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			for (transactions transactions : newListTran) {
				if (transactions.getDate().getYear() == (calendarThuNhap.getYearChooser().getYear() - 1900)) {
					listThuNhap.addAll(newListTran);
				}
			}

			model = new ThuNhapTableModel(listThuNhap);
			table.setModel(model);

		}
	}

	protected void loadDataMonth() {
		// TODO Auto-generated method stub
		listThuNhap.clear(); // Xóa dữ liệu cũ trước khi thêm mới

		for (categories category : this.listCategories) {
			List<transactions> newListTran = transactionsDAO.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			for (transactions transactions : newListTran) {
				if (transactions.getDate().getMonth() == calendarThuNhap.getMonthChooser().getMonth()
						&& transactions.getDate().getYear() == (calendarThuNhap.getYearChooser().getYear() - 1900))
					listThuNhap.addAll(newListTran);
			}
		}

		model = new ThuNhapTableModel(listThuNhap);
		table.setModel(model);

	}

	protected void loadDataDay() {
		// TODO Auto-generated method stub
		listThuNhap.clear(); // Xóa dữ liệu cũ trước khi thêm mới

		for (categories category : this.listCategories) {
			List<transactions> newListTran = transactionsDAO.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			for (transactions transactions : newListTran) {
				if (transactions.getDate().getDate() == calendarThuNhap.getDayChooser().getDay()
						&& transactions.getDate().getMonth() == calendarThuNhap.getMonthChooser().getMonth()
						&& transactions.getDate().getYear() == (calendarThuNhap.getYearChooser().getYear() - 1900))
					listThuNhap.addAll(newListTran);
			}

			model = new ThuNhapTableModel(listThuNhap);
			table.setModel(model);

		}
	}
}
