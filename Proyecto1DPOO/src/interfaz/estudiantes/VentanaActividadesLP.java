package interfaz.estudiantes;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Map;
import componentes.Actividad;
import componentes.LearningPath;
import controladores.ControladorActividad;
import controladores.ControladorLearningPath;

public class VentanaActividadesLP extends JFrame implements ListSelectionListener {

    private ControladorLearningPath controladorLP;
    private ControladorActividad controladorActividad;
    private LearningPath selectedLearningPath;
    private String loginActual;
    private JList<String> listaActividades;
    private DefaultListModel<String> listModel;
    private JButton btnHacerActividad;

    public VentanaActividadesLP(ControladorLearningPath controladorLP, ControladorActividad controladorActividad, 
                                LearningPath selectedLearningPath, String loginActual) {
        this.controladorLP = controladorLP;
        this.controladorActividad = controladorActividad;
        this.selectedLearningPath = selectedLearningPath;
        this.loginActual = loginActual;

        // Window setup
        setTitle("Actividades del Learning Path");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 500);

        // List to display activity IDs
        listModel = new DefaultListModel<>();
        listaActividades = new JList<>(listModel);
        listaActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaActividades.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(listaActividades);
        add(scrollPane, BorderLayout.CENTER);

        // Button to start an activity
        btnHacerActividad = new JButton("Hacer Actividad");
        btnHacerActividad.setEnabled(false);
        btnHacerActividad.addActionListener(e -> hacerActividad());

        // Button panel
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnHacerActividad);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        // Load the activities for the selected Learning Path
        cargarActividades();

        setVisible(true);
    }

    /**
     * Load activities for the selected Learning Path and display their IDs.
     * Only activities with at least one question will be displayed.
     */
    private void cargarActividades() {
        listModel.clear();

        // Assuming activities are stored in a HashMap with activity ID as the key
        Map<Actividad, Boolean> actividades = selectedLearningPath.getActividades();
        if (actividades != null) {
            actividades.forEach((actividad, isCompleted) -> {
                // Check if the activity has any questions (of any type)
                if (actividad.getPreguntasMultiples().size() > 0 ||
                    actividad.getPreguntasVerdaderoFalso().size() > 0 ||
                    actividad.getPreguntasAbiertas().size() > 0) {
                    listModel.addElement("ID: " + actividad.getId()); // Displaying activity ID
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "No hay preguntas en esta actividad.", 
                                          "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Handle activity selection event.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String selectedActivityId = listaActividades.getSelectedValue();
            if (selectedActivityId != null) {
                // Extract the ID from the selected string
                int activityId = Integer.parseInt(selectedActivityId.split(": ")[1]);
                // Enable the button to start the activity if the activity has questions
                Actividad selectedActivity = controladorActividad.getActividad(activityId);
                if (selectedActivity != null &&
                    (selectedActivity.getPreguntasMultiples().size() > 0 ||
                     selectedActivity.getPreguntasVerdaderoFalso().size() > 0 ||
                     selectedActivity.getPreguntasAbiertas().size() > 0)) {
                    btnHacerActividad.setEnabled(true);
                } else {
                    btnHacerActividad.setEnabled(false);
                }
            } else {
                btnHacerActividad.setEnabled(false);
            }
        }
    }

    /**
     * Handle activity and open the first question
     */
    public void hacerActividad() {
        String selectedActivityId = listaActividades.getSelectedValue();
        if (selectedActivityId != null) {
            int activityId = Integer.parseInt(selectedActivityId.split(": ")[1]);
            Actividad selectedActivity = controladorActividad.getActividad(activityId);

            if (selectedActivity != null &&
                (selectedActivity.getPreguntasMultiples().size() > 0 ||
                 selectedActivity.getPreguntasVerdaderoFalso().size() > 0 ||
                 selectedActivity.getPreguntasAbiertas().size() > 0)) {
                // Open the window corresponding to the activity type
                new VentanaActividad(selectedActivity, controladorActividad, loginActual);
            } else {
                JOptionPane.showMessageDialog(this, "Actividad no tiene preguntas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
