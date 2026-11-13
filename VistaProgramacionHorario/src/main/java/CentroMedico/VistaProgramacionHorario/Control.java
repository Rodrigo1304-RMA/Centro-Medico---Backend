
package CentroMedico.VistaProgramacionHorario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Control {

    @Autowired
    private Servicio s;

    @GetMapping("/ver")
    public String ver(Model m, @RequestParam(value = "codHor", required = false) Long codHor) {
        m.addAttribute("catalogo", s.listarCatalogo());
        m.addAttribute("carrito", s.listarCarrito());
        m.addAttribute("nuevaLinea", new Linea());
        m.addAttribute("horario", new Horario());

        // Si se acaba de confirmar un horario, consulta sus turnos en MicroIntervalos
        if (codHor != null) {
            m.addAttribute("intervalos", s.listarIntervalos(codHor));
        }

        return "programar";
    }
    
    @PostMapping("/catalogo/agregar")
    public String agregarAlCatalogo(@ModelAttribute ProgramacionHorario programacion) {
        s.agregarCatalogo(programacion);
        return "redirect:/ver";
    }

    @PostMapping("/cesta/agregar")
    public String agregar(Linea lin) {
        s.agregarFranja(lin);
        return "redirect:/ver";
    }

    @GetMapping("/cesta/limpiar")
    public String limpiar() {
        s.limpiarCarrito();
        return "redirect:/ver";
    }

    @PostMapping("/compuesto/confirmar")
    public String confirmarHorario(@ModelAttribute Horario horario, RedirectAttributes flash) {
        // 1. Validar al médico en MicroValidar
        if (!s.validarMedico(horario.getCodEmp())) {
            flash.addFlashAttribute("error", "El código médico ingresado no existe o no está registrado en MicroValidar.");
            return "redirect:/ver";
        }

        // 2. Obtener las franjas de la cesta antes de que MicroCompuesto la limpie
        List<Linea> cesta = s.listarCarrito();
        if (cesta.isEmpty()) {
            flash.addFlashAttribute("error", "Debe seleccionar al menos una franja horaria en la cesta.");
            return "redirect:/ver";
        }

        // 3. Graba la cabecera en MicroHorario mediante MicroCompuesto
        s.confirmarHorario(horario);
        Long idHorario = (horario.getCodHor() != null) ? horario.getCodHor() : 1L;

        // 4. Generar exactamente un intervalo por cada franja seleccionada
        for (Linea lin : cesta) {
            String horaInicio = lin.getFranja().split("-")[0].trim();
            int minutos = lin.getDurMin();

            s.registrarFranjaIntervalo(idHorario, horaInicio, minutos);
        }

        return "redirect:/ver?success&codHor=" + idHorario;
    }
    
        // 5. Muestra la pantalla para ingresar el código de horario a buscar
        @GetMapping("/verBuscarHorario")
        public String verBuscarHorario() {
            return "buscarHorario";
        }

        // 6. Procesa la búsqueda por código y envía los datos a la vista de detalle
        @PostMapping("/buscarHorario")
        public String buscarHorario(@RequestParam("codHor") Long codHor, Model m) {
            try {
                Horario h = s.buscarHorario(codHor);
                if (h != null) {
                    m.addAttribute("horarioEncontrado", h);
                    m.addAttribute("turnos", s.listarIntervalos(codHor));
                } else {
                    m.addAttribute("msg", "No existe horario con el código: " + codHor);
                }
            } catch (Exception e) {
                m.addAttribute("msg", "Error al consultar el horario: " + e.getMessage());
            }
            return "detalleHorario";
        }
}