package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import componentes.Actividad;
import controladores.ControladorActividad;

@SuppressWarnings("serial")
public class VentanaCrearActividad extends JFrame implements ActionListener {

	String loginActual;
	ControladorActividad controladorActividad;
	
	JTextField objetivos;
	JTextField descripcion;
	JComboBox<String> nivelDificultad;
	JComboBox<Integer> duracion;
	JComboBox<String> tipo;
	List<Actividad> actividadesPrevias;
	List<Actividad> actividadesSeguimiento;
	
	VentanaElegirActividadesPrePost ventanaElegirActividades;
	VentanaNotaMinima ventanaNotaMinima;
	VentanaQuizMultiple ventanaQuizMultiple;
	VentanaVerdaderoFalso ventanaVerdaderoFalso;
	VentanaPreguntas ventanaPreguntas;
	VentanaURL ventanaURL;
	
	
	public VentanaCrearActividad(String login , ControladorActividad controladorActividad) {
		
		this.loginActual = login;
		this.controladorActividad = controladorActividad;
		this.actividadesPrevias = new ArrayList<>();
		this.actividadesSeguimiento = new ArrayList<>();
		this.setLayout(new BorderLayout());

		
		//Panel informacion
		JPanel informacionlp = new JPanel();
		informacionlp.setLayout(new GridLayout(12,1));
		
		informacionlp.add(new JLabel("Descripcion: "));
		descripcion = new JTextField();
		informacionlp.add(descripcion);
		
		informacionlp.add(new JLabel("Objetivos (Separados por coma): "));
		objetivos = new JTextField();
		informacionlp.add(objetivos);
		
		informacionlp.add(new JLabel("Nivel de dificultad: "));
		String[] nivelesDificultad = {"Muy baja", "Baja", "Media", "Alta", "Muy alta"};
		nivelDificultad = new JComboBox<>(nivelesDificultad);
		informacionlp.add(nivelDificultad);
		
		informacionlp.add(new JLabel("Duracion (En minutos): "));
		Integer[] duraciones = {5, 10, 15, 30, 60, 90, 120, 240};
		duracion = new JComboBox<>(duraciones);
		informacionlp.add(duracion);
		
		informacionlp.add(new JLabel("Duracion (En minutos): "));
		String[] tipos = {"Tarea", "Encuesta", "Examen", "Quiz opcion multiple", "Quiz verdadero falso", "Recurso educativo"};
		tipo = new JComboBox<>(tipos);
		informacionlp.add(tipo);
		
		informacionlp.add(new JLabel("Actividades previas (No elegir nada es valido): "));
		JButton butActividadesObligatorias = new JButton("Elegir");
		butActividadesObligatorias.setActionCommand("Elegir1");
		butActividadesObligatorias.addActionListener(this);
        informacionlp.add(butActividadesObligatorias);
        
		informacionlp.add(new JLabel("Actividades de seguimiento (No elegir nada es valido): "));
		JButton butActividadesOpcionales = new JButton("Elegir");
		butActividadesOpcionales.setActionCommand("Elegir2");
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
	
	private void mostrarVentanaElegirActividades(boolean previo) {
		if( ventanaElegirActividades == null || !ventanaElegirActividades.isVisible( ) )
        {
			ventanaElegirActividades = new VentanaElegirActividadesPrePost(loginActual, controladorActividad, previo, actividadesPrevias, actividadesSeguimiento, this);
			JOptionPane.showMessageDialog(this, "Recuerde que debe mantener la tecla CTRL para elegir mas de una actividad!", "Recordatorio", JOptionPane.INFORMATION_MESSAGE);
			ventanaElegirActividades.setVisible( true );
        }
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
		String strObjetivos = objetivos.getText().trim();
		String strDescripcion = descripcion.getText().trim();
		String strNivelDificultad = nivelDificultad.getSelectedItem().toString();
		String strDuracion = duracion.getSelectedItem().toString();
		if( comando.equals("Cerrar") ) {
        	dispose();
        } else if ( comando.equals("Crear") ) {
        	if (strObjetivos.isEmpty() || strDescripcion.isEmpty()) {
        		JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        	} else {
        		int id = controladorActividad.crearActividad(loginActual);
        		controladorActividad.editarObjetivos(id, strObjetivos);
        		controladorActividad.editarDescripcion(id, strDescripcion);
        		controladorActividad.editarNivelDificultad(id, strNivelDificultad);
        		controladorActividad.editarDuracion(id, Integer.valueOf(strDuracion));
        		controladorActividad.editarTipo(id, tipo.getSelectedItem().toString());
        		ArrayList<Integer >idsPrevias = new ArrayList<>();
        		for (Actividad a : actividadesPrevias) {
        			idsPrevias.add(a.getId());
        		}
        		ArrayList<Integer >idsSeguimiento = new ArrayList<>();
        		for (Actividad a : actividadesSeguimiento) {
        			idsSeguimiento.add(a.getId());
        		}
        		controladorActividad.editarActividadesPrevias(id, idsPrevias);
        		controladorActividad.editarActividadesSeguimiento(id, idsSeguimiento);
        		dispose();
        		switch (tipo.getSelectedItem().toString()) {
        			case "Tarea":
        				break;
        			case "Encuesta":
        				mostrarVentanaPreguntas(loginActual, controladorActividad, id);
        				break;
        			case "Examen":
        				mostrarVentanaPreguntas(loginActual, controladorActividad, id);
        				mostrarVentanaNotaMinima(loginActual, controladorActividad, id);
        				break;
        			case "Quiz opcion multiple":
        				mostrarVentanaQuizMultiple(loginActual, controladorActividad, id);
        				mostrarVentanaNotaMinima(loginActual, controladorActividad, id);
        				break;
        			case "Quiz verdadero falso":
        				mostrarVentanaVerdaderoFalso(loginActual, controladorActividad, id);
        				mostrarVentanaNotaMinima(loginActual, controladorActividad, id);
        				break;
        			case "Recurso educativo":
        				MostrarVentanaURL(loginActual, controladorActividad, id);
        				break;
        		}
        		try {
					controladorActividad.guardarActividadesEnArchivo("actividades.json");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
        	}
        } else if ( comando.equals("Elegir1") ) {
        	mostrarVentanaElegirActividades( true );
        } else if ( comando.equals("Elegir2") ) {
        	mostrarVentanaElegirActividades( false );
        }
	}

	private void mostrarVentanaPreguntas(String login, ControladorActividad controlador, int id) {
		if( ventanaPreguntas == null || !ventanaPreguntas.isVisible( ) )
        {
			ventanaPreguntas = new VentanaPreguntas(login, controlador, id);
			ventanaPreguntas.setVisible( true );
        }
	}

	private void mostrarVentanaQuizMultiple(String login, ControladorActividad controlador, int id) {
		if( ventanaQuizMultiple == null || !ventanaQuizMultiple.isVisible( ) )
        {
			ventanaQuizMultiple = new VentanaQuizMultiple(login, controlador, id);
			ventanaQuizMultiple.setVisible( true );
        }
	}

	private void mostrarVentanaVerdaderoFalso(String login, ControladorActividad controlador, int id) {
		if( ventanaVerdaderoFalso == null || !ventanaVerdaderoFalso.isVisible( ) )
        {
			ventanaVerdaderoFalso = new VentanaVerdaderoFalso(login, controlador, id);
			ventanaVerdaderoFalso.setVisible( true );
        }
	}

	private void mostrarVentanaNotaMinima(String login, ControladorActividad controlador, int id) {
		if( ventanaNotaMinima == null || !ventanaNotaMinima.isVisible( ) )
        {
			ventanaNotaMinima = new VentanaNotaMinima(login, controlador, id);
			ventanaNotaMinima.setVisible( true );
        }
	}

	private void MostrarVentanaURL(String login, ControladorActividad controlador, int id) {
		if( ventanaURL == null || !ventanaURL.isVisible( ) )
        {
			ventanaURL = new VentanaURL(login, controlador, id);
			ventanaURL.setVisible( true );
        }
	}

	public void actualizarActividadesPrevias(List<Actividad> listaActividad) {
		// TODO Auto-generated method stub
		this.actividadesPrevias = listaActividad;
	}

	public void actualizarActividadesSeguimiento(List<Actividad> listaActividad) {
		// TODO Auto-generated method stub
		this.actividadesSeguimiento = listaActividad;
	}

}