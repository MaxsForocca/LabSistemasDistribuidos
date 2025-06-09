package sistemaempresajdbc;

public class Proyecto {
    private int IDProy;            // id autoincremental en BD
    private String Nombre;
    private String Descripcion;
    private String Fec_Inicio;     // formato "YYYY-MM-DD"
    private String Fec_Termino;    // formato "YYYY-MM-DD" o null
    private int IDDpto;
    private double Presupuesto;
    private String Estado;         // 'PLANIFICADO', 'EN_CURSO', 'TERMINADO', 'CANCELADO'
    private String nombreDepartamento;

    public Proyecto(int IDProy, String Nombre, String Descripcion, String Fec_Inicio, String Fec_Termino, int IDDpto, double Presupuesto, String Estado, String nombreDepartamento) {
        this.IDProy = IDProy;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Fec_Inicio = Fec_Inicio;
        this.Fec_Termino = Fec_Termino;
        this.IDDpto = IDDpto;
        this.Presupuesto = Presupuesto;
        this.Estado = Estado;
        this.nombreDepartamento = nombreDepartamento;
    }

    public Proyecto(String Nombre, String Descripcion, String Fec_Inicio, String Fec_Termino, int IDDpto, double Presupuesto, String Estado, String nombreDepartamento) {
        this(0, Nombre, Descripcion, Fec_Inicio, Fec_Termino, IDDpto, Presupuesto, Estado, nombreDepartamento);
    }

    // Getters y setters
    public int getIDProy() { return IDProy; }
    public String getNombre() { return Nombre; }
    public String getDescripcion() { return Descripcion; }
    public String getFec_Inicio() { return Fec_Inicio; }
    public String getFec_Termino() { return Fec_Termino; }
    public int getIDDpto() { return IDDpto; }
    public double getPresupuesto() { return Presupuesto; }
    public String getEstado() { return Estado; }
    public String getNombreDepartamento() { return nombreDepartamento; }

    public void setIDProy(int IDProy) { this.IDProy = IDProy; }
    public void setNombre(String nombre) { this.Nombre = nombre; }
    public void setDescripcion(String descripcion) { this.Descripcion = descripcion; }
    public void setFec_Inicio(String fec_Inicio) { this.Fec_Inicio = fec_Inicio; }
    public void setFec_Termino(String fec_Termino) { this.Fec_Termino = fec_Termino; }
    public void setIDDpto(int iDDpto) { this.IDDpto = iDDpto; }
    public void setPresupuesto(double presupuesto) { this.Presupuesto = presupuesto; }
    public void setEstado(String estado) { this.Estado = estado; }
    public void setNombreDepartamento(String nombreDepartamento) { this.nombreDepartamento = nombreDepartamento; }
}
