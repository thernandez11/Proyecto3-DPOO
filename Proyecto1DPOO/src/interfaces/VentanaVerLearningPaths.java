package interfaces;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import componentes.LearningPath;
import controladores.ControladorLearningPath;

@SuppressWarnings("serial")
public class VentanaVerLearningPaths extends JFrame implements ActionListener, ListSelectionListener {

	String loginActual;
	ControladorLearningPath controlador;
	
	private JList<LearningPath> listaLearningPaths;
	private DefaultListModel<LearningPath> dataModel;
	private JLabel titulo = new JLabel("Titulo: ");
	private JLabel descripcion = new JLabel("Descripcion: ");
	private JLabel nivelDificultad = new JLabel("Nivel de dificultad: ");
	private JLabel duracion = new JLabel("Duracion: ");
	private JLabel id = new JLabel("Id: ");
	private JLabel creador = new JLabel("Creador: ");
	
	
	public VentanaVerLearningPaths(String login, ControladorLearningPath controlador){
		this.loginActual = login;
		this.controlador = controlador;
		
		this.setLayout(new BorderLayout());
		dataModel = new DefaultListModel<>( );
		listaLearningPaths = new JList<>( dataModel );
		listaLearningPaths.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		listaLearningPaths.addListSelectionListener( this );
		Collection<LearningPath> learningPaths = controlador.getLearningPaths();
		
		JScrollPane scroll = new JScrollPane( listaLearningPaths );
        scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        this.add(scroll, BorderLayout.CENTER);
        actualizarLearningPaths(learningPaths);
        
        JButton butCerrar = new JButton("Cerrar");
        butCerrar.setActionCommand("Cerrar");
        butCerrar.addActionListener(this);
        this.add(butCerrar, BorderLayout.NORTH);
        
        JPanel detalles = new JPanel();
        detalles.setLayout(new GridLayout(6, 1));
        detalles.add(titulo);
        detalles.add(descripcion);
        detalles.add(nivelDificultad);
        detalles.add(duracion);
        detalles.add(id);
        detalles.add(creador);
        this.add(detalles, BorderLayout.SOUTH);
        
		this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
    public void actualizarLearningPaths( Collection<LearningPath> learningPaths )
    {
        List<LearningPath> nuevosLearningPaths = new ArrayList<LearningPath>( );
        for( LearningPath lp : learningPaths )
        {
            if( !dataModel.contains( lp ) )
            	nuevosLearningPaths.add( lp );
        }
        dataModel.addAll( nuevosLearningPaths );
    }
    
	private void actualizarDetalles(LearningPath selectedValue) {
		titulo.setText("Titulo: " + selectedValue.getTitulo());
		descripcion.setText("Descripcion: " + selectedValue.getDescripcionGeneral());
		nivelDificultad.setText("Nivel de dificultad: " + selectedValue.getNivelDificultad());
		duracion.setText("Duracion: " + selectedValue.getDuracion() + " minutos");
		id.setText("Id: " + selectedValue.getId());
		creador.setText("Creador: " + selectedValue.getLoginCreador());
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals("Cerrar") )
        {
        	dispose();
        }
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		Collection<LearningPath> learningPaths = controlador.getLearningPaths();
		actualizarLearningPaths(learningPaths);
		actualizarDetalles(listaLearningPaths.getSelectedValue());
	}

}
