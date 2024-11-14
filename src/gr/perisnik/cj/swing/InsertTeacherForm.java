package gr.perisnik.cj.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InsertTeacherForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textLastname;
	private JTextField textFirstname;
	private PreparedStatement pr;

	/**
	 * Create the frame.
	 */
	public InsertTeacherForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				textLastname.setText("");
				textFirstname.setText("");
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(InsertTeacherForm.class.getResource("/resources/insertTeacher.png")));
		setTitle("Insert Teacher");
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
		lblLastname.setBounds(66, 62, 86, 33);
		contentPane.add(lblLastname);
		
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFirstname.setForeground(Color.BLUE);
		lblFirstname.setBounds(66, 106, 86, 33);
		contentPane.add(lblFirstname);
		
		textLastname = new JTextField();
		textLastname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textLastname.setBounds(150, 66, 210, 25);
		contentPane.add(textLastname);
		textLastname.setColumns(50);
		
		textFirstname = new JTextField();
		textFirstname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFirstname.setBounds(151, 110, 210, 25);
		contentPane.add(textFirstname);
		textFirstname.setColumns(50);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(26, 30, 382, 145);
		contentPane.add(panel);
		
		JButton btnInsert = new JButton("INSERT");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Prepared statement for sql injection.
				String sql = "INSERT INTO TEACHERS (FIRSTNAME, LASTNAME) VALUES (?, ?)";
				String inputLastname; 
				String inputFirstname;
				int n = 0;
				
				try {
					Connection conn = Menu.getConn();
					pr = conn.prepareStatement(sql);
					
					inputLastname = textLastname.getText().trim();
					inputFirstname = textFirstname.getText().trim();
					
					if (inputLastname.equals("") || inputFirstname.equals("")) {
						JOptionPane.showMessageDialog(null, n + " records inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					
					pr.setString(1, inputFirstname);
					pr.setString(2, inputLastname);
					
					n = pr.executeUpdate();
					
					if (n == 0) {
						JOptionPane.showMessageDialog(null, n + " records inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					
					JOptionPane.showMessageDialog(null, n + " records inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
					
				} catch (SQLException exc) {
					exc.printStackTrace();
				} finally {
					try {
						if (pr != null) {
							pr.close();
						}
					} catch (SQLException exc) {
						exc.printStackTrace();
					}
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
				Main.getInsertTeacherForm().setVisible(false);
				Main.getSearchTeacherForm().setVisible(true);
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
