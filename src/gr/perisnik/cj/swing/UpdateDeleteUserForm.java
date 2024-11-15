package gr.perisnik.cj.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPasswordField;

public class UpdateDeleteUserForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsername;
	private JTextField textId;
	private ResultSet rs;
	private JPasswordField textPassword;
	

	/**
	 * Create the frame.
	 */
	public UpdateDeleteUserForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				String sql = "SELECT ID, USERNAME, PASSWORD FROM USERS WHERE USERNAME LIKE ?";
				
				try (Connection conn = Menu.getConn();
						PreparedStatement pr = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);) {					
					pr.setString(1, Main.getSearchUserForm().getInputUsername() + '%');
					rs = pr.executeQuery();
					
					if (rs.next()) {					
						textId.setText(Integer.toString(rs.getInt("ID")));
						textUsername.setText(rs.getString("USERNAME"));
						textPassword.setText("*********");
					} else {
						return;
					}
					
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
			@Override
			public void windowClosed(WindowEvent e) {
				textId.setText("");
				textUsername.setText("");
				textPassword.setText("");
			}
		});
		setTitle("Update/Delete User");
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
		lblUsername.setBounds(66, 82, 90, 33);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setForeground(Color.BLUE);
		lblPassword.setBounds(66, 126, 86, 33);
		contentPane.add(lblPassword);
		
		textUsername = new JTextField();
		textUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textUsername.setBounds(151, 86, 210, 25);
		contentPane.add(textUsername);
		textUsername.setColumns(50);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "DELETE FROM USERS WHERE ID = ?";
				int response;
				int n;
				
				try (Connection conn = Menu.getConn();
						PreparedStatement pr = conn.prepareStatement(sql);) {
					pr.setInt(1, Integer.parseInt(textId.getText().trim()));
					
					response = JOptionPane.showConfirmDialog(null, "Are you sure to delete this User?", "DELETE", JOptionPane.YES_NO_OPTION);
					
					if (response == JOptionPane.YES_OPTION) {
						n = pr.executeUpdate();
						JOptionPane.showMessageDialog(null, n + " rows deleted", "DELETE", JOptionPane.INFORMATION_MESSAGE);
//						Main.getUpdateDeleteUserForm().setVisible(false);
//						Main.getSearchUserForm().setVisible(true);
					}
					
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});
		btnDelete.setForeground(Color.RED);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDelete.setBounds(220, 226, 89, 24);
		contentPane.add(btnDelete);
		
		JButton btnClose = new JButton("CLOSE");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getUpdateDeleteUserForm().setVisible(false);
				Main.getSearchUserForm().setVisible(true);
			}
		});
		btnClose.setForeground(Color.RED);
		btnClose.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnClose.setBounds(319, 226, 89, 24);
		contentPane.add(btnClose);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(26, 186, 382, 1);
		contentPane.add(separator);
		
		JLabel lblId = new JLabel("ID");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setForeground(Color.BLUE);
		lblId.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblId.setBounds(66, 38, 77, 33);
		contentPane.add(lblId);
		
		textId = new JTextField();
		textId.setEditable(false);
		textId.setBackground(new Color(213, 255, 255));
		textId.setHorizontalAlignment(SwingConstants.LEFT);
		textId.setColumns(10);
		textId.setBounds(151, 44, 60, 25);
		contentPane.add(textId);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(26, 23, 382, 152);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(125, 107, 210, 25);
		panel.add(textPassword);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Prepared statement for sql injection.
				String sql = "UPDATE USERS SET USERNAME = ?, PASSWORD = ? WHERE ID = ?";
				String inputUsername; 
				String inputPassword;
				String inputId;
				int n = 0;
				
				try (Connection conn = Menu.getConn();
						PreparedStatement pr = conn.prepareStatement(sql);) {				
					inputUsername = textUsername.getText().trim();
					inputPassword = String.valueOf(textPassword.getPassword()).trim();
					inputId = textId.getText();
					
					if (inputUsername.equals("") || inputPassword.equals("") || inputPassword.equals("*********")) {
						JOptionPane.showMessageDialog(null, n + " records updated", "UPDATE", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					
					int workload = 12;
					String salt = BCrypt.gensalt(workload);
					String hashedPassword = BCrypt.hashpw(inputPassword, salt);
					
					pr.setString(1, inputUsername);
					pr.setString(2, hashedPassword);
					pr.setInt(3, Integer.parseInt(inputId));
					
					n = pr.executeUpdate();
					
					if (n == 0) {
						JOptionPane.showMessageDialog(null, n + " records updated", "UPDATE", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					
					JOptionPane.showMessageDialog(null, n + " records updated", "UPDATE", JOptionPane.PLAIN_MESSAGE);
					
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});
		btnUpdate.setForeground(Color.BLUE);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(121, 226, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnEnd = new JButton("");
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.last()) {
						textId.setText(rs.getString("ID"));
						textUsername.setText(rs.getString("USERNAME"));
						textPassword.setText("*********");
					}
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});
		btnEnd.setIcon(new ImageIcon(UpdateDeleteUserForm.class.getResource("/resources/ends.png")));
		btnEnd.setBounds(373, 198, 35, 23);
		contentPane.add(btnEnd);
		
		JButton btnNext = new JButton("");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.next()) {
						textId.setText(rs.getString("ID"));
						textUsername.setText(rs.getString("USERNAME"));
						textPassword.setText("*********");
					} else {
						rs.last();
					}
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});
		btnNext.setIcon(new ImageIcon(UpdateDeleteUserForm.class.getResource("/resources/next.png")));
		btnNext.setBounds(338, 198, 35, 23);
		contentPane.add(btnNext);
		
		JButton btnPrevious = new JButton("");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.previous()) {
						textId.setText(rs.getString("ID"));
						textUsername.setText(rs.getString("USERNAME"));
						textPassword.setText("*********");
					} else {
						rs.first();
					}
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});
		btnPrevious.setIcon(new ImageIcon(UpdateDeleteUserForm.class.getResource("/resources/previous.png")));
		btnPrevious.setBounds(303, 198, 35, 23);
		contentPane.add(btnPrevious);
		
		JButton btnStart = new JButton("");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.first()) {
						textId.setText(rs.getString("ID"));
						textUsername.setText(rs.getString("USERNAME"));
						textPassword.setText("*********");
					}
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});
		btnStart.setIcon(new ImageIcon(UpdateDeleteUserForm.class.getResource("/resources/start.png")));
		btnStart.setBounds(267, 198, 35, 23);
		contentPane.add(btnStart);
	}
}