package interfaz.estudiantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controladores.ControladorResena;
import controladores.ControladorActividad;
import controladores.ControladorLearningPath;
import controladores.ControladorRegistros;
import controladores.ControladorActividad;

public class VentanaMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public VentanaMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(3, 3, 0, 0));

        // Button to View Learning Paths
        JButton btnVerLP = new JButton("Ver Learning Paths");
        btnVerLP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Pass the required controllers and login to the new window
                ControladorLearningPath controladorLearningPath = new ControladorLearningPath(null);
                ControladorRegistros controladorRegistros = new ControladorRegistros();
                String loginActual = "estudiante123"; // Replace with actual login or pass it dynamically

                // Open the VentanaExplorarLP with the necessary parameters
                new VentanaExplorarLP(controladorLearningPath, loginActual);
            }
        });
        contentPane.add(btnVerLP);
        
        // Button to View Activities
        JButton btnVerActividades = new JButton("Ver actividades");
        btnVerActividades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and show VentanaVerActividad
                ControladorActividad controladorActividad = new ControladorActividad();
                new VentanaVerActividad(controladorActividad);
            }
        });
        contentPane.add(btnVerActividades);

        // Button to View Reviews of an Activity
        JButton btnVerResena = new JButton("Ver reseñas actividad");
        btnVerResena.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and show VentanaVerResena for a specific activity ID
                ControladorResena controladorResena = new ControladorResena();
                int idActividad = 1; // Example activity ID, replace with dynamic one
                new VentanaVerResena(controladorResena, idActividad);
            }
        });
        contentPane.add(btnVerResena);

        // Button to Create a Review
        JButton btnCrearResena = new JButton("Crear Reseña");
        btnCrearResena.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open the Create Review window (Not implemented yet)
            }
        });
        contentPane.add(btnCrearResena);

        // Button to Review Learning Path Progress
        JButton btnProgresoLP = new JButton("Revisar progreso LP");
        btnProgresoLP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open the progress window (Not implemented yet)
            }
        });
        contentPane.add(btnProgresoLP);

        // Button to View Activity Map
        JButton btnMapa = new JButton("Revisar mapa actividades");
        btnMapa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open activity map window (Not implemented yet)
            }
        });
        contentPane.add(btnMapa);

        // Button to Exit
        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Close the application
            }
        });
        contentPane.add(btnSalir);
    }

    // Main method to launch the application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaMenu frame = new VentanaMenu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
