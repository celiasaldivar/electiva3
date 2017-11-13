/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.electiva.vaadin.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "nota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nota.findAll", query = "SELECT n FROM Nota n")
    , @NamedQuery(name = "Nota.findByMateriaIdMateria", query = "SELECT n FROM Nota n WHERE n.notaPK.materiaIdMateria = :materiaIdMateria")
    , @NamedQuery(name = "Nota.findByAlumnoIdAlumno", query = "SELECT n FROM Nota n WHERE n.notaPK.alumnoIdAlumno = :alumnoIdAlumno")
    , @NamedQuery(name = "Nota.findByNota", query = "SELECT n FROM Nota n WHERE n.nota = :nota")})
public class Nota implements Serializable {

    
    
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotaPK notaPK;
    
    @Column(name = "nota")
    private Integer nota;
   
    @JoinColumn(name = "alumno_id_alumno", referencedColumnName = "id_alumno", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Alumno alumno;
   
    @JoinColumn(name = "materia_id_materia", referencedColumnName = "id_materia", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materia materia;

    public Nota() {
    }

    public Nota(NotaPK notaPK) {
        this.notaPK = notaPK;
    }

    public Nota(int materiaIdMateria, int alumnoIdAlumno) {
        this.notaPK = new NotaPK(materiaIdMateria, alumnoIdAlumno);
    }

    public NotaPK getNotaPK() {
        return notaPK;
    }

    public void setNotaPK(NotaPK notaPK) {
        this.notaPK = notaPK;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notaPK != null ? notaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.notaPK == null && other.notaPK != null) || (this.notaPK != null && !this.notaPK.equals(other.notaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.electiva.vaadin.entity.Nota[ notaPK=" + notaPK + " Calificacion: " + nota + "]";
    }
    
}
