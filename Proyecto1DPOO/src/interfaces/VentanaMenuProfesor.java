package interfaces;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import controladores.ControladorActividad;
import controladores.ControladorLearningPath;
import controladores.ControladorResena;

@SuppressWarnings("serial")
public class VentanaMenuProfesor extends JFrame implements ActionListener  {
	
	private ControladorActividad AC;
	private ControladorLearningPath LPC;
	private ControladorResena RC;
	private String loginActual;
	
	private VentanaVerLearningPaths ventanaVerLearningPaths;
	private VentanaVerActividades ventanaVerActividades;
	private VentanaVerResenas ventanaVerResenas;
	
	private VentanaCrearLearningPath ventanaCrearLearningPath;
	private VentanaCrearActividad ventanaCrearActividad;
	private VentanaCrearResena ventanaCrearResena;
	
	private VentanaEditarLearningPath ventanaEditarLearningPaths;
	private VentanaEditarActividad ventanaEditarActividad;
	
	public VentanaMenuProfesor(String login) throws IOException {
		
		this.loginActual = login;
		this.AC = new ControladorActividad();
        this.LPC = new ControladorLearningPath(AC);
        this.RC = new ControladorResena();
        
        AC.cargarActividadesDesdeArchivo("actividades.json");
		LPC.cargarLPDesdeArchivo("learningPaths.json");
		RC.cargarResenasDesdeArchivo("resenas.json");
        
        this.setLayout(new GridLayout(3, 3));
        
        String[] botones = {"Ver learning paths", "Ver actividades", "Ver reseñas actividad",
        		"Crear learning path", "Crear actividad", "Crear reseña", "Editar learning path",
        		"Editar actividad", "Salir"};
        
        for (String boton : botones) {
        	JButton but = new JButton(boton);
            but.setActionCommand(boton);
            but.addActionListener(this);
            this.add(but);
        }
        
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void actionPerformed( ActionEvent e )
    {
        String comando = e.getActionCommand( );
        switch (comando) {
        	case "Ver learning paths":
        		mostrarVentanaVerLearningPaths(loginActual, LPC);
        		break;
        	case "Ver actividades":
        		mostrarVentanaVerActividades(loginActual, AC);
        		break;
        	case "Ver reseñas actividad":
        		mostrarVentanaVerResenas(loginActual, RC);
        		break;
        	case "Crear learning path":
        		mostrarVentanaCrearLearningPath(loginActual, LPC, AC);
        		break;
        	case "Crear actividad":
        		mostrarVentanaCrearActividad(loginActual, AC);
        		break;
        	case "Crear reseña":
        		mostrarVentanaCrearResena(loginActual, RC, AC);
        		break;
        	case "Editar learning path":
        		mostrarVentanaEditarLearningPath(loginActual, LPC);
        		break;
        	case "Editar actividad":
        		mostrarVentanaEditarActividad(loginActual, AC);
        		break;
        	case "Salir":
        		dispose();
        		break;
        }
    }

	private void mostrarVentanaVerLearningPaths(String login, ControladorLearningPath controlador) {
		if( ventanaVerLearningPaths == null || !ventanaVerLearningPaths.isVisible( ) )
        {
			ventanaVerLearningPaths = new VentanaVerLearningPaths(login, controlador);
			ventanaVerLearningPaths.setVisible( true );
        }
	}

	private void mostrarVentanaVerActividades(String login, ControladorActividad controlador) {
		if( ventanaVerActividades == null || !ventanaVerActividades.isVisible( ) )
        {
			ventanaVerActividades = new VentanaVerActividades(login, controlador);
			ventanaVerActividades.setVisible( true );
        }
	}

	private void mostrarVentanaVerResenas(String login, ControladorResena controlador) {
		if( ventanaVerResenas == null || !ventanaVerResenas.isVisible( ) )
        {
			ventanaVerResenas = new VentanaVerResenas(login, controlador);
			ventanaVerResenas.setVisible( true );
        }
	}

	private void mostrarVentanaCrearLearningPath(String login, ControladorLearningPath controladorLearningPath, ControladorActividad controladorActividad) {
		if( ventanaCrearLearningPath == null || !ventanaCrearLearningPath.isVisible( ) )
        {
			ventanaCrearLearningPath = new VentanaCrearLearningPath(login, controladorLearningPath, controladorActividad);
			ventanaCrearLearningPath.setVisible( true );
        }
	}

	private void mostrarVentanaCrearActividad(String login, ControladorActividad controlador) {
		if( ventanaCrearActividad == null || !ventanaCrearActividad.isVisible( ) )
        {
			ventanaCrearActividad = new VentanaCrearActividad(login, controlador);
			ventanaCrearActividad.setVisible( true );
        }
	}

	private void mostrarVentanaCrearResena(String login, ControladorResena controladorResena, ControladorActividad controladorActividad) {
		if( ventanaCrearResena == null || !ventanaCrearResena.isVisible( ) )
        {
			ventanaCrearResena = new VentanaCrearResena(login, "Profesor", controladorResena, controladorActividad);
			ventanaCrearResena.setVisible( true );
        }
	}

	private void mostrarVentanaEditarLearningPath(String login, ControladorLearningPath controladorLearningPath, ControladorActividad controladorActividad) {
		if( ventanaEditarLearningPaths == null || !ventanaEditarLearningPaths.isVisible( ) )
        {
			ventanaEditarLearningPaths = new VentanaEditarLearningPaths(login, controladorLearningPath, controladorActividad);
			ventanaEditarLearningPaths.setVisible( true );
        }
	}

	private void mostrarVentanaEditarActividad(String login, ControladorActividad controlador) {
		if( ventanaEditarActividad == null || !ventanaEditarActividad.isVisible( ) )
        {
			ventanaEditarActividad = new VentanaEditarActividad(login, controlador);
			ventanaEditarActividad.setVisible( true );
        }
	}
		
}
