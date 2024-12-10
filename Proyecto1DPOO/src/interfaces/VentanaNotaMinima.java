package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controladores.ControladorActividad;

@SuppressWarnings("serial")
public class VentanaNotaMinima extends JFrame implements ActionListener {

	String loginActual;
	int id;
	ControladorActividad controlador;
	
	JComboBox<Integer> nota;
	
	public VentanaNotaMinima(String login , ControladorActividad controlador, int id) {
		
		this.loginActual = login;
		this.controlador = controlador;
		this.id = id;
		this.setLayout(new BorderLayout());

		
		//Panel informacion
		JPanel informacion = new JPanel();
		informacion.setLayout(new GridLayout(12,1));
		
		informacion.add(new JLabel("Nota minima: "));
		Integer[] notas = {10,20,30,40,50,60,70,80,100};
		nota = new JComboBox<>(notas);
		informacion.add(nota);
		this.add(informacion, BorderLayout.CENTER);
		
		//Panel Botones
		JPanel botones = new JPanel();
        botones.setLayout(new FlowLayout());
        JButton butCerrar = new JButton("Cerrar");
        butCerrar.setActionCommand("Cerrar");
        butCerrar.addActionListener(this);
        botones.add(butCerrar);
        JButton butEnviar = new JButton("Confirmar");
        butEnviar.setActionCommand("Crear");
        butEnviar.addActionListener(this);
        botones.add(butEnviar);
        this.add(botones, BorderLayout.NORTH);
        
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
		String strNota = nota.getSelectedItem().toString();
		if( comando.equals("Cerrar") ) {
        	dispose();
        } else if ( comando.equals("Crear") ) {
        	controlador.editarNotaMinima(id, Integer.valueOf(strNota));
        	dispose();
        }
	}

}

