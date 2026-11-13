
package CentroMedico.MicroHorario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio r;

    public Horario grabar(Horario h) {
        return r.save(h);
    }

    public List<Horario> listar() {
        return r.findAll();
    }

    public Horario buscar(Long codHor) {
        return r.findById(codHor).orElse(null);
    }
}
