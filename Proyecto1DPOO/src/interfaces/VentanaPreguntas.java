package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controladores.ControladorActividad;

@SuppressWarnings("serial")
public class VentanaPreguntas extends JFrame implements ActionListener {

	String loginActual;
	int id;
	ControladorActividad controlador;
	
	JTextField pregunta;
	List<String> preguntas;
	
	public VentanaPreguntas(String login , ControladorActividad controlador, int id) {
		
		this.loginActual = login;
		this.controlador = controlador;
		this.id = id;
		this.preguntas = new ArrayList<>();
		this.setLayout(new BorderLayout());

		
		//Panel informacion
		JPanel informacion = new JPanel();
		informacion.setLayout(new GridLayout(12,1));
		
		informacion.add(new JLabel("Pregunta: "));
		pregunta = new JTextField();
		informacion.add(pregunta);
		this.add(informacion, BorderLayout.CENTER);
		
		//Panel Botones
		JPanel botones = new JPanel();
        botones.setLayout(new FlowLayout());
        JButton butCerrar = new JButton("Cerrar");
        butCerrar.setActionCommand("Cerrar");
        butCerrar.addActionListener(this);
        botones.add(butCerrar);
        JButton butAnadir = new JButton("Añadir");
        butAnadir.setActionCommand("Añadir");
        butAnadir.addActionListener(this);
        botones.add(butAnadir);
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
		String strPregunta = pregunta.getText().trim();
		if( comando.equals("Cerrar") ) {
        	dispose();
        } else if ( comando.equals("Crear") ) {
        	controlador.editarPreguntasAbiertas(id, preguntas);
        	dispose();
        } else if ( comando.equals("Añadir") ) {
        	if (strPregunta.isEmpty()) {
        		JOptionPane.showMessageDialog(this, "No se puede agregar una pregunta vacia.", "Error", JOptionPane.ERROR_MESSAGE);
        	} else {
        		pregunta.setText(null);
        		preguntas.add(strPregunta);
        		JOptionPane.showMessageDialog(this, "Pregunta agregada!", "Exito", JOptionPane.INFORMATION_MESSAGE);
        	}
        }
	}

}
