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

    // 1. Obtiene el catálogo de programaciones desde MicroCatalogo (8081)
    public void agregarCatalogo(ProgramacionHorario p) {
        try {
            rt.postForObject("http://localhost:8081/agregar", p, Void.class);
        } catch (Exception e) {
            System.out.println("Error al agregar al catálogo: " + e.getMessage());
        }
    }
    public List<ProgramacionHorario> listarCatalogo() {
        try {
            ProgramacionHorario[] res = rt.getForObject("http://localhost:8081/listar", ProgramacionHorario[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception e) {
            return List.of();
        }
    }

    // 2. Obtiene las franjas agregadas en MicroCarrito (puerto 8084)
    public List<Linea> listarCarrito() {
        Linea[] res = rt.getForObject("http://localhost:8084/cesta/listar", Linea[].class);
        return res != null ? Arrays.asList(res) : List.of();
    }

    // 3. Agrega una franja a la cesta en MicroCarrito (puerto 8084)
    public void agregarFranja(Linea lin) {
        rt.postForObject("http://localhost:8084/cesta/agregar", lin, Void.class);
    }

    // 4. Limpia la cesta en MicroCarrito (puerto 8084)
    public void limpiarCarrito() {
        rt.delete("http://localhost:8084/cesta/nuevo");
    }

    // 5. Confirma y orquesta el horario completo con MicroCompuesto (puerto 8086)
    public void confirmarHorario(Horario h) {
        rt.postForObject("http://localhost:8086/compuesto/confirmar-horario", h, Void.class);
    }

    // 6. Valida la existencia del médico en MicroValidar (puerto 8082)
    public boolean validarMedico(Long codEmp) {
        try {
            // Se envía el código junto con la contraseña con la que fue registrado en MicroValidar
            Entrada entrada = new Entrada();
            entrada.setCodEmp(codEmp);
            entrada.setPas("123456");
            Object emp = rt.postForObject("http://localhost:8082/validar", entrada, Object.class);
            return emp != null;
        } catch (Exception e) {
            System.out.println("Aviso MicroValidar: " + e.getMessage());
            return false;
        }
    }
    
    // 7. Genera los intervalos llamando a MicroIntervalos (puerto 8083)
    
    // Agrega este método para que Control.java pueda llamarlo con un solo argumento
    public void generarIntervalos(Long codHor) {
        generarIntervalos(codHor, 20);
    }
    
    public void generarIntervalos(Long codHor, int duracion) {
        try {
            rt.postForObject("http://localhost:8083/generar/" + codHor + "?duracion=" + duracion, null, Void.class);
        } catch (Exception e) {
            System.out.println("Aviso al generar intervalos: " + e.getMessage());
        }
    }

    // 8. Lista los intervalos generados de un horario desde MicroIntervalos (puerto 8083)
    public List<IntervaloTurno> listarIntervalos(Long codHor) {
        try {
            IntervaloTurno[] res = rt.getForObject("http://localhost:8083/listar/" + codHor, IntervaloTurno[].class);
            return res != null ? Arrays.asList(res) : List.of();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    public void registrarFranjaIntervalo(Long codHor, String hora, int duracion) {
        try {
            String url = "http://localhost:8083/registrar-franja?codHor=" + codHor + "&hora=" + hora + "&duracion=" + duracion;
            rt.postForObject(url, null, Void.class);
        } catch (Exception e) {
            System.out.println("Error al registrar franja en MicroIntervalos: " + e.getMessage());
        }
    }
    
    // 9. Busca un horario por su código en MicroHorario (puerto 8085)
    public Horario buscarHorario(Long codHor) {
        try {
            return rt.getForObject("http://localhost:8085/buscar/" + codHor, Horario.class);
        } catch (Exception e) {
            System.out.println("Error al buscar horario en MicroHorario: " + e.getMessage());
            return null;
        }
    }
}
