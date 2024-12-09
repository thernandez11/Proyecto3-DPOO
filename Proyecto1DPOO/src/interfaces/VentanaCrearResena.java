package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import componentes.Actividad;
import controladores.ControladorActividad;
import controladores.ControladorResena;

@SuppressWarnings("serial")
public class VentanaCrearResena extends JFrame implements ActionListener, ListSelectionListener {

	String loginActual;
	String rolActual;
	ControladorResena controladorResena;
	ControladorActividad controladorActividad;
	
	private JList<Actividad> listaActividades;
	private DefaultListModel<Actividad> dataModel;
	
	private JTextField opinion;
	private JComboBox<Integer> rating;
	private Actividad elegida;
	
	
	public VentanaCrearResena(String login, String rolActual, ControladorResena controladorResena, ControladorActividad controladorActividad) {
		this.loginActual = login;
		this.rolActual = rolActual;
		this.controladorResena = controladorResena;
		this.controladorActividad = controladorActividad;
		
		this.setLayout(new BorderLayout());
		dataModel = new DefaultListModel<>( );
		listaActividades = new JList<>( dataModel );
		listaActividades.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		listaActividades.addListSelectionListener( this );
		Collection<Actividad> actividades = controladorActividad.getActividades();
		
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
        JButton butEnviar = new JButton("Enviar");
        butEnviar.setActionCommand("Enviar");
        butEnviar.addActionListener(this);
        botones.add(butEnviar);
        this.add(botones, BorderLayout.NORTH);
        
        JPanel panelResena = new JPanel();
        panelResena.setLayout(new GridLayout(2, 1));
        
        JPanel panelOpinion = new JPanel();
        panelOpinion.setLayout(new GridLayout(2,1));
        panelOpinion.add(new JLabel("Digite su opinion de la actividad:"));
        opinion = new JTextField();
        panelOpinion.add(opinion);
        panelResena.add(panelOpinion);
        JPanel panelRating = new JPanel();
        panelRating.setLayout(new GridLayout(2,1));
        panelRating.add(new JLabel("Elija el rating que merece la actividad:"));
        Integer[] numeros = {1, 2, 3, 4, 5};
        rating = new JComboBox<>(numeros);
        panelRating.add(rating);
        panelResena.add(panelRating);
        this.add(panelResena, BorderLayout.SOUTH);
        
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


	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
		String strOpinion = opinion.getText().trim();
		String strRating = rating.getSelectedItem().toString();
		Integer intRating = Integer.valueOf(strRating);
        if( comando.equals("Cerrar") )
        {
        	dispose();
        } else if ( comando.equals("Enviar") ) {
        	if (strOpinion.isEmpty() || elegida == null) {
        		JOptionPane.showMessageDialog(this, "Por favor, elige una actividad y escribe tu opinion.", "Error", JOptionPane.ERROR_MESSAGE);
        	} else {
        		JOptionPane.showMessageDialog(this, "Ha enviado su reseña exitosamente!", "Exito", JOptionPane.INFORMATION_MESSAGE);
        		controladorResena.crearResena(elegida.getId(), strOpinion, intRating, loginActual, rolActual);
        		try {
					controladorResena.guardarResenasEnArchivo("resenas.json");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
        		dispose();
        	}
        }
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		Collection<Actividad> actividades = controladorActividad.getActividades();
		elegida = listaActividades.getSelectedValue();
		actualizarActividades(actividades);
	}

}