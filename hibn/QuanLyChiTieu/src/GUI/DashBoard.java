
package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.CategoriesDAO;
import Entity.categories;
import Entity.users;
import GUI_Budgets.ThietLapNganSach;
import GUI_Categories.DanhMuc;
import GUI_Notification.NhacNhoVaThongBao;
import GUI_Transaction.ChiTieu;
import GUI_Transaction.ThuNhap;
import GUI_User.ThongTin;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Canvas;

public class DashBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelDuLieu;

	private static users user;
	private List<categories> listCategories = new ArrayList<categories>();
	private CategoriesDAO categoryDAO = new CategoriesDAO();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DashBoard frame = new DashBoard(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DashBoard(users user) {
		this.user = user;
		createContent();
	}

	public void createContent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JPanel panelButton = new JPanel();
		panelDuLieu = new JPanel();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panelDuLieu, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
						.addComponent(panelButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panelButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelDuLieu, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
		);
		GroupLayout gl_panelDuLieu = new GroupLayout(panelDuLieu);
		gl_panelDuLieu.setHorizontalGroup(
				gl_panelDuLieu.createParallelGroup(Alignment.LEADING).addGap(0, 664, Short.MAX_VALUE));
		gl_panelDuLieu.setVerticalGroup(
				gl_panelDuLieu.createParallelGroup(Alignment.TRAILING).addGap(0, 199, Short.MAX_VALUE));
		panelDuLieu.setLayout(gl_panelDuLieu);
		contentPane.setLayout(gl_contentPane);

		JButton btnThongTin = new JButton("THONG TIN");
		btnThongTin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				openThongTin();
			}
		});

		JButton btnDanhMuc = new JButton("DANH MUC");
		btnDanhMuc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				openDanhMuc();

			}
		});

		JButton btnCHITIEU = new JButton("CHI TIEU");
		btnCHITIEU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				openChiTieu();

			}
		});

		JButton btnTHUNHAP = new JButton("THU NHAP");
		btnTHUNHAP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				openThuNhap();

			}
		});

		JButton btnTHIETLAPNGANHANG = new JButton("THIET LAP NGAN HANG");
		btnTHIETLAPNGANHANG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				openThietLapNganSach();

			}
		});

		JButton btnNHACNHOCVATHONGBAO = new JButton("NHAC NHO VA THONG BAO");
		btnNHACNHOCVATHONGBAO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				openNhacNhoVaThongBao();

			}
		});
		GroupLayout gl_panelButton = new GroupLayout(panelButton);
		gl_panelButton.setHorizontalGroup(gl_panelButton.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelButton.createSequentialGroup().addGap(5)
						.addComponent(btnThongTin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(5).addComponent(btnDanhMuc, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE).addGap(5)
						.addComponent(btnCHITIEU, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE).addGap(5)
						.addComponent(btnTHUNHAP, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE).addGap(5)
						.addComponent(btnTHIETLAPNGANHANG, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addGap(5).addComponent(btnNHACNHOCVATHONGBAO, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panelButton.setVerticalGroup(gl_panelButton.createParallelGroup(Alignment.LEADING).addGroup(gl_panelButton
				.createSequentialGroup().addGap(5)
				.addGroup(gl_panelButton.createParallelGroup(Alignment.LEADING)
						.addComponent(btnThongTin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnDanhMuc).addComponent(btnCHITIEU).addComponent(btnTHUNHAP)
						.addComponent(btnTHIETLAPNGANHANG).addComponent(btnNHACNHOCVATHONGBAO))
				.addGap(18)));
		panelButton.setLayout(gl_panelButton);

	}

	private void addPanelToDashboard(JPanel panel) {
		panelDuLieu.removeAll();
		panelDuLieu.add(panel);

		GroupLayout gl_panelDuLieu = new GroupLayout(panelDuLieu);
		panelDuLieu.setLayout(gl_panelDuLieu);
		gl_panelDuLieu.setHorizontalGroup(gl_panelDuLieu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDuLieu.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panelDuLieu.setVerticalGroup(gl_panelDuLieu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDuLieu.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));

		panelDuLieu.revalidate();
		panelDuLieu.repaint();
	}

	protected void openThongTin() {
		// TODO Auto-generated method stub
		ThongTin thongtin = new ThongTin(user);
		addPanelToDashboard(thongtin);
	}

	protected void openNhacNhoVaThongBao() {
		NhacNhoVaThongBao nhacNhoVaThongBao = new NhacNhoVaThongBao(user);
		addPanelToDashboard(nhacNhoVaThongBao);
	}

	protected void openThietLapNganSach() {
		ThietLapNganSach thietLapNganSach = new ThietLapNganSach(user, getCategoryList());
		addPanelToDashboard(thietLapNganSach);
	}

	protected void openThuNhap() {
		ThuNhap thuNhap = new ThuNhap(user, getCategoryListIncome());
		addPanelToDashboard(thuNhap);
	}

	protected void openDanhMuc() {
		DanhMuc danhMuc = new DanhMuc(user);
		addPanelToDashboard(danhMuc);
	}

	protected void openChiTieu() {
		ChiTieu chiTieu = new ChiTieu(user, getCategoryListExpense());
		addPanelToDashboard(chiTieu);
	}


	private List<categories> getCategoryList() {
		return categoryDAO.getAllCategoriesByUserID(user.getUser_id());
	}

	private List<categories> getCategoryListExpense() {
		return categoryDAO.getAllCategoriesByUserID_expense(user.getUser_id());
	}

	private List<categories> getCategoryListIncome() {
		return categoryDAO.getAllCategoriesByUserID_income(user.getUser_id());
	}
}