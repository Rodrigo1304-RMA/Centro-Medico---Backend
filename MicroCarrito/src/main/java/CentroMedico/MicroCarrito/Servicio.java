package CentroMedico.MicroCarrito;

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
                    "Duración no permitida (" + durMin + " min). En el centro médico solo se aceptan intervalos de 10, 15 o máximo 20 minutos."
            );
        }
        return durMin;
    }

    public Linea agregar(Linea lin) {
        if (lin.getCan() <= 0) {
            lin.setCan(1);
        }
        lin.setDurMin(validarDuracion(lin.getDurMin()));
        return r.save(lin);
    }

    public void quitar(Long idLin) {
        r.deleteById(idLin);
    }

    public void limpiar() {
        r.deleteAll();
    }

    public void nuevo() {
        limpiar();
    }

    public List<Linea> listar() {
        return r.findAll();
    }

    public int getTotalMinutos() {
        return r.findAll().stream()
                .mapToInt(Linea::getSubTotalMin)
                .sum();
    }
}