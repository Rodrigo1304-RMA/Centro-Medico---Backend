package CentroMedico.MicroCompuesto;

import java.util.List;

public class Salida {

    private Horario horario;
    private ProgramacionHorario programacion;
    private List<Linea> franjas;
    private int duracionTotalMin;
    private String estadoCita;

    public Salida() {
    }

    public Salida(Horario horario, ProgramacionHorario programacion, List<Linea> franjas, int duracionTotalMin, String estadoCita) {
        this.horario = horario;
        this.programacion = programacion;
        this.franjas = franjas;
        this.duracionTotalMin = duracionTotalMin;
        this.estadoCita = estadoCita;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public ProgramacionHorario getProgramacion() {
        return programacion;
    }

    public void setProgramacion(ProgramacionHorario programacion) {
        this.programacion = programacion;
    }

    public List<Linea> getFranjas() {
        return franjas;
    }

    public void setFranjas(List<Linea> franjas) {
        this.franjas = franjas;
    }

    public int getDuracionTotalMin() {
        return duracionTotalMin;
    }

    public void setDuracionTotalMin(int duracionTotalMin) {
        this.duracionTotalMin = duracionTotalMin;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }
}