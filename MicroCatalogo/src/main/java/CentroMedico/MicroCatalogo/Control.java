
package CentroMedico.MicroCatalogo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Control {

    @Autowired
    private Servicio s;

    @GetMapping("/listar")
    public List<ProgramacionHorario> listar() {
        return s.listar();
    }

    @PostMapping("/agregar")
    public void agregar(@RequestBody ProgramacionHorario p) {
        s.grabar(p);
    }

    @GetMapping("/buscar/{id}")
    public ProgramacionHorario buscar(@PathVariable Long id) {
        return s.buscar(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        s.eliminar(id);
    }

    @PutMapping("/actualizar/{id}")
    public ProgramacionHorario actualizar(@PathVariable Long id, @RequestBody ProgramacionHorario p) {
        return s.actualizar(id, p);
    }
}
