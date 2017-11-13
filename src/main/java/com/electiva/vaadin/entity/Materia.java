package com.electiva.vaadin.entity;

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
@Table(name = "materia")
public class Materia {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materia")
    private List<Nota> notaList;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_materia")
    private Integer idMateria;

    @Column(name = "nombre_materia")
    private String nombreMateria;

    @Column(name = "codigo_materia")
    private String codigoMateria;

    @Column(name = "semestre")
    private Integer semestre;

    @XmlTransient
    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }

    @Override
    public String toString() {
        return  nombreMateria;
    }

}
