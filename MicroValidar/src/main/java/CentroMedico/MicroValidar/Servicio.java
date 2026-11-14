package CentroMedico.MicroValidar;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class Servicio implements CommandLineRunner {

    @Autowired
    private Repositorio r;

    @Override
    public void run(String... args) throws Exception {
        if (r.count() == 0) {
            r.save(new Empleado(null, "Admin Recepción", "EMPLEADO", "Administración", "123456"));
            r.save(new Empleado(null, "Dr. Carlos Soto", "MEDICO", "Cardiología", "123456"));
            r.save(new Empleado(null, "Dra. Elena Gómez", "MEDICO", "Pediatría", "123456"));
            r.save(new Empleado(null, "Juan Pérez (Paciente)", "PACIENTE", "General", "123456"));
        }
    }

    public Empleado validar(Entrada e) {
        if (e == null || e.getCodEmp() == null || e.getPas() == null) {
            return null;
        }
        return r.findByCodEmpAndPas(e.getCodEmp(), e.getPas());
    }

    public Empleado grabar(Empleado emp) {
        if (emp.getCar() != null) {
            emp.setCar(emp.getCar().toUpperCase().trim());
        }
        return r.save(emp);
    }

    public List<Empleado> listarPorRol(String car) {
        return r.findByCarIgnoreCase(car);
    }

    public List<Empleado> listar() {
        return r.findAll();
    }
}