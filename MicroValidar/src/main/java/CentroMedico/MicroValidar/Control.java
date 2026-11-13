
package CentroMedico.MicroValidar;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Control {

    @Autowired
    private Servicio s;

    @PostMapping("/validar")
    public Empleado validar(@RequestBody Entrada e) {
        return s.validar(e);
    }

    @PostMapping("/grabar")
    public void grabar(@RequestBody Empleado emp) {
        s.grabar(emp);
    }

    @GetMapping("/listar")
    public List<Empleado> listar() {
        return s.listar();
    }
}
