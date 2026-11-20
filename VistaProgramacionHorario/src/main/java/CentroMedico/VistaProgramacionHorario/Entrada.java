package CentroMedico.VistaProgramacionHorario;

public class Entrada {

    private Long idEmp;
    private Long idProg;
    private Long idInt;
    private Long idHor;
    private String pas;

    public Entrada() {
    }

    public Entrada(Long idEmp, Long idProg, Long idInt, Long idHor, String pas) {
        this.idEmp = idEmp;
        this.idProg = idProg;
        this.idInt = idInt;
        this.idHor = idHor;
        this.pas = pas;
    }

    public Long getIdEmp() { 
        return idEmp; 
    }
    
    public void setIdEmp(Long idEmp) { 
        this.idEmp = idEmp;
    }

    public Long getIdProg() { 
        return idProg; 
    }
    
    public void setIdProg(Long idProg) {
        this.idProg = idProg; 
    }

    public Long getIdInt() {
        return idInt; 
    }
    
    public void setIdInt(Long idInt) {
        this.idInt = idInt; 
    }

    public Long getIdHor() { 
        return idHor; 
    }
    
    public void setIdHor(Long idHor) {
        this.idHor = idHor;
    }

    public String getPas() { 
        return pas;
    }
    
    public void setPas(String pas) {
        this.pas = pas; 
    }
}