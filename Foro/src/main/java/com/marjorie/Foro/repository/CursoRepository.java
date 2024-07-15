package com.marjorie.Foro.repository;

import com.marjorie.Foro.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNombre(String nombre);
}

