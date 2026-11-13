
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
    private String con;

    public ProgramacionHorario() {
    }

    public ProgramacionHorario(Long codProg, String esp, String med, String con) {
        this.codProg = codProg;
        this.esp = esp;
        this.med = med;
        this.con = con;
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

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }
}