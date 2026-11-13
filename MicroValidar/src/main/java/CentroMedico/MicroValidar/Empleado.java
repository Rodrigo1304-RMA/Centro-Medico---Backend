
package CentroMedico.MicroValidar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codEmp;
    private String nom;
    private String car;
    private String pas;

    public Empleado() {
    }

    public Empleado(Long codEmp, String nom, String car, String pas) {
        this.codEmp = codEmp;
        this.nom = nom;
        this.car = car;
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

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }
}
