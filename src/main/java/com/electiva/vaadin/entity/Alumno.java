package com.electiva.vaadin.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "alumno")
public class Alumno {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumno")
    private List<Nota> notaList;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_alumno")
    private Integer idAlumno;

    @Column(name = "cedula")
    private Integer cedula;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(name = "email")
    private String email;

    @XmlTransient
    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }

    
    
}
