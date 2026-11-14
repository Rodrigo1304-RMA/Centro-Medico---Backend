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
    private String horaCita;
    private String esp;
    private String med;
    private String nomPaciente;
    private String est;
    private Long codEmp;       

    public Horario() {
    }

    public Horario(Long codHor, String fecAsig, String horaCita, String esp, String med, String nomPaciente, String est, Long codEmp) {
        this.codHor = codHor;
        this.fecAsig = fecAsig;
        this.horaCita = horaCita;
        this.esp = esp;
        this.med = med;
        this.nomPaciente = nomPaciente;
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

    public String getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
    }

    public String getEsp() {
        return esp;
    }

    public void setEsp(String esp) {
        this.esp = esp;
    }

    public String getMed() {
        return med;
    }

    public void setMed(String med) {
        this.med = med;
    }

    public String getNomPaciente() {
        return nomPaciente;
    }

    public void setNomPaciente(String nomPaciente) {
        this.nomPaciente = nomPaciente;
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