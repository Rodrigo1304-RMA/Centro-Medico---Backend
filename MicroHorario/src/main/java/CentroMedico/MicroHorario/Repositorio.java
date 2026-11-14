package CentroMedico.MicroHorario;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repositorio extends JpaRepository<Horario, Long> {
    List<Horario> findByMedContainingIgnoreCase(String med);
}