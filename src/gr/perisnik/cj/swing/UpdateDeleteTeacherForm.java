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
import javax.swing.border.EmptyBorder;
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

public class UpdateDeleteTeacherForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textLastname;
	private JTextField textFirstname;
	private JTextField textId;
	private Connection conn;
	private PreparedStatement pr;
	private ResultSet rs;
	

	/**
	 * Create the frame.
	 */
	public UpdateDeleteTeacherForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				String sql = "SELECT ID, FIRSTNAME, LASTNAME FROM TEACHERS WHERE LASTNAME LIKE ?";
				
				try {
					conn = Menu.getConn();
					pr = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					
					pr.setString(1, Main.getSearchTeacherForm().getInputLastname() + '%');
					rs = pr.executeQuery();
					
					if (rs.next()) {					
						textId.setText(Integer.toString(rs.getInt("ID")));
						textLastname.setText(rs.getString("LASTNAME"));
						textFirstname.setText(rs.getString("FIRSTNAME"));
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
				textLastname.setText("");
				textFirstname.setText("");
				
				try {
					if (rs != null) {
						rs.close();
					}
					
					if (pr != null) {
						pr.close();
					}
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateDeleteTeacherForm.class.getResource("/resources/insertTeacher.png")));
		setTitle("Update/Delete Teacher");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(210, 255, 255));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setForeground(Color.BLUE);
		lblLastname.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLastname.setToolTipText("");
		lblLastname.setBounds(66, 82, 90, 33);
		contentPane.add(lblLastname);
		
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFirstname.setForeground(Color.BLUE);
		lblFirstname.setBounds(66, 126, 86, 33);
		contentPane.add(lblFirstname);
		
		textLastname = new JTextField();
		textLastname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textLastname.setBounds(151, 86, 210, 25);
		contentPane.add(textLastname);
		textLastname.setColumns(50);
		
		textFirstname = new JTextField();
		textFirstname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFirstname.setBounds(151, 130, 210, 25);
		contentPane.add(textFirstname);
		textFirstname.setColumns(50);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "DELETE FROM TEACHERS WHERE ID = ?";
				int response;
				int n;
				
				try {
					conn = Menu.getConn();
					pr = conn.prepareStatement(sql);
					pr.setInt(1, Integer.parseInt(textId.getText().trim()));
					
					response = JOptionPane.showConfirmDialog(null, "Are you sure to delete this Teacher?", "DELETE", JOptionPane.YES_NO_OPTION);
					
					if (response == JOptionPane.YES_OPTION) {
						n = pr.executeUpdate();
						JOptionPane.showMessageDialog(null, n + " rows deleted", "DELETE", JOptionPane.INFORMATION_MESSAGE);
						Main.getUpdateDeleteTeacherForm().setVisible(false);
						Main.getSearchTeacherForm().setVisible(true);
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
				Main.getUpdateDeleteTeacherForm().setVisible(false);
				Main.getSearchTeacherForm().setVisible(true);
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
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Prepared statement for sql injection.
				String sql = "UPDATE TEACHERS SET FIRSTNAME = ?, LASTNAME = ? WHERE ID = ?";
				String inputLastname; 
				String inputFirstname;
				String inputId;
				int n = 0;
				
				try {
					Connection conn = Menu.getConn();
					pr = conn.prepareStatement(sql);
					
					inputLastname = textLastname.getText().trim();
					inputFirstname = textFirstname.getText().trim();
					inputId = textId.getText();
					
					if (inputLastname.equals("") || inputFirstname.equals("")) {
						JOptionPane.showMessageDialog(null, n + " records updated", "UPDATE", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					
					pr.setString(1, inputFirstname);
					pr.setString(2, inputLastname);
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
						textLastname.setText(rs.getString("LASTNAME"));
						textFirstname.setText(rs.getString("FIRSTNAME"));
					}
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});
		btnEnd.setIcon(new ImageIcon(UpdateDeleteTeacherForm.class.getResource("/resources/ends.png")));
		btnEnd.setBounds(373, 198, 35, 23);
		contentPane.add(btnEnd);
		
		JButton btnNext = new JButton("");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.next()) {
						textId.setText(rs.getString("ID"));
						textLastname.setText(rs.getString("LASTNAME"));
						textFirstname.setText(rs.getString("FIRSTNAME"));
					} else {
						rs.last();
					}
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});
		btnNext.setIcon(new ImageIcon(UpdateDeleteTeacherForm.class.getResource("/resources/next.png")));
		btnNext.setBounds(338, 198, 35, 23);
		contentPane.add(btnNext);
		
		JButton btnPrevious = new JButton("");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.previous()) {
						textId.setText(rs.getString("ID"));
						textLastname.setText(rs.getString("LASTNAME"));
						textFirstname.setText(rs.getString("FIRSTNAME"));
					} else {
						rs.first();
					}
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});
		btnPrevious.setIcon(new ImageIcon(UpdateDeleteTeacherForm.class.getResource("/resources/previous.png")));
		btnPrevious.setBounds(303, 198, 35, 23);
		contentPane.add(btnPrevious);
		
		JButton btnStart = new JButton("");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rs.first()) {
						textId.setText(rs.getString("ID"));
						textLastname.setText(rs.getString("LASTNAME"));
						textFirstname.setText(rs.getString("FIRSTNAME"));
					}
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});
		btnStart.setIcon(new ImageIcon(UpdateDeleteTeacherForm.class.getResource("/resources/start.png")));
		btnStart.setBounds(267, 198, 35, 23);
		contentPane.add(btnStart);
	}

}
