package interfaz.estudiantes;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class VentanaAuth extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAuth frame = new VentanaAuth();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaAuth() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel contentPane_1 = new JPanel();
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(contentPane_1);
		contentPane_1.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Ingrese su usuario:");
		contentPane_1.add(lblNewLabel);
		
		textField = new JTextField();
		contentPane_1.add(textField);
		textField.setColumns(10);
		
		JPanel contentPane_2 = new JPanel();
		contentPane_2.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(contentPane_2);
		contentPane_2.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Ingrese su contraseña:");
		contentPane_2.add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		contentPane_2.add(passwordField);
	}

}
