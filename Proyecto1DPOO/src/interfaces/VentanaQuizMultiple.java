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
public class VentanaQuizMultiple extends JFrame implements ActionListener {

	String loginActual;
	int id;
	ControladorActividad controlador;
	
	JTextField pregunta;
	
	JTextField opcionA;
	JTextField opcionAJ;
	JTextField opcionB;
	JTextField opcionBJ;
	JTextField opcionC;
	JTextField opcionCJ;
	JTextField opcionD;
	JTextField opcionDJ;
	
	JComboBox<String> correcta;
	
	HashMap<String, HashMap<String, String>>  preguntas;
	List<Integer> correctas;
	
	public VentanaQuizMultiple(String login , ControladorActividad controlador, int id) {
		
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
		
		informacion.add(new JLabel("Opcion A: "));
		opcionA = new JTextField();
		informacion.add(opcionA);
		this.add(informacion, BorderLayout.CENTER);
		informacion.add(new JLabel("Justificacion A: "));
		opcionAJ = new JTextField();
		informacion.add(opcionAJ);
		this.add(informacion, BorderLayout.CENTER);
		
		informacion.add(new JLabel("Opcion B: "));
		opcionB = new JTextField();
		informacion.add(opcionB);
		this.add(informacion, BorderLayout.CENTER);
		informacion.add(new JLabel("Justificacion B: "));
		opcionBJ = new JTextField();
		informacion.add(opcionBJ);
		this.add(informacion, BorderLayout.CENTER);
		
		informacion.add(new JLabel("Opcion C: "));
		opcionC = new JTextField();
		informacion.add(opcionC);
		this.add(informacion, BorderLayout.CENTER);
		informacion.add(new JLabel("Justificacion C: "));
		opcionCJ = new JTextField();
		informacion.add(opcionCJ);
		this.add(informacion, BorderLayout.CENTER);
		
		informacion.add(new JLabel("Opcion D: "));
		opcionD = new JTextField();
		informacion.add(opcionD);
		this.add(informacion, BorderLayout.CENTER);
		informacion.add(new JLabel("Justificacion D: "));
		opcionDJ = new JTextField();
		informacion.add(opcionDJ);
		this.add(informacion, BorderLayout.CENTER);
		
		informacion.add(new JLabel("Opcion Correcta: "));
		String[] opciones = {"A", "B", "C", "D"};
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
		String strOpcionA = opcionA.getText().trim();
		String strOpcionAJ = opcionAJ.getText().trim();
		String strOpcionB = opcionB.getText().trim();
		String strOpcionBJ = opcionBJ.getText().trim();
		String strOpcionC = opcionC.getText().trim();
		String strOpcionCJ = opcionCJ.getText().trim();
		String strOpcionD = opcionD.getText().trim();
		String strOpcionDJ = opcionDJ.getText().trim();
		if( comando.equals("Cerrar") ) {
        	dispose();
        } else if ( comando.equals("Crear") ) {
        	controlador.editarPreguntasMultiples(id, preguntas, correctas);
        	dispose();
        } else if ( comando.equals("Añadir") ) {
        	if (strPregunta.isEmpty() || strOpcionA.isEmpty() || strOpcionAJ.isEmpty() || strOpcionB.isEmpty() || strOpcionBJ.isEmpty()
        			|| strOpcionC.isEmpty() || strOpcionCJ.isEmpty() || strOpcionD.isEmpty() || strOpcionDJ.isEmpty()) {
        		JOptionPane.showMessageDialog(this, "Llene todos los campos por favor.", "Error", JOptionPane.ERROR_MESSAGE);
        	} else {
        		HashMap<String, String> opciones = new HashMap<>();
        		correctas.add(correcta.getSelectedIndex() + 1);
        		opciones.put(strOpcionA, strOpcionAJ);
        		opciones.put(strOpcionB, strOpcionBJ);
        		opciones.put(strOpcionC, strOpcionCJ);
        		opciones.put(strOpcionD, strOpcionDJ);
        		preguntas.put(strPregunta, opciones);
        		
        		JOptionPane.showMessageDialog(this, "Pregunta agregada!", "Exito", JOptionPane.INFORMATION_MESSAGE);
        		pregunta.setText(null);
        		opcionA.setText(null);
        		opcionAJ.setText(null);
        		opcionB.setText(null);
        		opcionBJ.setText(null);
        		opcionC.setText(null);
        		opcionCJ.setText(null);
        		opcionD.setText(null);
        		opcionDJ.setText(null);
        		correcta.setSelectedIndex(0);
        	}
        }
	}

}