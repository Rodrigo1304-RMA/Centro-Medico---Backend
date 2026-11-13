
package CentroMedico.MicroCatalogo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio r;

    public void grabar(ProgramacionHorario p) {
        r.save(p);
    }

    public void eliminar(Long codProg) {
        r.deleteById(codProg);
    }

    public ProgramacionHorario buscar(Long codProg) {
        return r.findById(codProg).orElse(null);
    }

    public List<ProgramacionHorario> listar() {
        return r.findAll();
    }

    public ProgramacionHorario actualizar(Long codProg, ProgramacionHorario p) {
        return r.findById(codProg).map(x -> {
            x.setEsp(p.getEsp());
            x.setMed(p.getMed());
            x.setCon(p.getCon());
            return r.save(x);
        }).orElse(null);
    }
}