
package CentroMedico.MicroIntervalos;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Control {

    @Autowired
    private Servicio s;

    @PostMapping("/generar/{codHor}")
    public List<IntervaloTurno> generar(
            @PathVariable Long codHor, 
            @RequestParam(value = "duracion", defaultValue = "20") int duracion) {
        return s.generar(codHor, duracion);
    }
    
    @PostMapping("/registrar-franja")
    public IntervaloTurno registrarFranja(
            @RequestParam Long codHor, 
            @RequestParam String hora, 
            @RequestParam int duracion) {
        return s.registrarIntervaloDirecto(codHor, hora, duracion);
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
