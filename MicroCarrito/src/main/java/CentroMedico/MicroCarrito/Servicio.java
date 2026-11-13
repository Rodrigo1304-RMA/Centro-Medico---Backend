
package CentroMedico.MicroCarrito;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio r;

    public Linea agregar(Linea lin) {
        return r.save(lin);
    }

    public void quitar(Long codLin) {
        r.deleteById(codLin);
    }

    public List<Linea> listar() {
        return r.findAll();
    }

    public void nuevo() {
        r.deleteAll();
    }

    public int getTotalMinutos() {
        return r.findAll().stream()
                .mapToInt(Linea::getSubTotalMinutos)
                .sum();
    }
}