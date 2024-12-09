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

import componentes.Resena;
import controladores.ControladorResena;

@SuppressWarnings("serial")
public class VentanaVerResenas extends JFrame implements ActionListener, ListSelectionListener {

	String loginActual;
	ControladorResena controlador;
	
	private JList<Resena> listaResenas;
	private DefaultListModel<Resena> dataModel;
	private JLabel opinion = new JLabel("Opinion: ");
	private JLabel rating = new JLabel("Rating: ");
	private JLabel loginAutor = new JLabel("Login autor: ");
	private JLabel rolAutor = new JLabel("Rol autor: ");
	private JLabel idActividad = new JLabel("Id Actividad: ");
	
	
	public VentanaVerResenas(String login, ControladorResena controlador){
		this.loginActual = login;
		this.controlador = controlador;
		
		this.setLayout(new BorderLayout());
		dataModel = new DefaultListModel<>( );
		listaResenas = new JList<>( dataModel );
		listaResenas.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		listaResenas.addListSelectionListener( this );
		Collection<Resena> resenas = controlador.getResenas();
		
		JScrollPane scroll = new JScrollPane( listaResenas );
        scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        this.add(scroll, BorderLayout.CENTER);
        actualizarResenas(resenas);
        
        JButton butCerrar = new JButton("Cerrar");
        butCerrar.setActionCommand("Cerrar");
        butCerrar.addActionListener(this);
        this.add(butCerrar, BorderLayout.NORTH);
        
        JPanel detalles = new JPanel();
        detalles.setLayout(new GridLayout(5, 1));
        detalles.add(opinion);
        detalles.add(rating);
        detalles.add(loginAutor);
        detalles.add(rolAutor);
        detalles.add(idActividad);
        this.add(detalles, BorderLayout.SOUTH);
        
		this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
    public void actualizarResenas( Collection<Resena> resenas )
    {
        List<Resena> nuevasResenas = new ArrayList<Resena>( );
        for( Resena r : resenas )
        {
            if( !dataModel.contains( r ) )
            	nuevasResenas.add( r );
        }
        dataModel.addAll( nuevasResenas );
    }
    
	private void actualizarDetalles(Resena selectedValue) {
		opinion.setText("Opinion: " + selectedValue.getOpinion());
		rating.setText("Rating: " + selectedValue.getRating());
		loginAutor.setText("Login autor: " + selectedValue.getLoginAutor());
		rolAutor.setText("Rol autor: " + selectedValue.getRolAutor());
		idActividad.setText("Id actividad: " + selectedValue.getIdActividad());
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
		Collection<Resena> resenas = controlador.getResenas();
		actualizarResenas(resenas);
		actualizarDetalles(listaResenas.getSelectedValue());
	}

}