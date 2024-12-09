package interfaz.estudiantes;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import controladores.ControladorResena;
import componentes.Resena;

public class VentanaVerResena extends JFrame implements ListSelectionListener {

    private ControladorResena controladorResena;
    private JList<Resena> listaResenas; // List to display reviews
    private DefaultListModel<Resena> listModel;

    private JLabel lblIdActividad, lblOpinion, lblRating, lblAutor, lblRol;

    public VentanaVerResena(ControladorResena controladorResena, int idActividad) {
        this.controladorResena = controladorResena;

        // Configuración de la ventana
        setTitle("Reseñas de Actividad " + idActividad);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);

        // Panel de lista
        listModel = new DefaultListModel<>();
        listaResenas = new JList<>(listModel);
        listaResenas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaResenas.addListSelectionListener(this);

        // Custom renderer to display IDs in the list
        listaResenas.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Resena resena = (Resena) value;
                return super.getListCellRendererComponent(list, "ID Actividad: " + resena.getIdActividad(), index, isSelected, cellHasFocus);
            }
        });

        JScrollPane scrollPane = new JScrollPane(listaResenas);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de detalles
        JPanel panelDetalles = new JPanel();
        panelDetalles.setLayout(new GridLayout(5, 1));
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles"));

        lblIdActividad = new JLabel("ID Actividad: ");
        lblOpinion = new JLabel("Opinión: ");
        lblRating = new JLabel("Calificación: ");
        lblAutor = new JLabel("Autor: ");
        lblRol = new JLabel("Rol del Autor: ");
        panelDetalles.add(lblIdActividad);
        panelDetalles.add(lblOpinion);
        panelDetalles.add(lblRating);
        panelDetalles.add(lblAutor);
        panelDetalles.add(lblRol);

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
        cargarResenas(idActividad);

        setVisible(true);
    }

    /**
     * Obtiene y muestra la lista de reseñas para una actividad específica.
     */
    private void cargarResenas(int idActividad) {
        ArrayList<Resena> resenas = controladorResena.resenasActividad(idActividad);
        listModel.clear();
        for (Resena resena : resenas) {
            listModel.addElement(resena); // Add Resena objects to the list
        }
    }

    /**
     * Se activa cuando se selecciona una reseña de la lista.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Resena selectedResena = listaResenas.getSelectedValue();
            if (selectedResena != null) {
                actualizarDetalles(selectedResena);
            }
        }
    }

    /**
     * Actualiza la sección de detalles con la información de la reseña seleccionada.
     */
    private void actualizarDetalles(Resena resena) {
        lblIdActividad.setText("ID Actividad: " + resena.getIdActividad());
        lblOpinion.setText("Opinión: " + resena.getOpinion());
        lblRating.setText("Calificación: " + resena.getRating());
        lblAutor.setText("Autor: " + resena.getLoginAutor());
        lblRol.setText("Rol del Autor: " + resena.getRolAutor());
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        ControladorResena controladorResena = new ControladorResena();

        // Add mock reviews for testing
        controladorResena.crearResena(1, "Muy buena actividad", 5, "Juan", "Estudiante");
        controladorResena.crearResena(1, "Interesante, pero podría mejorar", 3, "María", "Profesor");

        new VentanaVerResena(controladorResena, 1);
    }
}
