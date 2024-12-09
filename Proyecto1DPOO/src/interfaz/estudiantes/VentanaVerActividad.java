package interfaz.estudiantes;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import controladores.ControladorActividad;
import componentes.Actividad;

public class VentanaVerActividad extends JFrame implements ListSelectionListener {

    private ControladorActividad controladorActividad;
    private JList<Actividad> listaActividades;
    private DefaultListModel<Actividad> listModel;

    private JLabel lblId, lblTipo, lblDescripcion, lblNivel, lblDuracion, lblCreador, lblFechaLimite;

    public VentanaVerActividad(ControladorActividad controladorActividad) {
        this.controladorActividad = controladorActividad;

        // Configuración de la ventana
        setTitle("Actividades");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);

        // Panel de lista
        listModel = new DefaultListModel<>();
        listaActividades = new JList<>(listModel);
        listaActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Custom renderer to display the ID of Actividad in the list
        listaActividades.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Actividad actividad = (Actividad) value;
                return super.getListCellRendererComponent(list, "ID: " + actividad.getId(), index, isSelected, cellHasFocus);
            }
        });

        listaActividades.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(listaActividades);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de detalles
        JPanel panelDetalles = new JPanel();
        panelDetalles.setLayout(new GridLayout(7, 1));
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles"));

        lblId = new JLabel("ID: ");
        lblTipo = new JLabel("Tipo: ");
        lblDescripcion = new JLabel("Descripción: ");
        lblNivel = new JLabel("Nivel de Dificultad: ");
        lblDuracion = new JLabel("Duración: ");
        lblCreador = new JLabel("Creador: ");
        lblFechaLimite = new JLabel("Fecha Límite: ");
        panelDetalles.add(lblId);
        panelDetalles.add(lblTipo);
        panelDetalles.add(lblDescripcion);
        panelDetalles.add(lblNivel);
        panelDetalles.add(lblDuracion);
        panelDetalles.add(lblCreador);
        panelDetalles.add(lblFechaLimite);

        // Combina detalles y botón en un solo panel
        JPanel panelSouth = new JPanel(new BorderLayout());
        panelSouth.add(panelDetalles, BorderLayout.CENTER);

        // Botón de cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose()); // Cierra la ventana
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCerrar);

        panelSouth.add(panelBotones, BorderLayout.SOUTH);

        add(panelSouth, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarActividades();

        setVisible(true);
    }

    /**
     * Obtiene y muestra la lista de actividades.
     */
    private void cargarActividades() {
        Collection<Actividad> actividades = controladorActividad.getActividades();
        listModel.clear();
        for (Actividad actividad : actividades) {
            listModel.addElement(actividad); // Add the entire Actividad object
        }
    }

    /**
     * Se activa cuando se selecciona una actividad de la lista.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Actividad selectedActividad = listaActividades.getSelectedValue();
            if (selectedActividad != null) {
                actualizarDetalles(selectedActividad);
            }
        }
    }

    /**
     * Actualiza la sección de detalles con la información de la actividad seleccionada.
     */
    private void actualizarDetalles(Actividad actividad) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        lblId.setText("ID: " + actividad.getId());
        lblTipo.setText("Tipo: " + actividad.getTipo());
        lblDescripcion.setText("Descripción: " + actividad.getDescripcion());
        lblNivel.setText("Nivel de Dificultad: " + actividad.getNivelDificultad());
        lblDuracion.setText("Duración: " + actividad.getDuracion() + " minutos");
        lblCreador.setText("Creador: " + actividad.getLoginCreador());
        lblFechaLimite.setText("Fecha Límite: " + (actividad.getFechaLimite() != null ? actividad.getFechaLimite().format(formatter) : "N/A"));
    }

    public static void main(String[] args) {
        // Create the controller
        ControladorActividad controladorActividad = new ControladorActividad();

        // Add mock activities
        controladorActividad.crearActividad("juan123");
        controladorActividad.editarTipo(1, "Tarea");
        controladorActividad.editarDescripcion(1, "Completar el proyecto de final de curso.");
        controladorActividad.editarNivelDificultad(1, "Media");
        controladorActividad.editarDuracion(1, 180);
        controladorActividad.editarURL(1, "https://proyecto.final.com");

        controladorActividad.crearActividad("maria456");
        controladorActividad.editarTipo(2, "RecursoEducativo");
        controladorActividad.editarDescripcion(2, "Ver el video introductorio sobre grafos.");
        controladorActividad.editarNivelDificultad(2, "Fácil");
        controladorActividad.editarDuracion(2, 60);
        controladorActividad.editarURL(2, "https://video.grafos.com");

        // Launch the GUI
        new VentanaVerActividad(controladorActividad);
    }

}

