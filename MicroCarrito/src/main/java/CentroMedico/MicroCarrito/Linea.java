
package CentroMedico.MicroCarrito;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Linea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codLin;
    private String franja;   
    private int durMin;     
    private int can;       
    private Long codProg;    

    public Linea() {
    }

    public Linea(Long codLin, String franja, int durMin, int can, Long codProg) {
        this.codLin = codLin;
        this.franja = franja;
        this.durMin = durMin;
        this.can = can;
        this.codProg = codProg;
    }

    public int getSubTotalMinutos() {
        return durMin * can;
    }

    public Long getCodLin() {
        return codLin;
    }

    public void setCodLin(Long codLin) {
        this.codLin = codLin;
    }

    public String getFranja() {
        return franja;
    }

    public void setFranja(String franja) {
        this.franja = franja;
    }

    public int getDurMin() {
        return durMin;
    }

    public void setDurMin(int durMin) {
        this.durMin = durMin;
    }

    public int getCan() {
        return can;
    }

    public void setCan(int can) {
        this.can = can;
    }

    public Long getCodProg() {
        return codProg;
    }

    public void setCodProg(Long codProg) {
        this.codProg = codProg;
    }
}