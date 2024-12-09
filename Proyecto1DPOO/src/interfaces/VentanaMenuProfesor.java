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
public class VentanaMenuProfesor  extends JFrame implements ActionListener  {
	
	private ControladorActividad AC;
	private ControladorLearningPath LPC;
	private ControladorResena RC;
	private String loginActual;
	
	private VentanaVerLearningPaths ventanaVerLearningPaths;
	private VentanaVerActividades ventanaVerActividades;
	private VentanaVerResenas ventanaVerResenas;
	
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
        		mostrarVentanaCrearLearningPath(loginActual, LPC);
        		break;
        	case "Crear actividad":
        		mostrarVentanaCrearActividad(loginActual, AC);
        		break;
        	case "Crear reseña":
        		mostrarVentanaCrearResena(loginActual, RC);
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

	private void mostrarVentanaCrearLearningPath(String loginActual2, ControladorLearningPath lPC2) {
		if( ventanaVerLearningPaths == null || !ventanaVerLearningPaths.isVisible( ) )
        {
			ventanaVerLearningPaths = new VentanaVerLearningPaths(login, controlador);
			ventanaVerLearningPaths.setVisible( true );
        }
	}

	private void mostrarVentanaCrearActividad(String loginActual2, ControladorActividad aC2) {
		if( ventanaVerLearningPaths == null || !ventanaVerLearningPaths.isVisible( ) )
        {
			ventanaVerLearningPaths = new VentanaVerLearningPaths(login, controlador);
			ventanaVerLearningPaths.setVisible( true );
        }
	}

	private void mostrarVentanaCrearResena(String loginActual2, ControladorResena rC2) {
		if( ventanaVerLearningPaths == null || !ventanaVerLearningPaths.isVisible( ) )
        {
			ventanaVerLearningPaths = new VentanaVerLearningPaths(login, controlador);
			ventanaVerLearningPaths.setVisible( true );
        }
	}

	private void mostrarVentanaEditarLearningPath(String loginActual2, ControladorLearningPath lPC2) {
		if( ventanaVerLearningPaths == null || !ventanaVerLearningPaths.isVisible( ) )
        {
			ventanaVerLearningPaths = new VentanaVerLearningPaths(login, controlador);
			ventanaVerLearningPaths.setVisible( true );
        }
	}

	private void mostrarVentanaEditarActividad(String loginActual2, ControladorActividad aC2) {
		if( ventanaVerLearningPaths == null || !ventanaVerLearningPaths.isVisible( ) )
        {
			ventanaVerLearningPaths = new VentanaVerLearningPaths(login, controlador);
			ventanaVerLearningPaths.setVisible( true );
        }
	}
		
}
