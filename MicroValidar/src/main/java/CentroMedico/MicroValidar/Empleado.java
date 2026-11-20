package CentroMedico.MicroValidar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmp;
    private String nom;
    private String car;
    private String espMedica;
    private String pas;

    public Empleado() {
    }

    public Empleado(Long idEmp, String nom, String car, String espMedica, String pas) {
        this.idEmp = idEmp;
        this.nom = nom;
        this.car = car;
        this.espMedica = espMedica;
        this.pas = pas;
    }

    public Long getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(Long idEmp) {
        this.idEmp = idEmp;
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