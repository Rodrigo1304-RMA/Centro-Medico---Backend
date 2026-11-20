package CentroMedico.MicroCarrito;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cesta")
public class Control {

    @Autowired
    private Servicio s;

    @PostMapping("/agregar")
    public Linea agregar(@RequestBody Linea lin) {
        return s.agregar(lin);
    }

    @DeleteMapping("/quitar/{idLin}")
    public void quitar(@PathVariable Long idLin) {
        s.quitar(idLin);
    }

    @DeleteMapping({"/limpiar", "/nuevo"})
    public void limpiar() {
        s.limpiar();
    }

    @GetMapping("/listar")
    public List<Linea> listar() {
        return s.listar();
    }

    @GetMapping("/total")
    public int getTotalMinutos() {
        return s.getTotalMinutos();
    }
}