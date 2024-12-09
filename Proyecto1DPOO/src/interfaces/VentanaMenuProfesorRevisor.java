package interfaces;

import controladores.ControladorActividad;
import controladores.ControladorLearningPath;
import controladores.ControladorResena;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;

public class VentanaMenuProfesorRevisor extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private ControladorActividad AC;
	private ControladorLearningPath LPC;
	private ControladorResena RC;
	private String loginActual;

	private VentanaVerLearningPaths ventanaVerLearningPaths;
	private VentanaVerActividades ventanaVerActividades;
	private VentanaVerResenas ventanaVerResenas;
	private VentanaVerLearningPathsPropios ventanaVerLearningPathsPropios;
	

	public VentanaMenuProfesorRevisor(String login) throws IOException { 

		this.loginActual = login;
		this.AC = new ControladorActividad();
		this.LPC = new ControladorLearningPath(AC);
		this.RC = new ControladorResena();
		
		AC.cargarActividadesDesdeArchivo("actividades.json");
		LPC.cargarLPDesdeArchivo("learningPaths.json");
		RC.cargarResenasDesdeArchivo("resenas.json");

		this.setLayout(new GridLayout(3, 3));
		String[] botones = {"Ver learning paths", "Ver actividades", "Ver reseñas actividad",
				"Ver Learning Paths Propios", "Ver Estadísticas LP Propio", "Revisar Actividad Estudiante", "Revisar Progreso Estudiante",
				"Revisar Mapa Actividades Diarias", "Salir"};

		for (String boton : botones) {
			JButton but = new JButton(boton);
			but.setActionCommand(boton);
			but.addActionListener(this);
			this.add(but);
		}

		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();

		switch (command) {
			case "Ver learning paths":
				
				ventanaVerLearningPaths = new VentanaVerLearningPaths(loginActual, LPC);
				ventanaVerLearningPaths.setVisible(true);

				break;
			case "Ver actividades":

				ventanaVerActividades = new VentanaVerActividades(loginActual, AC);
				ventanaVerActividades.setVisible(true);

				break;

			case "Ver reseñas actividad":
			
				ventanaVerResenas = new VentanaVerResenas(loginActual, RC);
				ventanaVerResenas.setVisible(true);

				break;
			
			case "Ver Learning Paths Propios":
				ventanaVerLearningPathsPropios = new VentanaVerLearningPathsPropios(loginActual, LPC);
				ventanaVerLearningPathsPropios.setVisible(true);
				break;
			
			default:
				break;
		}
	}



}
