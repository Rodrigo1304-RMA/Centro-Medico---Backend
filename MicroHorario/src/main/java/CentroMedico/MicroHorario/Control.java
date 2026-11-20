package CentroMedico.MicroHorario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Control {

    @Autowired
    private Servicio s;

    @PostMapping("/grabar")
    public Horario grabar(@RequestBody Horario h) {
        return s.grabar(h);
    }

    @PutMapping("/confirmar/{idHor}")
    public Horario confirmarEstadoPendiente(@PathVariable Long idHor, @RequestParam String nomPac) {
        return s.confirmarEstadoPendiente(idHor, nomPac);
    }

    @GetMapping("/pendientes/{med}")
    public List<Horario> listarPendientesPorMedico(@PathVariable String med) {
        return s.listarPendientesPorMedico(med);
    }

    @GetMapping("/buscar/{idHor}")
    public Horario buscar(@PathVariable Long idHor) {
        return s.buscar(idHor);
    }

    @GetMapping("/listar")
    public List<Horario> listar() {
        return s.listar();
    }
}