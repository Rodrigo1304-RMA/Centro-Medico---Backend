package CentroMedico.MicroCarrito;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio r;

    public Linea agregar(Linea lin) {
        if (lin.getCan() <= 0) {
            lin.setCan(1);
        }
        if (lin.getDurMin() <= 0) {
            lin.setDurMin(20);
        }
        return r.save(lin);
    }

    public void quitar(Long codLin) {
        r.deleteById(codLin);
    }
    
    public void limpiar() {
        r.deleteAll();
    }
    
    public void nuevo() {
        limpiar();
    }

    public List<Linea> listar() {
        return r.findAll();
    }

    public int getTotalMinutos() {
        return r.findAll().stream()
                .mapToInt(Linea::getSubTotalMin)
                .sum();
    }
}