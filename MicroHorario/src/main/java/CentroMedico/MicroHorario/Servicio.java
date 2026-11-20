package CentroMedico.MicroHorario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio r;

    public boolean validarDuracionPermitida(int durMin) {
        return durMin == 10 || durMin == 15 || durMin == 20;
    }

    private int validarDuracion(int durMin) {
        if (!validarDuracionPermitida(durMin)) {
            throw new IllegalArgumentException(
                    "Duración no permitida (" + durMin + " min). En el centro médico solo se aceptan horarios de 10, 15 o máximo 20 minutos."
            );
        }
        return durMin;
    }

    public Horario grabar(Horario h) {
        if (h.getEst() == null || h.getEst().isBlank()) {
            h.setEst("Pendiente");
        }
        if (h.getDuracionMinutos() <= 0) {
            h.setDuracionMinutos(20);
        } else {
            h.setDuracionMinutos(validarDuracion(h.getDuracionMinutos()));
        }
        return r.save(h);
    }

    public Horario confirmarEstadoPendiente(Long idHor, String nomPac) {
        return r.findById(idHor).map(h -> {
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

    public Horario buscar(Long idHor) {
        return r.findById(idHor).orElse(null);
    }

    public List<Horario> listar() {
        return r.findAll();
    }
}