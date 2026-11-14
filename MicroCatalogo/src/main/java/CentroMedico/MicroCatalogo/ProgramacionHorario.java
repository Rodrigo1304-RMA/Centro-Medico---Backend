package CentroMedico.MicroCatalogo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProgramacionHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codProg;
    private String esp;
    private String med;
    private String fecAtencion;
    private String horaInicio;
    private String horaFin;
    private String con;
    private String estAtencion;

    public ProgramacionHorario() {
    }

    public ProgramacionHorario(Long codProg, String esp, String med, String fecAtencion, String horaInicio, String horaFin, String con, String estAtencion) {
        this.codProg = codProg;
        this.esp = esp;
        this.med = med;
        this.fecAtencion = fecAtencion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.con = con;
        this.estAtencion = estAtencion;
    }

    public Long getCodProg() {
        return codProg;
    }

    public void setCodProg(Long codProg) {
        this.codProg = codProg;
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

    public String getFecAtencion() {
        return fecAtencion;
    }

    public void setFecAtencion(String fecAtencion) {
        this.fecAtencion = fecAtencion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getEstAtencion() {
        return estAtencion;
    }

    public void setEstAtencion(String estAtencion) {
        this.estAtencion = estAtencion;
    }
}