package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controladores.ControladorActividad;

@SuppressWarnings("serial")
public class VentanaURL extends JFrame implements ActionListener {

	String loginActual;
	int id;
	ControladorActividad controlador;
	
	JTextField url;
	
	public VentanaURL(String login , ControladorActividad controlador, int id) {
		
		this.loginActual = login;
		this.controlador = controlador;
		this.id = id;
		this.setLayout(new BorderLayout());

		
		//Panel informacion
		JPanel informacion = new JPanel();
		informacion.setLayout(new GridLayout(12,1));
		
		informacion.add(new JLabel("URL: "));
		url = new JTextField();
		informacion.add(url);
		this.add(informacion, BorderLayout.CENTER);
		
		//Panel Botones
		JPanel botones = new JPanel();
        botones.setLayout(new FlowLayout());
        JButton butCerrar = new JButton("Cerrar");
        butCerrar.setActionCommand("Cerrar");
        butCerrar.addActionListener(this);
        botones.add(butCerrar);
        JButton butEnviar = new JButton("Confirmar");
        butEnviar.setActionCommand("Crear");
        butEnviar.addActionListener(this);
        botones.add(butEnviar);
        this.add(botones, BorderLayout.NORTH);
        
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
		String strUrl = url.getText().trim();
		if( comando.equals("Cerrar") ) {
        	dispose();
        } else if ( comando.equals("Crear") ) {
        	if (strUrl.isEmpty()) {
        		JOptionPane.showMessageDialog(this, "Por favor, llena el campo.", "Error", JOptionPane.ERROR_MESSAGE);
        	} else {
        		controlador.editarURL(id, strUrl);
            	dispose();
        	}
        }
	}

}

