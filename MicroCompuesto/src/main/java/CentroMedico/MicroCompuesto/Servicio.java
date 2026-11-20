package CentroMedico.MicroCompuesto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Servicio {

    @Autowired
    private RestTemplate rest;

    private final String URL_CATALOGO = "http://localhost:8081";
    private final String URL_VALIDAR = "http://localhost:8082";
    private final String URL_INTERVALOS = "http://localhost:8083";
    private final String URL_CARRITO = "http://localhost:8084/cesta";
    private final String URL_HORARIO = "http://localhost:8085";

    public boolean validarDuracionPermitida(int durMin) {
        return durMin == 10 || durMin == 15 || durMin == 20;
    }

    private int validarDuracion(int durMin) {
        if (!validarDuracionPermitida(durMin)) {
            throw new IllegalArgumentException(
                    "Duración no permitida (" + durMin + " min). En el centro médico solo se aceptan intervalos de 10, 15 o máximo 20 minutos."
            );
        }
        return durMin;
    }

    // 1. MicroValidar (8082)
    public Empleado registrarUsuario(Empleado e) {
        return rest.postForObject(URL_VALIDAR + "/grabar", e, Empleado.class);
    }

    public Empleado validarSesion(Entrada e) {
        return rest.postForObject(URL_VALIDAR + "/validar", e, Empleado.class);
    }

    public Empleado buscarEmpleado(Long idEmp) {
        if (idEmp == null) {
            return null;
        }
        return rest.getForObject(URL_VALIDAR + "/buscar/" + idEmp, Empleado.class);
    }

    public List<Empleado> listarUsuariosPorRol(String car) {
        Empleado[] arr = rest.getForObject(URL_VALIDAR + "/listarPorRol/" + car, Empleado[].class);
        return Arrays.asList(arr != null ? arr : new Empleado[0]);
    }

    public List<Empleado> listarUsuarios() {
        Empleado[] arr = rest.getForObject(URL_VALIDAR + "/listar", Empleado[].class);
        return Arrays.asList(arr != null ? arr : new Empleado[0]);
    }

    // 2. MicroCatalogo (8081) + MicroIntervalos (8083)
    public ProgramacionHorario registrarProgramacionDiaSiguiente(ProgramacionHorario p, int duracionMinutos) {
        int durValida = validarDuracion(duracionMinutos);
        ProgramacionHorario guardado = rest.postForObject(URL_CATALOGO + "/grabarDiaSiguiente", p, ProgramacionHorario.class);
        if (guardado != null && guardado.getIdProg() != null) {
            String hIni = (guardado.getHoraInicio() != null && !guardado.getHoraInicio().isBlank()) ? guardado.getHoraInicio() : "08:00";
            String hFin = (guardado.getHoraFin() != null && !guardado.getHoraFin().isBlank()) ? guardado.getHoraFin() : "09:20";
            String urlGen = URL_INTERVALOS + "/generar?idHor=" + guardado.getIdProg()
                    + "&hIni=" + hIni + "&hFin=" + hFin + "&dur=" + durValida;
            rest.postForObject(urlGen, null, IntervaloTurno[].class);
        }
        return guardado;
    }

    public List<ProgramacionHorario> listarPorEspecialidadDiaSiguiente(String esp) {
        String filtro = (esp != null) ? esp : "";
        ProgramacionHorario[] arr = rest.getForObject(URL_CATALOGO + "/filtrar?esp=" + filtro, ProgramacionHorario[].class);
        return Arrays.asList(arr != null ? arr : new ProgramacionHorario[0]);
    }

    public List<ProgramacionHorario> listarCatalogo() {
        ProgramacionHorario[] arr = rest.getForObject(URL_CATALOGO + "/listar", ProgramacionHorario[].class);
        return Arrays.asList(arr != null ? arr : new ProgramacionHorario[0]);
    }

    public ProgramacionHorario buscarProgramacion(Long idProg) {
        if (idProg == null) {
            return null;
        }
        return rest.getForObject(URL_CATALOGO + "/buscar/" + idProg, ProgramacionHorario.class);
    }

    // 3. MicroIntervalos (8083)
    public List<IntervaloTurno> listarIntervalos(Long idHor) {
        IntervaloTurno[] arr = rest.getForObject(URL_INTERVALOS + "/listar/" + idHor, IntervaloTurno[].class);
        return Arrays.asList(arr != null ? arr : new IntervaloTurno[0]);
    }

    // 4. MicroCarrito (8084)
    public void nuevaCesta() {
        rest.delete(URL_CARRITO + "/limpiar");
    }

    public Linea agregarFranja(Linea lin) {
        if (lin.getCan() <= 0) {
            lin.setCan(1);
        }
        lin.setDurMin(validarDuracion(lin.getDurMin()));
        return rest.postForObject(URL_CARRITO + "/agregar", lin, Linea.class);
    }

    public void quitarFranja(Long idLin) {
        rest.delete(URL_CARRITO + "/quitar/" + idLin);
    }

    public List<Linea> verCesta() {
        Linea[] arr = rest.getForObject(URL_CARRITO + "/listar", Linea[].class);
        return Arrays.asList(arr != null ? arr : new Linea[0]);
    }

    public int obtenerTotalMinutosCesta() {
        Integer total = rest.getForObject(URL_CARRITO + "/total", Integer.class);
        return total != null ? total : 0;
    }

    // 5. Orquestación principal -> construye DTO Salida usando los IDs de Entrada + microservicios
    public Salida confirmarProgramacion(Entrada e) {
        List<Linea> franjas = verCesta();
        int totalMin = obtenerTotalMinutosCesta();

        if (franjas.isEmpty()) {
            return new Salida(null, null, null, null, null, 0, "Sin franjas seleccionadas", franjas);
        }

        Empleado emp = (e != null && e.getIdEmp() != null) ? buscarEmpleado(e.getIdEmp()) : null;
        String nombrePaciente = (emp != null && emp.getNom() != null && !emp.getNom().isBlank())
                ? emp.getNom().replace(" (Paciente)", "").trim()
                : "Paciente";

        ProgramacionHorario progEntrada = (e != null && e.getIdProg() != null) ? buscarProgramacion(e.getIdProg()) : null;
        Horario ultimoHorarioGuardado = null;
        ProgramacionHorario progAsociada = progEntrada;
        String ultimoMedico = null;
        String ultimaEspecialidad = null;

        for (Linea lin : franjas) {
            int durLinea = validarDuracion(lin.getDurMin());

            Long idProgResolver = (lin.getIdProg() != null) ? lin.getIdProg() : (e != null ? e.getIdProg() : null);
            ProgramacionHorario prog = (idProgResolver != null) ? buscarProgramacion(idProgResolver) : progEntrada;
            if (prog != null) {
                progAsociada = prog;
            }

            String fecha = (prog != null && prog.getFecAtencion() != null && !prog.getFecAtencion().isBlank())
                    ? prog.getFecAtencion()
                    : LocalDate.now().plusDays(1).toString();

            String medico = (prog != null && prog.getMed() != null && !prog.getMed().isBlank())
                    ? prog.getMed()
                    : (lin.getMed() != null && !lin.getMed().isBlank() ? lin.getMed() : "Médico Asignado");

            String especialidad = (prog != null && prog.getEsp() != null && !prog.getEsp().isBlank())
                    ? prog.getEsp()
                    : (lin.getEsp() != null && !lin.getEsp().isBlank() ? lin.getEsp() : "General");

            ultimoMedico = medico;
            ultimaEspecialidad = especialidad;

            Horario nuevo = new Horario();
            nuevo.setFecAsig(fecha);
            nuevo.setHoraCita(lin.getFranja());
            nuevo.setEsp(especialidad);
            nuevo.setMed(medico);
            nuevo.setNomPaciente(nombrePaciente);
            nuevo.setDuracionMinutos(durLinea);
            nuevo.setEst("Programado");
            nuevo.setIdEmp(e != null ? e.getIdEmp() : null);

            ultimoHorarioGuardado = rest.postForObject(URL_HORARIO + "/grabar", nuevo, Horario.class);

            Long idIntResolver = (lin.getIdInt() != null) ? lin.getIdInt() : (e != null ? e.getIdInt() : null);
            if (idIntResolver != null) {
                rest.put(URL_INTERVALOS + "/actualizar-estado/" + idIntResolver + "?nuevoEst=Reservado", null);
            }
        }

        nuevaCesta();

        Long idHorSalida = (ultimoHorarioGuardado != null && ultimoHorarioGuardado.getIdHor() != null)
                ? ultimoHorarioGuardado.getIdHor()
                : (e != null ? e.getIdHor() : null);
        String fechaSalida = (ultimoHorarioGuardado != null && ultimoHorarioGuardado.getFecAsig() != null)
                ? ultimoHorarioGuardado.getFecAsig()
                : (progAsociada != null ? progAsociada.getFecAtencion() : LocalDate.now().plusDays(1).toString());
        String horaSalida = (ultimoHorarioGuardado != null && ultimoHorarioGuardado.getHoraCita() != null)
                ? ultimoHorarioGuardado.getHoraCita()
                : (!franjas.isEmpty() ? franjas.get(franjas.size() - 1).getFranja() : null);
        String medicoSalida = (ultimoHorarioGuardado != null && ultimoHorarioGuardado.getMed() != null)
                ? ultimoHorarioGuardado.getMed()
                : ultimoMedico;
        String espSalida = (ultimoHorarioGuardado != null && ultimoHorarioGuardado.getEsp() != null)
                ? ultimoHorarioGuardado.getEsp()
                : ultimaEspecialidad;
        int durSalida = (totalMin > 0)
                ? totalMin
                : (ultimoHorarioGuardado != null ? ultimoHorarioGuardado.getDuracionMinutos() : 20);
        String estadoSalida = (ultimoHorarioGuardado != null && ultimoHorarioGuardado.getEst() != null)
                ? ultimoHorarioGuardado.getEst()
                : "Programado";

        return new Salida(idHorSalida, fechaSalida, horaSalida, medicoSalida, espSalida, durSalida, estadoSalida, franjas);
    }

    // 6. Consultas a MicroHorario (8085)
    public List<Horario> listarCitasPendientesPorMedico(String med) {
        Horario[] arr = rest.getForObject(URL_HORARIO + "/pendientes/" + med, Horario[].class);
        return Arrays.asList(arr != null ? arr : new Horario[0]);
    }

    public List<Horario> listarTodosHorarios() {
        Horario[] arr = rest.getForObject(URL_HORARIO + "/listar", Horario[].class);
        return Arrays.asList(arr != null ? arr : new Horario[0]);
    }

    public Horario buscarHorario(Long idHor) {
        if (idHor == null) {
            return null;
        }
        return rest.getForObject(URL_HORARIO + "/buscar/" + idHor, Horario.class);
    }

    public HorarioCompuesto consolidar(Horario cabecera) {
        List<Linea> franjas = verCesta();
        int totalMinutos = obtenerTotalMinutosCesta();
        if (totalMinutos > 0 && !validarDuracionPermitida(cabecera.getDuracionMinutos())) {
            cabecera.setDuracionMinutos(totalMinutos <= 20 ? totalMinutos : 20);
        }
        Horario horarioGuardado = rest.postForObject(URL_HORARIO + "/grabar", cabecera, Horario.class);
        nuevaCesta();
        return new HorarioCompuesto(horarioGuardado, franjas, totalMinutos);
    }

    // 7. Construcción de DTO Salida con franjas pobladas (Linea: idLin, idProg, idInt, franja, durMin, can)
    private List<Linea> construirFranjasDesdeHorario(Horario h) {
        if (h == null || h.getHoraCita() == null || h.getHoraCita().isBlank()) {
            return Collections.emptyList();
        }

        Long idProgEncontrado = null;
        Long idIntEncontrado = null;

        try {
            List<ProgramacionHorario> catalogos = listarCatalogo();
            for (ProgramacionHorario p : catalogos) {
                boolean mismoMed = h.getMed() != null && h.getMed().equalsIgnoreCase(p.getMed());
                boolean mismaEsp = h.getEsp() != null && h.getEsp().equalsIgnoreCase(p.getEsp());
                boolean mismaFec = h.getFecAsig() != null && h.getFecAsig().equals(p.getFecAtencion());
                if (mismoMed && mismaEsp && mismaFec) {
                    idProgEncontrado = p.getIdProg();
                    List<IntervaloTurno> turnos = listarIntervalos(p.getIdProg());
                    for (IntervaloTurno it : turnos) {
                        if (h.getHoraCita().equals(it.getHora())) {
                            idIntEncontrado = it.getIdInt();
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        Linea linea = new Linea();
        linea.setIdLin(h.getIdHor());
        linea.setIdProg(idProgEncontrado != null ? idProgEncontrado : h.getIdHor());
        linea.setIdInt(idIntEncontrado != null ? idIntEncontrado : h.getIdHor());
        linea.setFranja(h.getHoraCita());
        linea.setDurMin(h.getDuracionMinutos());
        linea.setCan(1);

        return Collections.singletonList(linea);
    }

    public Salida verSalidaPorId(Long idHor) {
        Horario h = buscarHorario(idHor);
        if (h == null) {
            return null;
        }
        return new Salida(
                h.getIdHor(),
                h.getFecAsig(),
                h.getHoraCita(),
                h.getMed(),
                h.getEsp(),
                h.getDuracionMinutos(),
                h.getEst(),
                construirFranjasDesdeHorario(h)
        );
    }

    public List<Salida> listarSalidas() {
        List<Horario> horarios = listarTodosHorarios();
        List<Salida> salidas = new ArrayList<>();
        for (Horario h : horarios) {
            salidas.add(new Salida(
                    h.getIdHor(),
                    h.getFecAsig(),
                    h.getHoraCita(),
                    h.getMed(),
                    h.getEsp(),
                    h.getDuracionMinutos(),
                    h.getEst(),
                    construirFranjasDesdeHorario(h)
            ));
        }
        return salidas;
    }
}