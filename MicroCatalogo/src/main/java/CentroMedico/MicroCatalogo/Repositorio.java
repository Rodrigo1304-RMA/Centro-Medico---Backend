package CentroMedico.MicroCatalogo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repositorio extends JpaRepository<ProgramacionHorario, Long> {

    List<ProgramacionHorario> findByEspContainingIgnoreCase(String esp);
    List<ProgramacionHorario> findByEspContainingIgnoreCaseAndFecAtencion(String esp, String fecAtencion);
}