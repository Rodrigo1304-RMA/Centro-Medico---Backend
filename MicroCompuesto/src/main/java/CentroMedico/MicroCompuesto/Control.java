package CentroMedico.MicroCompuesto;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compuesto")
public class Control {

    @Autowired
    private Servicio s;

    // 1. Usuarios y Sesiones (MicroValidar)
    @PostMapping("/usuarios/registrar")
    public Empleado registrarUsuario(@RequestBody Empleado e) {
        return s.registrarUsuario(e);
    }

    @PostMapping("/usuarios/validar")
    public Empleado validarSesion(@RequestBody Entrada e) {
        return s.validarSesion(e);
    }

    @GetMapping("/usuarios/rol/{car}")
    public List<Empleado> listarUsuariosPorRol(@PathVariable String car) {
        return s.listarUsuariosPorRol(car);
    }

    @GetMapping("/usuarios/listar")
    public List<Empleado> listarUsuarios() {
        return s.listarUsuarios();
    }

    // 2. Programación de Catálogo e Intervalos (MicroCatalogo + MicroIntervalos)
    @PostMapping("/programacion/registrar")
    public ProgramacionHorario registrarProgramacionDiaSiguiente(@RequestBody ProgramacionHorario p) {
        return s.registrarProgramacionDiaSiguiente(p);
    }

    @GetMapping("/programacion/filtrar")
    public List<ProgramacionHorario> listarPorEspecialidadDiaSiguiente(
            @RequestParam(value = "esp", required = false, defaultValue = "") String esp) {
        return s.listarPorEspecialidadDiaSiguiente(esp);
    }

    @GetMapping("/programacion/listar")
    public List<ProgramacionHorario> listarCatalogo() {
        return s.listarCatalogo();
    }

    @GetMapping("/programacion/buscar/{codProg}")
    public ProgramacionHorario buscarProgramacion(@PathVariable Long codProg) {
        return s.buscarProgramacion(codProg);
    }

    @GetMapping("/intervalos/{codHor}")
    public List<IntervaloTurno> listarIntervalos(@PathVariable Long codHor) {
        return s.listarIntervalos(codHor);
    }

    // 3. Cesta Temporal (MicroCarrito)
    @DeleteMapping("/cesta/nueva")
    public void nuevaCesta() {
        s.nuevaCesta();
    }

    @PostMapping("/cesta/agregar")
    public Linea agregarFranja(@RequestBody Linea lin) {
        return s.agregarFranja(lin);
    }

    @DeleteMapping("/cesta/quitar/{codLin}")
    public void quitarFranja(@PathVariable Long codLin) {
        s.quitarFranja(codLin);
    }

    @GetMapping("/cesta/ver")
    public List<Linea> verCesta() {
        return s.verCesta();
    }

    @GetMapping("/cesta/total")
    public int obtenerTotalMinutosCesta() {
        return s.obtenerTotalMinutosCesta();
    }

    // 4. Confirmación de Citas y Agenda Médica (MicroHorario)
    @PostMapping("/citas/confirmar")
    public Salida confirmarCita(@RequestBody Entrada e) {
        return s.confirmarCita(e);
    }

    @GetMapping("/citas/pendientes/{med}")
    public List<Horario> listarCitasPendientesPorMedico(@PathVariable String med) {
        return s.listarCitasPendientesPorMedico(med);
    }

    @GetMapping("/citas/listar")
    public List<Horario> listarTodosHorarios() {
        return s.listarTodosHorarios();
    }

    @GetMapping("/citas/buscar/{codHor}")
    public Horario buscarHorario(@PathVariable Long codHor) {
        return s.buscarHorario(codHor);
    }

    @PostMapping("/confirmar-horario")
    public HorarioCompuesto confirmarHorario(@RequestBody Horario h) {
        return s.consolidar(h);
    }
}