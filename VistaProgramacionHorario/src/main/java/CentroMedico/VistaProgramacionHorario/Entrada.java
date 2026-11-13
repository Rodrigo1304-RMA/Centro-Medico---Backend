package CentroMedico.VistaProgramacionHorario;

public class Entrada {

    private Long codEmp;
    private String pas;

    public Entrada() {}

    public Entrada(Long codEmp, String pas) {
        this.codEmp = codEmp;
        this.pas = pas;
    }

    public Long getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(Long codEmp) {
        this.codEmp = codEmp;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }
}