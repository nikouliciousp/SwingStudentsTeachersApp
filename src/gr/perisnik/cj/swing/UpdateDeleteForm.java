package gr.perisnik.cj.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class UpdateDeleteForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textLastname;
	private JTextField textFirstname;
	private JTextField textId;

	/**
	 * Create the frame.
	 */
	public UpdateDeleteForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateDeleteForm.class.getResource("/resources/insertTeacher.png")));
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
		btnDelete.setForeground(Color.RED);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDelete.setBounds(220, 226, 89, 24);
		contentPane.add(btnDelete);
		
		JButton btnClose = new JButton("CLOSE");
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
		textId.setHorizontalAlignment(SwingConstants.RIGHT);
		textId.setColumns(10);
		textId.setBounds(151, 44, 60, 25);
		contentPane.add(textId);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(26, 23, 382, 152);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.setForeground(Color.BLUE);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(121, 226, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnEnd = new JButton("");
		btnEnd.setIcon(new ImageIcon(UpdateDeleteForm.class.getResource("/resources/ends.png")));
		btnEnd.setBounds(373, 198, 35, 23);
		contentPane.add(btnEnd);
		
		JButton btnNext = new JButton("");
		btnNext.setIcon(new ImageIcon(UpdateDeleteForm.class.getResource("/resources/next.png")));
		btnNext.setBounds(338, 198, 35, 23);
		contentPane.add(btnNext);
		
		JButton btnPrevious = new JButton("");
		btnPrevious.setIcon(new ImageIcon(UpdateDeleteForm.class.getResource("/resources/previous.png")));
		btnPrevious.setBounds(303, 198, 35, 23);
		contentPane.add(btnPrevious);
		
		JButton btnStart = new JButton("");
		btnStart.setIcon(new ImageIcon(UpdateDeleteForm.class.getResource("/resources/start.png")));
		btnStart.setBounds(267, 198, 35, 23);
		contentPane.add(btnStart);
	}

}
