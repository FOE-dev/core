package GUI_User;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import DAO.BudgetsDAO;
import DAO.CategoriesDAO;
import DAO.NotificationDAO;
import DAO.TransactionsDAO;
import DAO.UsersDao;
import Entity.budgets;
import Entity.categories;
import Entity.notifications;
import Entity.transactions;
import Entity.users;
import GUI.DangNhap;
import GUI.DashBoard;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.toedter.calendar.JCalendar;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class ThongTin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtGmail;
	private JTextField txtSoDu;
	private JTextField txtTongThuNhap;
	private JTextField txtTongChiTieu;
	private JButton btnTime;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel panelBieuDo;
	private JPanel panelThongTin;
	private JFreeChart bieuDoTongQuat;
	private JFreeChart bieuDoChiTiet;

	private double totalIncome;
	private double totalExpense;
	private double sodu;

	private users user;
	private List<categories> listCategories = new ArrayList<categories>();
	private List<transactions> listTransactions = new ArrayList<transactions>();
	private List<budgets> listBudgets = new ArrayList<budgets>();
	private List<notifications> listNotification = new ArrayList<notifications>();
	private UsersDao userDao = new UsersDao();
	private BudgetsDAO budgetsDao = new BudgetsDAO();
	private CategoriesDAO categoriesDao = new CategoriesDAO();
	private NotificationDAO notificationDao = new NotificationDAO();
	private TransactionsDAO transactionDao = new TransactionsDAO();

	private BieuDoTheoThoiGian bieudotime = new BieuDoTheoThoiGian();
	private JLabel lblNhacNho;
	private JLabel lblCanhbao;

	/**
	 * Create the panel.
	 */
	public ThongTin(users user) {
		this.user = user;
		createContent();
		CanhBao();
		NhacNho();
	}

	public void createContent() {
		bieuDoTongQuat = bieuDoTongQuat();
		bieuDoChiTiet = bieuDoChiTiet();
		panelThongTin = createThongTinPanel();
		panelBieuDo = createBieuDoPanel(bieuDoTongQuat, bieuDoChiTiet);
		GroupLayout groupLayout = createGroupLayout(panelThongTin, panelBieuDo);
		setLayout(groupLayout);

	}

	private JPanel createThongTinPanel() {

		JPanel panelThongTin = new JPanel();
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblGmail = new JLabel("Gmail");
		lblGmail.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblSoDu = new JLabel("Số Dư Hiện Tại");
		lblSoDu.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblTongThuNhap = new JLabel("Tổng thu nhập");
		lblTongThuNhap.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JLabel lblTongChiTieu = new JLabel("Tổng Chi Tiêu");
		lblTongChiTieu.setFont(new Font("Tahoma", Font.PLAIN, 26));

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtName.setColumns(10);

		txtGmail = new JTextField();
		txtGmail.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtGmail.setColumns(10);

		txtSoDu = new JTextField();
		txtSoDu.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtSoDu.setColumns(10);

		txtTongChiTieu = new JTextField();
		txtTongChiTieu.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtTongChiTieu.setColumns(10);

		txtTongThuNhap = new JTextField();
		txtTongThuNhap.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtTongThuNhap.setColumns(10);

		JLabel lblThiGian = new JLabel("Thời Gian");
		lblThiGian.setFont(new Font("Tahoma", Font.PLAIN, 26));

		JCalendar calendarBieuDo = new JCalendar();

		JRadioButton rdbtnDay = new JRadioButton("Day");
		rdbtnDay.setFont(new Font("Tahoma", Font.PLAIN, 26));
		buttonGroup.add(rdbtnDay);

		JRadioButton rdbtnMonth = new JRadioButton("Month");
		rdbtnMonth.setFont(new Font("Tahoma", Font.PLAIN, 26));
		buttonGroup.add(rdbtnMonth);

		JRadioButton rdbtnYear = new JRadioButton("Year");
		rdbtnYear.setFont(new Font("Tahoma", Font.PLAIN, 26));
		buttonGroup.add(rdbtnYear);

		totalIncome = bieudotime.totalAmountInTransactions_Income(user);
		totalExpense = bieudotime.totalAmountInTransactions_Expense(user);
		sodu = totalIncome - totalExpense;

		txtName.setText(user.getUsername());
		txtGmail.setText(user.getEmail());
		txtSoDu.setText(Double.toString(sodu));
		txtTongChiTieu.setText(Double.toString(totalExpense));
		txtTongThuNhap.setText(Double.toString(totalIncome));

		btnTime = new JButton("Chọn Thời Gian");
		btnTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnDay.isSelected())
					bieuDo_day(calendarBieuDo);
				if (rdbtnMonth.isSelected())
					bieuDo_Month(calendarBieuDo);
				if (rdbtnYear.isSelected())
					bieuDo_Year(calendarBieuDo);
			}
		});
		btnTime.setFont(new Font("Tahoma", Font.PLAIN, 26));

		lblCanhbao = new JLabel("CanhBao");
		lblCanhbao.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCanhbao.setVisible(false);

		lblNhacNho = new JLabel("NhacNho");
		lblNhacNho.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNhacNho.setVisible(false);

		JButton btnXaNgiDng = new JButton("Xóa Người Dùng");
		btnXaNgiDng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userDao.deleteUser(user.getUser_id());
				DangNhap dn = new DangNhap();
				dn.setVisible(true);

				JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				parentFrame.dispose();

			}
		});
		btnXaNgiDng.setFont(new Font("Tahoma", Font.PLAIN, 26));

		GroupLayout gl_panelThongTin = new GroupLayout(panelThongTin);
		gl_panelThongTin.setHorizontalGroup(gl_panelThongTin.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelThongTin.createSequentialGroup().addContainerGap().addGroup(gl_panelThongTin
						.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNhacNho, GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
						.addGroup(gl_panelThongTin.createSequentialGroup().addGroup(gl_panelThongTin
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelThongTin.createSequentialGroup()
										.addComponent(rdbtnMonth, GroupLayout.PREFERRED_SIZE, 103,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_panelThongTin.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panelThongTin.createSequentialGroup()
												.addComponent(rdbtnDay, GroupLayout.PREFERRED_SIZE, 103,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_panelThongTin.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panelThongTin.createSequentialGroup()
														.addComponent(lblThiGian, GroupLayout.PREFERRED_SIZE, 201,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED))
												.addGroup(gl_panelThongTin.createParallelGroup(Alignment.LEADING)
														.addGroup(gl_panelThongTin.createSequentialGroup()
																.addGroup(gl_panelThongTin
																		.createParallelGroup(Alignment.TRAILING)
																		.addComponent(lblSoDu, GroupLayout.DEFAULT_SIZE,
																				326, Short.MAX_VALUE)
																		.addComponent(lblTongThuNhap, Alignment.LEADING,
																				GroupLayout.DEFAULT_SIZE, 326,
																				Short.MAX_VALUE)
																		.addComponent(lblTongChiTieu,
																				GroupLayout.PREFERRED_SIZE, 326,
																				Short.MAX_VALUE))
																.addPreferredGap(ComponentPlacement.RELATED))
														.addComponent(lblName, Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
														.addComponent(lblGmail, Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))))
								.addGroup(gl_panelThongTin.createSequentialGroup()
										.addComponent(rdbtnYear, GroupLayout.PREFERRED_SIZE, 103,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_panelThongTin.createSequentialGroup()
										.addComponent(lblCanhbao, GroupLayout.PREFERRED_SIZE, 272,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(gl_panelThongTin.createParallelGroup(Alignment.TRAILING).addComponent(btnTime)
										.addGroup(gl_panelThongTin.createParallelGroup(Alignment.TRAILING)
												.addComponent(calendarBieuDo, GroupLayout.DEFAULT_SIZE, 365,
														Short.MAX_VALUE)
												.addComponent(txtName, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
												.addComponent(txtTongChiTieu, GroupLayout.DEFAULT_SIZE, 365,
														Short.MAX_VALUE)
												.addComponent(txtTongThuNhap, GroupLayout.DEFAULT_SIZE, 365,
														Short.MAX_VALUE)
												.addComponent(txtSoDu, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
												.addComponent(txtGmail, GroupLayout.DEFAULT_SIZE, 365,
														Short.MAX_VALUE))))
						.addComponent(btnXaNgiDng, Alignment.TRAILING)).addContainerGap()));
		gl_panelThongTin
				.setVerticalGroup(
						gl_panelThongTin.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelThongTin.createSequentialGroup().addContainerGap()
										.addGroup(gl_panelThongTin.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblName).addComponent(txtName, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_panelThongTin.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblGmail)
												.addComponent(txtGmail, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_panelThongTin.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblSoDu).addComponent(txtSoDu, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_panelThongTin.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblTongThuNhap)
												.addComponent(txtTongThuNhap, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_panelThongTin.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblTongChiTieu)
												.addComponent(txtTongChiTieu, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_panelThongTin.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panelThongTin.createSequentialGroup()
														.addComponent(lblThiGian, GroupLayout.PREFERRED_SIZE, 32,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(rdbtnDay)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(rdbtnMonth)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(rdbtnYear))
												.addComponent(calendarBieuDo, GroupLayout.PREFERRED_SIZE, 165,
														GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panelThongTin.createParallelGroup(Alignment.TRAILING)
												.addComponent(btnTime).addComponent(lblCanhbao,
														GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblNhacNho, GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
										.addComponent(btnXaNgiDng, GroupLayout.PREFERRED_SIZE, 41,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		panelThongTin.setLayout(gl_panelThongTin);
		return panelThongTin;
	}

	private JPanel createBieuDoPanel(JFreeChart chart1, JFreeChart chart2) {
		JPanel panelBieuDo = new JPanel();

		ChartPanel BieuDoTongQuat = new ChartPanel(chart1);
		ChartPanel BieuDoChiTiet = new ChartPanel(chart2);

		BieuDoChiTiet.setBackground(new Color(240, 240, 240));
		GroupLayout gl_BieuDoTongQuat = new GroupLayout(BieuDoTongQuat);
		gl_BieuDoTongQuat.setHorizontalGroup(
				gl_BieuDoTongQuat.createParallelGroup(Alignment.LEADING).addGap(0, 576, Short.MAX_VALUE));
		gl_BieuDoTongQuat.setVerticalGroup(
				gl_BieuDoTongQuat.createParallelGroup(Alignment.LEADING).addGap(0, 768, Short.MAX_VALUE));
		BieuDoTongQuat.setLayout(gl_BieuDoTongQuat);
		GroupLayout gl_BieuDoChiTiet = new GroupLayout(BieuDoChiTiet);
		gl_BieuDoChiTiet.setHorizontalGroup(
				gl_BieuDoChiTiet.createParallelGroup(Alignment.LEADING).addGap(0, 576, Short.MAX_VALUE));
		gl_BieuDoChiTiet.setVerticalGroup(
				gl_BieuDoChiTiet.createParallelGroup(Alignment.LEADING).addGap(0, 768, Short.MAX_VALUE));
		BieuDoChiTiet.setLayout(gl_BieuDoChiTiet);
		GroupLayout gl_panelBieuDo = new GroupLayout(panelBieuDo);
		gl_panelBieuDo.setHorizontalGroup(gl_panelBieuDo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBieuDo.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelBieuDo.createParallelGroup(Alignment.LEADING)
								.addComponent(BieuDoChiTiet, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 556,
										Short.MAX_VALUE)
								.addComponent(BieuDoTongQuat, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 556,
										Short.MAX_VALUE))
						.addContainerGap()));
		gl_panelBieuDo.setVerticalGroup(gl_panelBieuDo.createParallelGroup(Alignment.LEADING).addGroup(gl_panelBieuDo
				.createSequentialGroup().addComponent(BieuDoTongQuat, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(BieuDoChiTiet, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE).addContainerGap()));
		panelBieuDo.setLayout(gl_panelBieuDo);
		return panelBieuDo;
	}

	private GroupLayout createGroupLayout(JPanel panelThongTin, JPanel panelBieuDo) {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panelThongTin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelBieuDo, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(panelBieuDo, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 633,
										Short.MAX_VALUE)
								.addComponent(panelThongTin, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 457,
										Short.MAX_VALUE))
						.addContainerGap()));
		return groupLayout;
	}

	private JFreeChart bieuDoTongQuat() {
		// TODO Auto-generated method stub

		DefaultPieDataset dataTongQuat = new DefaultPieDataset();
		JFreeChart bieuDoChiTiet = ChartFactory.createPieChart("Tổng Quát", dataTongQuat, true, true, false);
		dataTongQuat.setValue("Tổng chi tiêu", bieudotime.totalAmountInTransactions_Expense(user));
		dataTongQuat.setValue("Tổng thu nhập", bieudotime.totalAmountInTransactions_Income(user));
		return bieuDoChiTiet;
	}

	private JFreeChart bieuDoChiTiet() {

		// TODO Auto-generated method stub
		DefaultPieDataset dataChiTiet = new DefaultPieDataset();
		JFreeChart bieuDoChiTiet = ChartFactory.createPieChart("Chi Tiết", dataChiTiet, true, true, false);
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_income(user.getUser_id());

		for (categories category : listTransactionIncome) {
			double tong = 0;
			for (transactions transaction : category.getListTransactions()) {
				tong += transaction.getAmount();
			}
			dataChiTiet.setValue(category.getName(), tong);
		}

		List<categories> listTransactionExpense = categoriesDao.getAllCategoriesByUserID_expense(user.getUser_id());

		for (categories category : listTransactionExpense) {
			double tong = 0;
			for (transactions transaction : category.getListTransactions()) {
				tong += transaction.getAmount();
			}
			dataChiTiet.setValue(category.getName(), tong);
		}

		return bieuDoChiTiet;
	}

	private JFreeChart bieuDoTongQuatByDay(JCalendar calendarBieuDo) {
		DefaultPieDataset dataTongQuat = new DefaultPieDataset();
		JFreeChart bieuDoChiTiet = ChartFactory.createPieChart("Tổng Quát", dataTongQuat, true, true, false);
		dataTongQuat.setValue("Tổng chi tiêu", bieudotime.totalAmountInTransactions_Expense_Day(user, calendarBieuDo));
		dataTongQuat.setValue("Tổng thu nhập", bieudotime.totalAmountInTransactions_Income_Day(user, calendarBieuDo));
		return bieuDoChiTiet;
	}

	private JFreeChart bieuDoChiTietByDay(JCalendar calendarBieuDo) {
		DefaultPieDataset dataChiTiet = new DefaultPieDataset();
		JFreeChart bieuDoChiTiet = ChartFactory.createPieChart("Chi Tiết", dataChiTiet, true, true, false);
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_income(user.getUser_id());

		for (categories category : listTransactionIncome) {
			double tong = 0;
			String categoryName = "";
			for (transactions transaction : category.getListTransactions()) {
				if (transaction.getDate().getDate() == calendarBieuDo.getDayChooser().getDay()
						&& transaction.getDate().getMonth() == calendarBieuDo.getMonthChooser().getMonth()
						&& transaction.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {
					categoryName = transaction.getCategories().getName();
					tong += transaction.getAmount();
				}
			}
			dataChiTiet.setValue(categoryName, tong);
		}

		List<categories> listTransactionExpense = categoriesDao.getAllCategoriesByUserID_expense(user.getUser_id());

		for (categories category : listTransactionExpense) {

			for (transactions transaction : category.getListTransactions()) {
				double tong = 0;
				String categoryName = "";
				if (transaction.getDate().getDate() == calendarBieuDo.getDayChooser().getDay()
						&& transaction.getDate().getMonth() == calendarBieuDo.getMonthChooser().getMonth()
						&& transaction.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {
					{
						categoryName = transaction.getCategories().getName();
						tong += transaction.getAmount();
					}
				}
				dataChiTiet.setValue(categoryName, tong);
			}
		}

		return bieuDoChiTiet;
	}

	private JFreeChart bieuDoTongQuatByYear(JCalendar calendarBieuDo) {
		DefaultPieDataset dataTongQuat = new DefaultPieDataset();
		JFreeChart bieuDoChiTiet = ChartFactory.createPieChart("Tổng Quát", dataTongQuat, true, true, false);
		dataTongQuat.setValue("Tổng chi tiêu", bieudotime.totalAmountInTransactions_Expense_Year(user, calendarBieuDo));
		dataTongQuat.setValue("Tổng thu nhập", bieudotime.totalAmountInTransactions_Income_Year(user, calendarBieuDo));
		return bieuDoChiTiet;
	}

	private JFreeChart bieuDoChiTietByYear(JCalendar calendarBieuDo) {
		DefaultPieDataset dataChiTiet = new DefaultPieDataset();
		JFreeChart bieuDoChiTiet = ChartFactory.createPieChart("Chi Tiết", dataChiTiet, true, true, false);
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_income(user.getUser_id());

		for (categories category : listTransactionIncome) {
			double tong = 0;
			String categoryName = "";
			for (transactions transaction : category.getListTransactions()) {
				if (transaction.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {
					categoryName = transaction.getCategories().getName();
					tong += transaction.getAmount();
				}
			}
			dataChiTiet.setValue(categoryName, tong);
		}

		List<categories> listTransactionExpense = categoriesDao.getAllCategoriesByUserID_expense(user.getUser_id());

		for (categories category : listTransactionExpense) {
			double tong = 0;
			String categoryName = "";
			for (transactions transaction : category.getListTransactions()) {

				if (transaction.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {
					{
						categoryName = transaction.getCategories().getName();
						tong += transaction.getAmount();
					}
				}
			}
			dataChiTiet.setValue(categoryName, tong);
		}

		return bieuDoChiTiet;
	}

	private JFreeChart bieuDoTongQuatByMonth(JCalendar calendarBieuDo) {
		DefaultPieDataset dataTongQuat = new DefaultPieDataset();
		JFreeChart bieuDoChiTiet = ChartFactory.createPieChart("Tổng Quát", dataTongQuat, true, true, false);
		dataTongQuat.setValue("Tổng chi tiêu",
				bieudotime.totalAmountInTransactions_Expense_Month(user, calendarBieuDo));
		dataTongQuat.setValue("Tổng thu nhập", bieudotime.totalAmountInTransactions_Income_Month(user, calendarBieuDo));
		return bieuDoChiTiet;
	}

	private JFreeChart bieuDoChiTietByMonthy(JCalendar calendarBieuDo) {
		DefaultPieDataset dataChiTiet = new DefaultPieDataset();
		JFreeChart bieuDoChiTiet = ChartFactory.createPieChart("Chi Tiết", dataChiTiet, true, true, false);
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_income(user.getUser_id());

		for (categories category : listTransactionIncome) {

			for (transactions transaction : category.getListTransactions()) {
				double tong = 0;
				String categoryName = "";
				if (transaction.getDate().getMonth() == calendarBieuDo.getMonthChooser().getMonth()
						&& transaction.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {
					categoryName = transaction.getCategories().getName();
					tong += transaction.getAmount();
				}
				dataChiTiet.setValue(categoryName, tong);
			}
		}

		List<categories> listTransactionExpense = categoriesDao.getAllCategoriesByUserID_expense(user.getUser_id());

		for (categories category : listTransactionExpense) {

			for (transactions transaction : category.getListTransactions()) {
				double tong = 0;
				String categoryName = "";
				if (transaction.getDate().getMonth() == calendarBieuDo.getMonthChooser().getMonth()
						&& transaction.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {
					{
						categoryName = transaction.getCategories().getName();
						tong += transaction.getAmount();
					}
				}
				dataChiTiet.setValue(categoryName, tong);
			}
		}

		return bieuDoChiTiet;
	}

	protected void bieuDo_Year(JCalendar calendarBieuDo) {
		removeAll();

		bieuDoTongQuat = bieuDoTongQuatByYear(calendarBieuDo);
		bieuDoChiTiet = bieuDoChiTietByYear(calendarBieuDo);

		panelBieuDo = createBieuDoPanel(bieuDoTongQuat, bieuDoChiTiet);
		GroupLayout groupLayout = createGroupLayout(panelThongTin, panelBieuDo);
		setLayout(groupLayout);

		panelBieuDo.revalidate();
		panelBieuDo.repaint();

		totalIncome = bieudotime.totalAmountInTransactions_Income_Year(user, calendarBieuDo);
		totalExpense = bieudotime.totalAmountInTransactions_Expense_Year(user, calendarBieuDo);
		sodu = totalIncome - totalExpense;

		txtName.setText(user.getUsername());
		txtGmail.setText(user.getEmail());
		txtSoDu.setText(Double.toString(sodu));
		txtTongChiTieu.setText(Double.toString(totalExpense));
		txtTongThuNhap.setText(Double.toString(totalIncome));
	}

	protected void bieuDo_Month(JCalendar calendarBieuDo) {
		// TODO Auto-generated method stub
		removeAll();

		bieuDoTongQuat = bieuDoTongQuatByMonth(calendarBieuDo);
		bieuDoChiTiet = bieuDoChiTietByMonthy(calendarBieuDo);

		panelBieuDo = createBieuDoPanel(bieuDoTongQuat, bieuDoChiTiet);
		GroupLayout groupLayout = createGroupLayout(panelThongTin, panelBieuDo);
		setLayout(groupLayout);

		panelBieuDo.revalidate();
		panelBieuDo.repaint();

		totalIncome = bieudotime.totalAmountInTransactions_Income_Month(user, calendarBieuDo);
		totalExpense = bieudotime.totalAmountInTransactions_Expense_Month(user, calendarBieuDo);
		sodu = totalIncome - totalExpense;

		txtName.setText(user.getUsername());
		txtGmail.setText(user.getEmail());
		txtSoDu.setText(Double.toString(sodu));
		txtTongChiTieu.setText(Double.toString(totalExpense));
		txtTongThuNhap.setText(Double.toString(totalIncome));

	}

	protected void bieuDo_day(JCalendar calendarBieuDo) {
		// Cập nhật dữ liệu cho ngày và vẽ lại biểu đồ
		removeAll();

		bieuDoTongQuat = bieuDoTongQuatByDay(calendarBieuDo);
		bieuDoChiTiet = bieuDoChiTietByDay(calendarBieuDo);

		panelBieuDo = createBieuDoPanel(bieuDoTongQuat, bieuDoChiTiet);
		GroupLayout groupLayout = createGroupLayout(panelThongTin, panelBieuDo);
		setLayout(groupLayout);

		panelBieuDo.revalidate();
		panelBieuDo.repaint();

		totalIncome = bieudotime.totalAmountInTransactions_Income_Day(user, calendarBieuDo);
		totalExpense = bieudotime.totalAmountInTransactions_Expense_Day(user, calendarBieuDo);
		sodu = totalIncome - totalExpense;

		txtName.setText(user.getUsername());
		txtGmail.setText(user.getEmail());
		txtSoDu.setText(Double.toString(sodu));
		txtTongChiTieu.setText(Double.toString(totalExpense));
		txtTongThuNhap.setText(Double.toString(totalIncome));
	}

	private void CanhBao() {

		List<categories> listCategories = new ArrayList<categories>();
		List<transactions> listTransaction = new ArrayList<transactions>();
		List<budgets> listBudget = new ArrayList<budgets>();
		listCategories = categoriesDao.getAllCategoriesByUserID_expense(user.getUser_id());
		for (categories categories : listCategories) {
			double totalTran = 0;
			double totalBud = 0;

			listTransaction = transactionDao.getAllTransactions_byUser_id(user.getUser_id(),
					categories.getCategory_id());
			for (transactions transaction : listTransaction) {
				totalTran += transaction.getAmount();
			}

			listBudget = budgetsDao.getAllBudgetsByUserID_CategoryId(user.getUser_id(), categories.getCategory_id());
			for (budgets budgets : listBudget) {
				totalBud += budgets.getAmount();
			}

			if (totalTran > totalBud) {
				lblCanhbao.setVisible(true);
				lblCanhbao.setText("Chi tieu vuot ngan sach");
			}

		}

	}

	private void NhacNho() {
		long millis = System.currentTimeMillis();
		Date sqlDate = new Date(millis);
		listNotification = notificationDao.getAllNotificationsByUserID(user.getUser_id());
		for (notifications notification : listNotification) {
			if (notification.getDate().getDate() == sqlDate.getDate()
					&& notification.getDate().getMonth() == sqlDate.getMonth()
					&& notification.getDate().getYear() == sqlDate.getYear()) {
				lblNhacNho.setVisible(true);
				lblNhacNho.setText("Đến Hạn Đóng Tiền : " + notification.getMessage());
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}