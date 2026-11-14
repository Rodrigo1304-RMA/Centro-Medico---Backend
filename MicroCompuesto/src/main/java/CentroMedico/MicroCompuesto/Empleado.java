package CentroMedico.MicroCompuesto;

public class Empleado {

    private Long codEmp;
    private String nom;
    private String car;
    private String espMedica;
    private String pas;

    public Empleado() {
    }

    public Empleado(Long codEmp, String nom, String car, String espMedica, String pas) {
        this.codEmp = codEmp;
        this.nom = nom;
        this.car = car;
        this.espMedica = espMedica;
        this.pas = pas;
    }

    public Long getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(Long codEmp) {
        this.codEmp = codEmp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getEspMedica() {
        return espMedica;
    }

    public void setEspMedica(String espMedica) {
        this.espMedica = espMedica;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }
}