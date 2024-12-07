package controladores;

import componentes.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import persistencia.PersistenciaRegistros;

public class ControladorRegistros {

	private HashMap<Integer, ArrayList<RegistroLearningPath>> registrosLp;
	
	public ControladorRegistros() {
		this.setRegistrosLp(new HashMap<>());
	}
	
	//Crear registros para estudiantes inscritos
	public void crearRegistroLpEstudiante(String loginActual, LearningPath lp) {
		LocalDateTime fecha = LocalDateTime.now();
		RegistroLearningPath rlp = new RegistroLearningPath(loginActual, fecha);
		int idLp = lp.getId();
		if (getRegistrosLp().containsKey(idLp)) {
			ArrayList<RegistroLearningPath> rlps = getRegistrosLp().get(idLp);
			rlps.add(rlp);
		} else {
			ArrayList<RegistroLearningPath> rlps = new ArrayList<>();
			rlps.add(rlp);
			getRegistrosLp().put(idLp, rlps);
		}
	}
	public void crearRegistrosActividad(String loginActual, LearningPath lp) {
		ArrayList<RegistroActividad> registros = new ArrayList<>();
		HashMap<Actividad, Boolean> actividades = lp.getActividades();
		Set<Actividad> as = actividades.keySet();
		for (Actividad a : as) {
			RegistroActividad ra = new RegistroActividad(a.getId(), actividades.get(a));
			switch (a.getTipo()) {
				case "Tarea":
					//Tarea no tiene nada especial
					break;
				case "RecursoEducativo":
					//Recurso Educativo no tiene nada especial
					break;
				case "Encuesta":
					editarRespuestasAbiertas(ra, a);
					break;
				case "Examen":
					editarRespuestasAbiertas(ra, a);
					break;
				case "QuizMultiple":
					editarRespuestasMultiples(ra, a);
					break;
				case "QuizVerdaderoFalso":
					editarRespuestasVerdaderoFalso(ra, a);
					break;
			}
			registros.add(ra);
		}
		RegistroLearningPath rlp = getRegistroLp(loginActual, lp.getId());
		rlp.setRegistrosA(registros);
	}
	
	//Consultar registros especificos
	public RegistroLearningPath getRegistroLp(String loginActual, int idLp) {
		ArrayList<RegistroLearningPath> rlps = getRegistrosLp().get(idLp);
		for(RegistroLearningPath rlp : rlps) {
			if (rlp.getLoginEstudiante().equals(loginActual)) {
				return rlp;
			}
		}
		return null;
	}
	public RegistroActividad getRegistroActividad(String loginActual, int idLp, int idA) {
		RegistroLearningPath rlp = getRegistroLp(loginActual, idLp);
		List<RegistroActividad> ras = rlp.getRegistrosA();
		for (RegistroActividad ra : ras) {
			if (ra.getIdActividad() == idA) {
				return ra;
			}
		}
		return null;
	}
	public List<Integer> getActividadesPendientes(String loginActual, int idLp) {
		List<Integer> ids = new ArrayList<>();
		RegistroLearningPath rlp = getRegistroLp(loginActual, idLp);
		List<RegistroActividad> ras = rlp.getRegistrosA();
		for (RegistroActividad ra : ras) {
			if (!(ra.getEstado().equals("Completado") || ra.getEstado().equals("Enviado"))) {
				ids.add(ra.getIdActividad());
			}
		}
		return ids;
	}
	public List<RegistroActividad> getActividadesEnviadasLp(int idLp) {
		List<RegistroActividad> ras = new ArrayList<>();
		ArrayList<RegistroLearningPath> rlps = getRegistrosLp().get(idLp);
		for (RegistroLearningPath rlp : rlps) {
			List<RegistroActividad> registros = rlp.getRegistrosA();
			for (RegistroActividad ra : registros) {
				if (ra.getEstado().equals("Enviado")) {
					ras.add(ra);
				}
			}
		}
		return ras;
	}
	
