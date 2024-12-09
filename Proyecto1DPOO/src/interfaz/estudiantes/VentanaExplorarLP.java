package interfaz.estudiantes;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import controladores.ControladorActividad;
import controladores.ControladorLearningPath;
import controladores.ControladorRegistros;
import componentes.Actividad;
import componentes.LearningPath;
import componentes.Opcion;
import componentes.PreguntaAbierta;
import componentes.PreguntaMultiple;
import componentes.PreguntaVerdaderoFalso;
import componentes.RegistroLearningPath;

public class VentanaExplorarLP extends JFrame implements ListSelectionListener {

    private ControladorLearningPath controladorLP;
    private ControladorRegistros controladorRegistros;
    private String loginActual;
    private ControladorActividad controladorActividad;
    private JList<LearningPath> listaLPs;
    private DefaultListModel<LearningPath> listModel;

    private JLabel lblTitulo, lblDescripcion, lblNivel, lblDuracion, lblFechaCreacion, lblFechaModificacion,
            lblVersion, lblCreador, lblId;

    public VentanaExplorarLP(ControladorLearningPath controladorLP, String loginActual) {
        this.controladorLP = controladorLP;
        this.loginActual = loginActual;

        // Configuración de la ventana
        setTitle("Learning Paths");
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);

        // Panel de lista
        listModel = new DefaultListModel<>();
        listaLPs = new JList<>(listModel);
        listaLPs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Custom renderer to display the title of LearningPath in the list
        listaLPs.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                LearningPath lp = (LearningPath) value;
                return super.getListCellRendererComponent(list, lp.getTitulo(), index, isSelected, cellHasFocus);
            }
        });

        listaLPs.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(listaLPs);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Panel de detalles
        JPanel panelDetalles = new JPanel();
        panelDetalles.setLayout(new GridLayout(9, 1));
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles"));

        lblTitulo = new JLabel("Título: ");
        lblDescripcion = new JLabel("Descripción: ");
        lblNivel = new JLabel("Nivel de Dificultad: ");
        lblDuracion = new JLabel("Duración: ");
        lblFechaCreacion = new JLabel("Fecha de Creación: ");
        lblFechaModificacion = new JLabel("Última Modificación: ");
        lblVersion = new JLabel("Versión: ");
        lblCreador = new JLabel("Creador: ");
        lblId = new JLabel("ID: ");
        panelDetalles.add(lblTitulo);
        panelDetalles.add(lblDescripcion);
        panelDetalles.add(lblNivel);
        panelDetalles.add(lblDuracion);
        panelDetalles.add(lblFechaCreacion);
        panelDetalles.add(lblFechaModificacion);
        panelDetalles.add(lblVersion);
        panelDetalles.add(lblCreador);
        panelDetalles.add(lblId);

        // Combina detalles y botones en un solo panel
        JPanel panelSouth = new JPanel(new BorderLayout());
        panelSouth.add(panelDetalles, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();

        JButton btnInscribirse = new JButton("Inscribirse");
        btnInscribirse.addActionListener(e -> inscribirLearningPath());

        panelBotones.add(btnInscribirse);

        panelSouth.add(panelBotones, BorderLayout.SOUTH);

        getContentPane().add(panelSouth, BorderLayout.SOUTH);
        
        JButton btnMisLearningPaths = new JButton("Mis Learning Paths");
        btnMisLearningPaths.addActionListener(e -> verMisLearningPaths());
        panelBotones.add(btnMisLearningPaths);
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnCerrar);

        
        
        // Cargar datos iniciales
        cargarLearningPaths();

        setVisible(true);
    }
 

    private void verMisLearningPaths() {
        // Check if there are any Learning Paths where the user is registered
        boolean hasRegisteredLPs = controladorLP.getLearningPaths().stream()
                .anyMatch(lp -> {
                    ArrayList<RegistroLearningPath> rlps = controladorRegistros.getRegistrosLp().get(lp.getId());
                    if (rlps != null) {
                        return rlps.stream().anyMatch(rlp -> rlp.getLoginEstudiante().equals(loginActual));
                    }
                    return false;
                });

        if (hasRegisteredLPs) {
            // Pass controladorActividad to the new window
            new VentanaMisLearningPaths(controladorLP, controladorRegistros, loginActual, controladorActividad);
        } else {
            JOptionPane.showMessageDialog(this, 
                "No estás inscrito en ningún Learning Path.", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    
    /**
     * Obtiene y muestra la lista de Learning Paths.
     */
    private void cargarLearningPaths() {
        Collection<LearningPath> learningPaths = controladorLP.getLearningPaths();
        listModel.clear();
        for (LearningPath lp : learningPaths) {
            listModel.addElement(lp); // Add entire LearningPath object
        }
    }

    /**
     * Se activa cuando se selecciona un Learning Path de la lista.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            LearningPath selectedLP = listaLPs.getSelectedValue();
            if (selectedLP != null) {
                actualizarDetalles(selectedLP);
            }
        }
    }

    /**
     * Actualiza la sección de detalles con la información del Learning Path seleccionado.
     */
    private void actualizarDetalles(LearningPath lp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        lblTitulo.setText("Título: " + lp.getTitulo());
        lblDescripcion.setText("Descripción: " + lp.getDescripcionGeneral());
        lblNivel.setText("Nivel de Dificultad: " + lp.getNivelDificultad());
        lblDuracion.setText("Duración: " + lp.getDuracion() + " minutos");
        lblFechaCreacion.setText("Fecha de Creación: " + (lp.getFechaCreacion() != null ? lp.getFechaCreacion().format(formatter) : "N/A"));
        lblFechaModificacion.setText("Última Modificación: " + (lp.getFechaModificacion() != null ? lp.getFechaModificacion().format(formatter) : "N/A"));
        lblVersion.setText("Versión: " + lp.getVersion());
        lblCreador.setText("Creador: " + lp.getLoginCreador());
        lblId.setText("ID: " + lp.getId());
    }

    /**
     * Maneja el registro en el Learning Path seleccionado.
     */
    private void inscribirLearningPath() {
        LearningPath selectedLP = listaLPs.getSelectedValue();
        if (selectedLP == null) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un Learning Path para inscribirse.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ensure records exist for the selected Learning Path ID
        if (controladorRegistros.getRegistrosLp().containsKey(selectedLP.getId())) {
            // Check if the student is already registered
            if (controladorRegistros.getRegistroLp(loginActual, selectedLP.getId()) != null) {
                JOptionPane.showMessageDialog(this, 
                    "Ya estás inscrito en el Learning Path: " + selectedLP.getTitulo(), 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Proceed with registration
        controladorRegistros.crearRegistroLpEstudiante(loginActual, selectedLP);
        controladorRegistros.crearRegistrosActividad(loginActual, selectedLP);

        JOptionPane.showMessageDialog(this, 
            "Te has inscrito exitosamente al Learning Path: " + selectedLP.getTitulo(), 
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
    }



}