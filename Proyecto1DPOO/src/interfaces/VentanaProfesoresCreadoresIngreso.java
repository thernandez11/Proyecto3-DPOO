package interfaces;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;


public class VentanaProfesoresCreadoresIngreso implements ActionListener {
	
	private JFrame ventana;
	private VentanaIngresoProfesor ventanaIngresoProfesor;
	private VentanaRegistroProfesor ventanaRegistroProfesor;
	
	public VentanaProfesoresCreadoresIngreso()throws IOException {
        
		System.out.println("a");
        ventana = new JFrame("Proyecto 3");
        ventana.setLayout(new FlowLayout());
        
        JButton butIngresar = new JButton("Ingresar");
        butIngresar.setActionCommand("Ingresar");
        butIngresar.addActionListener(this);
        ventana.add(butIngresar);
        
        JButton butRegistrar = new JButton("Registrar");
        butRegistrar.setActionCommand("Registrar");
        butRegistrar.addActionListener(this);
        ventana.add(butRegistrar);
        
        ventana.setSize(300, 200);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
	}
	
	public static void main( String[] args ) throws IOException
    {
        new VentanaProfesoresCreadoresIngreso();
    }
	
	public void actionPerformed( ActionEvent e )
    {
        String comando = e.getActionCommand( );
        if( comando.equals("Ingresar") )
        {
            try {
				mostrarVentanaIngreso( );
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
        else if( comando.equals("Registrar") )
        {
        	try {
				mostrarVentanaRegistro( );
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
    }

	private void mostrarVentanaIngreso() throws IOException {
		if( ventanaIngresoProfesor == null || !ventanaIngresoProfesor.isVisible( ) )
        {
			ventanaIngresoProfesor = new VentanaIngresoProfesor();
			ventanaIngresoProfesor.setVisible( true );
        }
	}
	
	private void mostrarVentanaRegistro() throws IOException {
		// TODO Auto-generated method stub
		if( ventanaRegistroProfesor == null || !ventanaRegistroProfesor.isVisible( ) )
        {
			ventanaRegistroProfesor = new VentanaRegistroProfesor();
			ventanaRegistroProfesor.setVisible( true );
        }
	}

	
}
