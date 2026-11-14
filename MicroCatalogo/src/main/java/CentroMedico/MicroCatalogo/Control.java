package CentroMedico.MicroCatalogo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping("/listar")
    public List<ProgramacionHorario> listar() {
        return s.listar();
    }

    @PostMapping({"/agregar", "/grabarDiaSiguiente"})
    public ProgramacionHorario grabarDiaSiguiente(@RequestBody ProgramacionHorario p) {
        return s.grabarDiaSiguiente(p);
    }

    @GetMapping("/buscar/{codProg}")
    public ProgramacionHorario buscar(@PathVariable Long codProg) {
        return s.buscar(codProg);
    }
    
    @GetMapping("/filtrar")
    public List<ProgramacionHorario> listarPorEspecialidadYFecha(
            @RequestParam(value = "esp", required = false, defaultValue = "") String esp,
            @RequestParam(value = "fecManana", required = false) String fecManana) {
        return s.listarPorEspecialidadYFecha(esp, fecManana);
    }

    @DeleteMapping("/eliminar/{codProg}")
    public void eliminar(@PathVariable Long codProg) {
        s.eliminar(codProg);
    }

    @PutMapping("/actualizar/{codProg}")
    public ProgramacionHorario actualizar(@PathVariable Long codProg, @RequestBody ProgramacionHorario p) {
        return s.actualizar(codProg, p);
    }
}
