package interfaces;

import componentes.LearningPath;
import controladores.ControladorLearningPath;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

public class VentanaVerLearningPathsPropios extends JFrame implements ActionListener, ListSelectionListener {

	String loginActual;
	ControladorLearningPath controlador;
	
	private JList<LearningPath> listaLearningPaths;
	private DefaultListModel<LearningPath> dataModel;
	private JLabel titulo = new JLabel("Titulo: ");
	private JLabel descripcion = new JLabel("Descripcion: ");
	private JLabel nivelDificultad = new JLabel("Nivel de dificultad: ");
	private JLabel duracion = new JLabel("Duracion: ");
	private JLabel id = new JLabel("Id: ");
	

	public VentanaVerLearningPathsPropios(String login, ControladorLearningPath controlador){
		this.loginActual = login;
		this.controlador = controlador;

		this.setLayout(new BorderLayout());
		dataModel = new DefaultListModel<>( );
		listaLearningPaths = new JList<>( dataModel );
		listaLearningPaths.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		listaLearningPaths.addListSelectionListener( this );
		Collection<LearningPath> learningPaths = controlador.getLearningPathsPropios(loginActual);

		JScrollPane scroll = new JScrollPane( listaLearningPaths );
		scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		this.add(scroll, BorderLayout.CENTER);

		actualizarLearningPaths(learningPaths);

		JButton butCerrar = new JButton("Cerrar");
		butCerrar.setActionCommand("Cerrar");
		butCerrar.addActionListener(this);
		this.add(butCerrar, BorderLayout.NORTH);
		
		JPanel details = new JPanel();
		details.setLayout(new GridLayout(5, 1));
		details.add(titulo);
		details.add(descripcion);
		details.add(nivelDificultad);
		details.add(duracion);
		details.add(id);
		this.add(details, BorderLayout.SOUTH);
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if (comando.equals("Cerrar")) {
			this.dispose();
		}

	}

	private void actualizarLearningPaths(Collection<LearningPath> learningPaths) {
		List<LearningPath> nuevosLearningPaths = new ArrayList<>();
		for (LearningPath learningPath : nuevosLearningPaths) {
			
			if (!dataModel.contains(learningPath)) {
				nuevosLearningPaths.add(learningPath);
			}
		}
		dataModel.addAll(learningPaths);
	}

	private void actualizarDetalles(LearningPath learningPath) {
		titulo.setText("Titulo: " + learningPath.getTitulo());
		descripcion.setText("Descripcion: " + learningPath.getDescripcionGeneral());
		nivelDificultad.setText("Nivel de dificultad: " + learningPath.getNivelDificultad());
		duracion.setText("Duracion: " + learningPath.getDuracion());
		id.setText("Id: " + learningPath.getId());
	}

	@Override
	public void valueChanged(javax.swing.event.ListSelectionEvent e) {
		LearningPath selectedValue = listaLearningPaths.getSelectedValue();
		if (selectedValue != null) {
			actualizarDetalles(selectedValue);
		}
	}




}
