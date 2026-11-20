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

    // 1. Usuarios y Sesiones (MicroValidar - 8082)
    @PostMapping("/usuarios/registrar")
    public Empleado registrarUsuario(@RequestBody Empleado e) {
        return s.registrarUsuario(e);
    }

    @PostMapping("/usuarios/validar")
    public Empleado validarSesion(@RequestBody Entrada e) {
        return s.validarSesion(e);
    }

    @GetMapping("/usuarios/buscar/{idEmp}")
    public Empleado buscarEmpleado(@PathVariable Long idEmp) {
        return s.buscarEmpleado(idEmp);
    }

    @GetMapping("/usuarios/rol/{car}")
    public List<Empleado> listarUsuariosPorRol(@PathVariable String car) {
        return s.listarUsuariosPorRol(car);
    }

    @GetMapping("/usuarios/listar")
    public List<Empleado> listarUsuarios() {
        return s.listarUsuarios();
    }

    // 2. Programación de Catálogo e Intervalos (MicroCatalogo - 8081 + MicroIntervalos - 8083)
    @PostMapping("/programacion/registrar")
    public ProgramacionHorario registrarProgramacionDiaSiguiente(
            @RequestBody ProgramacionHorario p,
            @RequestParam(value = "dur", defaultValue = "20") int dur) {
        return s.registrarProgramacionDiaSiguiente(p, dur);
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

    @GetMapping("/programacion/buscar/{idProg}")
    public ProgramacionHorario buscarProgramacion(@PathVariable Long idProg) {
        return s.buscarProgramacion(idProg);
    }

    @GetMapping("/intervalos/{idHor}")
    public List<IntervaloTurno> listarIntervalos(@PathVariable Long idHor) {
        return s.listarIntervalos(idHor);
    }

    // 3. Cesta Temporal (MicroCarrito - 8084)
    @DeleteMapping("/cesta/nueva")
    public void nuevaCesta() {
        s.nuevaCesta();
    }

    @PostMapping("/cesta/agregar")
    public Linea agregarFranja(@RequestBody Linea lin) {
        return s.agregarFranja(lin);
    }

    @DeleteMapping("/cesta/quitar/{idLin}")
    public void quitarFranja(@PathVariable Long idLin) {
        s.quitarFranja(idLin);
    }

    @GetMapping("/cesta/ver")
    public List<Linea> verCesta() {
        return s.verCesta();
    }

    @GetMapping("/cesta/total")
    public int obtenerTotalMinutosCesta() {
        return s.obtenerTotalMinutosCesta();
    }

    // 4. Confirmación y Salida Compuesta (MicroHorario - 8085)
    @PostMapping("/citas/confirmar")
    public Salida confirmarProgramacion(@RequestBody Entrada e) {
        return s.confirmarProgramacion(e);
    }

    @GetMapping("/citas/pendientes/{med}")
    public List<Horario> listarCitasPendientesPorMedico(@PathVariable String med) {
        return s.listarCitasPendientesPorMedico(med);
    }

    @GetMapping("/citas/listar")
    public List<Horario> listarTodosHorarios() {
        return s.listarTodosHorarios();
    }

    @GetMapping("/citas/buscar/{idHor}")
    public Horario buscarHorario(@PathVariable Long idHor) {
        return s.buscarHorario(idHor);
    }

    @PostMapping("/confirmar-horario")
    public HorarioCompuesto confirmarHorario(@RequestBody Horario h) {
        return s.consolidar(h);
    }
    
    @GetMapping("/salida/{idHor}")
    public Salida verSalida(@PathVariable Long idHor) {
        return s.verSalidaPorId(idHor);
    }

    @GetMapping("/salida/todos")
    public List<Salida> listarSalidas() {
        return s.listarSalidas();
    }
}