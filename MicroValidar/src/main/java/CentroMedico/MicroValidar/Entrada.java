package CentroMedico.MicroValidar;

public class Entrada {

    private Long idEmp;
    private String pas;

    public Entrada() {
    }

    public Entrada(Long idEmp, String pas) {
        this.idEmp = idEmp;
        this.pas = pas;
    }

    public Long getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(Long idEmp) {
        this.idEmp = idEmp;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }
}
