package gr.perisnik.cj.swing;

import java.awt.EventQueue;

public class Main {
	private static Menu menu;
	private static SearchTeacherForm searchTeacherForm;
	private static InsertTeacherForm insertTeacherForm;
	private static UpdateDeleteTeacherForm updateDeleteTeacherForm;
	private static SearchStudentForm searchStudentForm;
	private static InsertStudentForm insertStudentForm;
	private static UpdateDeleteStudentForm updateDeleteStudentForm;
	
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
					
					searchStudentForm = new SearchStudentForm();
					searchStudentForm.setLocationRelativeTo(null);
					searchStudentForm.setVisible(false);
								
					insertStudentForm = new InsertStudentForm();
					insertStudentForm.setLocationRelativeTo(null);
					insertStudentForm.setVisible(false);
					
					updateDeleteStudentForm = new UpdateDeleteStudentForm();
					updateDeleteStudentForm.setLocationRelativeTo(null);
					updateDeleteStudentForm.setVisible(false);
										
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static Menu getMenu() {
		return menu;
	}

	public static SearchTeacherForm getSearchTeacherForm() {
		return searchTeacherForm;
	}

	public static InsertTeacherForm getInsertTeacherForm() {
		return insertTeacherForm;
	}

	public static UpdateDeleteTeacherForm getUpdateDeleteTeacherForm() {
		return updateDeleteTeacherForm;
	}

	public static SearchStudentForm getSearchStudentForm() {
		return searchStudentForm;
	}

	public static InsertStudentForm getInsertStudentForm() {
		return insertStudentForm;
	}

	public static UpdateDeleteStudentForm getUpdateDeleteStudentForm() {
		return updateDeleteStudentForm;
	}
}