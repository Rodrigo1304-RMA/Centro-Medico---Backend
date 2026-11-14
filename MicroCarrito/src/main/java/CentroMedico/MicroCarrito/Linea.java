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
    private Long codProg;
    private Long codInt;
    private String med;
    private String esp;
    private String franja;
    private int durMin;
    private int can;   

    public Linea() {
    }

    public Linea(Long codLin, Long codProg, Long codInt, String med, String esp, String franja, int durMin, int can) {
        this.codLin = codLin;
        this.codProg = codProg;
        this.codInt = codInt;
        this.med = med;
        this.esp = esp;
        this.franja = franja;
        this.durMin = durMin;
        this.can = can;
    }

    public int getSubTotalMin() {
        return durMin * (can > 0 ? can : 1);
    }

    public int getSubTotalMinutos() {
        return getSubTotalMin();
    }

    public Long getCodLin() {
        return codLin;
    }

    public void setCodLin(Long codLin) {
        this.codLin = codLin;
    }

    public Long getCodProg() {
        return codProg;
    }

    public void setCodProg(Long codProg) {
        this.codProg = codProg;
    }

    public Long getCodInt() {
        return codInt;
    }

    public void setCodInt(Long codInt) {
        this.codInt = codInt;
    }

    public String getMed() {
        return med;
    }

    public void setMed(String med) {
        this.med = med;
    }

    public String getEsp() {
        return esp;
    }

    public void setEsp(String esp) {
        this.esp = esp;
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
}