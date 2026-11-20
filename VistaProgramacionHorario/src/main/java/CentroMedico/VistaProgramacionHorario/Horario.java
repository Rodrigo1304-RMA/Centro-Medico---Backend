package CentroMedico.VistaProgramacionHorario;

public class Horario {

    private Long idHor;
    private Long idEmp;
    private String fecha;
    private String hora;
    private String medico;
    private String especialidad;
    private int duracionMinutos;
    private String estado;
    private String nomPaciente;

    public Horario() {
    }

    public Horario(Long idHor, Long idEmp, String fecha, String hora, String medico, String especialidad, int duracionMinutos, String estado, String nomPaciente) {
        this.idHor = idHor;
        this.idEmp = idEmp;
        this.fecha = fecha;
        this.hora = hora;
        this.medico = medico;
        this.especialidad = especialidad;
        this.duracionMinutos = duracionMinutos;
        this.estado = estado;
        this.nomPaciente = nomPaciente;
    }

    public Long getIdHor() {
        return idHor; 
    }
    
    public void setIdHor(Long idHor) { 
        this.idHor = idHor;
    }

    public Long getIdEmp() { 
        return idEmp;
    }
    
    public void setIdEmp(Long idEmp) {
        this.idEmp = idEmp;
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

    public String getNomPaciente() {
        return nomPaciente; 
    }
    
    public void setNomPaciente(String nomPaciente) { 
        this.nomPaciente = nomPaciente;
    }
}