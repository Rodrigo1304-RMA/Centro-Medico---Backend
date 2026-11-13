
package CentroMedico.MicroCompuesto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compuesto")
public class Control {

    @Autowired
    private Servicio s;

    @PostMapping("/confirmar-horario")
    public HorarioCompuesto confirmarHorario(@RequestBody Horario h) {
        return s.consolidar(h);
    }
}