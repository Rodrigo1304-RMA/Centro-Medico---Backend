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
            r.save(new Empleado(null, "Admin", "EMPLEADO", "Administración", "123456"));
            r.save(new Empleado(null, "Carlos Soto", "MEDICO", "Cardiología", "123456"));
            r.save(new Empleado(null, "Elena Gómez", "MEDICO", "Pediatría", "123456"));
            r.save(new Empleado(null, "Juan Pérez", "PACIENTE", "General", "123456"));
        }
    }

    public Empleado validar(Entrada e) {
        if (e == null || e.getIdEmp() == null || e.getPas() == null) {
            return null;
        }
        return r.findByIdEmpAndPas(e.getIdEmp(), e.getPas());
    }

    public Empleado grabar(Empleado emp) {
        if (emp.getCar() != null) {
            emp.setCar(emp.getCar().toUpperCase().trim());
        }
        return r.save(emp);
    }

    public Empleado buscar(Long idEmp) {
        if (idEmp == null) {
            return null;
        }
        return r.findByIdEmp(idEmp);
    }

    public List<Empleado> listarPorRol(String car) {
        return r.findByCarIgnoreCase(car);
    }

    public List<Empleado> listar() {
        return r.findAll();
    }
}