package CentroMedico.MicroIntervalos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class IntervaloTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInt;
    private Long idHor;
    private String hora;
    private int durMin;
    private String estLin;

    public IntervaloTurno() {
    }

    public IntervaloTurno(Long idInt, Long idHor, String hora, int durMin, String estLin) {
        this.idInt = idInt;
        this.idHor = idHor;
        this.hora = hora;
        this.durMin = durMin;
        this.estLin = estLin;
    }

    public Long getIdInt() {
        return idInt;
    }

    public void setIdInt(Long idInt) {
        this.idInt = idInt;
    }

    public Long getIdHor() {
        return idHor;
    }

    public void setIdHor(Long idHor) {
        this.idHor = idHor;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getDurMin() {
        return durMin;
    }

    public void setDurMin(int durMin) {
        this.durMin = durMin;
    }

    public String getEstLin() {
        return estLin;
    }

    public void setEstLin(String estLin) {
        this.estLin = estLin;
    }
}