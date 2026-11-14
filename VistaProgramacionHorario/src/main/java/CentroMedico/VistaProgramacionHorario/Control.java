package CentroMedico.VistaProgramacionHorario;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Control {

    @Autowired
    private Servicio s;

    @GetMapping({"/", "/login"})
    public String mostrarLogin(Model m) {
        m.addAttribute("nuevoUsuario", new Empleado());
        m.addAttribute("usuariosDemo", s.listarUsuarios());
        return "login";
    }

    // 1. login(codUsu: Long, pas: String): String
    @PostMapping("/login")
    public String login(
            @RequestParam("codUsu") Long codUsu,
            @RequestParam("pas") String pas,
            HttpSession session,
            RedirectAttributes flash) {
        Empleado emp = s.validarSesion(codUsu, pas);
        if (emp == null) {
            flash.addFlashAttribute("error", "Credenciales inválidas. Verifique su Código de Usuario y Contraseña.");
            return "redirect:/login";
        }
        session.setAttribute("usuarioActual", emp);
        return "redirect:/ver";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // 2. registrarUsuario(u: Empleado): String
    @PostMapping("/registrarUsuario")
    public String registrarUsuario(@ModelAttribute Empleado u, RedirectAttributes flash) {
        if (u.getEspMedica() == null || u.getEspMedica().isBlank()) {
            u.setEspMedica("MEDICO".equalsIgnoreCase(u.getCar()) ? "Medicina General" : "General");
        }
        Empleado guardado = s.registrarUsuario(u);
        if (guardado != null && guardado.getCodEmp() != null) {
            flash.addFlashAttribute("exito", "Usuario registrado exitosamente. Tu Código de Acceso (ID) es: " + guardado.getCodEmp());
        } else {
            flash.addFlashAttribute("error", "No se pudo registrar el usuario. Verifique que MicroCompuesto y MicroValidar estén activos.");
        }
        return "redirect:/login";
    }

    // Dashboard Principal Unificado
    @GetMapping("/ver")
    public String ver(
            @RequestParam(value = "esp", required = false) String esp,
            @RequestParam(value = "codHor", required = false) Long codHor,
            @RequestParam(value = "nomMed", required = false) String nomMed,
            HttpSession session,
            Model m) {

        Empleado usuario = (Empleado) session.getAttribute("usuarioActual");
        if (usuario == null) {
            return "redirect:/login";
        }

        m.addAttribute("usuario", usuario);
        m.addAttribute("manana", LocalDate.now().plusDays(1).toString());
        m.addAttribute("medicos", s.listarMedicos());

        List<ProgramacionHorario> catalogo = (esp != null && !esp.isBlank())
                ? s.listarPorEspecialidadDiaSiguiente(esp)
                : s.listarCatalogo();
        m.addAttribute("catalogo", catalogo);
        m.addAttribute("espFiltro", esp != null ? esp : "");

        Long codProgSeleccionado = codHor;
        if (codProgSeleccionado == null && !catalogo.isEmpty()) {
            codProgSeleccionado = catalogo.get(0).getCodProg();
        }

        if (codProgSeleccionado != null) {
            m.addAttribute("progSeleccionada", s.buscarProgramacion(codProgSeleccionado));
            m.addAttribute("intervalos", s.listarIntervalos(codProgSeleccionado));
            m.addAttribute("codHorActual", codProgSeleccionado);
        }

        m.addAttribute("carrito", s.verCesta());
        m.addAttribute("totalMinutos", s.obtenerTotalMinutosCesta());
        m.addAttribute("nuevaProgramacion", new ProgramacionHorario());

        String medicoFiltro = nomMed;
        if ((medicoFiltro == null || medicoFiltro.isBlank()) && "MEDICO".equalsIgnoreCase(usuario.getCar())) {
            medicoFiltro = usuario.getNom();
        }

        if (medicoFiltro != null && !medicoFiltro.isBlank()) {
            m.addAttribute("citasMedico", s.listarCitasPendientesPorMedico(medicoFiltro));
            m.addAttribute("medicoConsultado", medicoFiltro);
        } else {
            m.addAttribute("citasMedico", s.listarTodosHorarios());
            m.addAttribute("medicoConsultado", "Todos los Médicos");
        }

        return "programar";
    }

    // 3. programarHorarioDiaSiguiente(p: ProgramacionHorario): String
    @PostMapping({"/programarHorarioDiaSiguiente", "/catalogo/agregar"})
    public String programarHorarioDiaSiguiente(
            @ModelAttribute ProgramacionHorario p,
            RedirectAttributes flash) {
        ProgramacionHorario guardado = s.registrarProgramacionDiaSiguiente(p);
        if (guardado != null && guardado.getCodProg() != null) {
            flash.addFlashAttribute("exito", "Programación #" + guardado.getCodProg() + " registrada para el día siguiente y turnos de <= 20 min generados automáticamente.");
            return "redirect:/ver?codHor=" + guardado.getCodProg();
        }
        flash.addFlashAttribute("error", "Error al registrar la programación médica.");
        return "redirect:/ver";
    }

    // 4. filtrarPorEspecialidad(esp: String): String
    @GetMapping("/filtrarPorEspecialidad")
    public String filtrarPorEspecialidad(@RequestParam(value = "esp", required = false, defaultValue = "") String esp) {
        return "redirect:/ver?esp=" + esp;
    }

    // 5. seleccionarIntervalo(lin: Linea): String
    @PostMapping({"/seleccionarIntervalo", "/cesta/agregar"})
    public String seleccionarIntervalo(@ModelAttribute Linea lin, RedirectAttributes flash) {
        s.agregarFranja(lin);
        flash.addFlashAttribute("exito", "Turno (" + lin.getFranja() + ") agregado a tu cesta de reserva.");
        return "redirect:/ver?codHor=" + (lin.getCodProg() != null ? lin.getCodProg() : "");
    }

    @GetMapping("/cesta/quitar/{codLin}")
    public String quitarDeCesta(@PathVariable Long codLin, RedirectAttributes flash) {
        s.quitarFranja(codLin);
        flash.addFlashAttribute("exito", "Turno retirado de la cesta.");
        return "redirect:/ver";
    }

    @GetMapping("/cesta/limpiar")
    public String limpiar() {
        s.nuevaCesta();
        return "redirect:/ver";
    }

    // 6. confirmarCitaPaciente(codPac: Long, nomPac: String): String
    @PostMapping("/confirmarCitaPaciente")
    public String confirmarCitaPaciente(
            @RequestParam("codPac") Long codPac,
            @RequestParam("nomPac") String nomPac,
            @RequestParam(value = "fecAsig", required = false) String fecAsig,
            @RequestParam(value = "esp", required = false) String esp,
            Model m,
            RedirectAttributes flash) {

        List<Linea> cestaActual = s.verCesta();
        if (cestaActual.isEmpty()) {
            flash.addFlashAttribute("error", "Debe seleccionar al menos un intervalo disponible en la cesta antes de confirmar la cita.");
            return "redirect:/ver";
        }

        Entrada entrada = new Entrada(codPac, nomPac, null, "PACIENTE", null, fecAsig, esp);
        Salida salida = s.confirmarCita(entrada);

        if (salida != null && salida.getHorario() != null) {
            m.addAttribute("salida", salida);
            m.addAttribute("horarioEncontrado", salida.getHorario());
            if (salida.getProgramacion() != null && salida.getProgramacion().getCodProg() != null) {
                m.addAttribute("turnos", s.listarIntervalos(salida.getProgramacion().getCodProg()));
            }
            return "detalleHorario";
        }

        flash.addFlashAttribute("error", "No se pudo consolidar la cita médica.");
        return "redirect:/ver";
    }

    // 7. verCitasPendientesMedico(nomMed: String): String
    @GetMapping("/verCitasPendientesMedico")
    public String verCitasPendientesMedico(@RequestParam("nomMed") String nomMed) {
        return "redirect:/ver?nomMed=" + nomMed;
    }

    // 8. Búsqueda directa de Cita / Horario por Código
    @PostMapping("/buscarHorario")
    public String buscarHorario(@RequestParam("codHor") Long codHor, Model m) {
        Horario h = s.buscarHorario(codHor);
        ProgramacionHorario p = s.buscarProgramacion(codHor);
        if (h != null) {
            m.addAttribute("horarioEncontrado", h);
        }
        if (p != null) {
            m.addAttribute("programacionEncontrada", p);
            m.addAttribute("turnos", s.listarIntervalos(codHor));
        }
        if (h == null && p == null) {
            m.addAttribute("msg", "No se encontró ninguna cita ni programación registrada con el código: " + codHor);
        }
        return "detalleHorario";
    }
}