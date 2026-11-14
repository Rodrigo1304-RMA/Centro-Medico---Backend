package CentroMedico.VistaProgramacionHorario;

public class Linea {

    private Long codLin;
    private Long codProg;
    private Long codInt;
    private String med;
    private String esp;
    private String franja;
    private Integer durMin;
    private Integer can;

    public Linea() {
        this.can = 1;
        this.durMin = 20;
    }

    public Linea(Long codLin, Long codProg, Long codInt, String med, String esp, String franja, Integer durMin, Integer can) {
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
        int d = (durMin != null) ? durMin : 20;
        int c = (can != null && can > 0) ? can : 1;
        return d * c;
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
}