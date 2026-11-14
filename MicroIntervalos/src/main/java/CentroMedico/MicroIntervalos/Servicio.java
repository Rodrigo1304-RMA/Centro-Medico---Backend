package CentroMedico.MicroIntervalos;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public List<IntervaloTurno> generarIntervalos20Min(Long codHor, String hIni, String hFin, int dur) {
        List<IntervaloTurno> lista = new ArrayList<>();
        int duracionValida = validarLimite(dur) ? dur : 20;

        LocalTime horaActual = LocalTime.of(8, 0);
        LocalTime horaLimite = LocalTime.of(9, 20);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");

        try {
            if (hIni != null && !hIni.isBlank()) {
                horaActual = LocalTime.parse(hIni.trim(), formato);
            }
            if (hFin != null && !hFin.isBlank()) {
                horaLimite = LocalTime.parse(hFin.trim(), formato);
            }
        } catch (Exception e) {
            horaActual = LocalTime.of(8, 0);
            horaLimite = horaActual.plusMinutes(duracionValida * 4L);
        }

        if (!horaLimite.isAfter(horaActual)) {
            horaLimite = horaActual.plusMinutes(duracionValida * 4L);
        }

        while (!horaActual.plusMinutes(duracionValida).isAfter(horaLimite)) {
            LocalTime siguienteHora = horaActual.plusMinutes(duracionValida);
            String franjaTexto = horaActual.format(formato) + " - " + siguienteHora.format(formato);
            IntervaloTurno it = new IntervaloTurno(null, codHor, franjaTexto, duracionValida, "Disponible");
            lista.add(r.save(it));
            horaActual = siguienteHora;
        }

        return lista;
    }

    public IntervaloTurno registrarFranja(Long codHor, String hora, int dur) {
        if (validarLimite(dur)) {
            IntervaloTurno it = new IntervaloTurno(null, codHor, hora, dur, "Disponible");
            return r.save(it);
        }
        return null;
    }

    public IntervaloTurno actualizarEstadoIntervalo(Long codInt, String nuevoEst) {
        return r.findById(codInt).map(it -> {
            it.setEstLin(nuevoEst);
            return r.save(it);
        }).orElse(null);
    }

    public List<IntervaloTurno> listarPorHorario(Long codHor) {
        return r.findByCodHor(codHor);
    }

    public List<IntervaloTurno> listar() {
        return r.findAll();
    }
}
