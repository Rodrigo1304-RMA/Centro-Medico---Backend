
package CentroMedico.MicroCompuesto;

import java.util.List;

public class HorarioCompuesto {

    private Horario horario;
    private List<Linea> franjas;
    private int duracionTotalMinutos;

    public HorarioCompuesto() {
    }

    public HorarioCompuesto(Horario horario, List<Linea> franjas, int duracionTotalMinutos) {
        this.horario = horario;
        this.franjas = franjas;
        this.duracionTotalMinutos = duracionTotalMinutos;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public List<Linea> getFranjas() {
        return franjas;
    }

    public void setFranjas(List<Linea> franjas) {
        this.franjas = franjas;
    }

    public int getDuracionTotalMinutos() {
        return duracionTotalMinutos;
    }

    public void setDuracionTotalMinutos(int duracionTotalMinutos) {
        this.duracionTotalMinutos = duracionTotalMinutos;
    }
}