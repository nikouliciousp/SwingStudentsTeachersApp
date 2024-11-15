package gr.perisnik.cj.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;

import org.mindrot.jbcrypt.BCrypt;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPasswordField;

public class InsertUserForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsername;
	private JPasswordField textPassword;

	/**
	 * Create the frame.
	 */
	public InsertUserForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				textUsername.setText("");
				textPassword.setText("");
			}
		});
		setTitle("Insert User");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(210, 255, 255));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.BLUE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUsername.setToolTipText("");
		lblUsername.setBounds(63, 62, 89, 33);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setForeground(Color.BLUE);
		lblPassword.setBounds(66, 106, 86, 33);
		contentPane.add(lblPassword);
		
		textUsername = new JTextField();
		textUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textUsername.setBounds(150, 66, 210, 25);
		contentPane.add(textUsername);
		textUsername.setColumns(50);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(26, 30, 382, 145);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(124, 80, 210, 25);
		panel.add(textPassword);
		
		JButton btnInsert = new JButton("INSERT");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Prepared statement for sql injection.
				String sql = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES (?, ?)";
				String inputUsername; 
				String inputPassword;
				int n = 0;
				
				try (Connection conn = Menu.getConn();
						PreparedStatement pr = conn.prepareStatement(sql);) {
					inputUsername = textUsername.getText().trim();
					inputPassword = String.valueOf(textPassword.getPassword()).trim();
					
					if (inputUsername.equals("") || inputPassword.equals("")) {
						JOptionPane.showMessageDialog(null, n + " records inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					
					//Max = 32
					int workload = 12;
					String salt = BCrypt.gensalt(workload);
					String hashedPassword = BCrypt.hashpw(inputPassword, salt);
					
					pr.setString(1, inputUsername);
					pr.setString(2, hashedPassword);
					
					n = pr.executeUpdate();
					
					if (n == 0) {
						JOptionPane.showMessageDialog(null, n + " records inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					
					JOptionPane.showMessageDialog(null, n + " records inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
					
				} catch (SQLException exc) {
					exc.printStackTrace();
				} 
			}
		});
		btnInsert.setForeground(Color.BLUE);
		btnInsert.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInsert.setBounds(220, 204, 89, 32);
		contentPane.add(btnInsert);
		
		JButton btnClose = new JButton("CLOSE");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getInsertUserForm().setVisible(false);
				Main.getSearchUserForm().setVisible(true);
			}
		});
		btnClose.setForeground(Color.RED);
		btnClose.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnClose.setBounds(319, 204, 89, 32);
		contentPane.add(btnClose);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(26, 186, 382, 1);
		contentPane.add(separator);
	}
}
