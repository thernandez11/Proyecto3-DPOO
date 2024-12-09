package interfaz.estudiantes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import componentes.Actividad;
import componentes.Opcion;
import componentes.PreguntaMultiple;
import componentes.PreguntaVerdaderoFalso;
import controladores.ControladorActividad;

class VentanaActividad extends JFrame {
    private Actividad actividad;
    private ControladorActividad controladorActividad;
    private String loginActual;

    public VentanaActividad(Actividad actividad, ControladorActividad controladorActividad, String loginActual) {
        this.actividad = actividad;
        this.controladorActividad = controladorActividad;
        this.loginActual = loginActual;

        setTitle("Realizando Actividad ID: " + actividad.getId());
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 400);

        // Add components based on activity type
        if (actividad.getTipo().equals("QuizMultiple")) {
            addQuizMultipleQuestions();
        } else if (actividad.getTipo().equals("QuizVerdaderoFalso")) {
            addQuizVerdaderoFalsoQuestions();
        } else {
            addGeneralActivityInfo();
        }

        setVisible(true);
    }

    private void addQuizMultipleQuestions() {
        // Example: Add multiple-choice questions dynamically
        List<PreguntaMultiple> preguntas = actividad.getPreguntasMultiples();
        JPanel panel = new JPanel();
        panel.setLayout((LayoutManager) new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (PreguntaMultiple pregunta : preguntas) {
            JLabel label = new JLabel(pregunta.getTextoPregunta());
            panel.add(label);

            for (Opcion opcion : pregunta.getOpciones()) {
                JRadioButton radioButton = new JRadioButton(opcion.getTextoOpcion());
                radioButton.setActionCommand(opcion.getTextoOpcion());
                panel.add(radioButton);
            }
        }

        add(panel, BorderLayout.CENTER);
    }

    private void addQuizVerdaderoFalsoQuestions() {
        // Example: Add true/false questions dynamically
        List<PreguntaVerdaderoFalso> preguntas = actividad.getPreguntasVerdaderoFalso();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (PreguntaVerdaderoFalso pregunta : preguntas) {
            JLabel label = new JLabel(pregunta.getTextoPregunta());
            panel.add(label);

            for (Opcion opcion : pregunta.getOpciones()) {
                JRadioButton radioButton = new JRadioButton(opcion.getTextoOpcion());
                radioButton.setActionCommand(opcion.getTextoOpcion());
                panel.add(radioButton);
            }
        }

        add(panel, BorderLayout.CENTER);
    }

    private void addGeneralActivityInfo() {
        // Add general activity information if the type is not a quiz or exam
        JLabel lblDescripcion = new JLabel("Descripción: " + actividad.getDescripcion());
        add(lblDescripcion, BorderLayout.CENTER);
    }
}

