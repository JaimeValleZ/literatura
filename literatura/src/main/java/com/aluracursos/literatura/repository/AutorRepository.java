package com.aluracursos.literatura.repository;

import com.aluracursos.literatura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    public Autor findByNombreContainingIgnoreCase(String nombre);

    public List<Autor> findByFechaFallecimientoGreaterThanEqual(Integer fecha);

}
