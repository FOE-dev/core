package GUI_Categories;

import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import DAO.BudgetsDAO;
import DAO.CategoriesDAO;
import DAO.TransactionsDAO;
import Entity.categories;
import Entity.users;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;

public class DanhMuc extends JPanel implements ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private DanhMucTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField txtName;
	private JComboBox<String> comboBoxType;

	private int index = -1;
	private List<categories> listCategories = new ArrayList<>();
	private CategoriesDAO categoriesDAO = new CategoriesDAO();
	private TransactionsDAO transactionsDAO = new TransactionsDAO();
	private BudgetsDAO budgetsDAO = new BudgetsDAO();
	private users user;

	/**
	 * Create the panel.
	 */
	public DanhMuc(users user) {
		this.user = user;
		createContent();
		table.getSelectionModel().addListSelectionListener(this);
		loadData();
	}

	private void refreshData() {
		txtName.setText("");
		comboBoxType.setSelectedIndex(-1);
		index = -1;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		index = table.getSelectedRow();
		if (index != -1) {
			categories category = listCategories.get(index);
			txtName.setText(category.getName());
			comboBoxType.setSelectedItem(category.getType());
		}
	}

	public void loadData() {
		listCategories = categoriesDAO.getAllCategories();
		model = new DanhMucTableModel(listCategories);
		table.setModel(model);
	}

	protected void updateDanhMuc() {
		if (index != -1) {
			categories category = listCategories.get(index);
			category.setName(txtName.getText());
			category.setType((String) comboBoxType.getSelectedItem());
			category.setUser(user);
			categoriesDAO.updateCategories(category);
			loadData();
			refreshData();
		}
	}

	protected void deleteDanhMuc() {
		if (index >= 0) {
			categoriesDAO.deleteCategories(listCategories.get(index).getCategory_id());
			loadData();
			refreshData();
		}
	}

	protected void addDanhMuc() {
		categories category = new categories(txtName.getText(), (String) comboBoxType.getSelectedItem(), user);
		categoriesDAO.addCategories(category);
		loadData();
		refreshData();
	}

	private void createContent() {
		scrollPane = new JScrollPane();
		setBounds(500, 500, 711, 504);

		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE).addContainerGap()));

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 28));

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 28));

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtName.setColumns(10);

		comboBoxType = new JComboBox<>();
		comboBoxType.setModel(new DefaultComboBoxModel<>(new String[] { "income", "expense" }));
		comboBoxType.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDanhMuc();
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteDanhMuc();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDanhMuc();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 20));

		GroupLayout glPanel = new GroupLayout(panel);
		glPanel.setHorizontalGroup(glPanel.createParallelGroup(Alignment.LEADING).addGroup(glPanel
				.createSequentialGroup().addContainerGap()
				.addGroup(glPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblType, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblName, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
				.addGap(26)
				.addGroup(glPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(comboBoxType, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(txtName))
				.addGap(36)
				.addGroup(glPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnUpdate, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(btnAdd, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
				.addGap(31)));
		glPanel.setVerticalGroup(glPanel.createParallelGroup(Alignment.LEADING).addGroup(glPanel.createSequentialGroup()
				.addContainerGap()
				.addGroup(glPanel.createParallelGroup(Alignment.TRAILING).addComponent(lblName)
						.addGroup(glPanel.createParallelGroup(Alignment.LEADING).addComponent(btnAdd).addComponent(
								txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnDelete).addGap(12)
				.addGroup(glPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(glPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblType).addComponent(
								comboBoxType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addComponent(btnUpdate))
				.addContainerGap(12, Short.MAX_VALUE)));
		panel.setLayout(glPanel);

		setLayout(groupLayout);
	}
}
