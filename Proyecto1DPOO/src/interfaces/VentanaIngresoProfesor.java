package interfaces;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controladores.ControladorProfesor;

@SuppressWarnings("serial")
public class VentanaIngresoProfesor extends JFrame implements ActionListener {
	
	JTextField user;
	JPasswordField contrasena;
	ControladorProfesor controladorProfesor;
	VentanaMenuProfesor ventanaMenu;
	
	public VentanaIngresoProfesor() throws IOException {
		
		this.controladorProfesor = new ControladorProfesor();
		controladorProfesor.cargarProfesoresDesdeArchivo("profesores.json");
		
        this.setLayout(new GridLayout(3, 1));
        JPanel panelUser = new JPanel();
        panelUser.setLayout(new GridLayout(2,1));
        panelUser.add(new JLabel("Digite su user:"));
        user = new JTextField();
        panelUser.add(user);
        this.add(panelUser);
        
        JPanel panelContrasena = new JPanel();
        panelContrasena.setLayout(new GridLayout(2,1));
        panelContrasena.add(new JLabel("Digite su contraseña:"));
        contrasena = new JPasswordField();
        panelContrasena.add(contrasena);
        this.add(panelContrasena);
       
        JButton butIngresar = new JButton("Ingresar");
        butIngresar.setActionCommand("Ingresar");
        butIngresar.addActionListener(this);
        this.add(butIngresar);
        
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed( ActionEvent e )
    {
        String comando = e.getActionCommand( );
        String strUser = user.getText().trim();
        char[] contrasenaCifrada = contrasena.getPassword();
        String strContrasena = String.valueOf(contrasenaCifrada);
        if( comando.equals("Ingresar") )
        {
        	if (strUser.isEmpty() || strContrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llena ambos campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            	if (!controladorProfesor.existeProfesor(strUser)) {
            		JOptionPane.showMessageDialog(this, "Este profesor no esta registrado, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            	} else {
            		if (controladorProfesor.ingresoProfesor(strUser, strContrasena)) {
            			JOptionPane.showMessageDialog(this, "Ha ingresado exitosamente!", "Exito", JOptionPane.INFORMATION_MESSAGE);
                		dispose();
                		try {
							mostrarVentanaMenu(strUser);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
            		} else {
            			JOptionPane.showMessageDialog(this, "Contraseña incorrecta, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            		}
            		
            	}
            }
        }
    }

	private void mostrarVentanaMenu(String strUser) throws IOException {
		if( ventanaMenu == null || !ventanaMenu.isVisible( ) )
        {
			ventanaMenu = new VentanaMenuProfesor(strUser);
			ventanaMenu.setVisible( true );
        }
	}
	
}
