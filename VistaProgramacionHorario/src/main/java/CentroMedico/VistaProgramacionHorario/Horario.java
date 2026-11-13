
package CentroMedico.VistaProgramacionHorario;

public class Horario {
    private Long codHor;
    private String fecAsig;
    private String est;
    private Long codEmp;

    public Horario() {}

    public Horario(Long codHor, String fecAsig, String est, Long codEmp) {
        this.codHor = codHor;
        this.fecAsig = fecAsig;
        this.est = est;
        this.codEmp = codEmp;
    }

    public Long getCodHor() { 
        return codHor; 
    }
    public void setCodHor(Long codHor) { 
        this.codHor = codHor; 
    }
    public String getFecAsig() { 
        return fecAsig; 
    }
    public void setFecAsig(String fecAsig) { 
        this.fecAsig = fecAsig; 
    }
    public String getEst() { 
        return est; 
    }
    public void setEst(String est) { 
        this.est = est; 
    }
    public Long getCodEmp() { 
        return codEmp; 
    }
    public void setCodEmp(Long codEmp) { 
        this.codEmp = codEmp; 
    }
}