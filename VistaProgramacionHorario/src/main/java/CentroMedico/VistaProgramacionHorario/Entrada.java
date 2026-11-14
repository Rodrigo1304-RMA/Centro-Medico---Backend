package CentroMedico.VistaProgramacionHorario;

public class Entrada {

    private Long codEmp;
    private String nomUsu;
    private String pas;
    private String rol;
    private Long codHor;
    private String fecAsig;
    private String esp;

    public Entrada() {
    }

    public Entrada(Long codEmp, String nomUsu, String pas, String rol, Long codHor, String fecAsig, String esp) {
        this.codEmp = codEmp;
        this.nomUsu = nomUsu;
        this.pas = pas;
        this.rol = rol;
        this.codHor = codHor;
        this.fecAsig = fecAsig;
        this.esp = esp;
    }

    public Long getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(Long codEmp) {
        this.codEmp = codEmp;
    }

    public String getNomUsu() {
        return nomUsu;
    }

    public void setNomUsu(String nomUsu) {
        this.nomUsu = nomUsu;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

    public String getEsp() {
        return esp;
    }

    public void setEsp(String esp) {
        this.esp = esp;
    }
}