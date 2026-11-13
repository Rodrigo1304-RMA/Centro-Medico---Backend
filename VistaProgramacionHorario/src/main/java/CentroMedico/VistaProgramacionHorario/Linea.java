package CentroMedico.VistaProgramacionHorario;

public class Linea {

    private Long codLin;
    private String franja;
    private Integer durMin;
    private Integer can;
    private Long codProg;

    public Linea() {
        this.can = 1;
    }
    
    public Linea(Long codLin, String franja, Integer durMin, Integer can, Long codProg) {
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

    public Integer getDurMin() {
        return durMin;
    }

    public void setDurMin(Integer durMin) {
        this.durMin = durMin;
    }

    public Integer getCan() {
        return can;
    }

    public void setCan(Integer can) {
        this.can = can;
    }

    public Long getCodProg() {
        return codProg;
    }

    public void setCodProg(Long codProg) {
        this.codProg = codProg;
    }
}