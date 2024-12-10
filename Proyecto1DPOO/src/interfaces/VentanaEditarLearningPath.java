package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import componentes.Actividad;
import controladores.ControladorActividad;
import controladores.ControladorLearningPath;

@SuppressWarnings("serial")
public class VentanaEditarLearningPath extends JFrame implements ActionListener {

	String loginActual;
	int id;
	ControladorLearningPath controladorLearningPath;
	ControladorActividad controladorActividad;
	
	JTextField titulo;
	JTextField descripcion;
	JComboBox<String> nivelDificultad;
	JComboBox<Integer> duracion;
	HashMap<Actividad, Boolean> actividades;
	
	VentanaElegirActividades ventanaElegirActividades;
	
	public VentanaEditarLearningPath(String login, ControladorLearningPath controladorLearningPath, ControladorActividad controladorActividad, int id) {
		
		this.loginActual = login;
		this.id = id;
		this.controladorLearningPath = controladorLearningPath;
		this.controladorActividad = controladorActividad;
		this.actividades = new HashMap<>();
		this.setLayout(new BorderLayout());

		
		//Panel informacion
		JPanel informacionlp = new JPanel();
		informacionlp.setLayout(new GridLayout(12,1));
		
		informacionlp.add(new JLabel("Titulo: "));
		titulo = new JTextField();
		informacionlp.add(titulo);
		
		informacionlp.add(new JLabel("Descripcion: "));
		descripcion = new JTextField();
		informacionlp.add(descripcion);
		
		informacionlp.add(new JLabel("Nivel de dificultad: "));
		String[] nivelesDificultad = {"Muy baja", "Baja", "Media", "Alta", "Muy alta"};
		nivelDificultad = new JComboBox<>(nivelesDificultad);
		informacionlp.add(nivelDificultad);
		
		informacionlp.add(new JLabel("Duracion (En minutos): "));
		Integer[] duraciones = {5, 10, 15, 30, 60, 90, 120, 240};
		duracion = new JComboBox<>(duraciones);
		informacionlp.add(duracion);
		
		informacionlp.add(new JLabel("Actividades obligatorias (No elegir nada es valido): "));
		JButton butActividadesObligatorias = new JButton("Elegir");
		butActividadesObligatorias.setActionCommand("ElegirObligatorias");
		butActividadesObligatorias.addActionListener(this);
        informacionlp.add(butActividadesObligatorias);
        
		informacionlp.add(new JLabel("Actividades opcionales (No elegir nada es valido): "));
		JButton butActividadesOpcionales = new JButton("Elegir");
		butActividadesOpcionales.setActionCommand("ElegirOpcionales");
		butActividadesOpcionales.addActionListener(this);
        informacionlp.add(butActividadesOpcionales);
		this.add(informacionlp, BorderLayout.CENTER);
		
		//Panel Botones
		JPanel botones = new JPanel();
        botones.setLayout(new FlowLayout());
        JButton butCerrar = new JButton("Cerrar");
        butCerrar.setActionCommand("Cerrar");
        butCerrar.addActionListener(this);
        botones.add(butCerrar);
        JButton butEnviar = new JButton("Crear");
        butEnviar.setActionCommand("Crear");
        butEnviar.addActionListener(this);
        botones.add(butEnviar);
        this.add(botones, BorderLayout.NORTH);
        
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void mostrarVentanaElegirActividades(boolean obligatoria) {
		if( ventanaElegirActividades == null || !ventanaElegirActividades.isVisible( ) )
        {
			ventanaElegirActividades = new VentanaElegirActividades(loginActual, controladorActividad, obligatoria, actividades, this);
			JOptionPane.showMessageDialog(this, "Recuerde que debe mantener la tecla CTRL para elegir mas de una actividad!", "Recordatorio", JOptionPane.INFORMATION_MESSAGE);
			ventanaElegirActividades.setVisible( true );
        }
	}
	
	public void actualizarMapa(HashMap<Actividad, Boolean> mapaActividades) {
		// TODO Auto-generated method stub
		this.actividades =  mapaActividades;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
		String strTitulo = titulo.getText().trim();
		String strDescripcion = descripcion.getText().trim();
		String strNivelDificultad = nivelDificultad.getSelectedItem().toString();
		String strDuracion = duracion.getSelectedItem().toString();
		if( comando.equals("Cerrar") ) {
        	dispose();
        } else if ( comando.equals("Crear") ) {
        	if (strTitulo.isEmpty() || strDescripcion.isEmpty()) {
        		JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        	} else {
        		int id = controladorLearningPath.crearLearningPath(loginActual);
        		controladorLearningPath.editarTitulo(id, strTitulo);
        		controladorLearningPath.editarDescripcionGeneral(id, strDescripcion);
        		controladorLearningPath.editarNivelDificultad(id, strNivelDificultad);
        		controladorLearningPath.editarDuracion(id, Integer.valueOf(strDuracion));
        		controladorLearningPath.editarActividades(id, actividades);
        		try {
					controladorLearningPath.guardarLPEnArchivo("learningPaths.json");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
        		JOptionPane.showMessageDialog(this, "Ha creado un learning path exitosamente!", "Exito", JOptionPane.INFORMATION_MESSAGE);
        		dispose();
        	}
        } else if ( comando.equals("ElegirObligatorias") ) {
        	mostrarVentanaElegirActividades( true );
        } else if ( comando.equals("ElegirOpcionales") ) {
        	mostrarVentanaElegirActividades( false );
        }
	}

}
