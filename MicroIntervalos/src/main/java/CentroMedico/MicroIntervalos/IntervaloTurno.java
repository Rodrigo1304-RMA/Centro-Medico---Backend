
package CentroMedico.MicroIntervalos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class IntervaloTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codInt;
    private Long codHor;
    private String hora;
    private int durMin;
    private String estLin;

    public IntervaloTurno() {
    }

    public IntervaloTurno(Long codInt, Long codHor, String hora, int durMin, String estLin) {
        this.codInt = codInt;
        this.codHor = codHor;
        this.hora = hora;
        this.durMin = durMin;
        this.estLin = estLin;
    }

    public Long getCodInt() {
        return codInt;
    }

    public void setCodInt(Long codInt) {
        this.codInt = codInt;
    }

    public Long getCodHor() {
        return codHor;
    }

    public void setCodHor(Long codHor) {
        this.codHor = codHor;
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
