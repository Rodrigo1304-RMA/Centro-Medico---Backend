
package CentroMedico.MicroValidar;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio r;

    public Empleado validar(Entrada e) {
        return r.findByCodEmpAndPas(e.getCodEmp(), e.getPas());
    }

    public void grabar(Empleado emp) {
        r.save(emp);
    }

    public List<Empleado> listar() {
        return r.findAll();
    }
}
