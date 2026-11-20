package CentroMedico.MicroCompuesto;

public class Linea {

    private Long idLin;
    private Long idProg;
    private Long idInt;
    private String med;
    private String esp;
    private String franja;
    private int durMin;
    private int can;

    public Linea() {
    }

    public Linea(Long idLin, Long idProg, Long idInt, String med, String esp, String franja, int durMin, int can) {
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
        return durMin * (can > 0 ? can : 1);
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

    public int getDurMin() { 
        return durMin;
    }
    
    public void setDurMin(int durMin) {
        this.durMin = durMin; 
    }

    public int getCan() { 
        return can; 
    }
    
    public void setCan(int can) { 
        this.can = can;
    }
}