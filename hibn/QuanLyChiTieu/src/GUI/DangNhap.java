package GUI;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.mindrot.jbcrypt.BCrypt;

import DAO.CategoriesDAO;
import DAO.UsersDao;
import Entity.users;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;

public class DangNhap extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtGMAIL;
    private JPasswordField txtPASSWORD;
    private JButton btnLOGIN;
    private JButton btnCREATE;

    private users user;
    private UsersDao usersDAO = new UsersDao();
    private CategoriesDAO categoryDAO = new CategoriesDAO();
    Random random = new Random();
	int code = 10000 + random.nextInt(90000);

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DangNhap frame = new DangNhap();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public DangNhap() {
        createContent();
    }

    public void createContent() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 654, 374);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("GMAIL");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));

        JLabel lblNewLabel_1 = new JLabel("PASSWORDS");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 26));

        txtGMAIL = new JTextField();
        txtGMAIL.setFont(new Font("Tahoma", Font.PLAIN, 26));
        txtGMAIL.setColumns(10);

        txtPASSWORD = new JPasswordField();
        txtPASSWORD.setFont(new Font("Tahoma", Font.PLAIN, 26));

        btnLOGIN = new JButton("LOGIN");
        btnLOGIN.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnLOGIN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        btnCREATE = new JButton("CREATE");
        btnCREATE.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnCREATE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDangKi();
            }
        });

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
        						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(txtPASSWORD, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        						.addComponent(txtGMAIL, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)))
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addComponent(btnLOGIN)
        					.addGap(18)
        					.addComponent(btnCREATE)))
        			.addContainerGap(372, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(36)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel)
        				.addComponent(txtGMAIL, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
        			.addGap(24)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_1)
        				.addComponent(txtPASSWORD, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
        			.addGap(97)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnLOGIN)
        				.addComponent(btnCREATE))
        			.addContainerGap(139, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
    }

    protected void openDangKi() {
        DangKi dangki = new DangKi();
        dangki.setVisible(true);
        dispose();
    }

    protected void performLogin() {
        user = usersDAO.performLogin(txtGMAIL.getText());
        if (user != null) {
            boolean checkPass = BCrypt.checkpw(new String(txtPASSWORD.getText()), user.getPassword_hash());
            if (checkPass) {
                user = usersDAO.getUserByEmail(txtGMAIL.getText());
                MFA xacthuc = new MFA(user);
                xacthuc.setVisible(true);
                dispose();
                return; // Thoát khỏi phương thức sau khi đăng nhập thành công
            }
        }
        JOptionPane.showMessageDialog(DangNhap.this,
                "Đăng nhập không thành công. Vui lòng kiểm tra lại email và mật khẩu.",
                "Đăng nhập không thành công", JOptionPane.ERROR_MESSAGE);
    }
    
    public void closeDashBoard()
    {
    	this.disable();
    }
}
