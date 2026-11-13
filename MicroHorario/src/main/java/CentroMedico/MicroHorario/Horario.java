
package CentroMedico.MicroHorario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codHor;
    private String fecAsig;
    private String est;
    private Long codEmp;    
    private String med;       
    private String esp;        
    private String con;        

    public Horario() {
    }

    public Horario(Long codHor, String fecAsig, String est, Long codEmp, String med, String esp, String con) {
        this.codHor = codHor;
        this.fecAsig = fecAsig;
        this.est = est;
        this.codEmp = codEmp;
        this.med = med;
        this.esp = esp;
        this.con = con;
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

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }
}