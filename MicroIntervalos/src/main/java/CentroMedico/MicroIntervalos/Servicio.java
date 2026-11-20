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

    public boolean validarDuracionPermitida(int durMin) {
        return durMin == 10 || durMin == 15 || durMin == 20;
    }

    private int validarYNormalizarDuracion(int durMin) {
        if (!validarDuracionPermitida(durMin)) {
            throw new IllegalArgumentException(
                    "Duración no permitida (" + durMin + " min). En el centro médico solo se permiten intervalos de 10, 15 o máximo 20 minutos."
            );
        }
        return durMin;
    }

    public List<IntervaloTurno> generarIntervalos(Long idHor, String hIni, String hFin, int dur) {
        int duracionValida = validarYNormalizarDuracion(dur);
        List<IntervaloTurno> lista = new ArrayList<>();

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
            IntervaloTurno it = new IntervaloTurno(null, idHor, franjaTexto, duracionValida, "Disponible");
            lista.add(r.save(it));
            horaActual = siguienteHora;
        }

        return lista;
    }

    public IntervaloTurno registrarFranja(Long idHor, String hora, int dur) {
        int duracionValida = validarYNormalizarDuracion(dur);
        IntervaloTurno it = new IntervaloTurno(null, idHor, hora, duracionValida, "Disponible");
        return r.save(it);
    }

    public IntervaloTurno actualizarEstadoIntervalo(Long idInt, String nuevoEst) {
        return r.findById(idInt).map(it -> {
            it.setEstLin(nuevoEst);
            return r.save(it);
        }).orElse(null);
    }

    public List<IntervaloTurno> listarPorHorario(Long idHor) {
        return r.findByIdHor(idHor);
    }

    public List<IntervaloTurno> listar() {
        return r.findAll();
    }
}