
package CentroMedico.MicroCompuesto;

public class Linea {

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