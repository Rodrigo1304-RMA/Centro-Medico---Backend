package CentroMedico.MicroIntervalos;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Control {

    @Autowired
    private Servicio s;

    @PostMapping("/generar")
    public List<IntervaloTurno> generarIntervalos(
            @RequestParam Long idHor,
            @RequestParam(value = "hIni", defaultValue = "08:00") String hIni,
            @RequestParam(value = "hFin", defaultValue = "09:20") String hFin,
            @RequestParam(value = "dur", defaultValue = "20") int dur) {
        return s.generarIntervalos(idHor, hIni, hFin, dur);
    }

    @PostMapping("/generar/{idHor}")
    public List<IntervaloTurno> generarPorRuta(
            @PathVariable Long idHor,
            @RequestParam(value = "hIni", defaultValue = "08:00") String hIni,
            @RequestParam(value = "hFin", defaultValue = "09:20") String hFin,
            @RequestParam(value = "duracion", defaultValue = "20") int duracion) {
        return s.generarIntervalos(idHor, hIni, hFin, duracion);
    }

    @PostMapping("/registrar-franja")
    public IntervaloTurno registrarFranja(
            @RequestParam Long idHor,
            @RequestParam String hora,
            @RequestParam int duracion) {
        return s.registrarFranja(idHor, hora, duracion);
    }

    @PutMapping("/actualizar-estado/{idInt}")
    public IntervaloTurno actualizarEstadoIntervalo(
            @PathVariable Long idInt,
            @RequestParam String nuevoEst) {
        return s.actualizarEstadoIntervalo(idInt, nuevoEst);
    }

    @GetMapping("/listar/{idHor}")
    public List<IntervaloTurno> listarPorHorario(@PathVariable Long idHor) {
        return s.listarPorHorario(idHor);
    }

    @GetMapping("/listar")
    public List<IntervaloTurno> listar() {
        return s.listar();
    }
}