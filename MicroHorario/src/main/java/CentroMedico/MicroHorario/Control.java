
package CentroMedico.MicroHorario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Control {

    @Autowired
    private Servicio s;

    @PostMapping("/grabar")
    public Horario grabar(@RequestBody Horario h) {
        return s.grabar(h);
    }

    @GetMapping("/listar")
    public List<Horario> listar() {
        return s.listar();
    }

    @GetMapping("/buscar/{codHor}")
    public Horario buscar(@PathVariable Long codHor) {
        return s.buscar(codHor);
    }
}
