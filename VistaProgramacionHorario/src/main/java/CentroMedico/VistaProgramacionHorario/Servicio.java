package CentroMedico.VistaProgramacionHorario;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Servicio {

    @Autowired
    private RestTemplate rt;

    private final String URL_COMPUESTO = "http://localhost:8086/compuesto";

    // 1. Gestión de Sesiones y Usuarios (vía MicroCompuesto -> MicroValidar)
    public Empleado validarSesion(Long codUsu, String pas) {
        try {
            Entrada e = new Entrada();
            e.setCodEmp(codUsu);
            e.setPas(pas);
            return rt.postForObject(URL_COMPUESTO + "/usuarios/validar", e, Empleado.class);
        } catch (Exception ex) {
            return null;
        }
    }

    public Empleado registrarUsuario(Empleado u) {
        try {
            return rt.postForObject(URL_COMPUESTO + "/usuarios/registrar", u, Empleado.class);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Empleado> listarMedicos() {
        try {
            Empleado[] res = rt.getForObject(URL_COMPUESTO + "/usuarios/rol/MEDICO", Empleado[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception ex) {
            return List.of();
        }
    }

    public List<Empleado> listarUsuarios() {
        try {
            Empleado[] res = rt.getForObject(URL_COMPUESTO + "/usuarios/listar", Empleado[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception ex) {
            return List.of();
        }
    }

    // 2. Programación de Catálogo e Intervalos (vía MicroCompuesto -> MicroCatalogo + MicroIntervalos)
    public ProgramacionHorario registrarProgramacionDiaSiguiente(ProgramacionHorario p) {
        try {
            return rt.postForObject(URL_COMPUESTO + "/programacion/registrar", p, ProgramacionHorario.class);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<ProgramacionHorario> listarCatalogo() {
        try {
            ProgramacionHorario[] res = rt.getForObject(URL_COMPUESTO + "/programacion/listar", ProgramacionHorario[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception ex) {
            return List.of();
        }
    }

    public List<ProgramacionHorario> listarPorEspecialidadDiaSiguiente(String esp) {
        try {
            String filtro = (esp != null) ? esp.trim() : "";
            ProgramacionHorario[] res = rt.getForObject(URL_COMPUESTO + "/programacion/filtrar?esp=" + filtro, ProgramacionHorario[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception ex) {
            return List.of();
        }
    }

    public ProgramacionHorario buscarProgramacion(Long codProg) {
        try {
            return rt.getForObject(URL_COMPUESTO + "/programacion/buscar/" + codProg, ProgramacionHorario.class);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<IntervaloTurno> listarIntervalos(Long codHor) {
        try {
            IntervaloTurno[] res = rt.getForObject(URL_COMPUESTO + "/intervalos/" + codHor, IntervaloTurno[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception ex) {
            return List.of();
        }
    }

    // 3. Cesta Temporal de Turnos (vía MicroCompuesto -> MicroCarrito)
    public List<Linea> verCesta() {
        try {
            Linea[] res = rt.getForObject(URL_COMPUESTO + "/cesta/ver", Linea[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception ex) {
            return List.of();
        }
    }

    public void agregarFranja(Linea lin) {
        try {
            rt.postForObject(URL_COMPUESTO + "/cesta/agregar", lin, Linea.class);
        } catch (Exception ex) {
            System.out.println("Error al agregar franja: " + ex.getMessage());
        }
    }

    public void quitarFranja(Long codLin) {
        try {
            rt.delete(URL_COMPUESTO + "/cesta/quitar/" + codLin);
        } catch (Exception ex) {
            System.out.println("Error al quitar franja: " + ex.getMessage());
        }
    }

    public void nuevaCesta() {
        try {
            rt.delete(URL_COMPUESTO + "/cesta/nueva");
        } catch (Exception ex) {
            System.out.println("Error al limpiar cesta: " + ex.getMessage());
        }
    }

    public int obtenerTotalMinutosCesta() {
        try {
            Integer res = rt.getForObject(URL_COMPUESTO + "/cesta/total", Integer.class);
            return res != null ? res : 0;
        } catch (Exception ex) {
            return 0;
        }
    }

    // 4. Confirmación de Citas y Consultas Médicas (vía MicroCompuesto -> MicroHorario)
    public Salida confirmarCita(Entrada e) {
        try {
            return rt.postForObject(URL_COMPUESTO + "/citas/confirmar", e, Salida.class);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Horario> listarCitasPendientesPorMedico(String nomMed) {
        try {
            Horario[] res = rt.getForObject(URL_COMPUESTO + "/citas/pendientes/" + nomMed, Horario[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception ex) {
            return List.of();
        }
    }

    public List<Horario> listarTodosHorarios() {
        try {
            Horario[] res = rt.getForObject(URL_COMPUESTO + "/citas/listar", Horario[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception ex) {
            return List.of();
        }
    }

    public Horario buscarHorario(Long codHor) {
        try {
            return rt.getForObject(URL_COMPUESTO + "/citas/buscar/" + codHor, Horario.class);
        } catch (Exception ex) {
            return null;
        }
    }
}