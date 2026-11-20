package CentroMedico.MicroValidar;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repositorio extends JpaRepository<Empleado, Long> {

    Empleado findByIdEmpAndPas(Long idEmp, String pas);
    Empleado findByIdEmp(Long idEmp);
    List<Empleado> findByCarIgnoreCase(String car);
}
