package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

import componentes.Actividad;
import controladores.ControladorActividad;

@SuppressWarnings("serial")
public class VentanaElegirActividadesPrePost extends JFrame implements ActionListener, ListSelectionListener {

	String loginActual;
	ControladorActividad controlador;
	boolean previo;
	List<Actividad> actividadesPrevias;
	List<Actividad> actividadesSeguimiento;
	VentanaCrearActividad ventanaCrearActividad;
	
	private JList<Actividad> listaActividades;
	private DefaultListModel<Actividad> dataModel;
	private JLabel tipo = new JLabel("Titulo: ");
	private JLabel descripcion = new JLabel("Descripcion: ");
	private JLabel objetivos = new JLabel("Objetivos: ");
	private JLabel nivelDificultad = new JLabel("Nivel de dificultad: ");
	private JLabel duracion = new JLabel("Duracion: ");
	private JLabel id = new JLabel("Id: ");
	private JLabel creador = new JLabel("Creador: ");
	
	
	public VentanaElegirActividadesPrePost(String login, ControladorActividad controlador, boolean previo, List<Actividad> actividadesPrevias, List<Actividad> actividadesSeguimiento, VentanaCrearActividad ventanaCrearActividad){
		this.loginActual = login;
		this.controlador = controlador;
		this.actividadesPrevias = actividadesPrevias;
		this.actividadesSeguimiento = actividadesSeguimiento;
		this.ventanaCrearActividad = ventanaCrearActividad;
		
		this.setLayout(new BorderLayout());
		dataModel = new DefaultListModel<>( );
		listaActividades = new JList<>( dataModel );
		listaActividades.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		listaActividades.addListSelectionListener( this );
		Collection<Actividad> actividades = controlador.getActividades();
		
		JScrollPane scroll = new JScrollPane( listaActividades );
        scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        this.add(scroll, BorderLayout.CENTER);
        actualizarActividades(actividades);
        
        JPanel botones = new JPanel();
        botones.setLayout(new FlowLayout());
        JButton butCerrar = new JButton("Cerrar");
        butCerrar.setActionCommand("Cerrar");
        butCerrar.addActionListener(this);
        botones.add(butCerrar);
        JButton butElegir = new JButton("Elegir");
        butElegir.setActionCommand("Elegir");
        butElegir.addActionListener(this);
        botones.add(butElegir);
        this.add(botones, BorderLayout.NORTH);
        
        JPanel detalles = new JPanel();
        detalles.setLayout(new GridLayout(7, 1));
        detalles.add(tipo);
        detalles.add(descripcion);
        detalles.add(objetivos);
        detalles.add(nivelDificultad);
        detalles.add(duracion);
        detalles.add(id);
        detalles.add(creador);
        this.add(detalles, BorderLayout.SOUTH);
        
		this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
    public void actualizarActividades( Collection<Actividad> actividades )
    {
        List<Actividad> nuevasActividades = new ArrayList<Actividad>( );
        for( Actividad a : actividades )
        {
            if( !dataModel.contains( a ) )
            	nuevasActividades.add( a );
        }
        dataModel.addAll( nuevasActividades );
    }
    
	private void actualizarDetalles(Actividad selectedValue) {
		if (selectedValue != null) {
			tipo.setText("Tipo: " + selectedValue.getTipo());
			descripcion.setText("Descripcion: " + selectedValue.getDescripcion());
			objetivos.setText("Objetivos: " + selectedValue.getObjetivos());
			nivelDificultad.setText("Nivel de dificultad: " + selectedValue.getNivelDificultad());
			duracion.setText("Duracion: " + selectedValue.getDuracion() + " minutos");
			id.setText("Id: " + selectedValue.getId());
			creador.setText("Creador: " + selectedValue.getLoginCreador());
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals("Cerrar") ) {
        	dispose();
        } else if( comando.equals("Elegir") ) {
        	ArrayList<Actividad> actividadesPrevias1 = new ArrayList<>();
        	ArrayList<Actividad> actividadesSeguimiento1 = new ArrayList<>();
        	List<Actividad> actividades = listaActividades.getSelectedValuesList();
        	if(previo) {
        		for (Actividad a : actividades) {
        			actividadesPrevias1.add(a);
        		}
        	} else {
        		for (Actividad a : actividades) {
        			actividadesSeguimiento1.add(a);
        		}
        	}
        	ventanaCrearActividad.actualizarActividadesPrevias(actividadesPrevias1);
        	ventanaCrearActividad.actualizarActividadesSeguimiento(actividadesSeguimiento1);
        	dispose();
        }
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Collection<Actividad> actividades = controlador.getActividades();
		actualizarActividades(actividades);
		actualizarDetalles(listaActividades.getSelectedValue());
	}

}
