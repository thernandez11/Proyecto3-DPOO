package interfaz.estudiantes;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import componentes.LearningPath;
import controladores.ControladorActividad;
import controladores.ControladorLearningPath;
import controladores.ControladorRegistros;

public class VentanaMisLearningPaths extends JFrame implements ListSelectionListener {

    private ControladorLearningPath controladorLP;
    private ControladorRegistros controladorRegistros;
    private String loginActual;
    private ControladorActividad controladorActividad; // New field for ControladorActividad

    private JList<LearningPath> listaLPs;
    private DefaultListModel<LearningPath> listModel;

    private JLabel lblTitulo, lblDescripcion, lblNivel, lblDuracion, lblFechaCreacion, lblFechaModificacion,
            lblVersion, lblCreador, lblId;
    private JButton btnInspeccionar;

    
    public VentanaMisLearningPaths(ControladorLearningPath controladorLP, ControladorRegistros controladorRegistros, String loginActual, ControladorActividad controladorActividad) {
        this.controladorLP = controladorLP;
        this.controladorRegistros = controladorRegistros;
        this.loginActual = loginActual;
        this.controladorActividad = controladorActividad;
            
        // Configuración de la ventana
        setTitle("Mis Learning Paths");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        add(scrollPane, BorderLayout.CENTER);

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

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        btnInspeccionar = new JButton("Inspeccionar");
        btnInspeccionar.setEnabled(false);
        btnInspeccionar.addActionListener(e -> inspeccionarLearningPath());

        panelBotones.add(btnInspeccionar);
        panelBotones.add(btnCerrar);

        // Combine details and buttons
        JPanel panelSouth = new JPanel(new BorderLayout());
        panelSouth.add(panelDetalles, BorderLayout.CENTER);
        panelSouth.add(panelBotones, BorderLayout.SOUTH);

        add(panelSouth, BorderLayout.SOUTH);

        // Cargar Learning Paths registrados
        cargarMisLearningPaths();

        setVisible(true);
    }

    /**
     * Filtra y carga los Learning Paths registrados por el usuario.
     */
    private void cargarMisLearningPaths() {
        Collection<LearningPath> allLearningPaths = controladorLP.getLearningPaths();
        listModel.clear();

        for (LearningPath lp : allLearningPaths) {
            if (controladorRegistros.getRegistroLp(loginActual, lp.getId()) != null) {
                listModel.addElement(lp);
            }
        }

        if (listModel.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No estás inscrito en ningún Learning Path.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
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
                btnInspeccionar.setEnabled(true);
            } else {
                btnInspeccionar.setEnabled(false);
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
     * Maneja el evento para inspeccionar un Learning Path.
     */
    private void inspeccionarLearningPath() {
        LearningPath selectedLP = listaLPs.getSelectedValue();
        if (selectedLP == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un Learning Path para inspeccionar.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        new VentanaActividadesLP(controladorLP, controladorActividad, selectedLP, loginActual); 
    }
}
