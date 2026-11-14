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
    public List<IntervaloTurno> generarIntervalos20Min(
            @RequestParam Long codHor,
            @RequestParam(value = "hIni", defaultValue = "08:00") String hIni,
            @RequestParam(value = "hFin", defaultValue = "09:20") String hFin,
            @RequestParam(value = "dur", defaultValue = "20") int dur) {
        return s.generarIntervalos20Min(codHor, hIni, hFin, dur);
    }

    @PostMapping("/generar/{codHor}")
    public List<IntervaloTurno> generarPorRuta(
            @PathVariable Long codHor,
            @RequestParam(value = "hIni", defaultValue = "08:00") String hIni,
            @RequestParam(value = "hFin", defaultValue = "09:20") String hFin,
            @RequestParam(value = "duracion", defaultValue = "20") int duracion) {
        return s.generarIntervalos20Min(codHor, hIni, hFin, duracion);
    }

    @PostMapping("/registrar-franja")
    public IntervaloTurno registrarFranja(
            @RequestParam Long codHor,
            @RequestParam String hora,
            @RequestParam int duracion) {
        return s.registrarFranja(codHor, hora, duracion);
    }

    @PutMapping("/actualizar-estado/{codInt}")
    public IntervaloTurno actualizarEstadoIntervalo(
            @PathVariable Long codInt,
            @RequestParam String nuevoEst) {
        return s.actualizarEstadoIntervalo(codInt, nuevoEst);
    }

    @GetMapping("/listar/{codHor}")
    public List<IntervaloTurno> listarPorHorario(@PathVariable Long codHor) {
        return s.listarPorHorario(codHor);
    }

    @GetMapping("/listar")
    public List<IntervaloTurno> listar() {
        return s.listar();
    }
}
