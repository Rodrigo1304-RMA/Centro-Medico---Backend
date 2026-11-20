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

    @PostMapping("/login")
    public String login(
            @RequestParam("idEmp") Long idEmp,
            @RequestParam("pas") String pas,
            HttpSession session,
            RedirectAttributes flash) {
        Empleado emp = s.validarSesion(idEmp, pas);
        if (emp == null) {
            flash.addFlashAttribute("error", "Credenciales inválidas. Verifique su ID de Usuario y Contraseña.");
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

    @PostMapping("/registrarUsuario")
    public String registrarUsuario(@ModelAttribute Empleado u, RedirectAttributes flash) {
        if (u.getEspMedica() == null || u.getEspMedica().isBlank()) {
            u.setEspMedica("MEDICO".equalsIgnoreCase(u.getCar()) ? "Medicina General" : "General");
        }
        Empleado guardado = s.registrarUsuario(u);
        if (guardado != null && guardado.getIdEmp() != null) {
            flash.addFlashAttribute("exito", "Usuario registrado exitosamente. Tu ID de Acceso es: " + guardado.getIdEmp());
        } else {
            flash.addFlashAttribute("error", "No se pudo registrar el usuario. Verifique que MicroCompuesto y MicroValidar estén activos.");
        }
        return "redirect:/login";
    }

    @GetMapping("/ver")
    public String ver(
            @RequestParam(value = "esp", required = false) String esp,
            @RequestParam(value = "idHor", required = false) Long idHor,
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

        Long idProgSeleccionado = idHor;
        if (idProgSeleccionado == null && !catalogo.isEmpty()) {
            idProgSeleccionado = catalogo.get(0).getIdProg();
        }

        if (idProgSeleccionado != null) {
            m.addAttribute("progSeleccionada", s.buscarProgramacion(idProgSeleccionado));
            m.addAttribute("intervalos", s.listarIntervalos(idProgSeleccionado));
            m.addAttribute("idHorActual", idProgSeleccionado);
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

    @PostMapping({"/programarHorarioDiaSiguiente", "/catalogo/agregar"})
    public String programarHorarioDiaSiguiente(
            @ModelAttribute ProgramacionHorario p,
            @RequestParam(value = "dur", defaultValue = "20") int dur,
            RedirectAttributes flash) {
        if (!s.validarDuracionPermitida(dur)) {
            flash.addFlashAttribute("error", "La duración del intervalo solo puede ser de 10, 15 o máximo 20 minutos.");
            return "redirect:/ver";
        }
        ProgramacionHorario guardado = s.registrarProgramacionDiaSiguiente(p, dur);
        if (guardado != null && guardado.getIdProg() != null) {
            flash.addFlashAttribute("exito", "Programación #" + guardado.getIdProg() + " registrada y turnos de " + dur + " min generados.");
            return "redirect:/ver?idHor=" + guardado.getIdProg();
        }
        flash.addFlashAttribute("error", "Error al registrar la programación médica.");
        return "redirect:/ver";
    }

    @GetMapping("/filtrarPorEspecialidad")
    public String filtrarPorEspecialidad(@RequestParam(value = "esp", required = false, defaultValue = "") String esp) {
        return "redirect:/ver?esp=" + esp;
    }

    @PostMapping({"/seleccionarIntervalo", "/cesta/agregar"})
    public String seleccionarIntervalo(@ModelAttribute Linea lin, RedirectAttributes flash) {
        boolean ok = s.agregarFranja(lin);
        if (!ok) {
            flash.addFlashAttribute("error", "Duración inválida. Solo se permiten intervalos de 10, 15 o máximo 20 minutos.");
            return "redirect:/ver?idHor=" + (lin.getIdProg() != null ? lin.getIdProg() : "");
        }
        flash.addFlashAttribute("exito", "Turno (" + lin.getFranja() + ") agregado a tu cesta de reserva.");
        return "redirect:/ver?idHor=" + (lin.getIdProg() != null ? lin.getIdProg() : "");
    }

    @GetMapping("/cesta/quitar/{idLin}")
    public String quitarDeCesta(@PathVariable Long idLin, RedirectAttributes flash) {
        s.quitarFranja(idLin);
        flash.addFlashAttribute("exito", "Turno retirado de la cesta.");
        return "redirect:/ver";
    }

    @GetMapping("/cesta/limpiar")
    public String limpiar() {
        s.nuevaCesta();
        return "redirect:/ver";
    }

    @PostMapping("/confirmarCitaPaciente")
    public String confirmarCitaPaciente(
            @RequestParam(value = "idEmp", required = false) Long idEmp,
            @RequestParam(value = "idProg", required = false) Long idProg,
            @RequestParam(value = "idInt", required = false) Long idInt,
            @RequestParam(value = "idHor", required = false) Long idHor,
            @RequestParam(value = "pas", required = false) String pas,
            Model m,
            RedirectAttributes flash) {

        List<Linea> cestaActual = s.verCesta();
        if (cestaActual.isEmpty()) {
            flash.addFlashAttribute("error", "Debe seleccionar al menos un intervalo disponible en la cesta antes de confirmar la cita.");
            return "redirect:/ver";
        }

        Long idProgRef = (idProg != null) ? idProg : cestaActual.get(0).getIdProg();
        Long idIntRef = (idInt != null) ? idInt : cestaActual.get(0).getIdInt();

        Entrada entrada = new Entrada(idEmp, idProgRef, idIntRef, idHor, pas);
        Salida salida = s.confirmarCita(entrada);

        if (salida != null && salida.getIdHor() != null) {
            m.addAttribute("salida", salida);
            if (idProgRef != null) {
                m.addAttribute("programacionEncontrada", s.buscarProgramacion(idProgRef));
                m.addAttribute("turnos", s.listarIntervalos(idProgRef));
            }
            return "detalleHorario";
        }

        flash.addFlashAttribute("error", "No se pudo consolidar la cita médica.");
        return "redirect:/ver";
    }

    @GetMapping("/verCitasPendientesMedico")
    public String verCitasPendientesMedico(@RequestParam("nomMed") String nomMed) {
        return "redirect:/ver?nomMed=" + nomMed;
    }

    @PostMapping("/buscarHorario")
    public String buscarHorario(@RequestParam("idHor") Long idHor, Model m) {
        Horario h = s.buscarHorario(idHor);
        ProgramacionHorario p = s.buscarProgramacion(idHor);
        if (h != null) {
            m.addAttribute("horarioEncontrado", h);
        }
        if (p != null) {
            m.addAttribute("programacionEncontrada", p);
            m.addAttribute("turnos", s.listarIntervalos(idHor));
        }
        if (h == null && p == null) {
            m.addAttribute("msg", "No se encontró ninguna cita ni programación registrada con el ID: " + idHor);
        }
        return "detalleHorario";
    }
}