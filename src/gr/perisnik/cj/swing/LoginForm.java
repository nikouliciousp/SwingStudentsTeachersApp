package gr.perisnik.cj.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.mindrot.jbcrypt.BCrypt;

import gr.perisnik.cj.swing.util.DBUtil;

import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class LoginForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Connection conn;
	private JTextField textUsername;
	private JPasswordField textPassword;

	/**
	 * Create the frame.
	 */
	public LoginForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				textUsername.setText("");
				textPassword.setText("");
			}
		});
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, -39, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(210, 255, 255));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputUsername = textUsername.getText().trim();
				String inputPassword = String.valueOf(textPassword.getPassword()).trim();
				String password = System.getenv("TS_ADMIN_PASSWORD");
				String hashedPassword;
				
				if (inputUsername.equals("") || inputPassword.equals("")) {
					return;
				}
				
				if (inputUsername.equals("admin") && inputPassword.equals(password)) {
					Main.getLoginForm().setVisible(false);
					Main.getSearchUserForm().setVisible(true);
				} else {
					String sql = "SELECT PASSWORD FROM USERS WHERE USERNAME = ?";
					
					try (Connection conn = DBUtil.getConnection();
							PreparedStatement pr = conn.prepareStatement(sql);) {
						
						pr.setString(1, inputUsername);
						
						ResultSet rs = pr.executeQuery();
						
						if (rs.next()) {
							hashedPassword = rs.getString("PASSWORD");
						} else {
							JOptionPane.showMessageDialog(null, "User not found", "ERROR", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						if (BCrypt.checkpw(inputPassword, hashedPassword)) {
							Main.getMenu().setVisible(true);
							Main.getLoginForm().setVisible(false);
						}
						
					} catch (SQLException exc) {
						exc.printStackTrace();
					}
				}
			}
		});
		btnLogin.setForeground(Color.BLUE);
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLogin.setBounds(319, 218, 89, 32);
		contentPane.add(btnLogin);
		
		JSeparator separatorOne = new JSeparator();
		separatorOne.setBounds(26, 69, 382, 1);
		contentPane.add(separatorOne);
		
		JLabel lblTitleOne = new JLabel("Login Form");
		lblTitleOne.setToolTipText("");
		lblTitleOne.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleOne.setForeground(Color.BLUE);
		lblTitleOne.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitleOne.setBounds(90, 25, 247, 33);
		contentPane.add(lblTitleOne);
		
		JLabel lblTitleTwo = new JLabel("Login Form");
		lblTitleTwo.setToolTipText("");
		lblTitleTwo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleTwo.setForeground(Color.WHITE);
		lblTitleTwo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitleTwo.setBounds(88, 23, 247, 33);
		contentPane.add(lblTitleTwo);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(26, 85, 382, 103);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUsername.setForeground(Color.BLUE);
		lblUsername.setBounds(10, 11, 99, 26);
		panel.add(lblUsername);
		
		textUsername = new JTextField();
		textUsername.setBounds(119, 12, 227, 26);
		panel.add(textUsername);
		textUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setForeground(Color.BLUE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setBounds(10, 65, 99, 26);
		panel.add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(119, 65, 227, 26);
		panel.add(textPassword);
		
		JSeparator separatorTwo = new JSeparator();
		separatorTwo.setBounds(26, 200, 382, 1);
		contentPane.add(separatorTwo);
	}

	public static Connection getConn() {
		return conn;
	}
}
