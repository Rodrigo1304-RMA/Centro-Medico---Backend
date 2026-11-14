package CentroMedico.MicroCompuesto;

import java.util.Arrays;
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

    // 1. MicroValidar (8082)
    public Empleado registrarUsuario(Empleado e) {
        return rest.postForObject(URL_VALIDAR + "/grabar", e, Empleado.class);
    }

    public Empleado validarSesion(Entrada e) {
        return rest.postForObject(URL_VALIDAR + "/validar", e, Empleado.class);
    }

    public List<Empleado> listarUsuariosPorRol(String car) {
        Empleado[] arr = rest.getForObject(URL_VALIDAR + "/listarPorRol/" + car, Empleado[].class);
        return Arrays.asList(arr != null ? arr : new Empleado[0]);
    }

    public List<Empleado> listarUsuarios() {
        Empleado[] arr = rest.getForObject(URL_VALIDAR + "/listar", Empleado[].class);
        return Arrays.asList(arr != null ? arr : new Empleado[0]);
    }

    // 2. MicroCatalogo (8081) + Generación automática en MicroIntervalos (8083)
    public ProgramacionHorario registrarProgramacionDiaSiguiente(ProgramacionHorario p) {
        ProgramacionHorario guardado = rest.postForObject(URL_CATALOGO + "/grabarDiaSiguiente", p, ProgramacionHorario.class);
        if (guardado != null && guardado.getCodProg() != null) {
            String hIni = (guardado.getHoraInicio() != null && !guardado.getHoraInicio().isBlank()) ? guardado.getHoraInicio() : "08:00";
            String hFin = (guardado.getHoraFin() != null && !guardado.getHoraFin().isBlank()) ? guardado.getHoraFin() : "09:20";
            String urlGen = URL_INTERVALOS + "/generar?codHor=" + guardado.getCodProg() + "&hIni=" + hIni + "&hFin=" + hFin + "&dur=20";
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

    public ProgramacionHorario buscarProgramacion(Long codProg) {
        return rest.getForObject(URL_CATALOGO + "/buscar/" + codProg, ProgramacionHorario.class);
    }

    // 3. MicroIntervalos (8083)
    public List<IntervaloTurno> listarIntervalos(Long codHor) {
        IntervaloTurno[] arr = rest.getForObject(URL_INTERVALOS + "/listar/" + codHor, IntervaloTurno[].class);
        return Arrays.asList(arr != null ? arr : new IntervaloTurno[0]);
    }

    // 4. MicroCarrito (8084)
    public void nuevaCesta() {
        rest.delete(URL_CARRITO + "/limpiar");
    }

    public Linea agregarFranja(Linea lin) {
        return rest.postForObject(URL_CARRITO + "/agregar", lin, Linea.class);
    }

    public void quitarFranja(Long codLin) {
        rest.delete(URL_CARRITO + "/quitar/" + codLin);
    }

    public List<Linea> verCesta() {
        Linea[] arr = rest.getForObject(URL_CARRITO + "/listar", Linea[].class);
        return Arrays.asList(arr != null ? arr : new Linea[0]);
    }

    public int obtenerTotalMinutosCesta() {
        Integer total = rest.getForObject(URL_CARRITO + "/total", Integer.class);
        return total != null ? total : 0;
    }

    // 5. Orquestación de Confirmación de Cita del Paciente (Carrito + Intervalos + Horario)
    public Salida confirmarCita(Entrada e) {
        List<Linea> franjas = verCesta();
        int totalMin = obtenerTotalMinutosCesta();

        if (franjas.isEmpty()) {
            return new Salida(null, null, franjas, 0, "Sin franjas seleccionadas");
        }

        Horario ultimoHorarioGuardado = null;
        ProgramacionHorario progAsociada = null;

        for (Linea lin : franjas) {
            ProgramacionHorario prog = null;
            if (lin.getCodProg() != null) {
                prog = buscarProgramacion(lin.getCodProg());
                if (prog != null) {
                    progAsociada = prog;
                }
            }

            Horario nuevo = new Horario();
            String fecha = (e.getFecAsig() != null && !e.getFecAsig().isBlank())
                    ? e.getFecAsig()
                    : (prog != null && prog.getFecAtencion() != null ? prog.getFecAtencion() : java.time.LocalDate.now().plusDays(1).toString());

            nuevo.setFecAsig(fecha);
            nuevo.setHoraCita(lin.getFranja());
            nuevo.setEsp(lin.getEsp() != null ? lin.getEsp() : (prog != null ? prog.getEsp() : e.getEsp()));
            nuevo.setMed(lin.getMed() != null ? lin.getMed() : (prog != null ? prog.getMed() : "Médico Turno"));
            nuevo.setNomPaciente(e.getNomUsu() != null ? e.getNomUsu() : "Paciente Registrado");
            nuevo.setEst("Pendiente");
            nuevo.setCodEmp(e.getCodEmp());

            ultimoHorarioGuardado = rest.postForObject(URL_HORARIO + "/grabar", nuevo, Horario.class);

            if (lin.getCodInt() != null) {
                rest.put(URL_INTERVALOS + "/actualizar-estado/" + lin.getCodInt() + "?nuevoEst=Reservado", null);
            }
        }

        nuevaCesta();
        return new Salida(ultimoHorarioGuardado, progAsociada, franjas, totalMin, "Pendiente");
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

    public Horario buscarHorario(Long codHor) {
        return rest.getForObject(URL_HORARIO + "/buscar/" + codHor, Horario.class);
    }

    // Método heredado para mantener compatibilidad
    public HorarioCompuesto consolidar(Horario cabecera) {
        List<Linea> franjas = verCesta();
        int totalMinutos = obtenerTotalMinutosCesta();
        Horario horarioGuardado = rest.postForObject(URL_HORARIO + "/grabar", cabecera, Horario.class);
        nuevaCesta();
        return new HorarioCompuesto(horarioGuardado, franjas, totalMinutos);
    }
}