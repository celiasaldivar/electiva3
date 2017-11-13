/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.electiva.vaadin.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author pc
 */
@Embeddable
public class NotaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "materia_id_materia")
    private int materiaIdMateria;
    @Basic(optional = false)
    @Column(name = "alumno_id_alumno")
    private int alumnoIdAlumno;

    public NotaPK() {
    }

    public NotaPK(int materiaIdMateria, int alumnoIdAlumno) {
        this.materiaIdMateria = materiaIdMateria;
        this.alumnoIdAlumno = alumnoIdAlumno;
    }

    public int getMateriaIdMateria() {
        return materiaIdMateria;
    }

    public void setMateriaIdMateria(int materiaIdMateria) {
        this.materiaIdMateria = materiaIdMateria;
    }

    public int getAlumnoIdAlumno() {
        return alumnoIdAlumno;
    }

    public void setAlumnoIdAlumno(int alumnoIdAlumno) {
        this.alumnoIdAlumno = alumnoIdAlumno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) materiaIdMateria;
        hash += (int) alumnoIdAlumno;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaPK)) {
            return false;
        }
        NotaPK other = (NotaPK) object;
        if (this.materiaIdMateria != other.materiaIdMateria) {
            return false;
        }
        if (this.alumnoIdAlumno != other.alumnoIdAlumno) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.electiva.vaadin.entity.NotaPK[ materiaIdMateria=" + materiaIdMateria + ", alumnoIdAlumno=" + alumnoIdAlumno + " ]";
    }
    
}
