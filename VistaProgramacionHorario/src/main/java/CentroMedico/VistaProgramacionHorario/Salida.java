package CentroMedico.VistaProgramacionHorario;

import java.util.List;

public class Salida {

    private Long idHor;
    private String fecha;
    private String hora;
    private String medico;
    private String especialidad;
    private int duracionMinutos;
    private String estado;
    private List<Linea> franjas;

    public Salida() {
    }

    public Salida(Long idHor, String fecha, String hora, String medico, String especialidad, int duracionMinutos, String estado, List<Linea> franjas) {
        this.idHor = idHor;
        this.fecha = fecha;
        this.hora = hora;
        this.medico = medico;
        this.especialidad = especialidad;
        this.duracionMinutos = duracionMinutos;
        this.estado = estado;
        this.franjas = franjas;
    }

    public Long getIdHor() { 
        return idHor;
    }
    
    public void setIdHor(Long idHor) {
        this.idHor = idHor; 
    }

    public String getFecha() { 
        return fecha; 
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }
    
    public void setHora(String hora) {
        this.hora = hora; 
    }

    public String getMedico() {
        return medico; 
    }
    
    public void setMedico(String medico) { 
        this.medico = medico;
    }

    public String getEspecialidad() { 
        return especialidad; 
    }
    
    public void setEspecialidad(String especialidad) { 
        this.especialidad = especialidad;
    }

    public int getDuracionMinutos() { 
        return duracionMinutos;
    }
    
    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getEstado() { 
        return estado;
    }
    
    public void setEstado(String estado) { 
        this.estado = estado;
    }

    public List<Linea> getFranjas() {
        return franjas; 
    }
    
    public void setFranjas(List<Linea> franjas) { 
        this.franjas = franjas;
    }
}