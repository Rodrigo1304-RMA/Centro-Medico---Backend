package CentroMedico.MicroValidar;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Control {

    @Autowired
    private Servicio s;

    @PostMapping("/validar")
    public Empleado validar(@RequestBody Entrada e) {
        return s.validar(e);
    }

    @PostMapping("/grabar")
    public Empleado grabar(@RequestBody Empleado emp) {
        return s.grabar(emp);
    }

    @GetMapping("/buscar/{idEmp}")
    public Empleado buscar(@PathVariable Long idEmp) {
        return s.buscar(idEmp);
    }

    @GetMapping("/listarPorRol/{car}")
    public List<Empleado> listarPorRol(@PathVariable String car) {
        return s.listarPorRol(car);
    }

    @GetMapping("/listar")
    public List<Empleado> listar() {
        return s.listar();
    }
}