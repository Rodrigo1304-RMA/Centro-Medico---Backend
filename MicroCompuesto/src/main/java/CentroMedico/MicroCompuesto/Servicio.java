
package CentroMedico.MicroCompuesto;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Servicio {

    @Autowired
    private RestTemplate rest;

    private final String URL_CARRITO = "http://localhost:8084/cesta";
    private final String URL_HORARIO = "http://localhost:8085";

    public HorarioCompuesto consolidar(Horario cabecera) {
        Linea[] arreglo = rest.getForObject(URL_CARRITO + "/listar", Linea[].class);
        List<Linea> franjas = Arrays.asList(arreglo != null ? arreglo : new Linea[0]);

        Integer totalMinutos = rest.getForObject(URL_CARRITO + "/total", Integer.class);

        Horario horarioGuardado = rest.postForObject(URL_HORARIO + "/grabar", cabecera, Horario.class);

        rest.delete(URL_CARRITO + "/nuevo");

        return new HorarioCompuesto(
                horarioGuardado,
                franjas,
                totalMinutos != null ? totalMinutos : 0
        );
    }
}