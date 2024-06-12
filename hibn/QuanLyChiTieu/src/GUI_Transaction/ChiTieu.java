package GUI_Transaction;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import DAO.TransactionsDAO;
import Entity.categories;
import Entity.transactions;
import Entity.users;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import com.toedter.calendar.JCalendar;

public class ChiTieu extends JPanel implements ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private ChiTieuTableModel model;

	private JTable table;
	private JScrollPane scrollPane;
	private JTextField txtAmount;
	private JTextField txtDescription;
	private JDateChooser txtDate;

	private List<categories> listCategories = new ArrayList<categories>();
	private List<transactions> listChiTieu = new ArrayList<>();
	private TransactionsDAO transactionsDAO = new TransactionsDAO();

	private users user;

	private int index = -1;
	JComboBox comboBoxDanhMuc;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JCalendar calendarChiTieu;

	public ChiTieu(users user, List<categories> listCategories) {
		this.listCategories = listCategories;
		this.user = user;
		createContent();
		table.getSelectionModel().addListSelectionListener(this);
		loadDataComboBox();
		loadData();
	}

	private void loadDataComboBox() {
		// TODO Auto-generated method stub
		DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<String>();
		for (categories ca : this.listCategories) {
			boxModel.addElement(ca.getName());
		}

		comboBoxDanhMuc.setModel(boxModel);
	}

	public void loadData() {
		listChiTieu.clear(); // Xóa dữ liệu cũ trước khi thêm mới

		for (categories category : this.listCategories) {
			List<transactions> newListTran = transactionsDAO.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			listChiTieu.addAll(newListTran);
		}

		model = new ChiTieuTableModel(listChiTieu);
		table.setModel(model);

	}

	private void refreshData() {
		txtAmount.setText("");
		txtDate.setDate(Calendar.getInstance().getTime());
		txtDescription.setText("");
		index = -1;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		index = table.getSelectedRow();
		if (index != -1) {
			transactions tran = listChiTieu.get(index);
			txtAmount.setText(Double.toString(tran.getAmount()));
			txtDate.setDate(tran.getDate());
			txtDescription.setText(tran.getDescription());
			comboBoxDanhMuc.setSelectedIndex(tran.getCategories().getCategory_id());
		}
	}

	protected void updateChiTieu() {
		transactions tran = listChiTieu.get(index);
		tran.setAmount(Double.parseDouble(txtAmount.getText()));
		tran.setDate(new java.sql.Date(txtDate.getDate().getTime()));
		tran.setDescription(txtDescription.getText());
		transactionsDAO.updateTransactions(tran);
		loadData();
		refreshData();
	}

	protected void deleteChiTieu() {
		if (index < 0)
			return;
		transactionsDAO.deleteTransactions(listChiTieu.get(index).getTransaction_id());
		loadData();
		refreshData();
	}

	protected void addChiTieu() {
		categories ca = new categories();
		for (categories categories : this.listCategories) {
			if (categories.getName().equals(comboBoxDanhMuc.getSelectedItem())) {
				ca = categories;
				break;
			}
		}
		transactions tran = new transactions(Double.parseDouble(txtAmount.getText()),
				new java.sql.Date(txtDate.getDate().getTime()), txtDescription.getText(), user, ca);
		transactionsDAO.addTransactions(tran);
		loadData();
		refreshData();
	}

	public void createContent() {
		setBounds(500, 500, 1294, 772);

		scrollPane = new JScrollPane();
		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Times New Roman", Font.PLAIN, 22));

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Times New Roman", Font.PLAIN, 22));

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Times New Roman", Font.PLAIN, 22));

		txtAmount = new JTextField();
		txtAmount.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		txtAmount.setColumns(10);

		txtDescription = new JTextField();
		txtDescription.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		txtDescription.setColumns(10);

		JButton btnADD = new JButton("ADD");
		btnADD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addChiTieu();
			}
		});
		btnADD.setFont(new Font("Tahoma", Font.PLAIN, 22));

		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteChiTieu();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 22));

		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateChiTieu();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 22));

		txtDate = new JDateChooser();

		JLabel lblDanhMc = new JLabel("Danh mục");
		lblDanhMc.setFont(new Font("Times New Roman", Font.PLAIN, 22));

		comboBoxDanhMuc = new JComboBox();

		JRadioButton rdbtnDay = new JRadioButton("Day");
		buttonGroup.add(rdbtnDay);
		rdbtnDay.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JRadioButton rdbtnMonth = new JRadioButton("Month");
		buttonGroup.add(rdbtnMonth);
		rdbtnMonth.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JRadioButton rdbtnYear = new JRadioButton("Year");
		buttonGroup.add(rdbtnYear);
		rdbtnYear.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JButton btnBaocao = new JButton("Báo Cáo Chi Tiết");
		btnBaocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rdbtnDay.isSelected())
					loadDataDay();
				if (rdbtnMonth.isSelected())
					loadDataMonth();
				if (rdbtnYear.isSelected())
					loadDataYear();
			}
		});
		btnBaocao.setFont(new Font("Tahoma", Font.PLAIN, 22));

		calendarChiTieu = new JCalendar();

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1274, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(lblDescription, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblDate, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblAmount, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
												.addComponent(lblDanhMc, GroupLayout.PREFERRED_SIZE, 103,
														GroupLayout.PREFERRED_SIZE))
										.addGap(30)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(txtDate, GroupLayout.PREFERRED_SIZE, 297,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
														.addComponent(txtDescription)
														.addComponent(txtAmount, GroupLayout.DEFAULT_SIZE, 297,
																Short.MAX_VALUE)
														.addComponent(comboBoxDanhMuc, 0, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)))
										.addGap(18)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(btnUpdate, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnADD, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
										.addGap(18)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(btnBaocao, GroupLayout.PREFERRED_SIZE, 216,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(calendarChiTieu, GroupLayout.PREFERRED_SIZE, 206,
														GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(rdbtnYear, GroupLayout.PREFERRED_SIZE, 103,
														Short.MAX_VALUE)
												.addComponent(rdbtnMonth, GroupLayout.PREFERRED_SIZE, 103,
														Short.MAX_VALUE)
												.addComponent(rdbtnDay, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(rdbtnDay)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblAmount)
								.addComponent(txtAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnADD)
								.addComponent(btnBaocao, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addComponent(rdbtnMonth)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(rdbtnYear))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
								.createSequentialGroup().addGap(29)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
										.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(txtDate,
												GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(groupLayout.createSequentialGroup().addGap(19).addComponent(lblDate)))
										.addComponent(btnDelete))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblDescription)
												.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDanhMc, GroupLayout.PREFERRED_SIZE, 26,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(comboBoxDanhMuc, GroupLayout.PREFERRED_SIZE, 29,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnUpdate))))
								.addComponent(calendarChiTieu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
				.addGap(46).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 572, GroupLayout.PREFERRED_SIZE)
				.addGap(5)));
		setLayout(groupLayout);
	}

	protected void loadDataYear() {
		// TODO Auto-generated method stub
		listChiTieu.clear(); // Xóa dữ liệu cũ trước khi thêm mới

		for (categories category : this.listCategories) {
			List<transactions> newListTran = transactionsDAO.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			for (transactions transactions : newListTran) {
				if (transactions.getDate().getYear() == (calendarChiTieu.getYearChooser().getYear() - 1900)) {
					listChiTieu.addAll(newListTran);
				}
			}

			model = new ChiTieuTableModel(listChiTieu);
			table.setModel(model);

		}
	}

	protected void loadDataMonth() {
		// TODO Auto-generated method stub
		listChiTieu.clear(); // Xóa dữ liệu cũ trước khi thêm mới

		for (categories category : this.listCategories) {
			List<transactions> newListTran = transactionsDAO.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			for (transactions transactions : newListTran) {
				if (transactions.getDate().getMonth() == calendarChiTieu.getMonthChooser().getMonth()
						&& transactions.getDate().getYear() == (calendarChiTieu.getYearChooser().getYear() - 1900))
					listChiTieu.addAll(newListTran);
			}
		}

		model = new ChiTieuTableModel(listChiTieu);
		table.setModel(model);

	}

	protected void loadDataDay() {
		// TODO Auto-generated method stub
		listChiTieu.clear(); // Xóa dữ liệu cũ trước khi thêm mới

		for (categories category : this.listCategories) {
			List<transactions> newListTran = transactionsDAO.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			for (transactions transactions : newListTran) {
				if (transactions.getDate().getDate() == calendarChiTieu.getDayChooser().getDay()
						&& transactions.getDate().getMonth() == calendarChiTieu.getMonthChooser().getMonth()
						&& transactions.getDate().getYear() == (calendarChiTieu.getYearChooser().getYear() - 1900)) 
					listChiTieu.addAll(newListTran);
			}
		}

		model = new ChiTieuTableModel(listChiTieu);
		table.setModel(model);

	}
}
