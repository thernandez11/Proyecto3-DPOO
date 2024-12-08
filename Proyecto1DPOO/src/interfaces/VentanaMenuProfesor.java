package interfaces;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class VentanaMenuProfesor  extends JFrame implements ActionListener  {
	
	public VentanaMenuProfesor() throws IOException {
		
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

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
		
}
