package com.aluracursos.literatura.modelo;

import com.aluracursos.literatura.dto.DatosAutor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = String.valueOf(datosAutor.nombre());
        this.fechaNacimiento = Integer.valueOf(datosAutor.fechaDeNacimiento());
        this.fechaFallecimiento = Integer.valueOf(datosAutor.fechaDeFallecimiento());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }


    @Override
    public String toString() {
        List<String> librosTransformados = libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.toList());

        return  '\n' +"--------Autor--------" + '\n' +
                " Nombre: " + nombre + '\n' +
                " Fecha de nacimiento: " + fechaNacimiento + '\n' +
                " Fecha de fallecimiento: " + fechaFallecimiento + '\n' +
                " Libros: " + librosTransformados + '\n';
    }


}
