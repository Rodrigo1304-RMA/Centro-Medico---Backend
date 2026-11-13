
package CentroMedico.MicroIntervalos;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio r;

    public boolean validarLimite(int durMin) {
        return durMin > 0 && durMin <= 20;
    }

    public List<IntervaloTurno> generar(Long codHor, int duracion) {
        List<IntervaloTurno> lista = new ArrayList<>();

        if (validarLimite(duracion)) {
            // Empieza a las 08:00
            java.time.LocalTime horaActual = java.time.LocalTime.of(8, 0);
            java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("HH:mm");

            // Genera por ejemplo 4 turnos sucesivos con la duración recibida
            for (int i = 0; i < 4; i++) {
                String horaStr = horaActual.format(formato);
                IntervaloTurno it = new IntervaloTurno(null, codHor, horaStr, duracion, "Disponible");
                lista.add(r.save(it));
                
                // Suma los minutos ingresados (10, 15, etc.) para el siguiente turno
                horaActual = horaActual.plusMinutes(duracion);
            }
        }
        return lista;
    }
    
    public IntervaloTurno registrarIntervaloDirecto(Long codHor, String horaInicio, int duracion) {
        if (validarLimite(duracion)) {
            IntervaloTurno it = new IntervaloTurno(null, codHor, horaInicio, duracion, "Disponible");
            return r.save(it);
        }
        return null;
    }

    public List<IntervaloTurno> listarPorHorario(Long codHor) {
        return r.findByCodHor(codHor);
    }

    public List<IntervaloTurno> listar() {
        return r.findAll();
    }
}
