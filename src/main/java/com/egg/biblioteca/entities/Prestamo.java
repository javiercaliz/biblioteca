package com.egg.biblioteca.entities;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Prestamo {
     @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator (name="uuid", strategy = "uuid2")
    private String id;
     @Temporal(javax.persistence.TemporalType.DATE)
     private Date inicio;
     @Temporal(javax.persistence.TemporalType.DATE)
     private Date devolucion;
     private Double multa;
     @OneToOne
     private Libro libro;
     @ManyToOne
     private Cliente cliente;

    public Prestamo() {
    }

    public Prestamo(Date inicio, Date devolucion, Double multa, Libro libro, Cliente cliente) {
        this.inicio = inicio;
        this.devolucion = devolucion;
        this.multa = multa;
        this.libro = libro;
        this.cliente = cliente;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the inicio
     */
    public Date getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the devolucion
     */
    public Date getDevolucion() {
        return devolucion;
    }

    /**
     * @param devolucion the devolucion to set
     */
    public void setDevolucion(Date devolucion) {
        this.devolucion = devolucion;
    }

    /**
     * @return the multa
     */
    public Double getMulta() {
        return multa;
    }

    /**
     * @param multa the multa to set
     */
    public void setMulta(Double multa) {
        this.multa = multa;
    }

    /**
     * @return the libros
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * @param libros the libros to set
     */
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
     
     
}
