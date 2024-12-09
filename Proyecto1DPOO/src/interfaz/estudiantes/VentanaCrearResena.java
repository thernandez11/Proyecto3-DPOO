package interfaz.estudiantes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import componentes.Actividad;
import controladores.ControladorActividad;
import controladores.ControladorResena;

@SuppressWarnings("serial")
public class VentanaCrearResena extends JFrame implements ActionListener, ListSelectionListener {

    private String loginActual;
    private String rolActual;
    private ControladorResena controladorResena;
    private ControladorActividad controladorActividad;

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

        // Window settings
        this.setTitle("Dejar una Reseña");
        this.setLayout(new BorderLayout());
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Activity list
        dataModel = new DefaultListModel<>();
        listaActividades = new JList<>(dataModel);
        listaActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaActividades.addListSelectionListener(this);
        listaActividades.setCellRenderer(new ActivityRenderer()); // Custom renderer

        JScrollPane scroll = new JScrollPane(listaActividades);
        this.add(scroll, BorderLayout.CENTER);

        // Review input panel
        JPanel panelResena = new JPanel();
        panelResena.setLayout(new GridLayout(2, 1));

        // Opinion panel
        JPanel panelOpinion = new JPanel();
        panelOpinion.setLayout(new GridLayout(2, 1));
        panelOpinion.add(new JLabel("Digite su opinión de la actividad:"));
        opinion = new JTextField();
        panelOpinion.add(opinion);
        panelResena.add(panelOpinion);

        // Rating panel
        JPanel panelRating = new JPanel();
        panelRating.setLayout(new GridLayout(2, 1));
        panelRating.add(new JLabel("Elija el rating que merece la actividad:"));
        Integer[] numeros = {1, 2, 3, 4, 5};
        rating = new JComboBox<>(numeros);
        panelRating.add(rating);
        panelResena.add(panelRating);

        this.add(panelResena, BorderLayout.NORTH);

        // Button panel
        JPanel botones = new JPanel();
        botones.setLayout(new GridLayout(1, 2));
        JButton butCerrar = new JButton("Cerrar");
        butCerrar.setActionCommand("Cerrar");
        butCerrar.addActionListener(this);
        botones.add(butCerrar);
        JButton butEnviar = new JButton("Enviar");
        butEnviar.setActionCommand("Enviar");
        butEnviar.addActionListener(this);
        botones.add(butEnviar);
        this.add(botones, BorderLayout.SOUTH);

        // Populate activities
        Collection<Actividad> actividades = controladorActividad.getActividades();
        actualizarActividades(actividades);
        setVisible(true);
    }

    public void actualizarActividades(Collection<Actividad> actividades) {
        dataModel.clear(); // Clear the current list
        for (Actividad a : actividades) {
            dataModel.addElement(a); // Add each activity
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        String strOpinion = opinion.getText().trim();
        Integer intRating = (Integer) rating.getSelectedItem();

        if (comando.equals("Cerrar")) {
            dispose();
        } else if (comando.equals("Enviar")) {
            if (strOpinion.isEmpty() || elegida == null) {
                JOptionPane.showMessageDialog(this, "Por favor, elige una actividad y escribe tu opinión.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "¡Reseña enviada exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
        if (!e.getValueIsAdjusting()) {
            elegida = listaActividades.getSelectedValue();
        }
    }

    /**
     * Custom renderer to display activity IDs.
     */
    private static class ActivityRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Actividad) {
                value = "Actividad ID: " + ((Actividad) value).getId();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    public static void main(String[] args) {
        // Mock controllers
        ControladorActividad controladorActividad = new ControladorActividad();
        ControladorResena controladorResena = new ControladorResena();

        // Add mock activities
        controladorActividad.crearActividad("juan123");
        controladorActividad.editarDescripcion(1, "Completar el proyecto de final de curso.");
        controladorActividad.crearActividad("maria456");
        controladorActividad.editarDescripcion(2, "Ver el video introductorio sobre grafos.");

        // Launch the GUI
        new VentanaCrearResena("testUser", "Estudiante", controladorResena, controladorActividad);
    }
}
