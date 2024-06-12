package GUI_Budgets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import DAO.BudgetsDAO;
import Entity.budgets;
import Entity.categories;
import Entity.users;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ThietLapNganSach extends JPanel implements ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private ThietLapNganSachTableModel model;
	private JScrollPane scrollPane;
	private JTextField txtAmount;
	private JComboBox<String> comboBox_DanhMuc;
	private JDateChooser txtStart_date;
	private JDateChooser txtEnd_date;

	private List<categories> listCategories = new ArrayList<>();
	private List<budgets> listBudgets = new ArrayList<>();
	private BudgetsDAO budgetsDAO = new BudgetsDAO();
	private users user;
	private int selectedIndex = -1;

	public ThietLapNganSach(users user, List<categories> listCategories) {
		this.user = user;
		this.listCategories = listCategories;
		initializeUI();
		table.getSelectionModel().addListSelectionListener(this);
		loadCategoryData();
		loadBudgetData();
	}

	private void loadBudgetData() {
		listBudgets = budgetsDAO.getAllBudgetsByUserID(user.getUser_id());
		model = new ThietLapNganSachTableModel(listBudgets);
		table.setModel(model);
	}

	private void loadCategoryData() {
		DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
		for (categories category : listCategories) {
			boxModel.addElement(category.getName());
		}
		comboBox_DanhMuc.setModel(boxModel);
	}

	private void clearInputFields() {
		txtAmount.setText("");
		txtStart_date.setDate(Calendar.getInstance().getTime());
		txtEnd_date.setDate(Calendar.getInstance().getTime());
		selectedIndex = -1;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		selectedIndex = table.getSelectedRow();
		if (selectedIndex != -1) {
			budgets selectedBudget = listBudgets.get(selectedIndex);
			txtAmount.setText(Double.toString(selectedBudget.getAmount()));
			txtStart_date.setDate(selectedBudget.getStart_date());
			txtEnd_date.setDate(selectedBudget.getEnd_date());
			comboBox_DanhMuc.setSelectedItem(selectedBudget.getCategories().getName());
		}
	}

	private void updateBudget() {
		if (selectedIndex >= 0) {
			budgets budgetToUpdate = listBudgets.get(selectedIndex);
			budgetToUpdate.setAmount(Double.parseDouble(txtAmount.getText()));
			budgetToUpdate.setStart_date(txtStart_date.getDate());
			budgetToUpdate.setEnd_date(txtEnd_date.getDate());

			String selectedCategoryName = (String) comboBox_DanhMuc.getSelectedItem();
			categories selectedCategory = listCategories.stream()
					.filter(cat -> cat.getName().equals(selectedCategoryName)).findFirst().orElse(null);

			budgetToUpdate.setCategories(selectedCategory);
			budgetsDAO.updateBudgets(budgetToUpdate);
			loadBudgetData();
			clearInputFields();
		}
	}

	private void deleteBudget() {
		if (selectedIndex >= 0) {
			budgetsDAO.deleteBudgets(listBudgets.get(selectedIndex).getBudget_id());
			loadBudgetData();
			clearInputFields();
		}
	}

	private void addBudget() {
		String selectedCategoryName = (String) comboBox_DanhMuc.getSelectedItem();
		categories selectedCategory = listCategories.stream().filter(cat -> cat.getName().equals(selectedCategoryName))
				.findFirst().orElse(null);

		budgets newBudget = new budgets(Double.parseDouble(txtAmount.getText()),
				new java.sql.Date(txtStart_date.getDate().getTime()),
				new java.sql.Date(txtEnd_date.getDate().getTime()), user, selectedCategory);

		budgetsDAO.addBudgets(newBudget);
		loadBudgetData();
		clearInputFields();
	}

	private void initializeUI() {
		scrollPane = new JScrollPane();
		setBounds(500, 500, 686, 513);
		setBackground(new Color(240, 240, 240));

		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 667, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup().addGap(5)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE).addGap(8)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				layout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE).addGap(6)));

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 26));

		txtAmount = new JTextField();
		txtAmount.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtAmount.setColumns(10);

		txtStart_date = new JDateChooser();
		txtEnd_date = new JDateChooser();

		comboBox_DanhMuc = new JComboBox<>();
		comboBox_DanhMuc.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBudget();
			}
		});

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteBudget();
			}
		});

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateBudget();
			}
		});

		GroupLayout panelLayout = new GroupLayout(panel);
		panelLayout.setHorizontalGroup(panelLayout.createParallelGroup(Alignment.LEADING).addGroup(panelLayout
				.createSequentialGroup()
				.addGroup(panelLayout.createParallelGroup(Alignment.LEADING).addComponent(lblAmount)
						.addComponent(lblStartDate).addComponent(lblEndDate).addComponent(lblCategory))
				.addGap(18)
				.addGroup(panelLayout.createParallelGroup(Alignment.LEADING, false).addComponent(txtAmount)
						.addComponent(txtStart_date, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(txtEnd_date, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBox_DanhMuc, 0, 240, Short.MAX_VALUE))
				.addGap(18)
				.addGroup(panelLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnUpdate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panelLayout.setVerticalGroup(panelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(panelLayout.createSequentialGroup()
						.addGroup(panelLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblAmount)
								.addComponent(txtAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAdd))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(panelLayout.createParallelGroup(Alignment.LEADING).addGroup(panelLayout
								.createSequentialGroup()
								.addGroup(panelLayout.createParallelGroup(Alignment.LEADING).addComponent(lblStartDate)
										.addComponent(txtStart_date, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(panelLayout.createParallelGroup(Alignment.LEADING).addComponent(lblEndDate)
										.addComponent(txtEnd_date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
								.addComponent(btnDelete))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(panelLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblCategory)
								.addComponent(comboBox_DanhMuc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnUpdate))
						.addContainerGap(34, Short.MAX_VALUE)));

		panel.setLayout(panelLayout);
		setLayout(layout);
	}
}
