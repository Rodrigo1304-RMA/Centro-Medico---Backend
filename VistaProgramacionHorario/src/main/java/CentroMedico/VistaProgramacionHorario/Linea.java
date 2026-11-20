package CentroMedico.VistaProgramacionHorario;

public class Linea {

    private Long idLin;
    private Long idProg;
    private Long idInt;
    private String med;
    private String esp;
    private String franja;
    private Integer durMin;
    private Integer can;

    public Linea() {
        this.can = 1;
        this.durMin = 20;
    }

    public Linea(Long idLin, Long idProg, Long idInt, String med, String esp, String franja, Integer durMin, Integer can) {
        this.idLin = idLin;
        this.idProg = idProg;
        this.idInt = idInt;
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

    public Long getIdLin() { 
        return idLin; 
    }
    
    public void setIdLin(Long idLin) { 
        this.idLin = idLin; 
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