	//Ediciones registroActividad
	public void editarFechaInicio(RegistroActividad ra) {
		LocalDateTime fecha = LocalDateTime.now();
		ra.setFechaInicio(fecha);
	}
	public void editarFechaTerminado(RegistroActividad ra) {
		LocalDateTime fecha = LocalDateTime.now();
		ra.setFechaTerminado(fecha);
	}
	public void editarEstado(RegistroActividad ra, String estado) {
		ra.setEstado(estado);
	}
	public void editarRespuestasAbiertas(RegistroActividad ra, Actividad a) {
		HashMap<String, String> respuestas = new HashMap<>();
		List<PreguntaAbierta> preguntas = a.getPreguntasAbiertas();
		for (PreguntaAbierta pregunta : preguntas) {
			respuestas.put(pregunta.getTextoPregunta(), null);
		}
		ra.setRespuestas(respuestas);
	}
	public void editarRespuestasMultiples(RegistroActividad ra, Actividad a) {
		HashMap<String, String> respuestas = new HashMap<>();
		List<PreguntaMultiple> preguntas = a.getPreguntasMultiples();
		for (PreguntaMultiple pregunta : preguntas) {
			respuestas.put(pregunta.getTextoPregunta(), null);
		}
		ra.setRespuestas(respuestas);
	}
	public void editarRespuestasVerdaderoFalso(RegistroActividad ra, Actividad a) {
		HashMap<String, String> respuestas = new HashMap<>();
		List<PreguntaVerdaderoFalso> preguntas = a.getPreguntasVerdaderoFalso();
		for (PreguntaVerdaderoFalso pregunta : preguntas) {
			respuestas.put(pregunta.getTextoPregunta(), null);
		}
		ra.setRespuestas(respuestas);
	}
	public void editarNota(RegistroActividad ra, int nota) {
		ra.setNota(nota);
	}
	
	//Estado y estadisticas registro learning path
	public float tiempoDedicadoPorActividad(int idLP) {
		float terminados = 0;
		float tiempo = 0;
		
		ArrayList<RegistroLearningPath> registros = getRegistrosLp().get(idLP);
		for (RegistroLearningPath rlp : registros) {
			List<RegistroActividad> registrosA = rlp.getRegistrosA();
			for (RegistroActividad ra : registrosA) {
				if (ra.getEstado().equals("Completada")) {
					terminados++;
					tiempo += ra.getFechaInicio().until(ra.getFechaTerminado(), ChronoUnit.MINUTES);
				}
			}
		}
		if (terminados != 0) {
			return tiempo / terminados;
		}
		return 0;
	}
	
	public float porcentajeCompletado(int idLP) {
	    ArrayList<RegistroLearningPath> registros = getRegistrosLp().get(idLP);
	    if (registros == null || registros.isEmpty()) {
	        return 0; // Sin registros
	    }
	    float completados = 0;
	    for (RegistroLearningPath rlp : registros) {
	        List<RegistroActividad> actividades = rlp.getRegistrosA(); // Obtiene actividades
	        for (RegistroActividad ra : actividades) {
	            if ("Completada".equals(ra.getEstado())) { // Revisa el estado de una actividad
	                completados++;
	            }
	        }
	    }
	    float totalActividades = registros.get(0).getRegistrosA().size(); // Numero de actividades en Learning Path
	    return (completados / totalActividades) * 100;
	}

	
	public boolean revisarEstadoRLP(RegistroLearningPath rlp) {
		if (rlp.getEstado().equals("Completada")) {
			return true;
		} else {
			return false;
		}
	}



	public HashMap<Integer, ArrayList<RegistroLearningPath>> getRegistrosLp() {
		return registrosLp;
	}

	public void setRegistrosLp(HashMap<Integer, ArrayList<RegistroLearningPath>> registrosLp) {
		this.registrosLp = registrosLp;
	}

	public void guardarRegistrosEnArchivo(String nombreArchivo) throws IOException {
		String directorioRelativo = "datos";
		File directorio = new File(directorioRelativo);

		if (!directorio.exists()) {
			directorio.mkdir();
		}

		File archivo = new File(directorio, nombreArchivo);

		PersistenciaRegistros.guardarRegistros(archivo.getAbsolutePath(), this);
	}

	public void cargarRegistrosDesdeArchivo(String nombreArchivo) throws IOException {
		String directorioRelativo = "datos";
		File directorio = new File(directorioRelativo);

		if (!directorio.exists()) {
			directorio.mkdir();
		}

		File archivo = new File(directorio, nombreArchivo);

		if (!archivo.exists()) {
			archivo.createNewFile();
			System.out.println("No existe el archivo " + nombreArchivo + ". Se ha creado uno nuevo");
		} else {
			PersistenciaRegistros.cargarRegistros(archivo.getAbsolutePath(), this);
		}
	}
}
