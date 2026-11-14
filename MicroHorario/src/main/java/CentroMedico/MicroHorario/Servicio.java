package CentroMedico.MicroHorario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio r;

    public Horario grabar(Horario h) {
        if (h.getEst() == null || h.getEst().isBlank()) {
            h.setEst("Pendiente");
        }
        return r.save(h);
    }

    public Horario confirmarEstadoPendiente(Long codHor, String nomPac) {
        return r.findById(codHor).map(h -> {
            h.setNomPaciente(nomPac);
            h.setEst("Pendiente");
            return r.save(h);
        }).orElse(null);
    }

    public List<Horario> listarPendientesPorMedico(String med) {
        if (med == null || med.isBlank()) {
            return r.findAll();
        }
        return r.findByMedContainingIgnoreCase(med.trim());
    }

    public Horario buscar(Long codHor) {
        return r.findById(codHor).orElse(null);
    }

    public List<Horario> listar() {
        return r.findAll();
    }
}
