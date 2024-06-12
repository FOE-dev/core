package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.mindrot.jbcrypt.BCrypt;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Random;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import DAO.UsersDao;
import Entity.users;

public class DangKi extends JFrame {

	private JPanel contentPane;
	private JTextField txtNAME;
	private JTextField txtGMAIL;
	private JPasswordField txtPASS;
	private JButton btnCREATE;
	private JTextField txtMA;
	private JButton btnSENDCODE;
	private JButton btnLOGIN;

	private UsersDao userDAO = new UsersDao();

	Random random = new Random();
	int code = 10000 + random.nextInt(90000);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DangKi frame = new DangKi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DangKi() {
		createContent();
	}

	private void createContent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("NAME");
		JLabel lblNewLabel_1 = new JLabel("GMAIL");
		JLabel lblNewLabel_2 = new JLabel("PASS");
		JLabel lblNewLabel_3 = new JLabel("CODE");

		txtNAME = new JTextField();
		txtNAME.setColumns(10);
		txtGMAIL = new JTextField();
		txtGMAIL.setColumns(10);
		txtPASS = new JPasswordField();
		txtMA = new JTextField();
		txtMA.setColumns(10);

		btnCREATE = new JButton("CREATE");
		btnCREATE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtNAME.getText().trim();
				String email = txtGMAIL.getText().trim();
				String password = new String(txtPASS.getPassword()).trim();
				String password_hash = BCrypt.hashpw(password, BCrypt.gensalt());
				String codeNhap = txtMA.getText().trim();

				if (name.isEmpty() || email.isEmpty() || password.isEmpty() || codeNhap.isEmpty()) {
					JOptionPane.showMessageDialog(DangKi.this, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				} else {
					if (codeNhap.equals(Integer.toString(code))) {
						users user = new users();
						user.setUsername(name);
						user.setEmail(email);
						user.setPassword_hash(password_hash);
						user.setCreated_at(new Timestamp(System.currentTimeMillis()));
						userDAO.addUser(user);

						JOptionPane.showMessageDialog(DangKi.this, "Đăng ký thành công!");
					} else {
						JOptionPane.showMessageDialog(DangKi.this, "Mã xác nhận không đúng!", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btnSENDCODE = new JButton("SEND CODE");
		btnSENDCODE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userDAO.isEmailExists(txtGMAIL.getText()))
					JOptionPane.showMessageDialog(DangKi.this, "Email Đã Tồn Tại");
				sendCodeByEmail(txtGMAIL.getText(), code);
				System.out.println(code);
			}
		});

		btnLOGIN = new JButton("LOGIN");
		btnLOGIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DangNhap dangnhap = new DangNhap();
				dangnhap.setVisible(true);
				dispose();
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(txtNAME, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 122,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(txtGMAIL, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 122,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(txtPASS, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 108,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(txtMA,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(btnCREATE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(btnSENDCODE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(btnLOGIN)))
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(23)
						.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lblNewLabel).addComponent(txtNAME, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lblNewLabel_1).addComponent(txtGMAIL, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lblNewLabel_2).addComponent(txtPASS, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lblNewLabel_3).addComponent(txtMA, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(btnCREATE).addComponent(btnSENDCODE).addComponent(btnLOGIN))
						.addGap(34)));
		contentPane.setLayout(gl_contentPane);
	}

	protected void sendCodeByEmail(String email, int code) {
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("ptttirom@gmail.com", "vfkw ynkc yduk lmaq");
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ptttirom@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Mã xác nhận đăng ký");
			message.setText("Mã xác nhận của bạn là: " + code);

			Transport.send(message);

			JOptionPane.showMessageDialog(this, "Đã gửi mã xác nhận thành công!");

		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi gửi mã xác nhận: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
