package CentroMedico.MicroIntervalos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repositorio extends JpaRepository<IntervaloTurno, Long> {
    List<IntervaloTurno> findByIdHor(Long idHor);
}