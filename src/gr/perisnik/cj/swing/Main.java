package gr.perisnik.cj.swing;

import java.awt.EventQueue;

public class Main {
	private static Menu menu;
	private static SearchTeacherForm searchTeacherForm;
	private static InsertTeacherForm insertTeacherForm;
	private static UpdateDeleteTeacherForm updateDeleteTeacherForm;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					menu = new Menu();
					menu.setLocationRelativeTo(null);
					menu.setVisible(true);
					
					searchTeacherForm = new SearchTeacherForm();
					searchTeacherForm.setLocationRelativeTo(null);
					searchTeacherForm.setVisible(false);
					
					insertTeacherForm = new InsertTeacherForm();
					insertTeacherForm.setLocationRelativeTo(null);
					insertTeacherForm.setVisible(false);
					
					updateDeleteTeacherForm = new UpdateDeleteTeacherForm();
					updateDeleteTeacherForm.setLocationRelativeTo(null);
					updateDeleteTeacherForm.setVisible(false);
										
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}