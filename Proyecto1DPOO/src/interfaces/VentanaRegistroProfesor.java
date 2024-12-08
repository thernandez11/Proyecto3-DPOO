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
public class VentanaRegistroProfesor extends JFrame implements ActionListener {
	
	JTextField user;
	JPasswordField contrasena;
	ControladorProfesor controladorProfesor;
	VentanaMenuProfesor ventanaMenu;
	
	public VentanaRegistroProfesor() throws IOException {
		
		this.controladorProfesor = new ControladorProfesor();
		controladorProfesor.cargarProfesoresDesdeArchivo("profesores.json");
		
        this.setLayout(new GridLayout(3, 1));
        JPanel panelUser = new JPanel();
        panelUser.setLayout(new GridLayout(2,1));
        panelUser.add(new JLabel("Digite el user que quiere tener:"));
        user = new JTextField();
        panelUser.add(user);
        this.add(panelUser);
        
        JPanel panelContrasena = new JPanel();
        panelContrasena.setLayout(new GridLayout(2,1));
        panelContrasena.add(new JLabel("Digite la contraseña para su cuenta:"));
        contrasena = new JPasswordField();
        panelContrasena.add(contrasena);
        this.add(panelContrasena);
       
        JButton butRegistrar = new JButton("Registrar");
        butRegistrar.setActionCommand("Registrar");
        butRegistrar.addActionListener(this);
        this.add(butRegistrar);
        
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed( ActionEvent e )
    {
        String comando = e.getActionCommand( );
        String strUser = user.getText().trim();
        char[] contrasenaCifrada = contrasena.getPassword();
        String strContrasena = String.valueOf(contrasenaCifrada);
        if( comando.equals("Registrar") )
        {
        	if (strUser.isEmpty() || strContrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llena ambos campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            	if (controladorProfesor.existeProfesor(strUser)) {
            		JOptionPane.showMessageDialog(this, "Este profesor ya esta registrado, intente nuevamente con otro user.", "Error", JOptionPane.ERROR_MESSAGE);
            	} else {
            		controladorProfesor.crearProfesor(strUser, strContrasena);
            		try {
						controladorProfesor.guardarProfesoresEnArchivo("profesores.json");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
            		JOptionPane.showMessageDialog(this, "Se ha registrado exitosamente!", "Exito", JOptionPane.INFORMATION_MESSAGE);
            		dispose();
            	}
            }
        }
    }
	
}
