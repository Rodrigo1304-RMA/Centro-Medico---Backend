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

    public boolean validarDuracionPermitida(int durMin) {
        return durMin == 10 || durMin == 15 || durMin == 20;
    }

    public Empleado validarSesion(Long idEmp, String pas) {
        try {
            Entrada e = new Entrada();
            e.setIdEmp(idEmp);
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

    public ProgramacionHorario registrarProgramacionDiaSiguiente(ProgramacionHorario p, int durMin) {
        try {
            int durValida = validarDuracionPermitida(durMin) ? durMin : 20;
            return rt.postForObject(URL_COMPUESTO + "/programacion/registrar?dur=" + durValida, p, ProgramacionHorario.class);
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

    public ProgramacionHorario buscarProgramacion(Long idProg) {
        try {
            return rt.getForObject(URL_COMPUESTO + "/programacion/buscar/" + idProg, ProgramacionHorario.class);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<IntervaloTurno> listarIntervalos(Long idHor) {
        try {
            IntervaloTurno[] res = rt.getForObject(URL_COMPUESTO + "/intervalos/" + idHor, IntervaloTurno[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception ex) {
            return List.of();
        }
    }

    public List<Linea> verCesta() {
        try {
            Linea[] res = rt.getForObject(URL_COMPUESTO + "/cesta/ver", Linea[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception ex) {
            return List.of();
        }
    }

    public boolean agregarFranja(Linea lin) {
        try {
            int d = (lin.getDurMin() != null) ? lin.getDurMin() : 20;
            if (!validarDuracionPermitida(d)) {
                return false;
            }
            lin.setDurMin(d);
            rt.postForObject(URL_COMPUESTO + "/cesta/agregar", lin, Linea.class);
            return true;
        } catch (Exception ex) {
            System.out.println("Error al agregar franja: " + ex.getMessage());
            return false;
        }
    }

    public void quitarFranja(Long idLin) {
        try {
            rt.delete(URL_COMPUESTO + "/cesta/quitar/" + idLin);
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

    public Horario buscarHorario(Long idHor) {
        try {
            return rt.getForObject(URL_COMPUESTO + "/citas/buscar/" + idHor, Horario.class);
        } catch (Exception ex) {
            return null;
        }
    }
}