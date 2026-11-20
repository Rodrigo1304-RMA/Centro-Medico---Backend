package CentroMedico.MicroCompuesto;

public class Horario {

    private Long idHor;
    private String fecAsig;
    private String horaCita;
    private String esp;
    private String med;
    private String nomPaciente;
    private int duracionMinutos;
    private String est;
    private Long idEmp;

    public Horario() {
    }

    public Horario(Long idHor, String fecAsig, String horaCita, String esp, String med, String nomPaciente, int duracionMinutos, String est, Long idEmp) {
        this.idHor = idHor;
        this.fecAsig = fecAsig;
        this.horaCita = horaCita;
        this.esp = esp;
        this.med = med;
        this.nomPaciente = nomPaciente;
        this.duracionMinutos = duracionMinutos;
        this.est = est;
        this.idEmp = idEmp;
    }

    public Long getIdHor() { 
        return idHor; 
    }
    
    public void setIdHor(Long idHor) { 
        this.idHor = idHor; 
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

    public int getDuracionMinutos() { 
        return duracionMinutos;
    }
    
    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos; 
    }

    public String getEst() { 
        return est; 
    }
    
    public void setEst(String est) { 
        this.est = est;
    }

    public Long getIdEmp() {
        return idEmp; 
    }
    
    public void setIdEmp(Long idEmp) {
        this.idEmp = idEmp; 
    }
}