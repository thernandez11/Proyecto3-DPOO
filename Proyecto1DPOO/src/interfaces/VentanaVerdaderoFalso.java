package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controladores.ControladorActividad;

@SuppressWarnings("serial")
public class VentanaVerdaderoFalso extends JFrame implements ActionListener {

	String loginActual;
	int id;
	ControladorActividad controlador;
	
	JTextField pregunta;
	
	JTextField opcionAJ;
	JTextField opcionBJ;
	
	JComboBox<String> correcta;
	
	HashMap<String, HashMap<String, String>>  preguntas;
	List<Integer> correctas;
	
	public VentanaVerdaderoFalso(String login , ControladorActividad controlador, int id) {
		
		this.loginActual = login;
		this.controlador = controlador;
		this.id = id;
		this.preguntas = new HashMap<>();
		this.correctas = new ArrayList<>();
		this.setLayout(new BorderLayout());

		
		//Panel informacion
		JPanel informacion = new JPanel();
		informacion.setLayout(new GridLayout(12,1));
		
		informacion.add(new JLabel("Pregunta: "));
		pregunta = new JTextField();
		informacion.add(pregunta);
		this.add(informacion, BorderLayout.CENTER);
		
		informacion.add(new JLabel("Justificacion Verdadero: "));
		opcionAJ = new JTextField();
		informacion.add(opcionAJ);
		this.add(informacion, BorderLayout.CENTER);
		
		informacion.add(new JLabel("Justificacion Falso: "));
		opcionBJ = new JTextField();
		informacion.add(opcionBJ);
		this.add(informacion, BorderLayout.CENTER);
		
		informacion.add(new JLabel("Opcion Correcta: "));
		String[] opciones = {"Verdadero", "Falso"};
		correcta = new JComboBox<>(opciones);
		informacion.add(correcta);
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
		String strOpcionA = "Verdadero";
		String strOpcionAJ = opcionAJ.getText().trim();
		String strOpcionB = "Falso";
		String strOpcionBJ = opcionBJ.getText().trim();
		if( comando.equals("Cerrar") ) {
        	dispose();
        } else if ( comando.equals("Crear") ) {
        	controlador.editarPreguntasVerdaderoFalso(id, preguntas, correctas);;
        	dispose();
        } else if ( comando.equals("Añadir") ) {
        	if (strPregunta.isEmpty() || strOpcionA.isEmpty() || strOpcionAJ.isEmpty() || strOpcionB.isEmpty() || strOpcionBJ.isEmpty()) {
        		JOptionPane.showMessageDialog(this, "Llene todos los campos por favor.", "Error", JOptionPane.ERROR_MESSAGE);
        	} else {
        		HashMap<String, String> opciones = new HashMap<>();
        		correctas.add(correcta.getSelectedIndex() + 1);
        		opciones.put(strOpcionA, strOpcionAJ);
        		opciones.put(strOpcionB, strOpcionBJ);
        		preguntas.put(strPregunta, opciones);
        		
        		JOptionPane.showMessageDialog(this, "Pregunta agregada!", "Exito", JOptionPane.INFORMATION_MESSAGE);
        		pregunta.setText(null);
        		opcionAJ.setText(null);
        		opcionBJ.setText(null);
        		
        		correcta.setSelectedIndex(0);
        	}
        }
	}
}
