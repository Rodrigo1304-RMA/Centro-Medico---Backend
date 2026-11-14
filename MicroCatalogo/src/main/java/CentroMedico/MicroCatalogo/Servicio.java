package CentroMedico.MicroCatalogo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio r;

    public ProgramacionHorario grabarDiaSiguiente(ProgramacionHorario p) {
        LocalDate manana = LocalDate.now().plusDays(1);
        if (p.getFecAtencion() == null || p.getFecAtencion().isBlank()) {
            p.setFecAtencion(manana.toString());
        } else {
            try {
                LocalDate fechaIngresada = LocalDate.parse(p.getFecAtencion());
                if (fechaIngresada.isBefore(manana)) {
                    p.setFecAtencion(manana.toString());
                }
            } catch (Exception e) {
                p.setFecAtencion(manana.toString());
            }
        }

        if (p.getHoraInicio() == null || p.getHoraInicio().isBlank()) {
            p.setHoraInicio("08:00");
        }
        if (p.getHoraFin() == null || p.getHoraFin().isBlank()) {
            p.setHoraFin("09:20");
        }
        if (p.getEstAtencion() == null || p.getEstAtencion().isBlank()) {
            p.setEstAtencion("Programado");
        }
        return r.save(p);
    }

    public void eliminar(Long codProg) {
        r.deleteById(codProg);
    }

    public ProgramacionHorario buscar(Long codProg) {
        return r.findById(codProg).orElse(null);
    }

    public List<ProgramacionHorario> listarPorEspecialidadYFecha(String esp, String fecManana) {
        if (esp == null || esp.isBlank()) {
            return r.findAll();
        }
        if (fecManana != null && !fecManana.isBlank()) {
            return r.findByEspContainingIgnoreCaseAndFecAtencion(esp, fecManana);
        }
        return r.findByEspContainingIgnoreCase(esp);
    }

    public List<ProgramacionHorario> listar() {
        return r.findAll();
    }

    public ProgramacionHorario actualizar(Long codProg, ProgramacionHorario p) {
        return r.findById(codProg).map(x -> {
            x.setEsp(p.getEsp());
            x.setMed(p.getMed());
            x.setFecAtencion(p.getFecAtencion());
            x.setHoraInicio(p.getHoraInicio());
            x.setHoraFin(p.getHoraFin());
            x.setCon(p.getCon());
            x.setEstAtencion(p.getEstAtencion());
            return r.save(x);
        }).orElse(null);
    }
}