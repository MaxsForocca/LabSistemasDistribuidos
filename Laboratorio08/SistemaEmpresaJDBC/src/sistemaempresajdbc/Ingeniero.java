
package sistemaempresajdbc;

public class Ingeniero {
    private int idIng;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String cargo;
    private int idDpto;
    private double salario;
    private String fechaIngreso;
    private String email;

    public Ingeniero(int idIng, String nombre, String apellido, String especialidad,
                     String cargo, int idDpto, double salario,
                     String fechaIngreso, String email) {
        this.idIng = idIng;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.cargo = cargo;
        this.idDpto = idDpto;
        this.salario = salario;
        this.fechaIngreso = fechaIngreso;
        this.email = email;
    }
    
    public int getIdIng() { return idIng; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEspecialidad() { return especialidad; }
    public String getCargo() { return cargo; }
    public int getIdDpto() { return idDpto; }
    public double getSalario() { return salario; }
    public String getFechaIngreso() { return fechaIngreso; }
    public String getEmail() { return email; }

    public void setIdIng(int idIng) { this.idIng = idIng; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public void setIdDpto(int idDpto) { this.idDpto = idDpto; }
    public void setSalario(double salario) { this.salario = salario; }
    public void setFechaIngreso(String fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public void setEmail(String email) { this.email = email; }
}
