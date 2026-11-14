package CentroMedico.MicroValidar;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repositorio extends JpaRepository<Empleado, Long> {
    Empleado findByCodEmpAndPas(Long codEmp, String pas);
    List<Empleado> findByCarIgnoreCase(String car);
}
